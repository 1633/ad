package com.imooc.ad.service;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.AdUnitDistrictRequest;
import com.imooc.ad.vo.AdUnitDistrictResponse;
import com.imooc.ad.vo.AdUnitItRequest;
import com.imooc.ad.vo.AdUnitItResponse;
import com.imooc.ad.vo.AdUnitKeywordRequest;
import com.imooc.ad.vo.AdUnitKeywordResponse;
import com.imooc.ad.vo.AdUnitRequest;
import com.imooc.ad.vo.AdUnitResponse;

/**
 * @author Seven
 * @date 2019/10/19 9:50
 * @description 推广单元的业务逻辑层接口
 */
public interface IAdUnitService {
    /**
     * 新增推广单元
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 创建推广单元关联的关键词维度
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建推广单元关联的兴趣维度
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 创建推广单元关联的地域维度
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

}
