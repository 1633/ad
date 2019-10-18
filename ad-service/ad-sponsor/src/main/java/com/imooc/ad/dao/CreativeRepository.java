package com.imooc.ad.dao;

import com.imooc.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seven
 * @date 2019/10/19 0:57
 * @description 操作创意表的数据库持久层 使用默认方法即可
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {

}
