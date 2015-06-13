package com.playmatecat.mina.stucture;

/**
 * NIO服务端返回的数据类型
 * @author blackcat
 *
 */
public class ResponseServiceAdapter extends NioTransferAdapter{
    
    private static final long serialVersionUID = 1L;

    /** 请求结果 **/
    private String responseJsonData;
    
    /** 发生的异常 **/
    private Exception exception;
    
    public ResponseServiceAdapter() {
        
    }
    
    /**
     * 用于服务端回传结果
     * @param resultJsonData 结果
     * @param reqNta 客户端请求的NioTransferAdapter对象
     */
    public ResponseServiceAdapter(String resultJsonData,RequestServiceAdapter reqNta) {
        this.setGUID(reqNta.getGUID());
        this.setRestServiceName(reqNta.getRestServiceName());
        this.setRequestJsonData(reqNta.getRequestJsonData());
        this.setStartTimeMillis(reqNta.getStartTimeMillis());
        this.setClazz(reqNta.getClazz());
        this.responseJsonData = resultJsonData;
    }

    public String getResponseJsonData() {
        return responseJsonData;
    }

    public void setResponseJsonData(String responseJsonData) {
        this.responseJsonData = responseJsonData;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


    
}
