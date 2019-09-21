/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : mars_web

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-06-29 19:33:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority_mark` varchar(255) DEFAULT NULL,
  `authority_name` varchar(255) DEFAULT NULL,
  `authority_url` varchar(255) DEFAULT NULL,
  `available` bit(1) NOT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `indexnum` int(11) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE,
  CONSTRAINT `sys_authority_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_authority` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('1', null, '系统权限', null, '', 'glyphicon  glyphicon-cog', '1', null, null);
INSERT INTO `sys_authority` VALUES ('2', 'system.userlist', '用户管理', '', '', null, '1', null, '1');
INSERT INTO `sys_authority` VALUES ('3', 'system.orglist', '机构管理', null, '\0', null, '2', null, '1');
INSERT INTO `sys_authority` VALUES ('4', 'system.config', '系统参数', null, '', null, '3', null, '1');
INSERT INTO `sys_authority` VALUES ('5', 'system.task', '任务管理', null, '\0', null, '4', null, '1');
INSERT INTO `sys_authority` VALUES ('6', 'system.logger', '系统日志', '', '', null, '5', '', '1');
INSERT INTO `sys_authority` VALUES ('10', null, '注册用户', null, '', 'glyphicon  glyphicon-cog', '1', null, null);
INSERT INTO `sys_authority` VALUES ('11', 'info.list', '用户管理', '', '', null, '1', null, '10');
INSERT INTO `sys_authority` VALUES ('12', 'info.notice', '通知公告', NULL, b'1', NULL, '2', NULL, '10');


-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `indexnum` int(10) DEFAULT NULL,
  `module` varchar(10) DEFAULT NULL,
  `value` int(10) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_code
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `configkey` varchar(255) DEFAULT NULL,
  `configname` varchar(255) DEFAULT NULL,
  `configvalue` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `module` varchar(10) DEFAULT NULL,
  `groupindex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('15', 'sys_user_login_init_password', '用户初始化密码', '123456', '1', null, 'base', '登陆配置');
INSERT INTO `sys_config` VALUES ('16', 'sys.user.password.update.hint', '密码修改提示时间(天)', '20', '1', null, 'base', '登陆配置');
INSERT INTO `sys_config` VALUES ('17', 'sys.user.password.err.lock.time', '锁定账户密码错误次数', '5', '1', null, 'base', '登陆配置');
INSERT INTO `sys_config` VALUES ('18', 'sys.user.login.session.out.time', '用户登录超时时间', '30', '1', null, 'base', '登陆配置');

INSERT INTO `sys_config` VALUES ('19', 'machine_code_password', '加密串', 'hookon2018', '1', NULL, 'base', '注册配置');
INSERT INTO `sys_config` VALUES ('20', 'renewal_month_count', '授权时长(月份)', '1', '1', NULL, 'base', '注册配置');


