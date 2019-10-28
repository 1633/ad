package com.imooc.ad.mysql;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/25 23:02
 * @description 数据库表相关的常量信息
 */
public class Constant {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "imooc_ad_data";

    /**
     * 计划表
     */
    public static class AD_PLAN_TABLE_INFO {
        public static final String TABLE_NAME = "ad_plan";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_PLAN_STATUS = "plan_status";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
    }

    /**
     * 创意表
     */
    public static class AD_CREATIVE_TABLE_INFO {
        public static final String TABLE_NAME = "ad_creative";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MATERIAL_TYPE = "material_type";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_AUDIT_STATUS = "audit_status";
        public static final String COLUMN_URL = "url";
    }

    /**
     * 推广单元表
     */
    public static class AD_UNIT_TABLE_INFO {
        public static final String TABLE_NAME = "ad_unit";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UNIT_STATUS = "unit_status";
        public static final String COLUMN_POSITION_TYPE = "position_type";
        public static final String COLUMN_PLAN_ID = "plan_id";
    }

    /**
     * 创意与推广计划关联表
     */
    public static class AD_CREATIVE_UNIT_TABLE_INFO {
        public static final String TABLE_NAME = "creative_unit";
        public static final String COLUMN_CREATIVE_ID = "creative_id";
        public static final String COLUMN_UNIT_ID = "unit_id";
    }

    /**
     * 地域表
     */
    public static class AD_UNIT_DISTRICT_TABLE_INFO {
        public static final String TABLE_NAME = "ad_unit_district";
        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_PROVINCE = "province";
        public static final String COLUMN_CITY = "city";
    }

    /**
     * 兴趣表
     */
    public static class AD_UNIT_IT_TABLE_INFO {
        public static final String TABLE_NAME = "ad_unit_it";
        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_IT_TAG = "it_tag";
    }

    /**
     * 关键词表
     */
    public static class AD_UNIT_KEYWORD_TABLE_INFO {
        public static final String TABLE_NAME = "ad_unit_keyword";
        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_KEYWORD = "keyword";
    }

    public static Map<String, String> TABLE2DB;
    static {
        TABLE2DB = new HashMap<>();
        TABLE2DB.put(AD_PLAN_TABLE_INFO.TABLE_NAME, DB_NAME);
        TABLE2DB.put(AD_CREATIVE_TABLE_INFO.TABLE_NAME, DB_NAME);
        TABLE2DB.put(AD_UNIT_TABLE_INFO.TABLE_NAME, DB_NAME);
        TABLE2DB.put(AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME, DB_NAME);
        TABLE2DB.put(AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME, DB_NAME);
        TABLE2DB.put(AD_UNIT_IT_TABLE_INFO.TABLE_NAME, DB_NAME);
        TABLE2DB.put(AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME, DB_NAME);
    }

}
