package com.playmatecat.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <pre>
 * spring上下文工具,用于获得spring上下文
 * 需要在xml中配置一下这个类才能启用
 * bean class="com.playmatecat.utils.spring.UtilsSpringContext" id = "UtilsSpringContext"
 * spring会自动注入这个类
 * </pre>
 * @author blackcat
 *
 */
public class UtilsSpringContext implements ApplicationContextAware {

	@Autowired
	private static ApplicationContext applicationContext;
	
	public static Object getBean(String beanName){
//		applicationContext.getBeanDefinitionNames();
		Object obj = null;
		try {
			obj = applicationContext.getBean(beanName);
        } catch (Exception e) {
	        //do nothing
        }

		return obj;		
	}

	public static <T> T getBean(Class<T> clazz){
		T instance = null;
		try {
			instance = (T) applicationContext.getBean(clazz);
        } catch (Exception e) {
	        //do nothing
        }
		return instance;		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		UtilsSpringContext.applicationContext=applicationContext;
	}
	
	public static ClassLoader getClassLoader() {
	    return applicationContext.getClassLoader();
	}
}
