package com.playmatecat.spring.mvc;

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.playmatecat.utils.spring.UtilsRequestMappingUrl;

/**
 * spring mvc的controller映射重新实现
 * @author blackcat
 *
 */
public class CatRequestMappingHandlerMapping extends RequestMappingHandlerMapping{

	@Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
		
		//把controller的requestMapping的url拼接结果放到工具类存储
		Set<String> patterns = getMappingPathPatterns(mapping);
		for (String pattern : patterns) {
			if (!getPathMatcher().isPattern(pattern)) {
				UtilsRequestMappingUrl.singletonUrlSet().add(pattern);
			}
		}
		
	    super.registerHandlerMethod(handler, method, mapping);
    }

}
