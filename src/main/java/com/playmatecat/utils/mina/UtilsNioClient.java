package com.playmatecat.utils.mina;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.playmatecat.mina.NioTransferAdapter;
import com.playmatecat.mina.client.ClientHandler;
import com.playmatecat.mina.client.NioTCPClient;
import com.playmatecat.utils.label.UtilsGUID;

public class UtilsNioClient {
    
    private static Logger logger = LogManager.getLogger(UtilsNioClient.class);
    
    /**结果集存放的map,通过GUID作为KEY,所有的client都共享这个map(key为GUID不会重复,请放心)**/
    public final static ConcurrentHashMap<String, NioTransferAdapter> RESULT_MAP 
        = new ConcurrentHashMap<String, NioTransferAdapter>();

    private static ReentrantLock lock = new ReentrantLock();
    
    /**mina nio service服务map**/
    private static HashMap<String, NioTCPClient> nioServiceMap = new HashMap<String, NioTCPClient>();
    
    /**
     * 初始化nio mina nio service服务map
     * @param nioServiceMap
     */
    public static void initNioServiceMap(HashMap<String, NioTCPClient> map) {
        nioServiceMap = map;
    }
    
    /**
     * 返回server map,自己请不要随意调用这个方法,一般由commons框架调用
     * @return
     */
    public static HashMap<String, NioTCPClient> readNioServiceMap() {
        return nioServiceMap;
    }
    
    /**
     * 销毁一个客户端实例
     * java8方法
     * @param client
     */
    public static void destoryClient(String serverkey, NioTCPClient client) {
        client.destory();
        logger.info(MessageFormat.format("[Mina server-{0} is destroy]", serverkey));
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
    public static void write(String serverKey, NioTransferAdapter nta) {
        IoSession session = nioServiceMap.get(serverKey).getSession();

        String guid = UtilsGUID.getGUID();
        nta.setGUID(guid);
        nta.setStartTimeMillis(System.currentTimeMillis());
        session.write(nta);

        // 获得返回数据
        while (true) {
            NioTransferAdapter rtnNta = RESULT_MAP.get(guid);

            // 检查是否超时(默认5分钟)
            long usedTime = System.currentTimeMillis() - nta.getStartTimeMillis();
            if (usedTime > ClientHandler.TIMEOUT_MILLIS) {
                break;
            }

            // 获得存储数据
            if (rtnNta != null) {
                // 释放空间
                RESULT_MAP.remove(guid);
                return;
            } else {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
