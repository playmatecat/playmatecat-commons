package com.playmatecat.utils.dataformat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.util.StreamUtils;

public class UtilsStream {
    
    private final static Logger looger = Logger.getLogger(UtilsStream.class);
    
    private UtilsStream() {}

    public static String inputStream2String(InputStream in) throws IOException {
    	
    	String rtn = null;
        try {
        	rtn = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        } catch (Exception e) {
            looger.error("输入流转字符串错误", e);
        }
        
        return rtn;
    }
    
    public static String inputStream2String(InputStream in, String charset) throws IOException {
    	String rtn = null;
        try {
        	rtn = StreamUtils.copyToString(in, Charset.forName(charset));
        } catch (Exception e) {
            looger.error("输入流转字符串错误", e);
        }
        
        return rtn;
    }

    public static InputStream Str2InputStream(String str) {
        InputStream inNocodeInuptStream = new ByteArrayInputStream(str.getBytes());
        return inNocodeInuptStream;
    }
    
    public static InputStream Str2InputStream(String str, String charset) {
        InputStream inNocodeInuptStream = null;
        try {
            inNocodeInuptStream = new ByteArrayInputStream(str.getBytes(charset));
        } catch (Exception e) {
            looger.error("字符串转输入流错误", e);
        }
        
        return inNocodeInuptStream;
    }
}