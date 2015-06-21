//package com.playmatecat.shiro.filter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.apache.commons.lang3.math.NumberUtils;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authz.AuthorizationFilter;
//
//import com.playmatecat.cas.service.SubSysCasService;
//import com.playmatecat.utils.spring.UtilsSpringContext;
//
///**
// * 过滤拦截URL请求，根据请求决定是否能够访问这个请求
// * @author root
// *
// */
//public class ShiroAuthUrlFilter extends AuthorizationFilter{
//
//    /**
//     * 子系统鉴权服务
//     */
//    private SubSysCasService subSysCasService;
//    
//    /**
//     * 在xml配置这个filter的bean时，你可以通过指定这个bean的unauthorizedUrl属性成员（见父类）,来指定跳转的url 
//     */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//        
//        boolean isAccessable = false;
//        
//        //思路subject.getprinciple获得用户id,走db,查出role和permisssion,再读取urls资源
//        Subject subject = getSubject(request, response);
//        String currentPrincipal = subject.getPreviousPrincipals().getPrimaryPrincipal().toString();
//        Long userId;
//        if(NumberUtils.isDigits(currentPrincipal)){
//            userId = Long.valueOf(currentPrincipal);
//        } else {
//            throw new Exception("用户认证信息身份信息必须为用户id");
//        }
//        
//        //从db获得该用户的角色、权限
//        if(subSysCasService == null) {
//            subSysCasService = (SubSysCasService) UtilsSpringContext.getBean("subSysCasService");
//        }
//        
//        //权限角色下无该URL，那么认为无权限访问这个URL
//        return false;
//    }
//   
//}
