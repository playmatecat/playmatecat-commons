package com.playmatecat.mina;

import java.io.Serializable;

/**
 * client请求nio server的传递数据适配器
 * 
 * @author blackcat
 *
 */
public class NioTransferAdapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 请求的唯一标码 **/
    private String GUID;

    /** 请求的服务名 **/
    private String restServiceName;

    /** 传递的数据 **/
    private String jsonData;

    /** JSONdata所对应的数据类型 **/
    private Class<? extends Object> clazz;

    public NioTransferAdapter(String jsonData) {
        this.jsonData = jsonData;
    }

    public NioTransferAdapter(String restServiceName, String jsonData, Class<? extends Object> clazz) {
        this.restServiceName = restServiceName;
        this.jsonData = jsonData;
        this.clazz = clazz;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String gUID) {
        GUID = gUID;
    }

    public String getRestServiceName() {
        return restServiceName;
    }

    public void setRestServiceName(String restServiceName) {
        this.restServiceName = restServiceName;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Class<? extends Object> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Object> clazz) {
        this.clazz = clazz;
    }

}
