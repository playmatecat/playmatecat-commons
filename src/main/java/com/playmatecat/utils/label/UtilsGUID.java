package com.playmatecat.utils.label;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成唯一编码
 * @author blackcat
 *
 */
public class UtilsGUID {
	private UtilsGUID(){}
	
	public static String getGUID() {
		String guids[] = new String[5];
		for(int i = 0; i < 5; i++) {
			guids[i] = UUID.randomUUID().toString();
		}
		String GUID = StringUtils.join(guids,"-");
		return GUID;
	}
}
