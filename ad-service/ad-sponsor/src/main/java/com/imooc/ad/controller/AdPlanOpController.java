package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 14:46
 * @description 广告计划的controller层操作 restFul请求 由于context-path: /ad-sponsor 索引有默认服务名称前缀
 */
@Slf4j
@RestController
public class AdPlanOpController {

    private final IAdPlanService planService;

    @Autowired
    public AdPlanOpController(IAdPlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/ad-plan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-pair: creator -> {}", JSON.toJSONString(request));

        return planService.createAdPlan(request);
    }

    @PostMapping("/ad-plans")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-pair: getByIds -> {}", JSON.toJSONString(request));

        return planService.getAdPlanByIds(request);
    }

    @PutMapping("/ad-plan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-pair: update -> {}", JSON.toJSONString(request));

        return planService.updateAdPlan(request);
    }

    @DeleteMapping("/ad-plan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-pair: delete -> {}", JSON.toJSONString(request));

        planService.deleteAdPlan(request);
    }

}
