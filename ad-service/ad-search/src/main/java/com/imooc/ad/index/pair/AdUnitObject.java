package com.imooc.ad.index.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seven
 * @date 2019/10/20 0:07
 * @description 推广单元的索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
    /**
     * 与推广计划的引用关系
     */
    private AdPlanObject planObject;

    /**
     * 更新索引信息
     *
     * @param newObject
     */
    public void update(AdUnitObject newObject) {
        if (newObject.getUnitId() != null) {
            this.unitId = newObject.getUnitId();
        }
        if (newObject.getUnitStatus() != null) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (newObject.getPositionType() != null) {
            this.positionType = newObject.getPositionType();
        }
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getPlanObject() != null) {
            this.planObject = newObject.getPlanObject();
        }
    }

}
