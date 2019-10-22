package com.imooc.ad.index;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.dump.DirConstant;
import com.imooc.ad.dump.table.AdCreativeTable;
import com.imooc.ad.dump.table.AdCreativeUnitTable;
import com.imooc.ad.dump.table.AdPlanTable;
import com.imooc.ad.dump.table.AdUnitDistrictTable;
import com.imooc.ad.dump.table.AdUnitItTable;
import com.imooc.ad.dump.table.AdUnitKeywordTable;
import com.imooc.ad.dump.table.AdUnitTable;
import com.imooc.ad.handler.AdLevelDataHandler;
import com.imooc.ad.mysql.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seven
 * @date 2019/10/22 21:50
 * @description 加载所有的索引信息 读取.data数据转换 必须滞后于DataTable加载 @DependsOn
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {
    /**
     * 类一初始化就需要执行该方法
     */
    @PostConstruct
    public void init() {
        // 加载推广计划类型的数据
        final List<String> planStrings = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_PLAN));
        planStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        // 加载创意类型的数据
        final List<String> creativeStrings = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_CREATIVE));
        creativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdCreativeTable.class), OpType.ADD));

        // 加载推广单元的数据
        final List<String> unitStrings = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT));
        unitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(u, AdUnitTable.class), OpType.ADD));

        // 推广单元创意关联数据
        final List<String> creativeUnits = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_CREATIVE_UNIT));
        creativeStrings.forEach(cu -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(cu, AdCreativeUnitTable.class), OpType.ADD));

        // 地域、兴趣、关键词数据
        final List<String> districtStrings = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT_DISTRICT));
        districtStrings.forEach(ds -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(ds, AdUnitDistrictTable.class), OpType.ADD));

        final List<String> itStrings = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT_IT));
        itStrings.forEach(is -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(is, AdUnitItTable.class), OpType.ADD));

        final List<String> keywordStrings = loadDumpData(String.format("%s%s",
                DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT_KEYWORD));
        keywordStrings.forEach(ks -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(ks, AdUnitKeywordTable.class), OpType.ADD));
    }


    /**
     * 指定文件名，读取一行行数据到list中并返回
     *
     * @param fileName
     * @return
     */
    private List<String> loadDumpData(String fileName) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(fileName))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
