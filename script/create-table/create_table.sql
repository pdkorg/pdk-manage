/*
Navicat MySQL Data Transfer

Source Server         : 103.19.66.3-root
Source Server Version : 50621
Source Host           : 103.19.66.3:3306
Source Database       : pdk_dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-10-23 22:14:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pdk_bd_address`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_address`;
CREATE TABLE `pdk_bd_address` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`receiver`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`city_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`area_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`street`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`post_num`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phone`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_default`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`full_address`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
INDEX `userId` (`user_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_city_area`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_city_area`;
CREATE TABLE `pdk_bd_city_area` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  smallint(6) NULL DEFAULT NULL ,
`parent_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_goods`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_goods`;
CREATE TABLE `pdk_bd_goods` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`goodstype_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_goodstype`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_goodstype`;
CREATE TABLE `pdk_bd_goodstype` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_position`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_position`;
CREATE TABLE `pdk_bd_position` (
`id`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_quick_reply`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_quick_reply`;
CREATE TABLE `pdk_bd_quick_reply` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`info`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`sort`  smallint(6) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_shop`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_shop`;
CREATE TABLE `pdk_bd_shop` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`info`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_bd_unit`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_bd_unit`;
CREATE TABLE `pdk_bd_unit` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_chat_chatmsg`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_chat_chatmsg`;
CREATE TABLE `pdk_chat_chatmsg` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`from_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`from_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_to_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_to_name`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`msg_type`  smallint(6) NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
`content`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source`  smallint(6) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`from_head_img`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_to_head_img`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `I_chat_chatmsg_1` (`from_id`) USING BTREE ,
INDEX `I_chat_chatmsg_2` (`send_to_id`) USING BTREE 
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_chat_unreadmsg`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_chat_unreadmsg`;
CREATE TABLE `pdk_chat_unreadmsg` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_head_img`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`msg_type`  smallint(6) NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
`content`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source`  smallint(6) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
INDEX `I_chat_unreadmsg_1` (`user_id`) USING BTREE 
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon`;
CREATE TABLE `pdk_coupon` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`activity_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_mny`  decimal(20,2) NULL DEFAULT NULL ,
`min_pay_mny`  decimal(20,2) NULL DEFAULT NULL ,
`act_begin_date`  date NULL DEFAULT NULL ,
`act_end_date`  date NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`template_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon_activity`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon_activity`;
CREATE TABLE `pdk_coupon_activity` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`auto_send_time`  datetime NULL DEFAULT NULL ,
`send_message`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_user_cnt`  int(11) NULL DEFAULT NULL ,
`send_total_mny`  decimal(20,2) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon_activity_rule`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon_activity_rule`;
CREATE TABLE `pdk_coupon_activity_rule` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`activity_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`connect_type`  smallint(6) NULL DEFAULT NULL ,
`rule_type`  smallint(6) NULL DEFAULT NULL ,
`opt_type`  smallint(6) NULL DEFAULT NULL ,
`chk_val`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`parent_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon_activity_template`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon_activity_template`;
CREATE TABLE `pdk_coupon_activity_template` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon_activity_template_b`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon_activity_template_b`;
CREATE TABLE `pdk_coupon_activity_template_b` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`template_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`coupon_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_mny`  decimal(20,2) NULL DEFAULT NULL ,
`min_pay_mny`  decimal(20,2) NULL DEFAULT NULL ,
`delay_days`  int(11) NULL DEFAULT NULL ,
`act_days`  int(11) NULL DEFAULT NULL ,
`begin_date`  date NULL DEFAULT NULL ,
`end_date`  date NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon_user_relation`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon_user_relation`;
CREATE TABLE `pdk_coupon_user_relation` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`coupon_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`order_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`send_mny`  decimal(20,2) NULL DEFAULT NULL ,
`min_pay_mny`  decimal(20,2) NULL DEFAULT NULL ,
`begin_date`  date NULL DEFAULT NULL ,
`end_date`  date NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`template_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `source_id` (`source_id`) USING BTREE ,
INDEX `user_id` (`user_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_coupon_user_sended`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_coupon_user_sended`;
CREATE TABLE `pdk_coupon_user_sended` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`activity_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`real_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`times`  int(11) NULL DEFAULT NULL ,
`pay_mny`  decimal(20,2) NULL DEFAULT NULL ,
`register_date`  date NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_flow_flowtype`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_flow_flowtype`;
CREATE TABLE `pdk_flow_flowtype` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`is_auto_assign_deliver`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`deliver_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_flow_flowunit`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_flow_flowunit`;
CREATE TABLE `pdk_flow_flowunit` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`order_status`  smallint(6) NULL DEFAULT NULL ,
`flow_action_code`  smallint(6) NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_flow_template`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_flow_template`;
CREATE TABLE `pdk_flow_template` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_flow_template_instance`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_flow_template_instance`;
CREATE TABLE `pdk_flow_template_instance` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`template_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_flow_template_instanceunit`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_flow_template_instanceunit`;
CREATE TABLE `pdk_flow_template_instanceunit` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`template_instance_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_unit_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`template_unit_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_finish`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`finish_time`  datetime NULL DEFAULT NULL ,
`op_employee_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`op_time`  datetime NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`is_push_msg`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`msg_template`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`iq`  smallint(6) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_flow_template_unit`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_flow_template_unit`;
CREATE TABLE `pdk_flow_template_unit` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`template_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_type_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_unit_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`role_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`is_push_msg`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`msg_template`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`iq`  smallint(6) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_message`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_message`;
CREATE TABLE `pdk_message` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`employee_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  smallint(6) NULL DEFAULT NULL ,
`message`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`is_read`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fr_employee_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_load`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_order_order`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_order_order`;
CREATE TABLE `pdk_order_order` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flowtype_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ywy_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`adress`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`delivery_status`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pay_type`  smallint(6) NULL DEFAULT NULL ,
`reserve_time`  datetime NULL DEFAULT NULL ,
`mny`  decimal(20,2) NULL DEFAULT 0.00 ,
`actual_mny`  decimal(20,2) NULL DEFAULT 0.00 ,
`coupon_mny`  decimal(20,2) NULL DEFAULT 0.00 ,
`pay_status`  smallint(6) NULL DEFAULT 0 ,
`start_time`  datetime NULL DEFAULT NULL ,
`end_time`  datetime NULL DEFAULT NULL ,
`shop_manager_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`cs_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flow_instance_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`is_flow_finish`  smallint(6) NULL DEFAULT 0 ,
`fee_mny`  decimal(20,2) NULL DEFAULT 0.00 ,
`flow_action`  smallint(6) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_order_orderdetail`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_order_orderdetail`;
CREATE TABLE `pdk_order_orderdetail` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`order_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`num`  double(10,2) NULL DEFAULT 0.00 ,
`unit_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`buy_adress`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`price`  decimal(20,2) NULL DEFAULT 0.00 ,
`service_mny`  decimal(20,2) NULL DEFAULT 0.00 ,
`oth_mny`  decimal(20,2) NULL DEFAULT 0.00 ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`img_url`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`reserve_time`  datetime NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`goods_mny`  decimal(20,2) NULL DEFAULT 0.00 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_order_tip`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_order_tip`;
CREATE TABLE `pdk_order_tip` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`employee_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`order_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mny`  decimal(20,2) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_employee`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_employee`;
CREATE TABLE `pdk_sm_employee` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`org_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`role_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sex`  smallint(6) NULL DEFAULT NULL ,
`id_card`  char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phone`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`password`  char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`header_img`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`position_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_employee_auxiliary`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_employee_auxiliary`;
CREATE TABLE `pdk_sm_employee_auxiliary` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`employee_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`role_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_employee_review`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_employee_review`;
CREATE TABLE `pdk_sm_employee_review` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`employee_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`order_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`score`  int(11) NULL DEFAULT NULL ,
`description`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
INDEX `I_sm_e_review_1` (`order_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_file`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_file`;
CREATE TABLE `pdk_sm_file` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`length`  int(11) NULL DEFAULT NULL ,
`type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`path`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`category`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_func`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_func`;
CREATE TABLE `pdk_sm_func` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`incode`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`href`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`icon`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`parent_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`sq`  smallint(6) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_funcassign`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_funcassign`;
CREATE TABLE `pdk_sm_funcassign` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`func_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`role_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_org`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_org`;
CREATE TABLE `pdk_sm_org` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`incode`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`info`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`parent_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sq`  smallint(6) NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_permission_func`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_permission_func`;
CREATE TABLE `pdk_sm_permission_func` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`role_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`func_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_role`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_role`;
CREATE TABLE `pdk_sm_role` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_user`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_user`;
CREATE TABLE `pdk_sm_user` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
`type`  smallint(6) NULL DEFAULT NULL ,
`real_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sex`  smallint(6) NULL DEFAULT NULL ,
`age`  smallint(6) NULL DEFAULT NULL ,
`phone`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`register_time`  datetime NULL DEFAULT NULL ,
`un_register_time`  datetime NULL DEFAULT NULL ,
`header_img`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL ,
`memo`  varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`event_key`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `source_id` (`source_id`) USING BTREE 
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_user_describe`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_user_describe`;
CREATE TABLE `pdk_sm_user_describe` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`vdescribe`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `pdk_sm_user_register_info`
-- ----------------------------
DROP TABLE IF EXISTS `pdk_sm_user_register_info`;
CREATE TABLE `pdk_sm_user_register_info` (
`id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`source_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`register_time`  datetime NULL DEFAULT NULL ,
`un_register_time`  datetime NULL DEFAULT NULL ,
`ts`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`dr`  smallint(6) NULL DEFAULT 0 ,
`old_sys_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `source_id` (`source_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;
