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
 * @Date 2019/10/17 23:32
 * @Description 推广计划实体类 与数据库表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {
    /**
     * id自增字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * 关联的用户id 名义上的外键
     */
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
    /**
     * 计划名称
     */
    @Basic
    @Column(name = "plan_name", nullable = false)
    private String planName;
    /**
     * 计划状态
     */
    @Basic
    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;
    /**
     * 计划开始时间
     */
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startData;
    /**
     * 计划结束时间
     */
    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endData;
    /**
     * 计划创建时间
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    /**
     * 计划修改时间
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    /**
     * 因为其他属性默认值，所以自定义构造函数
     *
     * @param userId
     * @param planName
     * @param startData
     * @param endData
     */
    public AdPlan(Long userId, String planName, Date startData, Date endData) {
        this.userId = userId;
        this.planName = planName;
        this.startData = startData;
        this.endData = endData;
        this.planStatus = CommonStatus.VALID.getStatus();
        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
    }
}
