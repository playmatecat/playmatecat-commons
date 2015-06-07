package com.playmatecat.cas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;
import com.playmatecat.cas.domains.dto.UriResourceDto;
import com.playmatecat.cas.mapper.SubSysCasMapper;
import com.playmatecat.utils.spring.UtilsProperties;

/**
 * 子系统服务类,除了controller之外,子系统鉴权授权也会调用它
 * @author Isa
 *
 */
@Service(value="subSysCasService")
public class SubSysCasService {
    
    @Autowired
    private SubSysCasMapper subSysCasMapper;
    
    
	public void say() {
		System.out.println("子系统鉴权限！！！");
	}
	
	/**
	 * 获得某个用户的角色
	 * @param userId
	 * @return
	 */
	public List<RoleDto> getUserRoles(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
		params.put("subSysDatabase", subSysDatabase);
	    return subSysCasMapper.getUserRoles(params);
	}
	
	/**
	 * 获得某个用户的权限
	 * @param userId
	 * @return
	 */
	public List<PermissionDto> getUserPermissions(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
		params.put("subSysDatabase", subSysDatabase);
	    return subSysCasMapper.getUserPermissions(params);
	}
	
	/**
     * 获得某个用户的可访问的uri列表
     * @param userId
     * @return
     */
	public List<UriResourceDto> getUserUriResources(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
		params.put("subSysDatabase", subSysDatabase);
		
	    return subSysCasMapper.getUserUriResources(params);
	}
}
