package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 1:48
 * @description 对应查询请求的封装对象 @RequestBody
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {
    /**
     * id序号列表
     */
    private List<Long> ids;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 验证查询操作，请求参数是否正确
     *
     * @return
     */
    public boolean validate() {
        return userId != null && !CollectionUtils.isEmpty(ids);
    }

}
