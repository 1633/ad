package com.imooc.ad.service;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CreativeRequest;
import com.imooc.ad.vo.CreativeResponse;

/**
 * @author Seven
 * @date 2019/10/19 9:50
 * @description 创意的业务逻辑层接口
 */
public interface ICreativeService {
    /**
     * 创建创意
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeResponse createCreative(CreativeRequest request) throws AdException;

}
