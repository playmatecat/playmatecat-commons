package com.playmatecat.mina.client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 提供掉线重连
 * 
 * @author blackcat
 *
 */
public class NioClientSessionListener implements IoServiceListener {
    
    /** 断线重连延迟 **/
    private static final int RECONNECT_DELAY = 1000;
    
    private NioTCPClient nioTCPClient;
    
    public NioClientSessionListener(NioTCPClient nioTCPClient){
        this.nioTCPClient = nioTCPClient;
    }

    public void serviceActivated(IoService service) throws Exception {

    }

    public void serviceIdle(IoService service, IdleStatus idleStatus) throws Exception {

    }

    public void serviceDeactivated(IoService service) throws Exception {

    }

    public void sessionCreated(IoSession session) throws Exception {

    }

    public void sessionClosed(IoSession session) throws Exception {

    }

    public void sessionDestroyed(IoSession session) throws Exception {
        while (true) {
            Thread.sleep(RECONNECT_DELAY);
            ConnectFuture future = nioTCPClient.getConnector().connect();
            // 等待连接创建成功
            future.awaitUninterruptibly();
            // 获取会话
            nioTCPClient.setSession(future.getSession());
        }
    }

}
