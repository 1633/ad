package com.imooc.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.mysql.vo.ParseTemplate;
import com.imooc.ad.mysql.vo.TableTemplate;
import com.imooc.ad.mysql.vo.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/24 22:31
 * @description 1、解析模版数据，2、实现索引到列名的映射关系
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "SELECT table_schema, table_name, column_name, ordinal_position " +
            "FROM information_schema.`COLUMNS` WHERE table_schema = ? AND table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 类在创建时执行，实现加载解析json文件
     */
    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {
        // 用类加载器定位文件位置
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        final InputStream inStream = cl.getResourceAsStream(path);

        try {
            final Template template = JSON.parseObject(inStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("fail to parse json file");
        }

    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            final TableTemplate table = entry.getValue();
            final List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            final List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            final List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{template.getDatabase(), table.getTableName()}, (rs, i) -> {
                final int pos = rs.getInt("ORDINAL_POSITION");
                final String colName = rs.getString("COLUMN_NAME");

                final boolean match = null != updateFields && updateFields.contains(colName)
                        || null != insertFields && insertFields.contains(colName)
                        || null != deleteFields && deleteFields.contains(colName);
                if (match) {
                    table.getPosMap().put(pos-1, colName);
                }

                return null;
            });
        }
    }

}
