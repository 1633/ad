package com.imooc.ad.dao;

import com.imooc.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Seven
 * @Date 2019/10/19 0:44
 * @Description 操作推广计划plan表的数据库持久层
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {
    /**
     * 计划id和用户id查询计划
     *
     * @param id
     * @param userId
     * @return
     */
    AdPlan findAdPlanByIdAndUserId(Long id, Long userId);

    /**
     * 通过计划id列表和用户id查询
     *
     * @param ids
     * @param userId
     * @return
     */
    List<AdPlan> findAllByIdAndUserId(List<Long> ids, Long userId);

    /**
     * 通过用户id和计划名称查询唯一计划
     *
     * @param userId
     * @param planName
     * @return
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据计划状态查询全部
     *
     * @param status
     * @return
     */
    List<AdPlan> findAllByPlanStatus(Integer status);

}
