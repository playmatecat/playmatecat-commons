package com.playmatecat.cas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;
import com.playmatecat.cas.domains.dto.UriResourceDto;
import com.playmatecat.cas.mapper.SubSysCasMapper;

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
	    return subSysCasMapper.getUserRoles(userId);
	}
	
	/**
	 * 获得某个用户的权限
	 * @param userId
	 * @return
	 */
	public List<PermissionDto> getUserPermissions(Long userId) {
	    return subSysCasMapper.getUserPermissions(userId);
	}
	
	/**
     * 获得某个用户的可访问的uri列表
     * @param userId
     * @return
     */
	public List<UriResourceDto> getUserUriResources(Long userId) {
	    return subSysCasMapper.getUserUriResources(userId);
	}
}
