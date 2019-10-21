package com.imooc.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Seven
 * @date 2019/10/20 16:30
 * @description 处理从集合中获取数据未空时，如何操作
 */
public class CommonUtils {
    /**
     * 从传入的map中获取值 如果不存在则从factory中产生一个
     * @param key
     * @param map
     * @param factory
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory){
        return map.computeIfAbsent(key, k -> factory.get());
    }

    /**
     * 拼接字符串 用横杠 - 连接 最后一个横杠去除
     * @param args
     * @return
     */
    public static String stringConcat(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append("-");
        }
        sb.substring(0, sb.length() - 1);
        return sb.toString();
    }

}
