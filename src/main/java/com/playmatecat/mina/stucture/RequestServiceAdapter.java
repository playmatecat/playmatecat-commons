package com.playmatecat.mina.stucture;


/**
 * 客户端请求NIO服务的数据类型
 * @author Isa
 *
 */
public class RequestServiceAdapter extends NioTransferAdapter {

    private static final long serialVersionUID = 1L;



    /**
     * 用户客户端构建请求
     * @param restServiceName
     * @param jsonData
     * @param clazz
     */
    public RequestServiceAdapter(String restServiceName, String requestJsonData, Class<? extends Object> clazz) {
        this.setRestServiceName(restServiceName);
        this.setRequestJsonData(requestJsonData);
        this.setClazz(clazz);
    }


}
