package com.imooc.ad.dao.condition;

import com.imooc.ad.entity.condition.CreativeUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seven
 * @date 2019/10/19 1:04
 * @description CreativeUnit的数据库持久层操作 使用默认方法即可
 */
public interface CreativeUnitRepository extends JpaRepository<CreativeUnit, Long> {

}
