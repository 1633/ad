package com.imooc.ad.client.hystrix;

import com.imooc.ad.client.SponsorClient;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 21:47
 * @description 熔断方法 被调用方服务失败时指定返回该熔断方法
 */
@Component("sponsorClintImpl")
public class SponsorClintImpl implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "something wrong when invoke ad-sponsor service.");
    }

}
