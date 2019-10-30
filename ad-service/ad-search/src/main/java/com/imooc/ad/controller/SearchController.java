package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.client.SponsorClient;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.search.ISearch;
import com.imooc.ad.search.vo.SearchRequest;
import com.imooc.ad.search.vo.SearchResponse;
import com.imooc.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 21:05
 * @description feign方式基于接口 调用对方服务
 */
@Slf4j
@RestController
public class SearchController {

    private final SponsorClient sponsorClient;
    private final ISearch search;

    /**
     * 没有识别到自动注入的，运行没有异常
     *
     * @param sponsorClient
     */
    @Autowired
    public SearchController(SponsorClient sponsorClient, ISearch search) {
        this.sponsorClient = sponsorClient;
        this.search = search;
    }

    @IgnoreResponseAdvice
    @PostMapping("/ad-plans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: feign method to test -> {}", JSON.toJSONString(request));

        return sponsorClient.getAdPlans(request);
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }

}
