package com.imooc.ad.constant;

import lombok.Getter;

/**
 * @author Seven
 * @Date 2019/10/18 0:34
 * @Description 创意的物料类型 同样可能用不上
 */
@Getter
public enum CreativeMaterialType {
    /**
     * jpg
     */
    JPG(1, "jpg"),
    /**
     * bmp
     */
    BMP(2, "bmp"),
    /**
     * mp4
     */
    MP4(3, "mp4"),
    /**
     * avi
     */
    AVI(4, "avi"),
    /**
     * txt
     */
    TXT(5, "txt");
    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;

    CreativeMaterialType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
