package com.imooc.ad.advice;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Seven
 * @Date 2019/10/17 22:02
 * @Description 统一的异常处理类 与CommonResponseAdvice 同样的类上注解
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 通过value 指定要处理的异常 是一个数组的value值 返回message与异常信息
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException ex) {
        CommonResponse<String> commonResponse = new CommonResponse<>(-1, "Business error!");
        commonResponse.setData(ex.getMessage());
        return commonResponse;
    }

}
