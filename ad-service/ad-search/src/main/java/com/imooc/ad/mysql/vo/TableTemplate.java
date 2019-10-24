package com.imooc.ad.mysql.vo;

import com.imooc.ad.mysql.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/24 21:53
 * @description 为了方便操作时读取表的一些信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();
    /**
     * 字段索引 -》 字段名 因为现实的是索引而不是直接现实字段信息
     */
    private Map<Integer, String> posMap = new HashMap<>();

}
