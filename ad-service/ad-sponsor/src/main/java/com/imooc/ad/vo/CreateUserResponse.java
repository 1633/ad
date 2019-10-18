package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author Seven
 * @date 2019/10/19 1:09
 * @description 对应响应的封装对象 也是json格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 请求传参的用户名称
     */
    private String username;
    /**
     * 用户token信息
     */
    private String token;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
