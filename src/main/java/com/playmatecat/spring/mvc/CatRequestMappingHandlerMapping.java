package com.playmatecat.spring.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.playmatecat.utils.spring.UtilsRequestMappingUrl;

/**
 * spring mvc的controller映射重新实现
 * 提供hasMatchedUrlRequestMapping函数,用于获得controller中的requestMapping是否存在这个URI
 * 
 * 
 * 注意必须配置在mvc-dispatcher.xml使用CatRequestMappingHandlerMapping来代替spring原有的RequestMappingHandlerMapping
 * <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
 * 替换成
 * <bean class="xxx.CatRequestMappingHandlerMapping">
 * @author blackcat
 *
 */
public class CatRequestMappingHandlerMapping<T> extends RequestMappingHandlerMapping{
    
    private final static Logger logger = Logger.getLogger(CatRequestMappingHandlerMapping.class);
    
    private final MultiValueMap<String, T> urlMap = new LinkedMultiValueMap<String, T>();
    
    private Map<T, HandlerMethod> handlerMethods;
    
    @SuppressWarnings("unchecked")
    public CatRequestMappingHandlerMapping() {
        UtilsRequestMappingUrl.initMappingHandler(this);
        
        //获得抽象方法的对象
        handlerMethods = (Map<T, HandlerMethod>) getHandlerMethods();
    }

	@SuppressWarnings("unchecked")
    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
	    
	    
	    HandlerMethod newHandlerMethod = createHandlerMethod(handler, method);
        HandlerMethod oldHandlerMethod = this.handlerMethods.get(mapping);
        if (oldHandlerMethod != null && !oldHandlerMethod.equals(newHandlerMethod)) {
            throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + newHandlerMethod.getBean() +
                    "' bean method \n" + newHandlerMethod + "\nto " + mapping + ": There is already '" +
                    oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
        }

        //放入到自定义类的成员urlMap中,用于匹配是否存在URI的requestMapping
        Set<String> patterns = getMappingPathPatterns(mapping);
        for (String pattern : patterns) {
            if (!getPathMatcher().isPattern(pattern)) {
                this.urlMap.add(pattern, (T) mapping);
            }
        }

		//把controller的requestMapping的url拼接结果放到工具类存储
		for (String pattern : patterns) {
			if (!getPathMatcher().isPattern(pattern)) {
				UtilsRequestMappingUrl.singletonUrlSet().add(pattern);
			}
		}
		
	    super.registerHandlerMethod(handler, method, mapping);
    }

   
    /**
     * 匹配url与controller的requestMapping中的表达式
     * @param info
     * @param request
     * @return
     */
    public boolean hasMatchedUrlRequestMapping(String lookupPath, HttpServletRequest request) {
        try {
            List<Match> matches = new ArrayList<Match>();
            List<T> directPathMatches = this.urlMap.get(lookupPath);
            if (directPathMatches != null) {
                addMatchingMappings(directPathMatches, matches, request);
            }
            if (matches.isEmpty()) {
                // No choice but to go through all mappings...
                addMatchingMappings(this.handlerMethods.keySet(), matches, request);
            }

            if (matches.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("匹配controller的requestMapping发生异常", e);
            return false;
        }
        
    }
	 
	
    /**
     * 重新写了一下spring的matching
     * @param mappings
     * @param matches
     * @param request
     */
    @SuppressWarnings("unchecked")
    private void addMatchingMappings(Collection<T> mappings, List<Match> matches, HttpServletRequest request) {
        for (T mapping : mappings) {
            T match = (T) getMatchingMapping((RequestMappingInfo) mapping, request);
            if (match != null) {
                matches.add(new Match(match, this.handlerMethods.get(mapping)));
            }
        }
    }
	 

	 
	  /**
	     * A thin wrapper around a matched HandlerMethod and its mapping, for the purpose of
	     * comparing the best match with a comparator in the context of the current request.
	     */
	    private class Match {

	        private final T mapping;

	        public Match(T mapping, HandlerMethod handlerMethod) {
	            this.mapping = mapping;
	        }

	        @Override
	        public String toString() {
	            return this.mapping.toString();
	        }
	    }


}
