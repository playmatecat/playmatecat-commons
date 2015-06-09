package com.playmatecat.cas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    /**初级最低等级**/
    private final static long NOVICE_LEVEL = 1L;
    
    /**匿名等级**/
    private final static long ANONYMOUS_LEVEL = 0L;
    
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
	public List<UriResourceDto> getUserUriResources(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
		params.put("userId", userId);
		
	    return subSysCasMapper.getUserUriResources(params);
	}
	
	/**
	 * 获得匿名用户的可访问的uri列表
	 * @return
	 */
	public List<UriResourceDto> getAnonymousUriResource() {
	    Map<String,Object> params = new HashMap<String,Object>();
        String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
        params.put("level", ANONYMOUS_LEVEL);
        
        return subSysCasMapper.getAnonymousUriResource(params);
	}
	
	
	/**
	 *  获得子系统用户等级信息id
	 *  若不存在等级信息,则创建用户等级信息,并且将用户所属等级设置为1级(最低),游客(匿名访问者)等级为0
	 * @return
	 */
	public Long getOrInitUserLevelId(Long userId) throws Exception {
	    
	    Long rtnLevelId;
	    
	    Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId", userId);
        
        String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
	    Long levelId = subSysCasMapper.getUserLevelId(params);
	    
	    if(levelId == null) {
	        //若不存在等级信息,则创建用户等级信息,并且将用户所属等级设置为1级(最低)
	        //获得系统最低等级的等级id
	        Map<String,Object> levelDictParams = new HashMap<String,Object>();
	        levelDictParams.put("subSysDatabase", subSysDatabase);
	        
	        levelDictParams.put("level", NOVICE_LEVEL);
	        Long lowestLevelId = subSysCasMapper.getLevelDictId(levelDictParams);
	        if(lowestLevelId == null) {
	            throw new Exception("子系统不存在等级表,或者不存在等级为1的等级");
	        }
	        
	        //将用户设置为最低等级,写入用户等级表
	        params.put("levelId", lowestLevelId);
	        subSysCasMapper.addUserLevel(params);
	        rtnLevelId = lowestLevelId;
	    } else {
	        rtnLevelId = levelId;
	    }
	    
	    return rtnLevelId;
	}
	
	/**
	 * 获得某个用户的等级所对应的权限
	 * @param levelId
	 * @return
	 */
	public List<PermissionDto> getLevelPermissions(Long levelId) {
	    Map<String,Object> params = new HashMap<String,Object>();
        String subSysDatabase = UtilsProperties.getProp("cas.subsys.sys.database");
        params.put("subSysDatabase", subSysDatabase);
        
        params.put("levelId", levelId);
        
        return subSysCasMapper.getLevelPermissions(params);
	}
}
