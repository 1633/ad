package com.imooc.ad.index.pair;

import com.imooc.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seven
 * @date 2019/10/20 0:16
 * @description 创意的索引操作类
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {
    /**
     * 内存存储索引信息
     */
    private static Map<Long, CreativeObject> objectMap;

    static {
        // 线程安全的map存储对象
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("before update: {}", objectMap);

        // 先查询在判断
        CreativeObject oldValue = get(key);
        if (null == oldValue) {
            objectMap.put(key, value);
        } else {
            oldValue.update(value);
        }

        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }

    public List<CreativeObject> fetch(Collection<Long> adIds) {
        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }
        final ArrayList<CreativeObject> result = new ArrayList<>();
        adIds.forEach(u -> {
            final CreativeObject object = get(u);
            if (null == object) {
                log.error("CreativeObject not found: {}", u);
                return;
            }
            result.add(object);
        });
        return result;
    }

}
