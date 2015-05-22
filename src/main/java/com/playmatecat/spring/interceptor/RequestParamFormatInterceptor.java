package com.playmatecat.spring.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 对请求参数进行拦截,下划线转成驼峰
 * 
 * @author blackcat
 * @deprecated 使用过滤器
 */
public class RequestParamFormatInterceptor extends HandlerInterceptorAdapter {

    // /**
    // * 重写派发到springMVC DispatcherServlet之前的预处理
    // */
    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse
    // response, Object handler) throws Exception {
    //
    // List<String> paramNameList = new ArrayList<String>();
    // @SuppressWarnings("rawtypes")
    // Enumeration enums = request.getParameterNames();
    // while(enums.hasMoreElements()) {
    // String peekReqAttrName = (String) enums.nextElement();
    // paramNameList.add(peekReqAttrName);
    // }
    //
    // for(String peekAttrName : paramNameList) {
    // Object tmpRequestObj = request.getAttribute(peekAttrName);
    // request.removeAttribute(peekAttrName);
    // request.setAttribute(camelName(peekAttrName), tmpRequestObj);
    // }
    //
    // return super.preHandle(request, response, handler);
    // }
    //
    // /**
    // * 下划线命名转驼峰命名
    // * @param name
    // * @return
    // */
    // private static String camelName(String name) {
    // StringBuilder result = new StringBuilder();
    // // 快速检查
    // if (name == null || name.isEmpty()) {
    // // 没必要转换
    // return "";
    // } else if (!name.contains("_")) {
    // // 不含下划线，仅将首字母小写
    // return name.substring(0, 1).toLowerCase() + name.substring(1);
    // }
    // // 用下划线将原始字符串分割
    // String camels[] = name.split("_");
    // for (String camel : camels) {
    // // 跳过原始字符串中开头、结尾的下换线或双重下划线
    // if (camel.isEmpty()) {
    // continue;
    // }
    // // 处理真正的驼峰片段
    // if (result.length() == 0) {
    // // 第一个驼峰片段，全部字母都小写
    // result.append(camel.toLowerCase());
    // } else {
    // // 其他的驼峰片段，首字母大写
    // result.append(camel.substring(0, 1).toUpperCase());
    // result.append(camel.substring(1).toLowerCase());
    // }
    // }
    // return result.toString();
    // }
}
