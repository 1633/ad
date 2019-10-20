package com.imooc.ad.index.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seven
 * @date 2019/10/20 0:07
 * @description 创意与推广单元关联关系的索引对象 使用创意id拼接推广单元id为index的key
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {

    private Long unitId;
    private Long adId;

}
