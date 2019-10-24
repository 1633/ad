package com.imooc.ad.mysql.vo;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/24 23:17
 * @description 将binlog监听到的数据转换为java对象 参考开源工具的Event对象
 */
@Data
public class BinlogRowData {

    private TableTemplate table;
    private EventType eventType;
    private List<Map<String, String>> after;
    private List<Map<String, String>> before;

}
