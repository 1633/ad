package com.imooc.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Seven
 * @Date 2019/10/17 22:09
 * @Description 配置类 这里目前是http的消息转换器 将java对象与json对象转换，即header头添加application/json
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 清除默认的转换器
        converters.clear();
        // 添加自定义的转换器
        converters.add(new MappingJackson2HttpMessageConverter());
    }

}
