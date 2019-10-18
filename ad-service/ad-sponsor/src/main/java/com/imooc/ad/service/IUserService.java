package com.imooc.ad.service;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CreateUserRequest;
import com.imooc.ad.vo.CreateUserResponse;

/**
 * @author Seven
 * @date 2019/10/19 1:07
 * @description user的业务逻辑层接口
 */
public interface IUserService {
    /**
     * 根据请求的vo对象创建用户，并返回响应的vo对象
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;

}
