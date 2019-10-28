package com.imooc.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Seven
 * @date 2019/10/20 16:30
 * @description 处理从集合中获取数据未空时，如何操作
 */
@Slf4j
public class CommonUtils {
    /**
     * 从传入的map中获取值 如果不存在则从factory中产生一个
     *
     * @param key
     * @param map
     * @param factory
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    /**
     * 拼接字符串 用横杠 - 连接 最后一个横杠去除
     *
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

    /**
     * Sat Nov 30 20:00:00 CST 2019 源数据string转换为Date对象 相隔8小时减掉
     *
     * @param dateString
     * @return
     */
    public static Date parseStringDate(String dateString) {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyy", Locale.US);
            return DateUtils.addHours(dateFormat.parse(dateString), -8);
        } catch (ParseException e) {
            log.error("parseStringDate error: {}", dateString);
            return null;
        }
    }


}
