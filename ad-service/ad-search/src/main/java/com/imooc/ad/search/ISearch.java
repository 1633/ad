package com.imooc.ad.search;

import com.imooc.ad.search.vo.SearchRequest;
import com.imooc.ad.search.vo.SearchResponse;

/**
 * @author Seven
 * @date 2019/10/29 22:12
 * @description 处理广告检索请求
 */
public interface ISearch {
    /**
     * 媒体方的请求返回检索结果
     * @param request
     * @return
     */
    SearchResponse fetchAds(SearchRequest request);

}
