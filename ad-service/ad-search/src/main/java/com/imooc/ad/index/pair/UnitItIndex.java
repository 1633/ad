package com.imooc.ad.index.pair;

import com.imooc.ad.index.IndexAware;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Seven
 * @date 2019/10/20 0:16
 * @description 推广单元の兴趣的索引操作类 倒排索引
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {
    /**
     * 内存存储索引信息 倒排/逆向索引 正向索引
     */
    private static Map<String, Set<Long>> itUnitMap;
    private static Map<Long, Set<String>> unitItMap;

    static {
        // 线程安全的map存储对象
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        // 键空返空集合
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        // 结果为空与非空返回
        Set<Long> result = itUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }

        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("before add: {}", unitItMap);
        // 添加逆向索引信息
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        // 添加正向索引信息
        for (Long unitId : value) {
            Set<String> itSet = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itSet.add(key);
        }

        log.info("after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        //更新功能成本过高，实现方式是用户删除再新增
        log.error("UnitItIndex can not support update.");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("before delete: {}", unitItMap);
        // 先处理倒排索引相关的map
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);

        // 再处理正向索引相关的map
        for (Long unitId : value) {
            Set<String> itSet = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itSet.remove(key);
        }

        log.info("after delete: {}", unitItMap);
    }

    /**
     * 判断传入的关键词组是否存在
     * @param unitId
     * @param itTags
     * @return
     */
    public boolean match(Long unitId, List<String> itTags) {
        // 判断倒排索引存储中是否存在推广单元id 且 value值不为空
        if (unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            Set<String> unitItTag = unitItMap.get(unitId);
            // 判断传入是否为存储map的子集
            return CollectionUtils.isSubCollection(itTags, unitItTag);
        }

        return false;
    }

}
