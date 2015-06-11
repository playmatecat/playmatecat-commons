package com.playmatecat.mina.servletListener;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.playmatecat.commons.constants.PropertiesKeyConstants;
import com.playmatecat.mina.client.NioTcpClient2;
import com.playmatecat.utils.spring.UtilsProperties;

public class MinaClientContextListener implements ServletContextListener{
    
    private HashMap<String, NioTcpClient2> nioClientMap = new HashMap<String, NioTcpClient2>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("my listener !!!!!!!!!!!!!!!!!!!!");
        System.out.println(UtilsProperties.getProp(PropertiesKeyConstants.CAS_SUBSYS_SYS_DATABASE));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        
    }

}
