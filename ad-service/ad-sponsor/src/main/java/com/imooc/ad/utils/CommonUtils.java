package com.imooc.ad.utils;

import com.imooc.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Seven
 * @date 2019/10/19 1:35
 * @description 业务中用到的通用工具类
 */
public class CommonUtils {
    /**
     * 日期转换格式
     */
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    /**
     * 将字符串转换为md5的大写字符串
     *
     * @param value
     * @return
     */
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * 将日期格式的字符串转换为日期类型
     *
     * @param dateString
     * @return
     * @throws AdException
     */
    public static Date parseStringToDate(String dateString) throws AdException {
        try {
            return DateUtils.parseDate(dateString, parsePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }

}
