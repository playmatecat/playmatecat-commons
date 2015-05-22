package com.playmatecat.utils.json;

import java.util.Map;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import com.alibaba.fastjson.JSON;

/**
 * json工具类
 * @author blackcat
 *
 */
public class UtilsJson {

    public static String parseObj2JsonStr(Object obj) {
        // alibaba json,it well ignore null filed,but json-smart must show null
        return JSON.toJSONString(obj);
    }

    public static Object parseJsonStr2Obj(String jsonStr, Class<? extends Object> clazz) {
        return JSONValue.parse(jsonStr, clazz);
    }

    public static Map<String, Object> parseObj2Map(Object obj) {
        return JSONValue.parse(JSON.toJSONString(obj), JSONObject.class);
    }

}
