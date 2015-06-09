package com.playmatecat.cas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playmatecat.cas.constants.LevelDictConstants;
import com.playmatecat.cas.domains.dto.PermissionDto;
import com.playmatecat.cas.domains.dto.RoleDto;
import com.playmatecat.cas.domains.dto.UriResourceDto;
import com.playmatecat.cas.domains.dto.UserLevelDto;
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
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
        params.put("userId", userId);
	    return subSysCasMapper.getUserRoles(params);
	}
	
	/**
	 * 获得某个用户的权限
	 * @param userId
	 * @return
	 */
	public List<PermissionDto> getUserPermissions(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
        params.put("userId", userId);
	    return subSysCasMapper.getUserPermissions(params);
	}
	
	/**
     * 获得某个用户的可访问的uri列表
     * @param userId
     * @return
     */
	public List<UriResourceDto> getRoleUriResources(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
		params.put("userId", userId);
		
	    return subSysCasMapper.getRoleUriResources(params);
	}
	
	/**
	 * 获得匿名用户的可访问的uri列表
	 * @return
	 */
	public List<UriResourceDto> getLevelUriResource(Integer level) {
	    Map<String,Object> params = new HashMap<String,Object>();
        String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
        params.put("level", level);
        
        return subSysCasMapper.getLevelUriResource(params);
	}
	
	
	/**
	 *  获得子系统用户的等级(不是等级id)
	 *  若不存在等级信息,则创建用户等级信息,并且将用户所属等级设置为1级(最低),游客(匿名访问者)等级为0
	 * @return
	 */
	public Integer getOrInitUserLevel(Long userId) {
	    
	    Integer rtnLevel;
	    
	    Map<String,Object> params = new HashMap<String,Object>();
	    String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        params.put("userId", userId);

	    Integer level = subSysCasMapper.getUserLevel(params);
	    
	    if(level == null) {
	        //若不存在等级信息,则创建用户等级信息,并且将用户所属等级设置为1级(最低)
	        Map<String,Object> newParams = new HashMap<String,Object>();
	        newParams.put("userId", userId);
	        newParams.put("subSysDatabase", subSysDatabase);
	        newParams.put("level", LevelDictConstants.NOVICE_LEVEL);
	        subSysCasMapper.addUserLevel(newParams);
	        rtnLevel = LevelDictConstants.NOVICE_LEVEL;
	    } else {
	        //返回用户拥有的等级
	        rtnLevel = level;
	    }
	    
	    return rtnLevel;
	}
	
	/**
	 * 获得某个用户的等级所对应的权限
	 * @param levelId
	 * @return
	 */
	public List<PermissionDto> getLevelPermissions(Integer level) {
	    Map<String,Object> params = new HashMap<String,Object>();
        String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
        params.put("level", level);
        
        return subSysCasMapper.getLevelPermissions(params);
	}
}