-- ----------------------------
-- Table structure for sys_idtable
-- ----------------------------
DROP TABLE IF EXISTS `sys_idtable`;
CREATE TABLE `sys_idtable` (
  `code` varchar(255) NOT NULL DEFAULT '',
  `value` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_idtable
-- ----------------------------

-- ----------------------------
-- Table structure for sys_optlog
-- ----------------------------
DROP TABLE IF EXISTS `sys_optlog`;
CREATE TABLE `sys_optlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `browser_version` varchar(255) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `module` varchar(20) DEFAULT NULL,
  `opt_type` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `available` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_optlog
-- ----------------------------
INSERT INTO `sys_optlog` VALUES ('1', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 09:59:48', null, '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('2', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 10:00:11', '1', '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('3', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 19:51:47', null, '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('4', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 19:51:58', '1', '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('5', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 20:41:32', null, '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('6', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 20:44:33', '1', '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('7', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 20:49:51', null, '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('8', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 20:49:59', '1', '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('9', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 20:50:31', '1', '退出登录', '');
INSERT INTO `sys_optlog` VALUES ('10', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-28 20:50:42', '1', '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('11', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-29 13:49:48', null, '用户登录', '');
INSERT INTO `sys_optlog` VALUES ('12', 'Chrome/67.0.3396.62', '127.0.0.1', '系统管理', '登录', null, null, '2018-06-29 13:49:58', '1', '用户登录', '');

-- ----------------------------
-- Table structure for sys_orga
-- ----------------------------
DROP TABLE IF EXISTS `sys_orga`;
CREATE TABLE `sys_orga` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orga_name` varchar(255) NOT NULL,
  `available` bit(1) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `lead_id` bigint(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `reserve` bit(1) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE,
  CONSTRAINT `sys_orga_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_orga` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_orga
-- ----------------------------
INSERT INTO `sys_orga` VALUES ('1', '无名科技', '', null, '深圳市福田中心区益田路江苏大厦', null, '', '1', '2017-07-26 17:49:35', '1', '2017-10-31 18:14:00', '', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `remark` varchar(1000) DEFAULT NULL,
  `role_name` varchar(255) NOT NULL,
  `available` bit(1) NOT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `reserve` bit(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理/角色列表', '超级管理员', '', '1', '', '2017-07-20 22:02:30', '1', '2018-06-19 11:03:45');

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`authority_id`),
  KEY `authority_id` (`authority_id`) USING BTREE,
  CONSTRAINT `sys_role_authority_ibfk_1` FOREIGN KEY (`authority_id`) REFERENCES `sys_authority` (`id`),
  CONSTRAINT `sys_role_authority_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_authority
-- ----------------------------
INSERT INTO `sys_role_authority` VALUES ('1', '1');
INSERT INTO `sys_role_authority` VALUES ('1', '2');
INSERT INTO `sys_role_authority` VALUES ('1', '3');
INSERT INTO `sys_role_authority` VALUES ('1', '4');
INSERT INTO `sys_role_authority` VALUES ('1', '5');
INSERT INTO `sys_role_authority` VALUES ('1', '6');
INSERT INTO `sys_role_authority` VALUES ('1', '10');
INSERT INTO `sys_role_authority` VALUES ('1', '11');

-- ----------------------------
-- Table structure for sys_role_operate
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_operate`;
CREATE TABLE `sys_role_operate` (
  `role_id` bigint(20) NOT NULL,
  `operate_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`operate_id`),
  KEY `sys_role_operate_ibfk_1` (`operate_id`) USING BTREE,
  CONSTRAINT `sys_role_operate_ibfk_1` FOREIGN KEY (`operate_id`) REFERENCES `sys_authority` (`id`),
  CONSTRAINT `sys_role_operate_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_operate
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(255) NOT NULL COMMENT '登录账号',
  `name` varchar(10) NOT NULL COMMENT '性名',
  `address` varchar(255) DEFAULT NULL COMMENT 'ip所在地',
  `available` bit(1) NOT NULL COMMENT '状态',
  `email` varchar(255) DEFAULT NULL,
  `commission` bigint(20) DEFAULT NULL,
  `complete` int(10) DEFAULT NULL,
  `zjh` varchar(100) DEFAULT NULL COMMENT '证件号',
  `tel` varchar(255) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `orga_id` bigint(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  `reserve` bit(1) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `add_date` date DEFAULT NULL COMMENT '入职时间',
  `position` varchar(40) DEFAULT NULL COMMENT '职位',
  `passwd_update_time` datetime DEFAULT NULL COMMENT '修改密码时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `ip` varchar(100) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `job_no` varchar(20) DEFAULT NULL COMMENT '工号',
  `weight` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '系统管理员', '1', '', 'lixl@tianjiwealth.com', null, null, '23232', '18118756468', '11111', null, 'e10adc3949ba59abbe56e057f20f883e', null, null, null, '2017-07-29', '1', '2017-04-14 14:47:17', '2018-06-29 13:49:58', '172.17.2.123', '1', '2017-07-29 19:12:08', '1', '2018-04-12 11:01:11', '1000', null);

-- ----------------------------
-- Table structure for sys_user_orga
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_orga`;
CREATE TABLE `sys_user_orga` (
  `user_id` bigint(20) NOT NULL,
  `orga_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`orga_id`),
  KEY `sys_user_orga_ibfk_2` (`orga_id`) USING BTREE,
  CONSTRAINT `sys_user_orga_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_orga_ibfk_2` FOREIGN KEY (`orga_id`) REFERENCES `sys_orga` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_orga
-- ----------------------------
INSERT INTO `sys_user_orga` VALUES ('1', '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '性名',
  `machine_code` varchar(255) NOT NULL COMMENT '机器码',
  `serial_key` varchar(100) DEFAULT NULL COMMENT '序列号',
  `expiration_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  `last_use_time` timestamp NULL DEFAULT NULL COMMENT '最后一次使用时间',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建者',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `available` bit(1) NOT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------

CREATE TABLE `user_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `content` text COMMENT '内容',
  `type` tinyint(2) DEFAULT NULL COMMENT '分类',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建者',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `available` bit(1) NOT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


INSERT INTO `user_notice`  VALUES ('1', '系统公告', '程序系统公告', '1', '1', '2018-07-02 18:46:05', '1', '2018-07-02 18:46:08', b'1', '');

