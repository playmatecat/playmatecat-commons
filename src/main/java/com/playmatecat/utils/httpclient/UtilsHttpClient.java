package com.playmatecat.utils.httpclient;

import java.io.InputStream;
import java.util.concurrent.FutureTask;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.playmatecat.utils.dataformat.UtilsStream;


public class UtilsHttpClient {
    
    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm).build();
    
    private UtilsHttpClient(){}
    

    /**
     * 获得一个简单的响应文本结果
     * @param url 访问地址
     * @return
     * @throws Exception
     */
    public static String getSimpleRespStr(String url) throws Exception{
        HttpGet httpGet = new HttpGet(url);
        GetRequestRunnable getReqCallable = new GetRequestRunnable(httpClient, httpGet);
        FutureTask<String> ft = new FutureTask<String>(getReqCallable);
        Thread thread = new Thread(ft);
        HttpClientThreadPool.getInstance().start(thread);

        return ft.get();
    }
    
    /**
     * 获得一个简单的响应结果的输入流
     * @param url
     * @return
     * @throws Exception
     */
    public static InputStream getSimpleRespInputStream(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        GetRequestRunnable getReqCallable = new GetRequestRunnable(httpClient, httpGet);
        FutureTask<String> ft = new FutureTask<String>(getReqCallable);
        Thread thread = new Thread(ft);
        HttpClientThreadPool.getInstance().start(thread);
        
        String result = ft.get();
        return UtilsStream.str2InputStream(result);
    }
}


