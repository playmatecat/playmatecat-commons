package com.playmatecat.cas.ctrl;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 各子系统跳转cas服务器的通用控制类
 * @author blackcat
 *
 */
@Controller
@RequestMapping("/sub-sys")
public class SubSysCasController {
	
	private Properties casProps;
	
	@RequestMapping("/cas-login")
	public String casLogin(HttpServletRequest request, HttpServletResponse response) {
		SecurityUtils.getSubject().isPermitted("test:test");

		//获得跳转登录前访问的最后地址
		String lastUrl;
		if(WebUtils.getSavedRequest(request) == null) {
			lastUrl = "";
		} else {
			lastUrl = WebUtils.getSavedRequest(request).getRequestUrl();
			lastUrl = lastUrl == null ? "" : lastUrl;
		}

		if(casProps == null) {
			try {
				casProps = PropertiesLoaderUtils.loadAllProperties("/config/cas/cas.properties");
	        } catch (Exception e) {
		        e.printStackTrace();
	        }
		}
		
		
		if(casProps.isEmpty()) {
			return null;
		}
		
		String casServerUrl = casProps.getProperty("cas.server.url");
		
		//子系统的http(s)+域名
		String subSysSiteUrl = casProps.getProperty("cas.subsys.url");

		//跳转到cas请求登录验证
		String casUrl = "redirect:" + casServerUrl + "?url=" + subSysSiteUrl + lastUrl;

		return casUrl;
	}
}
