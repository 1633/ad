package com.imooc.ad.constant;

import lombok.Getter;

/**
 * @author Seven
 * @Date 2019/10/18 0:31
 * @Description 描述创意类型的枚举类 可能用不上
 */
@Getter
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private Integer type;
    private String desc;

    CreativeType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
