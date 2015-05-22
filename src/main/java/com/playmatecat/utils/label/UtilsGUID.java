package com.playmatecat.utils.label;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成唯一编码
 * 
 * @author blackcat
 *
 */
public class UtilsGUID {
    private UtilsGUID() {
    }
    
    private static final int GUID_LENGTH = 5;

    public static String getGUID() {
        String[] guids = new String[GUID_LENGTH];
        for (int i = 0; i < GUID_LENGTH; i++) {
            guids[i] = UUID.randomUUID().toString();
        }
        String guid = StringUtils.join(guids, "-");
        return guid;
    }
}
