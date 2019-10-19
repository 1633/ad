package com.imooc.ad.client;

import com.imooc.ad.client.hystrix.SponsorClintImpl;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 21:31
 * @description feign方式的接口类 基于接口调用对方服务
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClintImpl.class)
public interface SponsorClient {
    /**
     * 通过路径指定要调用的@FeignClient(value = "eureka-client-ad-sponsor")的/ad-sponsor/ad-plans对应的controller方法
     * 这个 getAdPlans 方法名与 被调用方的 方法名无需相同，而是通过path路径确立映射关系的
     *
     * @param request
     * @return
     */
    @PostMapping("/ad-sponsor/ad-plans")
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);

}
