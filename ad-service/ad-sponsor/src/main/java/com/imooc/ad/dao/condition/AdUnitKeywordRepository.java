package com.imooc.ad.dao.condition;

import com.imooc.ad.entity.condition.AdUnitKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seven
 * @date 2019/10/19 1:00
 * @description AdUnitKeyword的数据库持久层操作 使用默认方法即可
 */
public interface AdUnitKeywordRepository extends JpaRepository<AdUnitKeyword, Long> {

}
