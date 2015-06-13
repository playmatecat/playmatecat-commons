package com.playmatecat.spring.exception.aop;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import com.playmatecat.utils.json.UtilsJson;

/**
 * 通用AOP异常处理
 * 
 * @author blackcat
 *
 */
public class CommonExceptionAOPHandler implements ThrowsAdvice {
    
    private static Logger logger = LogManager.getLogger(CommonExceptionAOPHandler.class);
    
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Exception{
        String errorClass = target.getClass().getName();
        String errorMethod = method.getName();
        String argsJson = UtilsJson.parseObj2JsonStr(args);
        String errMsg = MessageFormat.format("错误信息:{0}, 类:{0}, 方法:{1}, 参数:{3}", 
                new Object[]{ex.getMessage(), errorClass, errorMethod, argsJson});
        logger.error(errMsg, ex);
        //throw new Exception(errMsg, ex);
    }
    
}
