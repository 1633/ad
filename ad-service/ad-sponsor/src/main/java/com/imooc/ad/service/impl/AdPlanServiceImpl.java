package com.imooc.ad.service.impl;

import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.utils.CommonUtils;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Seven
 * @date 2019/10/19 2:20
 * @description 推广计划的业务逻辑层实现类 并标记事务注解
 */
@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {
    /**
     * 注入的两个持久层对象
     */
    private final AdPlanRepository planRepository;
    private final AdUserRepository userRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository planRepository, AdUserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        // 验证参数正确性
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        // 判断用户是否存在
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan adPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        // 判断推广计划是否存在
        if (adPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        // 保存推广计划并通过BeanUtils工具类复制属性
        AdPlan adPlanSave = planRepository.save(new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringToDate(request.getStartData()),
                CommonUtils.parseStringToDate(request.getEndData())));
        AdPlanResponse adPlanResponse = new AdPlanResponse();
        BeanUtils.copyProperties(adPlanSave, adPlanResponse);

        return adPlanResponse;
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        // 验证参数正确性
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan = planRepository.findAdPlanByIdAndUserId(request.getId(), request.getUserId());
        // 判断推广计划是否存在
        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 逻辑删除 将推广计划状态变更
        adPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        adPlan.setUpdateTime(new Date());

        planRepository.save(adPlan);
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        // 验证参数正确性
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan = planRepository.findAdPlanByIdAndUserId(request.getId(), request.getUserId());
        // 判断推广计划是否存在
        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 进行字段的更新
        if (request.getPlanName() != null) {
            adPlan.setPlanName(request.getPlanName());
        }
        if (request.getStartData() != null) {
            adPlan.setEndData(CommonUtils.parseStringToDate(request.getStartData()));
        }
        if (request.getEndData() != null) {
            adPlan.setEndData(CommonUtils.parseStringToDate(request.getEndData()));
        }
        adPlan.setUpdateTime(new Date());

        // 更新推广计划并通过BeanUtils工具类复制属性
        AdPlan updateUser = planRepository.save(adPlan);
        AdPlanResponse adPlanResponse = new AdPlanResponse();
        BeanUtils.copyProperties(updateUser, adPlanResponse);

        return adPlanResponse;
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        // 验证参数正确性
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdPlan> list = planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
        return list;
    }
}
