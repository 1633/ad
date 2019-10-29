package com.imooc.ad.search.vo;

import com.imooc.ad.index.pair.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/29 22:14
 * @description 搜索的响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative{
        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;
        /**
         * 展示检测 url
         */
        private List<String> showMonitorUrl = Arrays.asList("https://www.baidu.com", "https://www.jd.com");
        /**
         * 点击检测 url
         */
        private List<String> clickMonitorUrl = Arrays.asList("https://www.baidu.com", "https://www.jd.com");
    }

    public static Creative convert(CreativeObject object) {
        final Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }

}
