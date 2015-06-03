package com.playmatecat.shiro.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.playmatecat.utils.spring.UtilsSpringContext;

public class ShiroAuthUrlInterceptor implements HandlerInterceptor {

    /**
     * 预处理回调方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        //关于获得所有controller映射的思路，
        //参考dispatchr-mvc-servlet.xml文件,以及AbstractHandlerMethodMapping源码spring的第217行registerHandlerMethod方法
        
        
        //先判断是否存在这个映射url,不存在则redirect到404,并且返回false,不再执行下一个拦截器
        //若db检查权限无法获得这个url资源，也就是没权限，那么返回403,并且返回false,不再执行下一个拦截器
        
        return false;
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
