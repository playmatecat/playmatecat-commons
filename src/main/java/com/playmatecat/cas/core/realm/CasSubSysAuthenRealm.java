package com.playmatecat.cas.core.realm;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.playmatecat.cas.service.SubSysCasService;
import com.playmatecat.utils.spring.UtilsSpringContext;

/**
 * 子系统鉴权授权类
 * 每个子系统进行自己的鉴权
 * @author blackcat
 *
 */
public class CasSubSysAuthenRealm extends AuthorizingRealm {
	
	/**
	 * 子系统鉴权服务
	 */
	private SubSysCasService subSysCasService;

	/**
	 * 鉴权、授权
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo authorizationInfo = null;

		Object primaryPrincipal = principals.getPrimaryPrincipal();
		if(primaryPrincipal != null) {
			String currentPrincipal = principals.getPrimaryPrincipal().toString();
			if(NumberUtils.isDigits(currentPrincipal)){
				Long userId = Long.valueOf(currentPrincipal);
				
				//从db获得该用户的角色、权限
				if(subSysCasService == null) {
					subSysCasService = (SubSysCasService) UtilsSpringContext.getBean("subSysCasService");
				}
				
				subSysCasService.say();
				
				//把角色和权限信息写入用户信息
				authorizationInfo = new SimpleAuthorizationInfo();
//				authorizationInfo.addStringPermission("?module:?permission");
//				authorizationInfo.addRole("?role:?childRole");
			}
		}
		
	    return authorizationInfo;
    }
	
	/**
	 * 登录认证又CAS服务器处理,理论上子系统不做登录认证任何事
	 */
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	    // do nothing
	    return null;
    }

}
