package com.imooc.ad.index.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Seven
 * @date 2019/10/20 0:07
 * @description 推广计划的索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startData;
    private Date endData;

    /**
     * 更新索引信息
     * @param newObject
     */
    public void update(AdPlanObject newObject) {
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getUserId() != null) {
            this.userId = newObject.getUserId();
        }
        if (newObject.getPlanStatus() != null) {
            this.planStatus = newObject.getPlanStatus();
        }
        if (newObject.getStartData() != null) {
            this.startData = newObject.getStartData();
        }
        if (newObject.getEndData() != null) {
            this.endData = newObject.getEndData();
        }
    }

}
