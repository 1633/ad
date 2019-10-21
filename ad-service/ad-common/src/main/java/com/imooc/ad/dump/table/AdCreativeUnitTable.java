package com.imooc.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seven
 * @date 2019/10/20 21:04
 * @description 对应数据库创意与推广单元中间表要导出的字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeUnitTable {

    private Long unitId;
    private Long creativeId;

}
