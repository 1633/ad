package com.imooc.ad.index;

import lombok.Getter;

/**
 * @author Seven
 * @date 2019/10/30 21:50
 * @description 标识有效无效状态
 */
@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }

}
