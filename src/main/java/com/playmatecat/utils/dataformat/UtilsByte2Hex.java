package com.playmatecat.utils.dataformat;

/***
 * 字节和十六进制转换工具
 * @author root
 *
 */
public class UtilsByte2Hex {
    /** 字节二进制转16进制"与"运算码  **/
    private static final int HEX_EX_CODE = 0xFF;
    
    /**十六进制的进位大小**/
    private static final int HEX_HEIGHT = 16;
    
    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & HEX_EX_CODE);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
            
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), HEX_HEIGHT);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), HEX_HEIGHT);
            result[i] = (byte) (high * HEX_HEIGHT + low);
        }
        return result;
    }
}
