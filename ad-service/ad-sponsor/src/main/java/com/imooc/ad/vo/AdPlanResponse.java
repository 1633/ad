package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Seven
 * @date 2019/10/19 1:48
 * @description 对应响应的封装对象 也是json格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanResponse {
    /**
     * id序号
     */
    private Long id;
    /**
     * 计划名称
     */
    private String planName;

}
