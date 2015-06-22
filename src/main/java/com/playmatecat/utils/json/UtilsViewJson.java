package com.playmatecat.utils.json;

import java.util.Map;

import net.minidev.json.JSONObject;

/**
 * 用于页面控制显示层的json工具类
 * @author blackcat
 *
 */
public class UtilsViewJson {
    private UtilsViewJson(){}
    
    /**
     * 获得成功信息的json
     * @param jsonDataObj 你的数据对象(一般是VO),在这个方法中会被转为json
     */
    public static Map<String,Object> getSuccessJson(Object jsonDataObj) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "success");

        jsonObj.put("data", UtilsJson.parseObj2Map(jsonDataObj));
        return jsonObj;
    }
    
    /**
     * 获得成功信息的json
     * @param jsonData 你的json数据
     */
    public static Map<String,Object> getFailJson(String failMessage) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "fail");
        
        jsonObj.put("message", failMessage);
        return jsonObj;
    } 
}
