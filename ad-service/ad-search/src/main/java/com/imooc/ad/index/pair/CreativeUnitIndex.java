package com.imooc.ad.index.pair;

import com.imooc.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Seven
 * @date 2019/10/20 0:16
 * @description 创意与推广单元的索引操作类 倒排索引 使用创意id拼接推广单元id为index的key
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {
    /**
     * 内存存储索引信息 倒排/逆向索引 正向索引
     */
    /**
     * <adId-unitId, CreativeObject>
     */
    private static Map<String, CreativeUnitObject> objectMap;
    /**
     * <adId, unitId set>
     */
    private static Map<Long, Set<Long>> creativeUnitMap;
    /**
     * <unitId, adId set>
     */
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        // 线程安全的map存储对象
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        // 更新三个map
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);

        Set<Long> adIdSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(adIdSet)) {
            adIdSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), adIdSet);
        }
        adIdSet.add(value.getUnitId());

        Set<Long> unitIdSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(unitIdSet)) {
            unitIdSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), unitIdSet);
        }
        unitIdSet.add(value.getAdId());

        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex can not support update.");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        // 删除三个map
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);

        Set<Long> adIdSet = creativeUnitMap.get(value.getAdId());
        if (!CollectionUtils.isEmpty(adIdSet)) {
            adIdSet.remove(value.getUnitId());
        }

        Set<Long> unitIdSet = unitCreativeMap.get(value.getUnitId());
        if (!CollectionUtils.isEmpty(unitIdSet)) {
            unitIdSet.remove(value.getAdId());
        }

        log.info("after delete: {}", objectMap);
    }
    
}
