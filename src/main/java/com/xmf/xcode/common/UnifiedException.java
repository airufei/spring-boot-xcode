package com.xmf.xcode.common;

import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * 统一异常处理
 * 2018-09-09 15:46
 *
 * @auther airufei
 * @return
 */
@SuppressWarnings("all")
@ControllerAdvice
public class UnifiedException {
    private static Logger logger = LoggerFactory.getLogger(UnifiedException.class);
    @Value("${spring.application.name}")
    private String serviceName;

    @ExceptionHandler({Exception.class, Throwable.class, Error.class, IOException.class, RuntimeException.class})
    @ResponseBody
    RetData handleException(Throwable e, HttpServletRequest request, HttpServletResponse response){
        RetData mobileData  = new RetData();;
        Map<String, Object> data = null;
        String message = ResultCodeMessage.EXCEPTION_MESSAGE;
        mobileData.setCode(ResultCodeMessage.FAILURE);
        mobileData.setMessage(message);
        mobileData.setData(data);
        logger.error(StringUtil.getExceptionMsg(e));

        return mobileData;
    }

}
