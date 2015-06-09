package com.playmatecat.shiro.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.playmatecat.cas.constants.LevelDictConstants;
import com.playmatecat.cas.domains.dto.UriResourceDto;
import com.playmatecat.cas.service.SubSysCasService;
import com.playmatecat.spring.exception.NotFoundPermException;
import com.playmatecat.spring.exception.NotFoundURIException;
import com.playmatecat.utils.spring.UtilsRequestMappingUrl;
import com.playmatecat.utils.spring.UtilsSpringContext;

/**
 * shiro对url进行动态鉴权，实现url权限判断功能
 * 如果没有权限或者未找到页面，会抛出对应异常，然后又spring全局异常拦截器处理，跳转到对应的页面
 * @author root
 *
 */
public class ShiroAuthUrlInterceptor implements HandlerInterceptor {
    
    private static Logger logger = LogManager.getLogger("ShiroAuthUrlInterceptor");
	
	/**这个列表的uri会忽略**/
	private static List<String> ignoreUriList;
    
    private static NotFoundURIException notFoundURIException = new NotFoundURIException();
    
    private static NotFoundPermException notFoundPermException = new NotFoundPermException();
    
    /** 子系统鉴权服务 **/
    private static SubSysCasService subSysCasService;
    
    /**spring路径ANT通配 ?（匹配任何单字符），*（匹配0或者任意数量的字符），**（匹配0或者更多的目录）**/
    private static PathMatcher matcher = new AntPathMatcher();
    
    /**
     * 预处理回调方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	
        String requestPath = request.getRequestURI();
        
        //初始化服务类
        if(subSysCasService == null) {
            subSysCasService = (SubSysCasService) UtilsSpringContext.getBean("subSysCasService");
        }
    	
        //先判断是否在忽略略表中(一般对static资源路径忽略),若忽略则直接返回true调用下一个拦截器
        if(isIgnorePath(requestPath)) {
            return true;
        }
        
        //判断controller是否存在这个映射url,不存在则redirect到404,并且返回false,不再执行下一个拦截器
        if(!UtilsRequestMappingUrl.containsURI(request)) {
            throw notFoundURIException;
        }

        //如果始终未找到可以匹配的URI资源权限，那么认为没有权限访问这个请求的路径
        if(!hasAccessableUri(requestPath)) {
            throw notFoundPermException;
        }
        
        return true;
    }
    
    /**
     * 判断是否是拦截器忽略的uri路径
     * @param requestPath
     * @return
     */
    private static boolean isIgnorePath(String requestPath) {
        
        boolean isIgnorePath = false;
        
        //先判断是否在忽略略表中,若忽略则直接返回true调用下一个拦截器
        for(String peekIgnoreUri : ignoreUriList) {
            String patternPath = peekIgnoreUri;
            if(matcher.match(patternPath, requestPath)) {
                isIgnorePath = true;
                break;
            }
        }
        
        return isIgnorePath;
    }
    
    /**
     * 判断当前访问者(包括匿名)是否可以访问这个uri
     * @param requestPath
     * @return
     */
    private static boolean hasAccessableUri(String requestPath) throws Exception{
        boolean hasMatchedUri = false;
        

        //@STEP 获得游客等级所可以访问的匿名的URI
        //若有可以匿名访问这request URI,那么则return true
        if(hasMatchedAnonUri(requestPath)) {
            return true;
        }
        
        
        //@STEP 若已是是已登录用户(user级)
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        if(principals != null) {
            //判断是否有角色对应的权限的URI
            if(hasMatchedRoleUri(requestPath)){
                return true;
            }
            
            //判断用户拥有的等级是否有对应的URI权限
            if(hasMatchedLevelUri(requestPath)) {
                return true;
            }

        }
        
        return hasMatchedUri;
    }
    
    
    /**
     * 获得是否存在游客等级所可以访问的匿名的URI
     * @param requestPath
     */
    private static boolean hasMatchedAnonUri(String requestPath) {
        boolean isMatched = false;

        // @STEP 获得游客等级所可以访问的匿名的URI
        // 若有可以匿名访问这request URI,那么则return true
        List<UriResourceDto> anonUriResourceList = subSysCasService.getLevelUriResource(LevelDictConstants.ANONYMOUS_LEVEL);
        for (UriResourceDto peekUriResource : anonUriResourceList) {
            String patternPath = peekUriResource.getUriWildcard();
            isMatched = matcher.match(patternPath, requestPath);
            if (isMatched == true) {
                break;
            }
        }
        
        return isMatched;
    }
    
    /**
     * 判断是否有角色对应的权限的URI
     * @param requestPath
     * @return
     */
    private static boolean hasMatchedRoleUri(String requestPath) {
        boolean isMatched = false;
         
        if(SecurityUtils.getSubject().getPrincipals() != null) {
            //若已是是已登录用户(user级),那么查看用户所属等级对应的可见URI
            String currentPrincipal = SecurityUtils.getSubject().getPrincipals()
                    .getPrimaryPrincipal().toString();
            
            Long userId = Long.valueOf(currentPrincipal);
            
            //@STEP 获得用户角色的URI特殊权限
            List<UriResourceDto> uriResourceList = subSysCasService.getRoleUriResources(userId);
            
            //判断是否有URI特殊权限
            for(UriResourceDto peekUriResource : uriResourceList) {
                String patternPath = peekUriResource.getUriWildcard();
                isMatched = matcher.match(patternPath, requestPath);
                if(isMatched == true) {
                    break;
                }
            }
        }
       
        return isMatched;
    }
    
    /**
     * 判断用户拥有的等级是否有对应的URI权限
     * @param requestPath
     * @return
     * @throws Exception
     */
    private static boolean hasMatchedLevelUri(String requestPath) throws Exception{
        boolean isMatched = false;
        
        if(SecurityUtils.getSubject().getPrincipals() != null) {
            //若已是是已登录用户(user级),那么查看用户所属等级对应的可见URI
            String currentPrincipal = SecurityUtils.getSubject().getPrincipals()
                    .getPrimaryPrincipal().toString();
            Long userId = Long.valueOf(currentPrincipal);
        
            //@STEP 获得用户等级可以访问的权限
            // 获得子系统用户等级信息
            Integer level = subSysCasService.getOrInitUserLevel(userId);
            
            //若当前等级有可以访问这request URI,那么则return true
            List<UriResourceDto> levelUriResourceList 
                = subSysCasService.getLevelUriResource(level);
            for(UriResourceDto peekUriResource : levelUriResourceList) {
                String patternPath = peekUriResource.getUriWildcard();
                isMatched = matcher.match(patternPath, requestPath);
                if(isMatched == true) {
                    break;
                }
            }
        
        }
        
        return isMatched;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //do nothing
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //do nothing
    }

	public List<String> getIgnoreUriList() {
		return ignoreUriList;
	}

	public void setIgnoreUriList(List<String> ignoreUriList) {
		this.ignoreUriList = ignoreUriList;
	}

}
