package com.imooc.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seven
 * @date 2019/10/29 22:28
 * @description 设备信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    private String deviceCode;
    private String mac;
    private String ip;
    private String model;
    private String displaySize;
    private String screenSize;
    private String serialName;

}
