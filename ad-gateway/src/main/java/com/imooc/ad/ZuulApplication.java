package com.imooc.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Seven
 * @Date 2019/10/16 23:11
 * @Description 路由网关服务的服务启动类 用注解@EnableZuulProxy标识，且作为客户端要注册到Eureka服务中，
 * 用组合注解@SpringCloudApplication标识
 */
@SpringCloudApplication
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
