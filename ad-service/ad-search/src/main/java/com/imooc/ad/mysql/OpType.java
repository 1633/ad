package com.imooc.ad.mysql;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author Seven
 * @date 2019/10/20 22:50
 * @description mysql对表的操作类型
 */
public enum OpType {
    /**
     * 增
     */
    ADD,
    /**
     * 改
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 其他
     */
    OTHER;

    /**
     * 将binlog日志中的操作类型转换为OpType类型
     * @param eventType
     * @return
     */
    public static OpType to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }

}
