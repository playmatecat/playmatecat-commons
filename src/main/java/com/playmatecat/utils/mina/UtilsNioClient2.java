package com.playmatecat.utils.mina;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.playmatecat.mina.NioTransferAdapter;
import com.playmatecat.mina.client.ClientHandler;
import com.playmatecat.mina.client.NioTcpClient2;
import com.playmatecat.utils.label.UtilsGUID;

public class UtilsNioClient2 {
    public static final ConcurrentHashMap<String, NioTransferAdapter> RESULT_MAP = new ConcurrentHashMap<String, NioTransferAdapter>();

    private static Logger logger = Logger.getLogger(UtilsNioClient.class);

    private static ReentrantLock lock = new ReentrantLock();
    
    /**mina nio service服务map**/
    private static HashMap<String, NioTcpClient2> nioServiceMap = new HashMap<String, NioTcpClient2>();
    
    /**
     * 初始化nio mina nio service服务map
     * @param nioServiceMap
     */
    public static void initNioServiceMap(HashMap<String, NioTcpClient2> map) {
        nioServiceMap = map;
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
