package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 1:48
 * @description 对应响应的封装对象 也是json格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictResponse {

    private List<Long> ids;

}
