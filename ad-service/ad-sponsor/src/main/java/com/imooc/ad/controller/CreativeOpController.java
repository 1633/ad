package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.ICreativeService;
import com.imooc.ad.vo.CreativeRequest;
import com.imooc.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seven
 * @date 2019/10/19 14:46
 * @description 创意的controller层操作 restFul请求 由于context-path: /ad-sponsor 索引有默认服务名称前缀
 */
@Slf4j
@RestController
public class CreativeOpController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOpController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {
        log.info("creative: create -> {}", JSON.toJSONString(request));

        return creativeService.createCreative(request);
    }

}
