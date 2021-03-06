package com.playmatecat.cas.mapper;

import java.util.List;
import java.util.Map;

import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;
import com.playmatecat.cas.domains.dto.UriResourceDto;
import com.playmatecat.cas.domains.dto.UserLevelDto;

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
	 * 获得某个用户的等级所对应的权限
	 * @param levelId
	 * @return
	 */
	public List<PermissionDto> getLevelPermissions(Map<String,Object> params);
	
	/**
	 * 获得某个用户拥有的角色可访问的uri列表
	 * @param params
	 * @return
	 */
	public List<UriResourceDto> getRoleUriResources(Map<String,Object> params);
	
	/**
	 * 获得某个等级用户的可访问的uri列表
	 * @param params
	 * @return
	 */
	public List<UriResourceDto> getLevelUriResource(Map<String,Object> params);
	
	/**
	 * 获得某个用户的用户等级
	 * @param params
	 * @return
	 */
	public Integer getUserLevel(Map<String,Object> params);
	
	/**
	 * 添加某个用户的用户等级
	 * @param userLevelDto
	 * @return
	 */
	public int addUserLevel(Map<String,Object> params);

}
