package com.playmatecat.utils.spring;

import java.util.Map;

import com.playmatecat.spring.properties.CatPropertyConfigurer;

/**
 * 读取spring配置的properties值(可配合beans profile但是要配合CatPropertyConfigurer)
 * @see CatPropertyConfigurer
 * @author root
 *
 */
public class UtilsProperties {
    private static Map<String, Object> ctxPropertiesMap;
    
    private UtilsProperties(){}
    
    /**
     * 请不要在非框架部分直接读写这个方法,这个singletonMap只用于commons框架中
     * @return
     */
    public static Map<String, Object> singletonMap() {
        return ctxPropertiesMap;
    }
    
    
    /**
     * 获得某个spring配置的properties的值
     * eg:getProp("jdbc.url");
     * @param name
     * @return
     */
    public static String getProp(String name) {
        return ctxPropertiesMap.get(name).toString();
    }
}
