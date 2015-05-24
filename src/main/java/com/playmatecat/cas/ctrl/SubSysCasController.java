package com.playmatecat.cas.ctrl;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping("/cas-login")
	public String casLogin(HttpServletRequest request, HttpServletResponse response) {
		//获得跳转登录前访问的最后地址
		String lastUrl;
		if(WebUtils.getSavedRequest(request) == null) {
			lastUrl = "";
		} else {
			lastUrl = WebUtils.getSavedRequest(request).getRequestUrl();
			lastUrl = lastUrl == null ? "" : lastUrl;
		}

		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadAllProperties("/config/cas/cas.properties");
        } catch (Exception e) {
	        e.printStackTrace();
        }
		
		if(props.isEmpty()) {
			return null;
		}
		
		String casServerUrl = props.getProperty("cas.server.url");
		
		//跳转到cas请求登录验证
		String casUrl = "redirect:" + casServerUrl + "?url=" + lastUrl;
		return casUrl;
	}
}
