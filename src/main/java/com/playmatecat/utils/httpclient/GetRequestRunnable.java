package com.playmatecat.utils.httpclient;

import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import com.playmatecat.utils.dataformat.UtilsInputStream2Str;

class GetRequestRunnable implements Callable<String> {

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;

    public GetRequestRunnable(CloseableHttpClient httpClient, HttpGet httpget) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpget = httpget;
    }    
    
    public String call() throws Exception {
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .build();
            httpget.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(
                    httpget, context);
            
            try {
                HttpEntity entity = response.getEntity();
                return UtilsInputStream2Str.inputStream2String(entity.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
