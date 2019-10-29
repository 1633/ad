package com.imooc.ad.search.impl;

import com.imooc.ad.index.DataTable;
import com.imooc.ad.index.pair.AdUnitIndex;
import com.imooc.ad.search.ISearch;
import com.imooc.ad.search.vo.SearchRequest;
import com.imooc.ad.search.vo.SearchResponse;
import com.imooc.ad.search.vo.feature.DistrictFeature;
import com.imooc.ad.search.vo.feature.FeatureRelation;
import com.imooc.ad.search.vo.feature.ItFeature;
import com.imooc.ad.search.vo.feature.KeywordFeature;
import com.imooc.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Seven
 * @date 2019/10/29 23:29
 * @description 处理广告检索请求的实现类
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        // 请求的广告位信息
        final List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        // 三个feature及对应关系
        final KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        final DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        final ItFeature itFeature = request.getFeatureInfo().getItFeature();
        final FeatureRelation relation = request.getFeatureInfo().getRelation();
        // 构造响应对象
        final SearchResponse response = new SearchResponse();
        final Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            // 根据流量类型获取初始 AdUnit
            final Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
        }
        return null;
    }
}
