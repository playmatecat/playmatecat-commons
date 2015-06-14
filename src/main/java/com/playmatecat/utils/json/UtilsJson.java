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

    /**
     * 将json字符串转为对象
     * 注意!对象必须有空参数默认构造方法!
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static Object parseJsonStr2Obj(String jsonStr, Class<? extends Object> clazz) {
        /*
         * JSONValue.parse(jsonStr, clazz);
         * smart json 反序列化好像有BUG
         */
        return JSON.parseObject(jsonStr, clazz);
    }

    public static Map<String, Object> parseObj2Map(Object obj) {
        return  JSON.parseObject(JSON.toJSONString(obj));
    }

}
