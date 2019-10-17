package com.imooc.ad.entity;

import com.zaxxer.hikari.util.FastList;
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
 * @Date 2019/10/18 0:11
 * @Description 推广之 创意
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_creative")
public class Creative {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * 创意名称
     */
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 创意主类型 图片、视频、文本等类型
     */
    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;
    /**
     * 物料类型 如图片是jpg、png等
     */
    @Basic
    @Column(name = "material_type", nullable = false)
    private Integer materialType;
    /**
     * 物料的高度
     */
    @Basic
    @Column(name = "height", nullable = false)
    private Integer height;
    /**
     * 物料的宽度
     */
    @Basic
    @Column(name = "width", nullable = false)
    private Integer width;
    /**
     * 物料的大小 kb、字节等
     */
    @Basic
    @Column(name = "size", nullable = false)
    private Long size;
    /**
     * 物料的时长，视频不为0
     */
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration;
    /**
     * 物料的审核状态
     */
    @Basic
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;
    /**
     * 物料上传的用户ID 名义上的外键
     */
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
    /**
     * 物料的地址信息
     */
    @Basic
    @Column(name = "url", nullable = false)
    private String url;
    /**
     * 创意被创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    /**
     * 创意更新时间
     */
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

}
