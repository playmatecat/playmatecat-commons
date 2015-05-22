package com.playmatecat.utils.dataformat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.util.StreamUtils;

/**
 * 流工具
 * @author root
 *
 */
public class UtilsStream {

    private static final Logger LOGGER = Logger.getLogger(UtilsStream.class);

    private UtilsStream() {
    }

    /**
     * 输入流转字符串
     * @param in
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream in) throws IOException {

        String rtn = null;
        try {
            rtn = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        } catch (Exception e) {
            LOGGER.error("输入流转字符串错误", e);
        }

        return rtn;
    }
    
    /**
     * 输入流转字符串
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream in, String charset) throws IOException {
        String rtn = null;
        try {
            rtn = StreamUtils.copyToString(in, Charset.forName(charset));
        } catch (Exception e) {
            LOGGER.error("输入流转字符串错误", e);
        }

        return rtn;
    }
    
    /**
     * 字符串转输入流
     * @param str
     * @return
     */
    public static InputStream str2InputStream(String str) {
        InputStream inNocodeInuptStream = new ByteArrayInputStream(str.getBytes());
        return inNocodeInuptStream;
    }

    /**
     * 字符串转输入流
     * @param str
     * @param charset
     * @return
     */
    public static InputStream str2InputStream(String str, String charset) {
        InputStream inNocodeInuptStream = null;
        try {
            inNocodeInuptStream = new ByteArrayInputStream(str.getBytes(charset));
        } catch (Exception e) {
            LOGGER.error("字符串转输入流错误", e);
        }

        return inNocodeInuptStream;
    }
}