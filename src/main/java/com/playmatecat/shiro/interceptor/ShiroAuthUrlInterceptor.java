package com.playmatecat.shiro.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.expression.AccessException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.playmatecat.cas.domains.dto.UriResourceDto;
import com.playmatecat.cas.service.SubSysCasService;
import com.playmatecat.spring.exception.NotFoundPermException;
import com.playmatecat.spring.exception.NotFoundURIException;
import com.playmatecat.utils.spring.UtilsRequestMappingUrl;
import com.playmatecat.utils.spring.UtilsSpringContext;

/**
 * shiro对url进行动态鉴权，实现url权限判断功能
 * 如果没有权限或者未找到页面，会抛出对应异常，然后又spring全局异常拦截器处理，跳转到对应的页面
 * @author root
 *
 */
public class ShiroAuthUrlInterceptor implements HandlerInterceptor {
    
    private static NotFoundURIException notFoundURIException = new NotFoundURIException();
    
    private static NotFoundPermException notFoundPermException = new NotFoundPermException();
    
    /** 子系统鉴权服务 **/
    private SubSysCasService subSysCasService;
    
    
    /**
     * 预处理回调方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        //关于获得所有controller映射的思路，
        //参考dispatchr-mvc-servlet.xml文件,以及AbstractHandlerMethodMapping源码spring的第217行registerHandlerMethod方法
        
        
        //先判断是否存在这个映射url,不存在则redirect到404,并且返回false,不再执行下一个拦截器
        if(!UtilsRequestMappingUrl.containsURI(request)) {
            throw notFoundURIException;
        }
      
        //若db检查权限无法获得这个url资源，也就是没权限，那么返回403,并且返回false,不再执行下一个拦截器
        if(subSysCasService == null) {
            subSysCasService = (SubSysCasService) UtilsSpringContext.getBean("subSysCasService");
        }
        
        String currentPrincipal = SecurityUtils.getSubject().getPrincipals()
                .getPrimaryPrincipal().toString();
        
        Long userId = Long.valueOf(currentPrincipal);
        
        List<UriResourceDto> uriResourceList = subSysCasService.getUserUriResources(userId);
        
        
        if( 1 == 1) {
            throw notFoundPermException;
        }
        return true;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //do nothing
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //do nothing
    }

}
