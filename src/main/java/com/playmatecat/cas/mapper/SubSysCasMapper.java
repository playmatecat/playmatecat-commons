package com.playmatecat.cas.mapper;

import java.util.List;

import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;

/**
 * cas mybatis接口
 * @author blackcat
 *
 */
public interface SubSysCasMapper {
	public int doSomething(int id);
	
	/**
	 * 获得某个用户的角色
	 * @return
	 */
	public List<RoleDto> getUserRoles(Long userId);
	
	/**
	 * 获得某个用户的权限
	 * @param userId
	 * @return
	 */
	public List<PermissionDto> getUserPermissions(Long userId);
}
