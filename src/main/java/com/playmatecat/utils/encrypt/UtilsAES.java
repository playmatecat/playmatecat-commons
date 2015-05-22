package com.playmatecat.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.playmatecat.utils.dataformat.UtilsByte2Hex;

/**
 * AES加密解密工具
 * @author root
 *
 */
public class UtilsAES {

    /** 请不要修改令牌,否则解密失效 **/
    private static final String keyToken = "2BA81AC075E9F82855A334496B9C6DC22EFD5B2FF302B538495844C5D79E16DD";

    /** 加密长度 **/
    private static final int AES_ENCRYPT_LENGTH = 128;
    
    /**
     * 加密方式名常量
     */
    private interface EncryptType {
        String AES = "AES";
        String SHA1PRNG = "SHA1PRNG";
    }
    
    /**
     * 加密
     * 
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    public static String encrypt(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(EncryptType.AES);

            // 兼容linux的secureRandom
            SecureRandom secureRandom = SecureRandom.getInstance(EncryptType.SHA1PRNG);
            secureRandom.setSeed(keyToken.getBytes());

            kgen.init(AES_ENCRYPT_LENGTH, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, EncryptType.AES);
            Cipher cipher = Cipher.getInstance(EncryptType.AES);// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return UtilsByte2Hex.parseByte2HexStr(result);// 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
    public static String decrypt(String content) {
        if (content == null) {
            return null;
        }

        byte[] contentBytes = UtilsByte2Hex.parseHexStr2Byte(content);
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(EncryptType.AES);

            // 兼容linux的secureRandom
            SecureRandom secureRandom = SecureRandom.getInstance(EncryptType.SHA1PRNG);
            secureRandom.setSeed(keyToken.getBytes());

            kgen.init(AES_ENCRYPT_LENGTH, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, EncryptType.AES);
            Cipher cipher = Cipher.getInstance(EncryptType.AES);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(contentBytes);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        String content = "TQ32R-WFBDM-GFHD2-QGVMH-3P9GC";
//        // 加密
//        System.out.println("加密前：" + content);
//        String encryptResult = encrypt(content);
//        System.out.println(encryptResult);
//
//        // 解密
//        String decryptResult = decrypt(encryptResult);
//        System.out.println("解密后：" + decryptResult);
//    }

}
