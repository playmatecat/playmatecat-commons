package com.playmatecat.cas.core.realm;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;
import com.playmatecat.cas.service.SubSysCasService;
import com.playmatecat.commons.constants.PropertiesKeyConstants;
import com.playmatecat.utils.spring.UtilsProperties;
import com.playmatecat.utils.spring.UtilsSpringContext;

/**
 * 子系统鉴权授权类
 * 每个子系统进行自己的鉴权
 * @author blackcat
 *
 */
public class CasSubSysAuthenRealm extends AuthorizingRealm {
    
    private static Logger logger = LogManager.getLogger("CasSubSysAuthenRealm");
	
    /**子系统有用用户等级表=true**/
    private final static String USER_LEVEL_OPEN = "true";
    
	/** 子系统鉴权服务 **/
    private SubSysCasService subSysCasService;

	/**
	 * 鉴权、授权
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = null;

        Object primaryPrincipal = principals.getPrimaryPrincipal();
        if (primaryPrincipal != null) {
            String currentPrincipal = principals.getPrimaryPrincipal().toString();
            // 判断存入的是否是用户数字id
            if (NumberUtils.isDigits(currentPrincipal)) {
                Long userId = Long.valueOf(currentPrincipal);

                // 从db获得该用户的角色、权限
                if (subSysCasService == null) {
                    subSysCasService = (SubSysCasService) UtilsSpringContext.getBean("subSysCasService");
                }

                // 把角色和权限信息写入用户信息
                authorizationInfo = new SimpleAuthorizationInfo();

                List<RoleDto> roleList = subSysCasService.getUserRoles(userId);
                for (RoleDto peekRole : roleList) {
                    authorizationInfo.addRole(peekRole.getCode());
                }

                List<PermissionDto> permissionList = subSysCasService.getUserPermissions(userId);
                for (PermissionDto peekPermission : permissionList) {
                    authorizationInfo.addStringPermission(peekPermission.getCode());
                }

                // 若子系统拥有等级表,尝试去获得用户所属等级对应的权限
                if (UtilsProperties.getProp(PropertiesKeyConstants.CAS_SUBSYS_USER_LEVEL_TOGGLE).equals(USER_LEVEL_OPEN)) {
                    // 获得子系统用户等级信息
                    // 若不存在等级信息,则创建用户等级信息,并且将用户所属等级设置为1级(最低)
                    Integer level = subSysCasService.getOrInitUserLevel(userId);
                    
                    // 获得等级所对应的权限,将权限加入到用户的权限中
                    if(level != null) {
                        List<PermissionDto> levelPermList = subSysCasService.getLevelPermissions(level);
                        for (PermissionDto peekLevelPerm : levelPermList) {
                            authorizationInfo.addStringPermission(peekLevelPerm.getCode());
                        }
                    }
                }

                subSysCasService.say();

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
