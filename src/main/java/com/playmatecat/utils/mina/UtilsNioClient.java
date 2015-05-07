package com.playmatecat.utils.mina;

import java.text.MessageFormat;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
	
	/** cpu数量 **/
    private final static int numberOfCores = Runtime.getRuntime().availableProcessors();

    /** 阻塞系数 **/
    private final static double blockingCoefficient = 0.95;
    
    /**
     * 线程池最大数量
     * 线程数=CPU可用核心数/（1 - 阻塞系数），其中阻塞系数在在0到1范围内。
     * 计算密集型程序的阻塞系数为0，IO密集型程序的阻塞系数接近1。
     */
    private final static int poolMaxSize = (int) (numberOfCores / (1 - blockingCoefficient));
	
    /**concurrent包的线程池**/
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(poolMaxSize, poolMaxSize,
		    60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	
	
	public final static Object readLock = new Object();
	
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
		
		long a = System.currentTimeMillis();
		String GUID = UtilsGUID.getGUID();
		nta.setGUID(GUID);
		session.write(nta);
		long b = System.currentTimeMillis();
		System.out.println("write" + (b -a));

		
		
//		long s = System.currentTimeMillis();
//		
//		CompletionService<NioTransferAdapter> completionService = 
//				new ExecutorCompletionService<NioTransferAdapter>(threadPoolExecutor);
//		
//		completionService.submit(()-> {
//				long startGetTime = System.currentTimeMillis();
//				NioTransferAdapter rtnNta = null;
//				//获得返回数据
//				while(rtnNta == null) {
//					long now = System.currentTimeMillis();
//					//调用超时
//					if((now - startGetTime) / 1000 > 180) {
//						logger.info(MessageFormat.format("Request NIO server out of time,service:{0},args:{1}",
//								nta.getRestServiceName(), nta.getJSONdata()));
//						break;
//					}
//					
//					synchronized (readLock) {
//						if(resultMap.contains(GUID)) {
//							rtnNta = resultMap.get(GUID);
//							//释放空间
//							resultMap.remove(GUID);
//							return rtnNta;
//						} else {
//							//@see ClientHandler此处可能session写入map,然后先notifyAll。然后本线程阻塞,无人唤醒
//							readLock.wait();
//						}
//					}
//				}
//				
//				return null;
//			});
//		
//		NioTransferAdapter rtnNta = null;
//		try {
//			rtnNta = completionService.take().get();
//        } catch (Exception e) {
//	        e.printStackTrace();
//        }
//		
//		System.out.println("I got " + rtnNta.getJSONdata());
		
//		long e = System.currentTimeMillis();
//		
//		System.out.println("read" + (e -s));
		//获得返回数据
		while(true) {
			NioTransferAdapter rtnNta = resultMap.get(GUID);
			if(rtnNta != null) {
				//释放空间
				resultMap.remove(GUID);
				
				logger.info("");
				System.out.println("I got " + rtnNta.getJSONdata());
				return;
			} else {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
