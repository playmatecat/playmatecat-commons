//package com.playmatecat.dbcp;
//
//import org.apache.commons.dbcp2.BasicDataSource;
//
//import com.playmatecat.utils.encrypt.UtilsBase64;
//
//public class PCBasicDataSource extends BasicDataSource{
//    
//    /**密码是否启用加密,默认是加密的**/
//    private static Boolean isEncrypt = true;
//
// 
//    
//    public static Boolean getIsEncrypt() {
//        return isEncrypt;
//    }
//
//
//
//    public static void setIsEncrypt(Boolean isEncrypt) {
//        PCBasicDataSource.isEncrypt = isEncrypt;
//    }
//
//
//
//    /**
//     * <p>Sets the {@link #password}.</p>
//     * <p>
//     * Note: this method currently has no effect once the pool has been
//     * initialized.  The pool is initialized the first time one of the
//     * following methods is invoked: <code>getConnection, setLogwriter,
//     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
//     *
//     * @param password new value for the password
//     */
//    @Override
//    public void setPassword(String password) {
//        if(isEncrypt) {
//            password = UtilsBase64.decrypt(password);
//        }
//        super.setPassword(password);
//    }
//    
//}
