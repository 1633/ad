package com.imooc.ad.index;

import lombok.Getter;

/**
 * @author Seven
 * @date 2019/10/26 11:56
 * @description 数据表层级关系枚举类
 */
@Getter
public enum DateLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DateLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }

}
