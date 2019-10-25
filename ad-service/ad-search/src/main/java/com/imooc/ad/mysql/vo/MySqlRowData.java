package com.imooc.ad.mysql.vo;

import com.imooc.ad.mysql.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/25 22:54
 * @description 增量数据投递对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;
    private String level;
    private OpType opType;
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

}
