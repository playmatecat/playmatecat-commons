package com.playmatecat.cas.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 各子系统跳转cas服务器的通用控制类
 * @author blackcat
 *
 */
@Controller
@RequestMapping("/sub-sys")
public class SubSysLoginController {
	
	@RequestMapping("/cas-login")
	public String casLogin(HttpServletRequest request, HttpServletResponse response) {
		//获得跳转登录前访问的最后地址
		String lastUrl = WebUtils.getSavedRequest(request).getRequestUrl();
		
		//跳转到cas请求登录验证
		String casUrl = "cas server loginUrl" + "?url=" + lastUrl;
		return casUrl;
	}
}
