package com.imooc.ad;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.condition.AdUnitItRepository;
import com.imooc.ad.dao.condition.AdUnitKeywordRepository;
import com.imooc.ad.dao.condition.CreativeUnitRepository;
import com.imooc.ad.dump.DirConstant;
import com.imooc.ad.dump.table.AdCreativeTable;
import com.imooc.ad.dump.table.AdCreativeUnitTable;
import com.imooc.ad.dump.table.AdPlanTable;
import com.imooc.ad.dump.table.AdUnitDistrictTable;
import com.imooc.ad.dump.table.AdUnitItTable;
import com.imooc.ad.dump.table.AdUnitKeywordTable;
import com.imooc.ad.dump.table.AdUnitTable;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.entity.Creative;
import com.imooc.ad.entity.condition.AdUnitDistrict;
import com.imooc.ad.entity.condition.AdUnitIt;
import com.imooc.ad.entity.condition.AdUnitKeyword;
import com.imooc.ad.entity.condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seven
 * @date 2019/10/20 21:18
 * @description 实际是从数据库导出数据的操作类(用测试的状态导出) 无需配置环境NONE
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SponsorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpTest {

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;
    @Autowired
    private AdUnitItRepository unitItRepository;
    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;


    /**
     * 导出推广计划数据
     */
    @Test
    public void dumpAdPlanTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_PLAN);
        List<AdPlan> plans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        // null则直接返回
        if (CollectionUtils.isEmpty(plans)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdPlanTable> planTables = new ArrayList<>();
        plans.forEach(p -> {
            final AdPlanTable planTable = new AdPlanTable();
            BeanUtils.copyProperties(p, planTable);
            planTables.add(planTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump AdPlanTable error!");
        }
    }


    /**
     * 导出推广单元数据
     */
    @Test
    public void dumpAdUnitTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT);
        final List<AdUnit> units = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        // null则直接返回
        if (CollectionUtils.isEmpty(units)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdUnitTable> unitTables = new ArrayList<>();
        units.forEach(p -> {
            final AdUnitTable unitTable = new AdUnitTable();
            BeanUtils.copyProperties(p, unitTable);
            unitTables.add(unitTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump AdUnitTable error!");
        }
    }


    /**
     * 导出创意数据
     */
    @Test
    public void dumpCreativeTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_CREATIVE);
        final List<Creative> creatives = creativeRepository.findAll();
        // null则直接返回
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(p -> {
            final AdCreativeTable creativeTable = new AdCreativeTable();
            BeanUtils.copyProperties(p, creativeTable);
            creativeTables.add(creativeTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump AdCreativeTable error!");
        }
    }


    /**
     * 导出创意与单元关联表数据
     */
    @Test
    public void dumpCreativeUnitTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_CREATIVE_UNIT);
        final List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        // null则直接返回
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdCreativeUnitTable> creativeTables = new ArrayList<>();
        creativeUnits.forEach(p -> {
            final AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();
            BeanUtils.copyProperties(p, creativeUnitTable);
            creativeTables.add(creativeUnitTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump AdCreativeUnitTable error!");
        }
    }


    /**
     * 导出地域表数据
     */
    @Test
    public void dumpUnitDistrictTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT_DISTRICT);
        final List<AdUnitDistrict> unitDistricts = unitDistrictRepository.findAll();
        // null则直接返回
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(p -> {
            final AdUnitDistrictTable unitDistrictTable = new AdUnitDistrictTable();
            BeanUtils.copyProperties(p, unitDistrictTable);
            unitDistrictTables.add(unitDistrictTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump UnitDistrictTable error!");
        }
    }


    /**
     * 导出兴趣表数据
     */
    @Test
    public void dumpUnitItTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT_IT);
        final List<AdUnitIt> unitIts = unitItRepository.findAll();
        // null则直接返回
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(p -> {
            final AdUnitItTable unitItTable = new AdUnitItTable();
            BeanUtils.copyProperties(p, unitItTable);
            unitItTables.add(unitItTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump UnitItTable error!");
        }
    }


    /**
     * 导出关键字表数据
     */
    @Test
    public void dumpUnitKeywordTable() {
        String fileName = String.format("%s%s", DirConstant.DATA_ROOT_DIR, DirConstant.AD_UNIT_KEYWORD);
        final List<AdUnitKeyword> unitKeywords = unitKeywordRepository.findAll();
        // null则直接返回
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }
        // 否则工具类复制属性并添加
        ArrayList<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(p -> {
            final AdUnitKeywordTable unitKeywordTable = new AdUnitKeywordTable();
            BeanUtils.copyProperties(p, unitKeywordTable);
            unitKeywordTables.add(unitKeywordTable);
        });
        // 设置路径并写入文件
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump UnitKeywordTable error!");
        }
    }

}
