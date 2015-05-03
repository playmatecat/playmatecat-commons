package com.playmatecat.utils.dataformat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class UtilsInputStream2Str {
    
    private final static Logger looger = Logger.getLogger(UtilsInputStream2Str.class);
    
    private UtilsInputStream2Str() {}

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        try {
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
        } catch (Exception e) {
            looger.error("输入流转字符串错误", e);
        }
        
        return out.toString();
    }
    
    public static String inputStream2String(InputStream in, String charset) throws IOException {
        StringBuffer out = new StringBuffer();
        try {
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1;) {
                out.append(new String(b, 0, n, charset));
            }
        } catch (Exception e) {
            looger.error("输入流转字符串错误", e);
        }
        
        return out.toString();
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