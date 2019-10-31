package com.imooc.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author Seven
 * @date 2019/10/31 23:52
 * @description 监控启动类
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

}
