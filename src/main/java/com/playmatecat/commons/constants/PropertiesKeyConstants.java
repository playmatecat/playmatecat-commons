package com.playmatecat.commons.constants;

import com.playmatecat.mina.servletListener.MinaClientContextListener;

/**
 * 通用的参数key
 * @author blackcat
 *
 */
public interface PropertiesKeyConstants {
    
    /**子系统是否拥有用户等级这个概念,是否开启用户等级**/
    public final static String CAS_SUBSYS_USER_LEVEL_TOGGLE = "cas.subsys.user.level.toggle";

    /**鉴权服务器的登录验证url**/
    public final static String CAS_SERVER_URL = "cas.server.url";
    
    /**子系统的权限数据库名**/
    public final static String CAS_SUBSYS_SYS_DATABASE = "cas.subsys.sys.database";
    
    /**mina服务器列表(包括端口),Key名:IP:端口,格式例如: customName:127.0.0.1:8501,逗号分割
     * @see MinaClientContextListener#contextInitialized(javax.servlet.ServletContextEvent)**/
    public final static String MINA_SERVICE_ADDRESS_LIST = "mina.service.address.list";
}
