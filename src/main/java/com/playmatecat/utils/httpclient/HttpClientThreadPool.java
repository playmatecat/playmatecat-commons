package com.playmatecat.utils.httpclient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * http 线程池
 * @author blackcat
 *
 */
class HttpClientThreadPool {
    
    /** 单例对象 **/
    private static HttpClientThreadPool threadPool;
    
    /** cpu数量 **/
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors();

    /** 阻塞系数 **/
    private static final double BLOCKING_COEFFICIENT = 0.9;
    
    /**
     * 线程池最大数量
     * 线程数=CPU可用核心数/（1 - 阻塞系数），其中阻塞系数在在0到1范围内。
     * 计算密集型程序的阻塞系数为0，IO密集型程序的阻塞系数接近1。
     */
    private static final int POOL_MAX_SIZE = (int) (CORE_NUM / (1 - BLOCKING_COEFFICIENT));
    
    /** 当前执行线程数量  **/
    private static int runningThradCount;
    
    /** 线程是否已满的标志位 **/
    private static volatile boolean isFull;
    
    /** 执行的线程列队 **/
    private static List<Thread> threadList = new LinkedList<Thread>();
    
    /** 锁,用于共享变量 当前执行线程数量 runningThradCount的操作 **/
    private static final ReentrantLock LOCK;
    
    static {
        runningThradCount = 0;
        isFull = false;
        LOCK = new ReentrantLock();
    }
    
    private HttpClientThreadPool(){}
    
    public static HttpClientThreadPool getInstance(){
        if(threadPool == null) {
            synchronized (HttpClientThreadPool.class) {
                if(threadPool == null) {
                    init();
                    //等待扫描器开启
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                threadPool = new HttpClientThreadPool();
            }
        }
        return threadPool;
    }
    
    /**
     * 初始化线程池
     * 思路：创建一个线程，不断循环扫描当前执行线程列队中的存活状状态,若列队中线程死亡,则计数减1,空出位置给其他线程任务使用
     */
    private static void init() {
        Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        //扫描是否有线程存活
                        List<Thread> tmpList = new ArrayList<Thread>(threadList);
                        
                        tmpList.stream().filter(peekThread -> peekThread.isAlive())
                            .forEach(peekThread -> {
                                    threadList.remove(peekThread);
                                    LOCK.lock();
                                    runningThradCount--;
                                    LOCK.unlock();
                                    if(runningThradCount < POOL_MAX_SIZE) {
                                        isFull = false;
                                    }
                                });
                        
                        Thread.sleep(1);
                    }//end of while
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        
        //设置为守护线程
        thread.setDaemon(true);
        thread.start();
    }
    
    /**
     * 以线程池管理下执行线程
     * @param thread 需要执行的线程
     */
    public void start(Thread thread) {
        //线程是否已开启
        boolean isStarted = false;
        thread.setDaemon(true);
        while(!isStarted) {
            if(runningThradCount <= POOL_MAX_SIZE) {
                LOCK.lock();
                //volatile内存屏障
                if(!isFull) {
                    //当有多个线程阻塞在上面的lock时,可能会running数量瞬间超过上限,造成跳过下面的if,此时应该循环判断是否已列队满调用
                    if (runningThradCount <= POOL_MAX_SIZE) {
                        runningThradCount++;
                        threadList.add(thread);
                        thread.start();
                        isStarted = true;
                    }
                }
                LOCK.unlock();
            } else {
                LOCK.lock();
                //volatile内存屏障
                if(!isFull) {  
                    isFull = true;
                    runningThradCount++;
                    threadList.add(thread);
                    thread.start();
                    isStarted = true;
                }
                LOCK.unlock();
            }
            
            //sleep for while true
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
