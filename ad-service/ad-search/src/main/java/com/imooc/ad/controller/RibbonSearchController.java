package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 21:05
 * @description restTemplate的controller类 用的ribbon方式(复杂的&过时的，至少使用基于ribbon的feign)
 */
@Slf4j
@RestController
public class RibbonSearchController {

    private final RestTemplate restTemplate;

    @Autowired
    public RibbonSearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/ad-plans/ribbon")
    public CommonResponse<List<AdPlan>> getAdPlanByRibbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: ribbon method to test -> {}", JSON.toJSONString(request));

        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/ad-plans",
                request, CommonResponse.class).getBody();
    }

}
