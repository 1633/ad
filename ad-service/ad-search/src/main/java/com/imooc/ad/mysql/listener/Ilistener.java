package com.imooc.ad.mysql.listener;

import com.imooc.ad.mysql.vo.BinlogRowData;

/**
 * @author Seven
 * @date 2019/10/24 23:22
 * @description 接口层 对binlog数据的操作接口
 */
public interface Ilistener {
    /**
     * 注册监听事件
     */
    void register();

    /**
     * 实现增量数据的更新
     *
     * @param eventData
     */
    void onEvent(BinlogRowData eventData);

}
