package com.playmatecat.shiro.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
	
	/**这个列表的uri会忽略**/
	private static List<String> ignoreUriList;
    
    private static NotFoundURIException notFoundURIException = new NotFoundURIException();
    
    private static NotFoundPermException notFoundPermException = new NotFoundPermException();
    
    /** 子系统鉴权服务 **/
    private SubSysCasService subSysCasService;
    
    
    /**
     * 预处理回调方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	//spring路径ANT通配
        //?（匹配任何单字符），*（匹配0或者任意数量的字符），**（匹配0或者更多的目录）
        PathMatcher matcher = new AntPathMatcher();
        String requestPath = request.getRequestURI();
    	
        //先判断是否在忽略略表中,若忽略则直接返回true调用下一个拦截器
        for(String peekIgnoreUri : ignoreUriList) {
            String patternPath = peekIgnoreUri;
            if(matcher.match(patternPath, requestPath)) {
                return true;
            }
        }
        

        //判断是否存在这个映射url,不存在则redirect到404,并且返回false,不再执行下一个拦截器
        if(!UtilsRequestMappingUrl.containsURI(request)) {
            throw notFoundURIException;
        }
      
        //若db检查权限无法获得这个url资源，也就是没权限，那么返回403,并且返回false,不再执行下一个拦截器
        if(subSysCasService == null) {
            subSysCasService = (SubSysCasService) UtilsSpringContext.getBean("subSysCasService");
        }
        
        
        
        boolean hasMatchedUri = false;
        //TODO 获得游客等级所可以访问的匿名的URI
        //若有课可以匿名访问这request URI,那么则return true
        
        
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        if(principals != null) {
            //若已是是已登录用户(user级),那么查看用户所属等级对应的可见URI
            String currentPrincipal = SecurityUtils.getSubject().getPrincipals()
                    .getPrimaryPrincipal().toString();
            
            Long userId = Long.valueOf(currentPrincipal);
            
            //获得用户角色的URI特殊权限
            List<UriResourceDto> uriResourceList = subSysCasService.getUserUriResources(userId);
            
            
            
            
            //TODO 判断用户等级表中是否存在该用户,若不存在则创建相关信息,并且将用户所属等级设置为1级(最低)
            //若存在等级表中存在该用户,那么抽取他的等级所对应的URI资源
            
            
            //判断是否有URI特殊权限
            for(UriResourceDto peekUriResource : uriResourceList) {
                String patternPath = peekUriResource.getUriWildcard();
                hasMatchedUri = matcher.match(patternPath, requestPath);
                if(hasMatchedUri == true) {
                    break;
                }
            }
        }
       
        
        //如果始终未找到可以匹配的URI资源，那么认为没有权限访问这个请求的路径
        if(!hasMatchedUri) {
            throw notFoundPermException;
        }
        
        return true;
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
