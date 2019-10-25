package com.imooc.ad.sender;

import com.imooc.ad.mysql.vo.MySqlRowData;

/**
 * @author Seven
 * @date 2019/10/25 23:30
 * @description 投递数据库的增量数据 投递方式可以多样
 */
public interface ISender {
    /**
     * 投递增量数据的接口类
     * @param rowData
     */
    void sender(MySqlRowData rowData);

}
