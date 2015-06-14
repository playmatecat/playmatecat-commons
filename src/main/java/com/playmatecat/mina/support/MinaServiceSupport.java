package com.playmatecat.mina.support;

import java.util.ArrayList;

import com.playmatecat.mina.stucture.RequestServiceAdapter;
import com.playmatecat.utils.mina.UtilsNioClient;

/**
 * 调用mina服务的帮助类
 * @author blackcat
 *
 */
public class MinaServiceSupport {
    private MinaServiceSupport() {}
    
    /**
     * 调用mina service服务
     * @param serverKey mina服务的连接器key
     * @param nta 请求的数据结构体
     * @param rtnClazz 最终返回格式化成的数据类型
     */
    public static Object call(String serverKey, RequestServiceAdapter nta, Class rtnClazz) throws Exception{
        return UtilsNioClient.write(serverKey, nta, rtnClazz);
    }
}
