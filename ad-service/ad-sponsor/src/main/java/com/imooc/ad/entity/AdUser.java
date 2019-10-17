package com.imooc.ad.entity;

import com.imooc.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Seven
 * @Date 2019/10/17 23:11
 * @Description 与数据库对应的实体对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {
    /**
     * id自增字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * 用户名称
     */
    @Basic
    @Column(name = "username", nullable = false)
    private String username;
    /**
     * 用户token
     */
    @Basic
    @Column(name = "token", nullable = false)
    private String token;
    /**
     * 用@Transient标识为非数据库表对应的信息，默认@Basic可不填
     * 用户可用状态
     */
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;
    /**
     * 用户被创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    /**
     * 用户信息更新时间
     */
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    /**
     * 因为其他属性默认值，所以自定义构造函数
     *
     * @param username
     * @param token
     */
    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
    }
}
