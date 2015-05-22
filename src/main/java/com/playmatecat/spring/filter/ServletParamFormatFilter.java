package com.playmatecat.spring.filter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
/**
 * @deprecated 无法实现,servlet不许修改参数值
 */
//public class ServletParamFormatFilter implements Filter{
//
//	@Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//	    // do nothing
//    }
//
//	@Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//	   
//		List<String> paramNameList = new ArrayList<String>();
//		@SuppressWarnings("rawtypes")
//        Enumeration enums = request.getParameterNames();
//		while(enums.hasMoreElements()) {
//			String peekReqAttrName = (String) enums.nextElement();
//			paramNameList.add(peekReqAttrName);
//		}
//		
//		for(String peekAttrName : paramNameList) {
//			String tmpRequestObjStr = request.getParameter(peekAttrName);
//			request.setAttribute(camelName(peekAttrName), tmpRequestObjStr);
//			request.removeAttribute(peekAttrName);
//
//		}
//		
//		chain.doFilter(request, response);
//    }
//
//	@Override
//    public void destroy() {
//		// do nothing
//    }
//	
//	
//	/**
//	 * 下划线命名转驼峰命名
//	 * @param name
//	 * @return
//	 */
//	private static String camelName(String name) {
//	    StringBuilder result = new StringBuilder();
//	    // 快速检查
//	    if (name == null || name.isEmpty()) {
//	        // 没必要转换
//	        return "";
//	    } else if (!name.contains("_")) {
//	        // 不含下划线，仅将首字母小写
//	        return name.substring(0, 1).toLowerCase() + name.substring(1);
//	    }
//	    // 用下划线将原始字符串分割
//	    String camels[] = name.split("_");
//	    for (String camel :  camels) {
//	        // 跳过原始字符串中开头、结尾的下换线或双重下划线
//	        if (camel.isEmpty()) {
//	            continue;
//	        }
//	        // 处理真正的驼峰片段
//	        if (result.length() == 0) {
//	            // 第一个驼峰片段，全部字母都小写
//	            result.append(camel.toLowerCase());
//	        } else {
//	            // 其他的驼峰片段，首字母大写
//	            result.append(camel.substring(0, 1).toUpperCase());
//	            result.append(camel.substring(1).toLowerCase());
//	        }
//	    }
//	    return result.toString();
//	}
//
//}
