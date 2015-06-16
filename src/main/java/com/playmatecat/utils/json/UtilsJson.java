package com.playmatecat.utils.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * json工具类
 * @author blackcat
 *
 */
public class UtilsJson {
    
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    
    public static String parseObj2JsonStr(Object obj) throws Exception {
        // alibaba json,it well ignore null filed,but json-smart must show null
        
        //return JSON.toJSONString(obj);
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 将json字符串转为对象
     * 注意!对象必须有空参数默认构造方法!
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static Object parseJsonStr2Obj(String jsonStr, Class<? extends Object> clazz) throws Exception {
        /*
         * JSONValue.parse(jsonStr, clazz);
         * smart json 反序列化好像有BUG
         */
        
        //return JSON.parseObject(jsonStr, clazz);
        return objectMapper.readValue(jsonStr, clazz);
    }

    public static Map<String, Object> parseObj2Map(Object obj) throws Exception {
//        //return  JSON.parseObject(JSON.toJSONString(obj));
        return objectMapper.readValue(parseObj2JsonStr(obj), new TypeReference<HashMap<String,Object>>() {});
    }
    
}
