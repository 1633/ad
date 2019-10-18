package com.imooc.ad.constant;

import lombok.Getter;

/**
 * @author Seven
 * @Date 2019/10/17 23:24
 * @Description 通用状态枚举类型 只用getter 实际可以定义到common模块中
 */
@Getter
public enum CommonStatus {
    /**
     * 有效状态
     */
    VALID(1, "有效状态"),
    /**
     * 无效状态
     */
    INVALID(0, "无效状态");
    /**
     * 状态值
     */
    private Integer status;
    /**
     * 状态描述
     */
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
