package com.playmatecat.mina.client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 提供掉线重连
 * @author blackcat
 *
 */
public class NioClientSessionListener implements IoServiceListener {

	public void serviceActivated(IoService service) throws Exception {

	}

	public void serviceIdle(IoService service, IdleStatus idleStatus)
			throws Exception {

	}

	public void serviceDeactivated(IoService service) throws Exception {

	}

	public void sessionCreated(IoSession session) throws Exception {

	}

	public void sessionClosed(IoSession session) throws Exception {

	}

	public void sessionDestroyed(IoSession session) throws Exception {
		while (true) {
			Thread.sleep(3000);
			ConnectFuture future = NioTCPClient.connector.connect();
			// 等待连接创建成功
			future.awaitUninterruptibly();
			// 获取会话
			NioTCPClient.session = future.getSession();
		}
	}

}
