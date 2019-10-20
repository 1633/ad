package com.imooc.ad.index;

/**
 * @author Seven
 * @date 2019/10/20 0:01
 * @description index索引的增删改操作接口
 */
public interface IndexAware<K, V> {
    /**
     * 查询获取索引
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 存储索引信息
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 修改索引 实际存在修改否则新增
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除索引
     *
     * @param key
     * @param value
     */
    void delete(K key, V value);

}
