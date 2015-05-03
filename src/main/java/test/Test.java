package test;

import com.playmatecat.mina.NioTransferAdapter;
import com.playmatecat.utils.mina.UtilsNioClient;

public class Test {
	public static void main(String[] args) throws Exception{
		
//		 long l = Runtime.getRuntime().totalMemory()/1024/1024;
//         long f = Runtime.getRuntime().freeMemory()/1024/1024;
//         long rem = l - f;
//		
//		NioTransferAdapter nta1;
//		long start = System.currentTimeMillis();
//		for(int i = 0;i < 10000;i++) {
//			nta1 = new NioTransferAdapter(String.valueOf(i), "{}", null);
//			UtilsNioClient.write(nta1);
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("finish:" + (end -start) + " ms");
		
		UtilsNioClient.init();
		
		for(int i = 0; i < 2; i++) {
			RunThread rt = new RunThread();
			rt.start();
		}
		
	}
	
	
	public static class RunThread extends Thread {

		@Override
		public void run() {
			NioTransferAdapter nta1;
			long start = System.currentTimeMillis();
			for(int i = 0;i < 1;i++) {
				nta1 = new NioTransferAdapter(String.valueOf(i), "{}", null);
				UtilsNioClient.write(nta1);
			}
			long end = System.currentTimeMillis();
			System.out.println("finish:" + (end -start) + " ms");
		}
		
	}
}
