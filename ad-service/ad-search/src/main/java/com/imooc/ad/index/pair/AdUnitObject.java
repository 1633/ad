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

    private static boolean isOpen(int positionType) {
        // 位运算 &运算中1&1=1,1&0=0,0&0=0
        return (positionType & AdUnitConstants.POSITION_TYPE.OPEN) > 0;
    }

    private static boolean isAttach(int positionType) {
        // 位运算 &运算中1&1=1,1&0=0,0&0=0
        return (positionType & AdUnitConstants.POSITION_TYPE.ATTACH) > 0;
    }

    private static boolean isAttachMiddle(int positionType) {
        // 位运算 &运算中1&1=1,1&0=0,0&0=0
        return (positionType & AdUnitConstants.POSITION_TYPE.ATTACH_MIDDLE) > 0;
    }

    private static boolean isAttachPause(int positionType) {
        // 位运算 &运算中1&1=1,1&0=0,0&0=0
        return (positionType & AdUnitConstants.POSITION_TYPE.ATTACH_PAUSE) > 0;
    }

    private static boolean isAttachPost(int positionType) {
        // 位运算 &运算中1&1=1,1&0=0,0&0=0
        return (positionType & AdUnitConstants.POSITION_TYPE.ATTACH_POST) > 0;
    }

    /**
     * 统一校验广告位置
     * @param adSlotType
     * @param positionType
     * @return
     */
    public static boolean isAdSlotTypeOk(int adSlotType, int positionType) {
        switch (adSlotType) {
            case AdUnitConstants.POSITION_TYPE.OPEN:
                return isOpen(positionType);
            case AdUnitConstants.POSITION_TYPE.ATTACH:
                return isAttach(positionType);
            case AdUnitConstants.POSITION_TYPE.ATTACH_MIDDLE:
                return isAttachMiddle(positionType);
            case AdUnitConstants.POSITION_TYPE.ATTACH_PAUSE:
                return isAttachPause(positionType);
            case AdUnitConstants.POSITION_TYPE.ATTACH_POST:
                return isAttachPost(positionType);
            default:
                return false;
        }
    }

}
