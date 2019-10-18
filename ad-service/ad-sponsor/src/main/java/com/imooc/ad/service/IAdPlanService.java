package com.imooc.ad.service;

import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 1:47
 * @description 推广计划的业务逻辑层接口
 */
public interface IAdPlanService {
    /**
     * 根据请求的vo对象创建推广计划，并返回响应的vo对象
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 根据请求的vo对象删除推广计划
     *
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 根据请求的vo对象修改推广计划，并返回响应的vo对象
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 根据请求的vo对象获取列表的推广计划，并返回列表的响应AdPlan
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

}
