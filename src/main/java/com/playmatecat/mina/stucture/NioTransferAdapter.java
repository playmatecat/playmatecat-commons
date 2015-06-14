package com.playmatecat.mina.stucture;

import java.io.Serializable;

/**
 * client请求nio server的传递数据适配器
 * 
 * @author blackcat
 *
 */
public abstract class NioTransferAdapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 请求的唯一标码 **/
    private String GUID;
    
    /** nio request起始时间 **/
    private long startTimeMillis;
    
    /** 请求的服务名 **/
    private String restServiceName;

    /** 向服务端发出请求的数据 **/
    private String requestJsonData;
    
    /** JSONdata所对应的数据类型(请求和返回必须用同一个类型的VO) **/
    private Class<? extends Object> clazz;

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String gUID) {
        GUID = gUID;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public String getRestServiceName() {
        return restServiceName;
    }

    public void setRestServiceName(String restServiceName) {
        this.restServiceName = restServiceName;
    }

    public String getRequestJsonData() {
        return requestJsonData;
    }

    public void setRequestJsonData(String requestJsonData) {
        this.requestJsonData = requestJsonData;
    }

    public Class<? extends Object> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Object> clazz) {
        this.clazz = clazz;
    }
    
    



}
