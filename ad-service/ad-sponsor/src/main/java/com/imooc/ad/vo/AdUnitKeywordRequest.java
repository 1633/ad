package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Seven
 * @date 2019/10/19 1:48
 * @description 对应请求的封装对象 @RequestBody
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordRequest {

    private List<UnitKeyword> unitKeywords;

    /**
     * 允许批量创建keyword信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyword {
        private Long unitId;
        private String keyword;
    }

}
