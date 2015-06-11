package com.playmatecat.mina.servletListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.playmatecat.commons.constants.PropertiesKeyConstants;
import com.playmatecat.mina.client.NioTcpClient2;
import com.playmatecat.utils.mina.UtilsNioClient2;
import com.playmatecat.utils.spring.UtilsProperties;

/**
 * 初始化client端的mina服务
 * @author blackcat
 *
 */
public class MinaClientContextListener implements ServletContextListener{
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获得mina address配置参数
        String minaServerAddr = UtilsProperties.getProp(PropertiesKeyConstants.MINA_SERVICE_ADDRESS_LIST);
        String[] serverAddrArray = minaServerAddr.split(",");
        List<String> serverList = Arrays.asList(serverAddrArray);
        
        HashMap<String, NioTcpClient2> minaServiceMap = new HashMap<String, NioTcpClient2>();
        
        //分解读取参数
        serverList.stream().forEach(peekServer -> {
            String[] ServerArgs = peekServer.split(":");
            String keyName = ServerArgs[0];
            String address = ServerArgs[1];
            Integer port = Integer.valueOf(ServerArgs[2]);
            
            NioTcpClient2 nioTcpClient = new NioTcpClient2(address, port);
            minaServiceMap.put(keyName, nioTcpClient);
        });
        
        UtilsNioClient2.initNioServiceMap(minaServiceMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        
    }

}
