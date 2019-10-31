package com.imooc.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.index.CommonStatus;
import com.imooc.ad.index.DataTable;
import com.imooc.ad.index.pair.AdUnitIndex;
import com.imooc.ad.index.pair.AdUnitObject;
import com.imooc.ad.index.pair.CreativeIndex;
import com.imooc.ad.index.pair.CreativeObject;
import com.imooc.ad.index.pair.CreativeUnitIndex;
import com.imooc.ad.index.pair.UnitDistrictIndex;
import com.imooc.ad.index.pair.UnitItIndex;
import com.imooc.ad.index.pair.UnitKeywordIndex;
import com.imooc.ad.search.ISearch;
import com.imooc.ad.search.vo.SearchRequest;
import com.imooc.ad.search.vo.SearchResponse;
import com.imooc.ad.search.vo.feature.DistrictFeature;
import com.imooc.ad.search.vo.feature.FeatureRelation;
import com.imooc.ad.search.vo.feature.ItFeature;
import com.imooc.ad.search.vo.feature.KeywordFeature;
import com.imooc.ad.search.vo.media.AdSlot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author Seven
 * @date 2019/10/29 23:29
 * @description 处理广告检索请求的实现类
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {
    /**
     * 定义了fetchAds的断路器方法 结合启动类上的注解@EnableCircuitBreaker 反射是效率很低的
     * @param request
     * @param e
     * @return
     */
    public SearchResponse fetchAdsFallback(SearchRequest request, Throwable e) {
        return new SearchResponse();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fetchAdsFallback")
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
            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getOrRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, itFeature);
            }
            final List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            final List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            final List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            // 通过AdSlot实现对CreativeObject的过滤
            filterCreativeByAdSlot(creatives, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());
            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
        }
        log.info("fetchAds: {} -> {}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    /**
     * and 方式的三个标签过滤
     * @param adUnitIds
     * @param keywordFeature
     */
    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(adUnitIds, adUnitId ->
                    DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords()));
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(adUnitIds, adUnitId ->
                    DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts()));
        }
    }

    private void filterItTagFeature(Collection<Long> adUnitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(adUnitIds, adUnitId ->
                    DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts()));
        }
    }

    /**
     * or 方式的三个标签过滤
     * @param adUnitIdSet
     * @param keywordFeature
     * @param districtFeature
     * @param itFeature
     * @return
     */
    private Set<Long> getOrRelationUnitIds(Set<Long> adUnitIdSet, KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        // 保存副本，也是用来操作副本的
        final HashSet<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        final HashSet<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        final HashSet<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        // 逐层计算并集并返回
        return new HashSet<>(CollectionUtils.union(
                CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet), itUnitIdSet));
    }

    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(unitObjects, object -> object.getUnitStatus().equals(status.getStatus())
                && object.getPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives,
                                        Integer width, Integer height, List<Integer> type) {
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(creatives, creative -> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                && creative.getWidth().equals(width) && creative.getHeight().equals(height)
                && type.contains(creative.getType()));
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }
        final CreativeObject randomObject = creatives.get(Math.abs(new Random().nextInt()) % creatives.size());
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }

}
