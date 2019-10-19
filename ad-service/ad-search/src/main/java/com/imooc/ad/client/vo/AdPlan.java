package com.imooc.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Seven
 * @date 2019/10/19 20:59
 * @description feign演示类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlan {

    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startData;
    private Date endData;
    private Date createTime;
    private Date updateTime;

}
