package com.playmatecat.spring.exception;

/**
 * 未找到权限异常
 * 当权限拦截器发现用户没有访问某个URL的权限时会抛出这个异常
 * @see ShiroAuthUrlInterceptor
 * @author blackcat
 *
 */
public class NotFoundPermException extends Exception{

    private static final long serialVersionUID = 2920620909247811920L;

}
