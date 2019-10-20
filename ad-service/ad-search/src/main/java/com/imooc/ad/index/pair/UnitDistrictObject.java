package com.imooc.ad.index.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seven
 * @date 2019/10/20 0:07
 * @description 推广单元の地域的索引对象 为了与关键词、兴趣匹配，使用连字符将省市拼接
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictObject {

    private Long unitId;
    private String province;
    private String city;

}
