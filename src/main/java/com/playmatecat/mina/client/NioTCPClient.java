package com.playmatecat.mina.client;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class NioTCPClient {
	
	private static Logger logger = Logger.getLogger(NioTCPClient.class);
	
	/**服务端口**/
	private static final int PORT = 8501;
	/**服务IP**/
	private static final String ADDRESS = "127.0.0.1";
	/**超时**/
	private static final int TIME_OUT = 60;
	
	/**公用全局的connector**/
	static NioSocketConnector connector;
	
	static IoSession session;
	
	static ConnectFuture future;
	
	private static NioClientSessionListener nioClientSessionListener = new NioClientSessionListener();

	/**
	 * 程序入口
	 * @param args
	 * @throws Throwable
	 */
	public static void init() throws Exception {
		//@STEP1 创建NIO连接器
	    connector = new NioSocketConnector();
	    //设定超时值
	    connector.setConnectTimeoutMillis(TIME_OUT);
	    
	    //@STEP2 创建一个过滤器链配置.注意链式顺序
//	    connector.getFilterChain().addLast("logger", new LoggingFilter());
	    
	    //指定数据解析器
//	    connector.getFilterChain().addLast("codec", 
//	    		new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
	    
	    connector.getFilterChain().addLast("codec", 
	    		new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
	    
	    
	    //@STEP3 指定消息处理器
	    connector.setHandler(new ClientHandler());
	    
		// 读取缓冲区
	    connector.getSessionConfig().setReadBufferSize(1024);
	    //connector.getSessionConfig().setMaxReadBufferSize(128);
		// io空停滞时间
	    connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 超时时间
	    connector.getSessionConfig().setWriteTimeout(TIME_OUT);
	    //监听器，断线则重连
	    connector.addListener(nioClientSessionListener);
	    
	    //@STEP4 尝试建立连接,连接成功则跳出循环
	    while(true) {
	    	try {
		    	future = connector.connect(new InetSocketAddress(ADDRESS, PORT));
		    	// 等待连接创建成功  
		    	future.awaitUninterruptibly();
		    	session = future.getSession();
		    	break;
			} catch (Exception e) {
				logger.error("nio server连接失败!", e);
			}
	    }
	    
	}
	
	public static ConnectFuture getFuture() {
		return future;
	}
	
	/**
	 * 获得连接器
	 * @return
	 */
	public static NioSocketConnector getConnector() {
		return connector;
	}
	
	/**
	 * 获取session
	 * @return
	 */
	public static IoSession getSession() {
		return session;
	}
	
	/**
	 * 销毁连接
	 */
	public void destory() {
		connector.removeListener(nioClientSessionListener);
		 //等待所有的请求处理完毕
	    session.getCloseFuture().awaitUninterruptibly();
	    //断开连接
	    connector.dispose();
	}
}
