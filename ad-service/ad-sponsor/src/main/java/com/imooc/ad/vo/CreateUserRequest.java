package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Seven
 * @date 2019/10/19 1:09
 * @description 对应请求的封装对象 @RequestBody
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    /**
     * 请求传参的用户名称
     */
    private String username;

    /**
     * 验证用户名是否为空
     * @return
     */
    public boolean validate() {
        return !StringUtils.isEmpty(username);
    }

}
