package com.gooalgene.wutbiolab.exception;

import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class WutbiolabExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handler(Exception e, HttpServletRequest request, HandlerMethod handlerMethod) {
        if (e instanceof WutbiolabException) {
            WutbiolabException wutbiolabException=(WutbiolabException)e;
            return ResponseUtil.error(wutbiolabException.getMessage());
        }else {
            if(log.isErrorEnabled()){
                log.error("【未捕获错误】,错误接口：{},错误方法：{}",request.getRequestURI(),handlerMethod.getMethod().getName(), e);
            }else {
                log.error("【未捕获错误】", e);
            }
            return ResponseUtil.error(e.getMessage());
        }
    }
}
