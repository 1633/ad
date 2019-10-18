package com.imooc.ad.constant;

import lombok.Getter;

/**
 * @author Seven
 * @Date 2019/10/18 0:31
 * @Description 描述创意类型的枚举类 可能用不上
 */
@Getter
public enum CreativeType {
    /**
     * 图片
     */
    IMAGE(1, "图片"),
    /**
     * 视频
     */
    VIDEO(2, "视频"),
    /**
     * 文本
     */
    TEXT(3, "文本");
    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;

    CreativeType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
