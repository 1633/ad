package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IUserService;
import com.imooc.ad.vo.CreateUserRequest;
import com.imooc.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seven
 * @date 2019/10/19 14:46
 * @description 广告投放主的controller层操作 restFul请求 由于context-path: /ad-sponsor 索引有默认服务名称前缀
 */
@Slf4j
@RestController
public class UserOpController {

    private final IUserService userService;

    @Autowired
    public UserOpController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor: creator -> {}", JSON.toJSONString(request));

        return userService.createUser(request);
    }

}
