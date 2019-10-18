package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IUserService;
import com.imooc.ad.utils.CommonUtils;
import com.imooc.ad.vo.CreateUserRequest;
import com.imooc.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seven
 * @date 2019/10/19 1:17
 * @description user的业务逻辑层实现类 并标记事务注解
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    /**
     * 注入的持久层对象
     */
    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        // 参数验证不通过 抛出异常
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        String username = request.getUsername();
        AdUser user = userRepository.findAdUserByUsername(username);
        // 判断数据库是否存在用户
        if (user != null) {
            throw new AdException(Constants.ErrorMsg.SAME_USERNAME_ERROR);
        }

        // 保存用户并通过BeanUtils工具类复制属性
        AdUser adUser = userRepository.save(new AdUser(username, CommonUtils.md5(username)));
        CreateUserResponse createUserResponse = new CreateUserResponse();
        BeanUtils.copyProperties(adUser, createUserResponse);

        return createUserResponse;
    }
}
