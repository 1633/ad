package com.imooc.ad.index.pair;

import com.imooc.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seven
 * @date 2019/10/20 0:16
 * @description 推广单元的索引操作类
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {
    /**
     * 内存存储索引信息
     */
    private static Map<Long, AdUnitObject> objectMap;

    static {
        // 线程安全的map存储对象
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {
        final HashSet<Long> adUnitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (AdUnitObject.isAdSlotTypeOk(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });
        return adUnitIds;
    }

    public List<AdUnitObject> fetch(Collection<Long> adUnitIds) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }
        final ArrayList<AdUnitObject> result = new ArrayList<>();
        adUnitIds.forEach(au -> {
            final AdUnitObject object = get(au);
            if (null == object) {
                log.error("AdUnitObject not found: {}", au);
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("before update: {}", objectMap);

        // 先查询在判断
        AdUnitObject oldValue = get(key);
        if (null == oldValue) {
            objectMap.put(key, value);
        } else {
            oldValue.update(value);
        }

        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }

}
