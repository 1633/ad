package com.imooc.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.imooc.ad.mysql.TemplateHolder;
import com.imooc.ad.mysql.vo.BinlogRowData;
import com.imooc.ad.mysql.vo.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Seven
 * @date 2019/10/24 23:27
 * @description
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String, Ilistener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String _dbName, String _tableName, Ilistener ilistener) {
        log.info("register : {} - {}", _dbName, _tableName);
        this.listenerMap.put(genKey(_dbName, _tableName), ilistener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            final TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 表名和库名是否已经完成填空(指字段)
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        final String key = genKey(this.dbName, this.tableName);
        final Ilistener ilistener = this.listenerMap.get(key);
        if (ilistener == null) {
            log.debug("skip {}", key);
            return;
        }

        log.info("trigger event: {}", type.name());

        try {

            final BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }

            rowData.setEventType(type);
            ilistener.onEvent(rowData);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            this.dbName ="";
            this.tableName = "";
        }
    }

    /**
     * 将eventData转变为BinlogData
     * @param eventData
     * @return
     */
    private BinlogRowData buildRowData(EventData eventData) {
        final TableTemplate table = templateHolder.getTable(tableName);
        if (table == null) {
            log.warn("table {} not found!", tableName);
            return null;
        }

        final ArrayList<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            final HashMap<String, String> afterMap = new HashMap<>();
            final int colLen = after.length;
            for (int i = 0; i < colLen; i++) {
                final String colName = table.getPosMap().get(i);

                if (colName == null) {
                    log.debug("ignore position: {}", i);
                    continue;
                }

                final String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }
            final BinlogRowData rowData = new BinlogRowData();
            rowData.setAfter(afterMapList);
            rowData.setTable(table);

            return rowData;
        }
        return null;
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream().
                    map(Map.Entry::getValue).collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        // 什么都不符合则返回空集合
        return Collections.emptyList();
    }

}
