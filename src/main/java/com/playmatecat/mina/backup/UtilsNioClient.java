//package com.playmatecat.mina.backup;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.locks.ReentrantLock;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.session.IoSession;
//
//import com.playmatecat.mina.NioTransferAdapter;
//import com.playmatecat.mina.backup.NioTCPClient;
//import com.playmatecat.mina.client.ClientHandler;
//import com.playmatecat.utils.label.UtilsGUID;
//
///**
// * mina Nio客户端工具类
// * 
// * @author blackcat
// *
// */
//public class UtilsNioClient {
//
//    public static final ConcurrentHashMap<String, NioTransferAdapter> RESULT_MAP = new ConcurrentHashMap<String, NioTransferAdapter>();
//
//    private static Logger logger = Logger.getLogger(UtilsNioClient.class);
//
//    private static ReentrantLock lock = new ReentrantLock();
//
//    private static IoSession session;
//
//    private UtilsNioClient() {
//    }
//
//    public static void init() {
//        // 若未创建连接，则创建
//        if (NioTCPClient.getConnector() == null) {
//            try {
//                NioTCPClient.init();
//            } catch (Exception e) {
//                logger.error("创建nio连接失败！", e);
//            }
//        }
//
//        session = NioTCPClient.getSession();
//    }
//
//    /**
//     * NIO发送数据
//     * 
//     * @param nta
//     */
//    public static void write(NioTransferAdapter nta) {
//        if (session == null) {
//            lock.lock();
//            if (session == null) {
//                init();
//            }
//            lock.unlock();
//        }
//
//        String guid = UtilsGUID.getGUID();
//        nta.setGUID(guid);
//        nta.setStartTimeMillis(System.currentTimeMillis());
//        session.write(nta);
//
//        // 获得返回数据
//        while (true) {
//            NioTransferAdapter rtnNta = RESULT_MAP.get(guid);
//
//            // 检查是否超时(默认5分钟)
//            long usedTime = System.currentTimeMillis() - nta.getStartTimeMillis();
//            if (usedTime > ClientHandler.TIMEOUT_MILLIS) {
//                break;
//            }
//
//            // 获得存储数据
//            if (rtnNta != null) {
//                // 释放空间
//                RESULT_MAP.remove(guid);
//                return;
//            } else {
//                try {
//                    Thread.sleep(10);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//}
