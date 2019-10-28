package com.imooc.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.dump.table.AdCreativeTable;
import com.imooc.ad.dump.table.AdCreativeUnitTable;
import com.imooc.ad.dump.table.AdPlanTable;
import com.imooc.ad.dump.table.AdUnitDistrictTable;
import com.imooc.ad.dump.table.AdUnitItTable;
import com.imooc.ad.dump.table.AdUnitKeywordTable;
import com.imooc.ad.dump.table.AdUnitTable;
import com.imooc.ad.handler.AdLevelDataHandler;
import com.imooc.ad.index.DateLevel;
import com.imooc.ad.mysql.Constant;
import com.imooc.ad.mysql.vo.MySqlRowData;
import com.imooc.ad.sender.ISender;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/26 12:01
 * @description 发送对线
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MySqlRowData rowData) {
        final String level = rowData.getLevel();
        if (DateLevel.LEVEL2.getLevel().equals(level)) {
            level2RowDateSend(rowData);
        } else if (DateLevel.LEVEL3.getLevel().equals(level)) {
            level3RowDateSend(rowData);
        } else if (DateLevel.LEVEL4.getLevel().equals(level)) {
            level4RowDateSend(rowData);
        } else {
            log.error("MySqlRowData Error: {}", JSON.toJSONString(rowData));
        }
    }

    /**
     * 2层级投递
     * @param rowData
     */
    private void level2RowDateSend(MySqlRowData rowData) {
        if (rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            final ArrayList<AdPlanTable> planTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                final AdPlanTable planTable = new AdPlanTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            planTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                        default:
                            break;
                    }
                });
                planTables.add(planTable);
            }
            planTables.forEach(p -> AdLevelDataHandler.handleLevel2(p, rowData.getOpType()));
        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
            final ArrayList<AdCreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                final AdCreativeTable creativeTable = new AdCreativeTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                        default:
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(p -> AdLevelDataHandler.handleLevel2(p, rowData.getOpType()));
        }
    }

    /**
     * 3层级投递
     * @param rowData
     */
    private void level3RowDateSend(MySqlRowData rowData) {
        if (rowData.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)) {
            final ArrayList<AdUnitTable> unitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                final AdUnitTable unitTable = new AdUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                        default:
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(u -> AdLevelDataHandler.handleLevel3(u, rowData.getOpType()));
        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {
            final ArrayList<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                final AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setCreativeId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                        default:
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(u -> AdLevelDataHandler.handleLevel3(u, rowData.getOpType()));
        }
    }

    /**
     * 4层级投递 直接switch判断
     * @param rowData
     */
    private void level4RowDateSend(MySqlRowData rowData) {
        switch (rowData.getTableName()) {
            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                final ArrayList<AdUnitDistrictTable> districtTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    final AdUnitDistrictTable districtTable = new AdUnitDistrictTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                districtTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                districtTable.setProvince(v);
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                districtTable.setCity(v);
                                break;
                            default:
                                break;
                        }
                    });
                    districtTables.add(districtTable);
                }
                districtTables.forEach(d -> AdLevelDataHandler.handleLevel4(d, rowData.getOpType()));
                break;
            case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:
                final ArrayList<AdUnitItTable> itTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    final AdUnitItTable itTable = new AdUnitItTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                itTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                itTable.setItTag(v);
                                break;
                            default:
                                break;
                        }
                    });
                    itTables.add(itTable);
                }
                itTables.forEach(i -> AdLevelDataHandler.handleLevel4(i, rowData.getOpType()));
                break;
            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:
                final ArrayList<AdUnitKeywordTable> keywordTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    final AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                            default:
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                }
                keywordTables.forEach(k -> AdLevelDataHandler.handleLevel4(k, rowData.getOpType()));
                break;
            default:
                break;
        }
    }

}
