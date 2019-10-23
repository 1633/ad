/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.22 : Database - imooc_ad_data
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
-- CREATE DATABASE /*!32312 IF NOT EXISTS*/`imooc_ad_data` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- USE `imooc_ad_data`;

/*Table structure for table `ad_creative` */

DROP TABLE IF EXISTS `ad_creative`;

CREATE TABLE `ad_creative` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创意所属用户',
  `NAME` varchar(48) NOT NULL COMMENT '广告创意名称',
  `TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '广告创意类型，图片、视频、文本等类型',
  `material_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '广告创意的物料类型 如图片有：jpg、png、bmp等',
  `height` bigint(10) NOT NULL DEFAULT '0' COMMENT '物料的高度',
  `width` bigint(10) NOT NULL DEFAULT '0' COMMENT '物料的宽度',
  `size` bigint(20) NOT NULL DEFAULT '0' COMMENT '物料的大小 kb、字节等',
  `duration` bigint(10) NOT NULL DEFAULT '0' COMMENT '物料的时长，视频不为0，其余为0',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '物料的审核状态',
  `url` varchar(512) NOT NULL COMMENT '物料的url信息',
  `create_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '创意创建时间',
  `update_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '创意修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='广告创意表';

/*Data for the table `ad_creative` */

insert  into `ad_creative`(`id`,`user_id`,`NAME`,`TYPE`,`material_type`,`height`,`width`,`size`,`duration`,`audit_status`,`url`,`create_time`,`update_time`) values (1,1,'漫天小卡片',1,1,250,340,180,0,1,'http://www.jd.com','2019-10-19 22:25:00','2019-10-19 22:25:00'),(2,1,'塞门缝卡',2,2,180,250,160,0,1,'http://www.baidu.com','2019-10-19 23:25:00','2019-10-19 23:25:00'),(3,2,'发传单',3,3,110,240,150,0,1,'http://www.126.com','2019-10-19 23:30:00','2019-10-19 23:30:00'),(4,2,'短视频',4,4,960,1080,10240,180,1,'http://www.163.com','2019-10-19 23:45:00','2019-10-19 23:45:00');

/*Table structure for table `ad_plan` */

DROP TABLE IF EXISTS `ad_plan`;

CREATE TABLE `ad_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广计划所属用户',
  `plan_name` varchar(48) NOT NULL COMMENT '推广计划名称',
  `plan_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '推广计划状态',
  `start_date` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '推广计划开始时间',
  `end_date` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '推广计划结束时间',
  `create_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '推广计划创建时间',
  `update_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '推广计划修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='推广计划表';

/*Data for the table `ad_plan` */

insert  into `ad_plan`(`id`,`user_id`,`plan_name`,`plan_status`,`start_date`,`end_date`,`create_time`,`update_time`) values (1,1,'init-plan',1,'2019-10-20 12:00:00','2019-10-27 12:00:00','2019-10-19 22:20:00','2019-10-19 22:20:00'),(2,1,'end-plan',1,'2019-10-27 12:00:00','2019-11-03 12:00:00','2019-10-19 23:00:00','2019-10-19 23:00:00'),(3,2,'init-plan',1,'2019-10-20 12:00:00','2019-10-27 12:00:00','2019-10-19 23:10:00','2019-10-19 23:10:00'),(4,2,'end-plan',1,'2019-10-27 12:00:00','2019-11-03 12:00:00','2019-10-19 23:10:00','2019-10-19 23:10:00');

/*Table structure for table `ad_unit` */

DROP TABLE IF EXISTS `ad_unit`;

CREATE TABLE `ad_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `plan_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广单元所属计划',
  `unit_name` varchar(48) NOT NULL COMMENT '推广单元名称',
  `unit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '推广单元状态',
  `position_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '广告展开位置类型 开屏、贴片、中贴、暂停贴等位置',
  `budget` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广单元预算金额',
  `create_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '推广单元创建时间',
  `update_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '推广单元修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='推广单元表';

/*Data for the table `ad_unit` */

insert  into `ad_unit`(`id`,`plan_id`,`unit_name`,`unit_status`,`position_type`,`budget`,`create_time`,`update_time`) values (1,1,'init-unit',1,1,3000,'2019-10-19 22:20:00','2019-10-19 22:20:00'),(2,1,'end-unit',1,2,6000,'2019-10-19 23:20:00','2019-10-19 23:20:00'),(3,2,'init-unit',1,3,2500,'2019-10-19 23:30:00','2019-10-19 23:30:00'),(4,2,'end-unit',1,2,5500,'2019-10-19 23:30:00','2019-10-19 23:30:00');

