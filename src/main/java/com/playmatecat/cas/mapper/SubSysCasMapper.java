package com.playmatecat.cas.mapper;

import java.util.List;
import java.util.Map;

import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;
import com.playmatecat.cas.domains.dto.UriResourceDto;

/**
 * cas mybatis接口
 * @author blackcat
 *
 */
public interface SubSysCasMapper {
	
	/**
	 * 获得某个用户的角色
	 * @param params
	 * @return
	 */
	public List<RoleDto> getUserRoles(Map<String,Object> params);
	
	/**
	 * 获得某个用户的权限
	 * @param params
	 * @return
	 */
	public List<PermissionDto> getUserPermissions(Map<String,Object> params);
	
	/**
	 * 获得某个用户的可访问的uri列表
	 * @param params
	 * @return
	 */
	public List<UriResourceDto> getUserUriResources(Map<String,Object> params);
}
