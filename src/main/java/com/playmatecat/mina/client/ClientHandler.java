package com.playmatecat.mina.client;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.playmatecat.mina.stucture.NioTransferAdapter;
import com.playmatecat.mina.stucture.ResponseServiceAdapter;
import com.playmatecat.utils.mina.UtilsNioClient;

/**
 * client端消息处理
 * @author blackcat
 *
 */
public class ClientHandler extends IoHandlerAdapter {

    private static Logger logger = Logger.getLogger(ClientHandler.class);
    
    /** 超时时间5分钟  **/
    public final static long TIMEOUT_MILLIS = 300000;
    
    /** 绑定本handler的Nio客户端**/
    private NioTCPClient nioTCPClient;
    
    /** session是否关闭 (线程安全)**/
    private volatile boolean isSessionClosed = false;
    
    public ClientHandler(NioTCPClient nioTCPClient) {
        this.nioTCPClient = nioTCPClient;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("[Nio Client]session opened.");
        super.sessionOpened(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        ResponseServiceAdapter nta = (ResponseServiceAdapter) message;
        // 检查是否超时,防止写入死亡数据(死亡数据永远不会从map里清除)
        long usedTime = System.currentTimeMillis() - nta.getStartTimeMillis();
        if(usedTime < TIMEOUT_MILLIS) {
            logger.debug(MessageFormat.format("[Nio Server]<<service name:{0}", nta.getRestServiceName()));
            logger.debug(MessageFormat.format("[Nio Server]<<json data:{0}", nta.getRequestJsonData()));
            logger.debug(MessageFormat.format("[Nio Server]<<dto class:{0}", nta.getClazz()));
            logger.debug(MessageFormat.format("[Nio Server]>>result:{0}", nta.getResponseJsonData()));
            logger.debug(MessageFormat.format("[Nio Server]>>cost:{0} ms", usedTime));
            UtilsNioClient.RESULT_MAP.put(nta.getGUID(), nta);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        //如果发送时发现连接被关闭了,那么重建连接
        if(isSessionClosed) {
            try {
                ConnectFuture future = nioTCPClient.getConnector().connect();
                // 等待连接创建成功
                future.awaitUninterruptibly();
                // 获取会话
                nioTCPClient.setSession(future.getSession());
            } catch (Exception e) {
                logger.error("发送时重建nio server连接失败!" + nioTCPClient.getAddress() + ":" + nioTCPClient.getPort(), e);
                return;
            }
            //重建成功则重置状态位
            isSessionClosed = false;
        }
        
        NioTransferAdapter nta = (NioTransferAdapter) message;
        super.messageSent(session, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("[Nio Client]session closed.");
        super.sessionClosed(session);
        isSessionClosed = true;
    }

}
