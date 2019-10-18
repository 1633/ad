package com.imooc.ad.dao;

import com.imooc.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 0:53
 * @description 操作推广单元 ad_unit 表的数据库持久层
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {
    /**
     * 根据计划id和推广单元名称查询
     *
     * @param planId
     * @param unitName
     * @return
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 根据推广单元状态查询所有
     *
     * @param status
     * @return
     */
    List<AdUnit> findAllByUnitStatus(Integer status);

}
