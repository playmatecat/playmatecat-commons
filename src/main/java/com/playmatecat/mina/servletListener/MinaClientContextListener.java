package com.playmatecat.mina.servletListener;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.playmatecat.commons.constants.PropertiesKeyConstants;
import com.playmatecat.mina.client.NioTCPClient;
import com.playmatecat.utils.mina.UtilsNioClient;
import com.playmatecat.utils.spring.UtilsProperties;


/**
 * 初始化client端的mina服务
 * @author blackcat
 *
 */
public class MinaClientContextListener implements ServletContextListener{
    
    private static Logger logger = LogManager.getLogger(MinaClientContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获得mina address配置参数
        String minaServerAddr = UtilsProperties.getProp(PropertiesKeyConstants.MINA_SERVICE_ADDRESS_LIST);
        String[] serverAddrArray = minaServerAddr.split(",");
        List<String> serverList = Arrays.asList(serverAddrArray);
        
        HashMap<String, NioTCPClient> minaServiceMap = new HashMap<String, NioTCPClient>();
        
        //分解读取参数
        serverList.stream().forEach(peekServer -> {
            String[] ServerArgs = peekServer.split(":");
            String keyName = ServerArgs[0];
            String address = ServerArgs[1];
            Integer port = Integer.valueOf(ServerArgs[2]);
            
            //创建mina实例
            NioTCPClient nioTcpClient = new NioTCPClient(address, port);
            //存入放入到工具类
            logger.info(MessageFormat.format("成功初始化   mina service client:{0} on {1}:{2}", new Object[]{keyName,address,port.toString()}));
            minaServiceMap.put(keyName, nioTcpClient);
        });
        
        UtilsNioClient.initNioServiceMap(minaServiceMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        UtilsNioClient.readNioServiceMap().forEach(UtilsNioClient::destoryClient);
    }

}
