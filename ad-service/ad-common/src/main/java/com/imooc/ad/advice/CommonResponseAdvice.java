package com.imooc.ad.advice;

import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Seven
 * @Date 2019/10/17 21:39
 * @Description 统一返回类的增强，将返回对象包装成CommonResponse结构的对象
 */
@RestControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 响应是否需要支持拦截 由参数确认 主要是自己定义的自定义注解 @IgnoreResponseAdvice
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断 类与方法 上是否有要忽略的自定义注解
        if (!returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return true;
        }
        if (!returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return true;
        }

        return false;
    }

    /**
     * 在写回之前进行拦截并操作
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Nullable
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        CommonResponse<Object> returnResponse = new CommonResponse<>(0, "");

        // 判断传入的body是否为已经包装的CommonResponse类，分情况返回
        if (null == body) {
            return returnResponse;
        } else if (body instanceof CommonResponse) {
            returnResponse = (CommonResponse<Object>) body;
        } else {
            returnResponse.setMessage("请求处理成功~");
            returnResponse.setData(body);
        }

        return returnResponse;
    }

}