/*Table structure for table `ad_unit_district` */

DROP TABLE IF EXISTS `ad_unit_district`;

CREATE TABLE `ad_unit_district` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `unit_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属推广单元',
  `province` varchar(64) NOT NULL COMMENT '推广省份',
  `city` varchar(128) NOT NULL COMMENT '推广城市',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='广告单元の推广地域表';

/*Data for the table `ad_unit_district` */

insert  into `ad_unit_district`(`id`,`unit_id`,`province`,`city`) values (1,1,'湖南','怀化'),(2,1,'湖南','长沙'),(3,1,'广东','广州'),(4,1,'广东','深圳'),(5,1,'黑龙江','哈尔滨'),(6,2,'湖南','常德'),(7,2,'湖南','张家界'),(8,2,'湖北','荆门'),(9,2,'湖北','武汉'),(10,3,'江苏','常州'),(11,3,'广东','汕尾'),(12,3,'广东','珠海'),(13,3,'云南','昆明'),(14,4,'四川','成都'),(15,4,'浙江','杭州'),(16,4,'浙江','苏州'),(17,4,'福建','福州');

/*Table structure for table `ad_unit_it` */

DROP TABLE IF EXISTS `ad_unit_it`;

CREATE TABLE `ad_unit_it` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `unit_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属推广单元',
  `it_tag` varchar(128) NOT NULL COMMENT '兴趣标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='广告单元の兴趣标签表';

/*Data for the table `ad_unit_it` */

insert  into `ad_unit_it`(`id`,`unit_id`,`it_tag`) values (1,1,'游戏'),(2,1,'电影'),(3,1,'体育'),(4,1,'赛车'),(5,1,'文学'),(6,2,'跑步'),(7,2,'游泳'),(8,2,'骑自行车'),(9,2,'散步'),(10,3,'太极拳'),(11,3,'形意拳'),(12,3,'螳螂拳'),(13,3,'梅花桩'),(14,4,'龙门'),(15,4,'飞甲'),(16,4,'黄金甲'),(17,4,'铁布衫');

/*Table structure for table `ad_unit_keyword` */

DROP TABLE IF EXISTS `ad_unit_keyword`;

CREATE TABLE `ad_unit_keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `unit_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属推广单元',
  `keyword` varchar(128) NOT NULL COMMENT '关键词',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='广告单元の关键词表';

/*Data for the table `ad_unit_keyword` */

insert  into `ad_unit_keyword`(`id`,`unit_id`,`keyword`) values (1,1,'LOL'),(2,1,'风暴英雄'),(3,1,'守望先锋'),(4,1,'DOTA2'),(5,2,'LOL'),(6,2,'DOTA2'),(7,2,'机械键盘'),(8,2,'公交车'),(9,3,'小米'),(10,3,'魅族'),(11,3,'华为'),(12,3,'iPhone'),(13,4,'显卡'),(14,4,'SSD'),(15,4,'内存条'),(16,4,'主板'),(17,4,'水冷机箱');

/*Table structure for table `ad_user` */

DROP TABLE IF EXISTS `ad_user`;

CREATE TABLE `ad_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(256) NOT NULL DEFAULT '' COMMENT '用户名',
  `token` varchar(256) NOT NULL DEFAULT '' COMMENT '按username生成的md5 token',
  `user_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `create_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '2019-10-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `ad_user` */

insert  into `ad_user`(`id`,`username`,`token`,`user_status`,`create_time`,`update_time`) values (1,'yijialu','yiyijialujiayijialuluyijialu',1,'2019-10-19 22:17:36','2019-10-19 22:17:36'),(2,'liuxiaomei','fghrwtgfs45756fgh',1,'2019-10-19 23:00:00','2019-10-19 23:00:00');

/*Table structure for table `creative_unit` */

DROP TABLE IF EXISTS `creative_unit`;

CREATE TABLE `creative_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `creative_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创意的id',
  `unit_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广单元的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='广告单元与创意中间表';

/*Data for the table `creative_unit` */

insert  into `creative_unit`(`id`,`creative_id`,`unit_id`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,2,2),(7,2,3),(8,2,4),(9,3,1),(10,3,2),(11,3,3),(12,3,4),(13,4,1),(14,4,2),(15,4,3),(16,4,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
