package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitService;
import com.imooc.ad.vo.AdUnitDistrictRequest;
import com.imooc.ad.vo.AdUnitDistrictResponse;
import com.imooc.ad.vo.AdUnitItRequest;
import com.imooc.ad.vo.AdUnitItResponse;
import com.imooc.ad.vo.AdUnitKeywordRequest;
import com.imooc.ad.vo.AdUnitKeywordResponse;
import com.imooc.ad.vo.AdUnitRequest;
import com.imooc.ad.vo.AdUnitResponse;
import com.imooc.ad.vo.CreativeUnitRequest;
import com.imooc.ad.vo.CreativeUnitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seven
 * @date 2019/10/19 14:46
 * @description 广告单元的controller层操作 restFul请求 由于context-path: /ad-sponsor 索引有默认服务名称前缀
 */
@Slf4j
@RestController
public class AdUnitOpController {

    private final IAdUnitService unitService;

    @Autowired
    public AdUnitOpController(IAdUnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping("/ad-unit")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-unit: create -> {}", JSON.toJSONString(request));

        return unitService.createUnit(request);
    }

    @PostMapping("/ad-unit-keyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-unit-keyword: create -> {}", JSON.toJSONString(request));

        return unitService.createUnitKeyword(request);
    }

    @PostMapping("/ad-unit-it")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-unit-it: create -> {}", JSON.toJSONString(request));

        return unitService.createUnitIt(request);
    }

    @PostMapping("/ad-unit-district")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-unit-district: create -> {}", JSON.toJSONString(request));

        return unitService.createUnitDistrict(request);
    }

    @PostMapping("/ad-unit-creative")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-unit-creative: create -> {}", JSON.toJSONString(request));

        return unitService.createCreativeUnit(request);
    }

}
