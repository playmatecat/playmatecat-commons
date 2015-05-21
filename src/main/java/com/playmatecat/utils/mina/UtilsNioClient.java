package com.playmatecat.utils.mina;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.playmatecat.mina.NioTransferAdapter;
import com.playmatecat.mina.client.NioTCPClient;
import com.playmatecat.utils.label.UtilsGUID;



/**
 * mina Nio客户端工具类
 * @author blackcat
 *
 */
public class UtilsNioClient {
	private static Logger logger = Logger.getLogger(UtilsNioClient.class);
	
	public final static ConcurrentHashMap<String, NioTransferAdapter> resultMap = new ConcurrentHashMap<String, NioTransferAdapter>();
	
	private static ReentrantLock lock = new ReentrantLock();
	
	private UtilsNioClient(){}
	
	
	private static IoSession session;
	
	public static void init() {
		// 若未创建连接，则创建
		if (NioTCPClient.getConnector() == null) {
			try {
				NioTCPClient.init();
			} catch (Exception e) {
				logger.error("创建nio连接失败！", e);
			}
		}
		
		session = NioTCPClient.getSession();
	}
	
	/**
	 * 
	 */
	public static void write(NioTransferAdapter nta) {
		if(session == null) {
			lock.lock();
			if(session == null) {
				init();
			}
			lock.unlock();
		}
		
		String GUID = UtilsGUID.getGUID();
		nta.setGUID(GUID);
		session.write(nta);

		//获得返回数据
		while(true) {
			NioTransferAdapter rtnNta = resultMap.get(GUID);
			if(rtnNta != null) {
				//释放空间
				resultMap.remove(GUID);
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
