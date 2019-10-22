package com.imooc.ad.handler;

import com.imooc.ad.dump.table.AdCreativeTable;
import com.imooc.ad.dump.table.AdCreativeUnitTable;
import com.imooc.ad.dump.table.AdPlanTable;
import com.imooc.ad.dump.table.AdUnitDistrictTable;
import com.imooc.ad.dump.table.AdUnitItTable;
import com.imooc.ad.dump.table.AdUnitKeywordTable;
import com.imooc.ad.dump.table.AdUnitTable;
import com.imooc.ad.index.DataTable;
import com.imooc.ad.index.IndexAware;
import com.imooc.ad.index.pair.AdPlanIndex;
import com.imooc.ad.index.pair.AdPlanObject;
import com.imooc.ad.index.pair.AdUnitIndex;
import com.imooc.ad.index.pair.AdUnitObject;
import com.imooc.ad.index.pair.CreativeIndex;
import com.imooc.ad.index.pair.CreativeObject;
import com.imooc.ad.index.pair.CreativeUnitIndex;
import com.imooc.ad.index.pair.CreativeUnitObject;
import com.imooc.ad.index.pair.UnitDistrictIndex;
import com.imooc.ad.index.pair.UnitItIndex;
import com.imooc.ad.index.pair.UnitKeywordIndex;
import com.imooc.ad.mysql.OpType;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Seven
 * @date 2019/10/20 22:46
 * @description 将json的.data数据读取并转换为索引对象 plan和creative是高一级的，unit是次一级的，关键词等是更低一级的
 */
@Slf4j
public class AdLevelDataHandler {
    /**
     * 推广计划 属性复制后 对索引进行操作
     *
     * @param planTable
     * @param type
     */
    public static void handleLevel2(AdPlanTable planTable, OpType type) {
        final AdPlanObject planObject = new AdPlanObject();
        BeanUtils.copyProperties(planTable, planObject);
        // 字段不匹配 手动设置
        planObject.setPlanId(planTable.getId());

        handleBinlogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, type);
    }

    /**
     * 创意 重载方法
     *
     * @param creativeTable
     * @param type
     */
    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {
        final CreativeObject creativeObject = new CreativeObject();
        BeanUtils.copyProperties(creativeTable, creativeObject);

        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, type);
    }

    /**
     * 第三层级的 推广单元
     *
     * @param unitTable
     * @param type
     */
    public static void handleLevel3(AdUnitTable unitTable, OpType type) {
        // 获取level2中的引用对象
        final AdPlanObject planObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (planObject == null) {
            log.error("推广计划没有，不应该加载推广单元，所以出错: {}", unitTable.getPlanId());
            return;
        }

        final AdUnitObject unitObject = new AdUnitObject();
        BeanUtils.copyProperties(unitTable, unitObject);
        // 字段不匹配 手动设置
        unitObject.setUnitId(unitTable.getId());
        unitObject.setPlanObject(planObject);

        handleBinlogEvent(DataTable.of(AdUnitIndex.class), unitTable.getId(), unitObject, type);
    }

    /**
     * 第三层级的 创意与推广单元关联表
     *
     * @param creativeUnitTable
     * @param type
     */
    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {
        // 不支持更新
        if (type == OpType.UPDATE) {
            log.error("索引不支持更新！");
            return;
        }

        // 从上一级中的索引中获取，没有则不应该加载
        final AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        final CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getCreativeId());

        if (unitObject == null || creativeObject == null) {
            log.error("推广单元或者创意没有，不应该加载关联关系索引，所以出错: {}", creativeUnitTable);
            return;
        }

        final CreativeUnitObject creativeUnitObject = new CreativeUnitObject();
        creativeUnitObject.setUnitId(creativeUnitTable.getUnitId());
        creativeUnitObject.setAdId(creativeUnitTable.getCreativeId());

        final String strConcat = CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString());
        handleBinlogEvent(DataTable.of(CreativeUnitIndex.class), strConcat, creativeUnitObject, type);
    }

    /**
     * 第四层级的 推广单元地域
     *
     * @param unitDistrictTable
     * @param type
     */
    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {
        // 不支持更新
        if (type == OpType.UPDATE) {
            log.error("索引不支持更新！");
            return;
        }

        // 从上一级中的索引中获取，没有则不应该加载
        final AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("推广单元没有，不应该加载地域关联关系索引，所以出错: {}", unitDistrictTable);
            return;
        }

        // 键值分别为 str串、long的set
        final String strConcat = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        final Set<Long> value = new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), strConcat, value, type);
    }

    /**
     * 第四层级的 推广单元兴趣
     *
     * @param unitItTable
     * @param type
     */
    public static void handleLevel4(AdUnitItTable unitItTable, OpType type) {
        // 不支持更新
        if (type == OpType.UPDATE) {
            log.error("索引不支持更新！");
            return;
        }

        // 从上一级中的索引中获取，没有则不应该加载
        final AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (unitObject == null) {
            log.error("推广单元没有，不应该加载兴趣关联关系索引，所以出错: {}", unitItTable);
            return;
        }

        // 键值分别为 tag名称、long的set
        final Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitItIndex.class), unitItTable.getItTag(), value, type);
    }

    /**
     * 第四层级的 推广单元关键词
     *
     * @param unitKeywordTable
     * @param type
     */
    public static void handleLevel4(AdUnitKeywordTable unitKeywordTable, OpType type) {
        // 不支持更新
        if (type == OpType.UPDATE) {
            log.error("索引不支持更新！");
            return;
        }

        // 从上一级中的索引中获取，没有则不应该加载
        final AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitKeywordTable.getUnitId());
        if (unitObject == null) {
            log.error("推广单元没有，不应该加载关键词关联关系索引，所以出错: {}", unitKeywordTable);
            return;
        }

        // 键值分别为 关键词名称、long的set
        final Set<Long> value = new HashSet<>(Collections.singleton(unitKeywordTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), unitKeywordTable.getKeyword(), value, type);
    }

    /**
     * 监听Binlog日志的操作状态，对索引进行处理
     *
     * @param indexAware
     * @param key
     * @param value
     * @param type
     * @param <K>
     * @param <V>
     */
    private static <K, V> void handleBinlogEvent(IndexAware<K, V> indexAware, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                indexAware.add(key, value);
                break;
            case UPDATE:
                indexAware.update(key, value);
                break;
            case DELETE:
                indexAware.delete(key, value);
                break;
            default:
                break;
        }
    }

}
