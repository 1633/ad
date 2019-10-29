package com.imooc.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seven
 * @date 2019/10/29 22:25
 * @description 地理位置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    /**
     * 纬度
     */
    private Float latitude;
    /**
     * 经度
     */
    private Float longitude;

    private String province;

    private String city;

}
