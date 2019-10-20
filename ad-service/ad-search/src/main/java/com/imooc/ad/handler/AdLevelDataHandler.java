package com.imooc.ad.handler;

import com.imooc.ad.dump.table.AdPlanTable;
import com.imooc.ad.index.DataTable;
import com.imooc.ad.index.IndexAware;
import com.imooc.ad.index.pair.AdPlanIndex;
import com.imooc.ad.index.pair.AdPlanObject;
import com.imooc.ad.mysql.OpType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author Seven
 * @date 2019/10/20 22:46
 * @description 将json的.data数据读取并转换为索引对象 plan和creative是高一级的，unit是次一级的，关键词等是更低一级的
 */
@Slf4j
public class AdLevelDataHandler {
    /**
     * 属性复制后 对索引进行操作
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
