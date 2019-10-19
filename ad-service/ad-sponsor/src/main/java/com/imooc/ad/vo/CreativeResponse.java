package com.imooc.ad.vo;

import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Seven
 * @date 2019/10/19 1:48
 * @description 对应响应的封装对象 也是json格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeResponse {

    private Long id;
    private String name;

}
