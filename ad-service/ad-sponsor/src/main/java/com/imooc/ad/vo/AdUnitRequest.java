package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Seven
 * @date 2019/10/19 1:48
 * @description 对应请求的封装对象 @RequestBody
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;
    /**
     * 广告展开位置
     */
    private Integer positionType;
    private Long budget;

    /**
     * 简单校验数据不为空
     * @return
     */
    public boolean createValidate() {
        return null != planId && !StringUtils.isEmpty(unitName) && positionType != null && budget != null;
    }

}
