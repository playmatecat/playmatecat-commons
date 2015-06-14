package com.playmatecat.mina.support;

import com.playmatecat.mina.stucture.RequestServiceAdapter;
import com.playmatecat.utils.json.UtilsJson;
import com.playmatecat.utils.mina.UtilsNioClient;

/**
 * 调用mina服务的帮助类
 * @author blackcat
 *
 */
public class MinaServiceSupport {
    private MinaServiceSupport() {}
    
    /**
     * 
     * @param serverKey mina服务的连接器key
     * @param restServiceName 服务名(eg:组件.方法    eg:userCpt.addUser)
     * @param viewObject 视图对象
     * @return 返回的也是视图对象,返回与请求用的必须是同一类型
     * @throws Exception
     */
    public static Object call(String serverKey, String  restServiceName ,Object viewObject) throws Exception{
        RequestServiceAdapter reqNta = new RequestServiceAdapter(restServiceName, UtilsJson.parseObj2JsonStr(viewObject), viewObject.getClass());
        return UtilsNioClient.write(serverKey, reqNta, viewObject.getClass());
    }
}
