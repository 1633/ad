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
public class AdPlanRequest {
    /**
     * id序号
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 计划开始时间
     */
    private String startData;
    /**
     * 计划结束时间
     */
    private String endData;

    /**
     * 验证保存操作，请求参数是否正确
     *
     * @return
     */
    public boolean createValidate() {
        return userId != null
                && !StringUtils.isEmpty(planName)
                && !StringUtils.isEmpty(startData)
                && !StringUtils.isEmpty(endData);
    }

    /**
     * 验证更新操作，请求参数是否正确
     *
     * @return
     */
    public boolean updateValidate() {
        return id != null && userId != null;
    }

    /**
     * 验证删除操作，请求参数是否正确
     *
     * @return
     */
    public boolean deleteValidate() {
        return id != null && userId != null;
    }

}
