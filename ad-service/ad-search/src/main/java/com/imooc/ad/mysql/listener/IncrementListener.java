package com.imooc.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.imooc.ad.mysql.Constant;
import com.imooc.ad.mysql.OpType;
import com.imooc.ad.mysql.vo.BinlogRowData;
import com.imooc.ad.mysql.vo.MySqlRowData;
import com.imooc.ad.mysql.vo.TableTemplate;
import com.imooc.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/25 23:33
 * @description 增量数据的处理器
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "indexSender")
    private ISender sender;
    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info.");
        Constant.TABLE2DB.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        final TableTemplate table = eventData.getTable();
        final EventType eventType = eventData.getEventType();

        // 包装成最后要投递的数据
        final MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        final OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        // 设置第四个属性
        final List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (fieldList == null) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {
            final Map<String, String> _afterMap = new HashMap<>();
            // 从map中去key和value
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                _afterMap.put(key, value);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
