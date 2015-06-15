package com.playmatecat.utils.mina;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.playmatecat.mina.client.ClientHandler;
import com.playmatecat.mina.client.NioTCPClient;
import com.playmatecat.mina.stucture.RequestServiceAdapter;
import com.playmatecat.mina.stucture.ResponseServiceAdapter;
import com.playmatecat.utils.json.UtilsJson;
import com.playmatecat.utils.label.UtilsGUID;

public class UtilsNioClient<T> {
    
    private static Logger logger = LogManager.getLogger(UtilsNioClient.class);
    
    /**结果集存放的map,通过GUID作为KEY,所有的client都共享这个map(key为GUID不会重复,请放心)**/
    public final static ConcurrentHashMap<String, ResponseServiceAdapter> RESULT_MAP 
        = new ConcurrentHashMap<String, ResponseServiceAdapter>();

    private static ReentrantLock lock = new ReentrantLock();
    
    /**mina nio service服务map**/
    private static HashMap<String, List<NioTCPClient>> nioServiceMap = new HashMap<String, List<NioTCPClient>>();
    
    /**
     * 初始化nio mina nio service服务map
     * @param nioServiceMap
     */
    public static void initNioServiceMap(HashMap<String, List<NioTCPClient>> map) {
        nioServiceMap = map;
    }
    
    /**
     * 返回server map,自己请不要随意调用这个方法,一般由commons框架调用
     * @return
     */
    public static HashMap<String, List<NioTCPClient>> readNioServiceMap() {
        return nioServiceMap;
    }
    
    /**
     * 销毁一个客户端实例
     * java8方法
     * @param client
     */
    public static void destoryClient(NioTCPClient client) {
        client.getSession().close(true);
        client.destory();
        logger.info(MessageFormat.format("[Mina server-{0}:{1} is destroy]",
                new Object[]{client.getAddress(),client.getPort()}));
    }
    
    /**
     * NIO发送数据
     * 
     * @param nta
     */
    /**
     * NIO发送请求数据到服务端
     * @param serverKey 服务的keyName @MinaClientContextListener
     * @param nta
     */
    @SuppressWarnings("unchecked")
    public static <T> T write(String serverKey, RequestServiceAdapter nta, Class<T> clazz) throws Exception{
        List<NioTCPClient> clientList = nioServiceMap.get(serverKey);
        if(clientList == null || clientList.size() == 0) {
            throw new Exception("服务未连接,或者不存在该服务");
        }
        
        int clientSize = clientList.size();
        int rnd = RandomUtils.nextInt(0, clientSize);
        
        //随机获得一个服务端(均衡负载)
        IoSession session = clientList.get(rnd).getSession();
        
        String guid = UtilsGUID.getGUID();
        nta.setGUID(guid);
        nta.setStartTimeMillis(System.currentTimeMillis());
        session.write(nta);
        
        // 获得返回数据
        while (true) {
            ResponseServiceAdapter rtnNta = RESULT_MAP.get(guid);

            // 检查是否超时(默认5分钟)
            long usedTime = System.currentTimeMillis() - nta.getStartTimeMillis();
            if (usedTime > ClientHandler.TIMEOUT_MILLIS) {
                break;
            }

            // 获得存储数据
            if (rtnNta != null) {
                // 释放map空间
                RESULT_MAP.remove(guid);
                
                T result = null;
                
                //若服务层出现异常,则直接抛出异常
                if(rtnNta.getException() != null) {
                    throw rtnNta.getException();
                }
                
                //尝试转换成结果类型数据
                try {
                    result = (T) UtilsJson.parseJsonStr2Obj(rtnNta.getResponseJsonData(), clazz);
                } catch (Exception e) {
                    String errMsg = MessageFormat.format("服务返回数据格式不匹配.GUID:{0}, 服务返回数据:{1}, 转换类型:{2}",
                            new Object[]{guid, rtnNta.getResponseJsonData(), clazz.getClass().getName()});
                    String simpleErrMsg = MessageFormat.format("服务返回数据格式不匹配.GUID:{0}", guid);
                    logger.error(errMsg);
                    throw new Exception(simpleErrMsg);
                }
                        
                return result;
            } else {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        
        throw new Exception("服务请求处理超时");
    }
}
