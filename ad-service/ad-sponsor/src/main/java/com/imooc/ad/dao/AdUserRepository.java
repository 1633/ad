package com.imooc.ad.dao;

import com.imooc.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seven
 * @Date 2019/10/19 0:41
 * @Description 操作user表的数据库持久层
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {
    /**
     * 通过用户名查询用户(唯一的)
     * @param username
     * @return
     */
    AdUser findAdUserByUsername(String username);

}
