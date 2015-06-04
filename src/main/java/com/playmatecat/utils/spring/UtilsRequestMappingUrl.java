package com.playmatecat.utils.spring;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UrlPathHelper;

import com.playmatecat.spring.mvc.CatRequestMappingHandlerMapping;



/**
 * 获得spring controller上的requestMapping的url.
 * 提供contains方法,来检查controller的reqeustMapping注解中是否包含了request请求的url映射
 * 
 * 注意必须配置在mvc-dispatcher.xml使用CatRequestMappingHandlerMapping来代替spring原有的RequestMappingHandlerMapping
 * <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
 * 替换成
 * <bean class="xxx.CatRequestMappingHandlerMapping">
 * @CatRequestMappingHandlerMapping
 * @author blackcat
 *
 */
public class UtilsRequestMappingUrl {
	/**用于存储url的set**/
	private final static HashSet<String> urlSet = new HashSet<String>();
	
	/**spring提供的url去参简化成最简url工具,可以获得lookupPath不含参数的路径(不包含域名信息,例如/test/name)**/
	private static UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	@SuppressWarnings("rawtypes")
    private static CatRequestMappingHandlerMapping catRequestMappingHandlerMapping;
	
	private UtilsRequestMappingUrl(){}
	
	/**
	 * 此方法只有框commons架调，自己不要随便调用
	 */
	@SuppressWarnings("rawtypes")
    public static void initMappingHandler(CatRequestMappingHandlerMapping mappingHandler) {
	    catRequestMappingHandlerMapping = mappingHandler;
	}
	
	/**
	 * 此方法只有框commons架调，自己不要随便调用
	 */
	public static HashSet<String> singletonUrlSet() {
		return urlSet;
	}
	
	/**
	 * 判断这个request的URI是否在controller的映射requestMapping中
	 * @param request
	 * @return
	 */
	public static boolean containsURI(HttpServletRequest request) {
		String lookupPath = getLookupPath(request);
//		return urlSet.contains(lookupPath);
		
		return catRequestMappingHandlerMapping.hasMatchedUrlRequestMapping(lookupPath, request);
	}
	
	/**
	 * 获得lookupPath 不含参数的路径(不包含域名信息,例如/test/name)
	 * @param request http请求
	 * @return
	 */
	private static String getLookupPath(HttpServletRequest request) {
		//注意urlPathHelper可以设定编码，是否去掉分号等，具体见它的javadoc文档
		return urlPathHelper.getLookupPathForRequest(request);
	}
	
}
