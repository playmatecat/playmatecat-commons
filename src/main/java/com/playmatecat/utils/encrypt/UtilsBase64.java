package com.playmatecat.utils.encrypt;

import org.springframework.util.Base64Utils;

/**
 * base64加密
 * @author blackcat
 *
 */
public class UtilsBase64 {

    /**
     * 加密
     * @param src
     * @return
     */
    public static String encrypt(String src) {
        for(int i = 0; i < 10; i++) {
            byte[] b = Base64Utils.encode(src.getBytes());
            src = new String(b);
        }
        return src;
    }
    
    /**
     * 解密
     * @param src
     * @return
     */
    public static String decrypt(String src) {
        for(int i = 0; i < 10; i++) {
            byte[] b = Base64Utils.decode(src.getBytes());
            src = new String(b);
        }
        return src;
    }
    
}
