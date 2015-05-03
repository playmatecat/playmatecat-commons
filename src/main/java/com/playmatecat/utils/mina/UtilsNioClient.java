package com.playmatecat.utils.mina;

import java.util.concurrent.ConcurrentHashMap;

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
	
	private UtilsNioClient(){}
	
	
	private static IoSession session;
	
	public static void init() {
		// 若未创建连接，则创建
		if (NioTCPClient.getConnector() == null) {
			synchronized (logger) {
				if (NioTCPClient.getConnector() == null) {
					try {
						NioTCPClient.init();
					} catch (Exception e) {
						logger.error("创建nio连接失败！", e);
					}
				}
			}
		}
		
		
		session = NioTCPClient.getSession();
	}
	
	/**
	 * 
	 */
	public static void write(NioTransferAdapter nta) {
		
		
		
		
//		IoSession session;
//		synchronized(logger) {
//			session = NioTCPClient.getSession();
//		}
		
		String GUID = UtilsGUID.getGUID();
		
		long start1 = System.currentTimeMillis();
		nta.setGUID(GUID);
		session.write(nta);
		
//		IoSession session = NioTCPClient.getSession();
//		String GUID = UtilsGUID.getGUID();
		long end1 = System.currentTimeMillis();
		//System.out.println("write use:" + (end1 -start1) + " ms");
		
		long start = System.currentTimeMillis();
		//获得返回数据
		while(true) {
			NioTransferAdapter rtnNta = resultMap.get(GUID);
			if(rtnNta != null) {
				//释放空间
				resultMap.remove(GUID);
				System.out.println("I got " + rtnNta.getJSONdata());
				long end = System.currentTimeMillis();
				//System.out.println("read use:" + (end -start) + " ms");
				return;
			} else {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
