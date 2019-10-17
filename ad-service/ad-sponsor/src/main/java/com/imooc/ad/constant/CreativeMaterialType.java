package com.imooc.ad.constant;

import lombok.Getter;

/**
 * @author Seven
 * @Date 2019/10/18 0:34
 * @Description 创意的物料类型 同样可能用不上
 */
@Getter
public enum CreativeMaterialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),
    MP4(3, "mp4"),
    AVI(4, "avi"),
    TXT(5, "txt");

    private Integer type;
    private String desc;

    CreativeMaterialType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
