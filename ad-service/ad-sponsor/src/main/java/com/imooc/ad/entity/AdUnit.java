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
 * @Date 2019/10/17 23:45
 * @Description 推广单元实体类 与数据库表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit")
public class AdUnit {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * 计划id 名义上的外键
     */
    @Basic
    @Column(name = "plan_id", nullable = false)
    private Long planId;
    /**
     * 推广单元名称
     */
    @Basic
    @Column(name = "unit_name", nullable = false)
    private String unitName;
    /**
     * 推广单元状态
     */
    @Basic
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;
    /**
     * 广告展开位置类型 开屏、贴片、中贴、暂停贴等位置
     */
    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;
    /**
     * 预算金额
     */
    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;
    /**
     * 推广单元创建时间
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    /**
     * 推广单元修改时间
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    /**
     * 因为其他属性默认值，所以自定义构造函数
     *
     * @param planId
     * @param unitName
     * @param positionType
     * @param budget
     */
    public AdUnit(Long planId, String unitName, Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        this.positionType = positionType;
        this.budget = budget;
        this.unitStatus = CommonStatus.VALID.getStatus();
        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
    }

}
