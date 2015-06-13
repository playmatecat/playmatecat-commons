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
    
    /** 请求结果 **/
    private String resultJsonData;
    
    /** nio request起始时间 **/
    private long startTimeMillis;

    /** JSONdata所对应的数据类型 **/
    private Class<? extends Object> clazz;

    /** 发生的异常 **/
    private Exception exception;
    
    /**
     * 用于服务端回传结果
     * @param resultJsonData 结果
     * @param reqNta 客户端请求的NioTransferAdapter对象
     */
    public NioTransferAdapter(String resultJsonData,NioTransferAdapter reqNta) {
        this.GUID = reqNta.getGUID();
        this.restServiceName = reqNta.getRestServiceName();
        this.jsonData = reqNta.getJsonData();
        this.startTimeMillis = reqNta.getStartTimeMillis();
        this.clazz = reqNta.clazz;
        this.resultJsonData = resultJsonData;
    }

    /**
     * 用户客户端构建请求
     * @param restServiceName
     * @param jsonData
     * @param clazz
     */
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

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public String getResultJsonData() {
        return resultJsonData;
    }

    public void setResultJsonData(String resultJsonData) {
        this.resultJsonData = resultJsonData;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


}
