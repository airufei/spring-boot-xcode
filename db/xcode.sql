/*
 Navicat Premium Data Transfer

 Source Server         : xmf_db_dev
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 192.168.1.56:3306
 Source Schema         : xcode

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 17/09/2020 15:28:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for x_code_scheme
-- ----------------------------
DROP TABLE IF EXISTS `x_code_scheme`;
CREATE TABLE `x_code_scheme`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `category` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `package_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sub_module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `function_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `function_name_simple` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `function_author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '生成表编号',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `flag` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '删除标记（0：正常；1：删除）',
  `module_page_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '页面模块',
  `sub_page_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '子包',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '代码生成路径',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `gen_scheme_del_flag`(`flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '子模块' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for x_code_table
-- ----------------------------
DROP TABLE IF EXISTS `x_code_table`;
CREATE TABLE `x_code_table`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `comments` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `flag` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `gen_table_name`(`name`) USING BTREE,
  INDEX `gen_table_del_flag`(`flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '备注信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for x_code_table_column
-- ----------------------------
DROP TABLE IF EXISTS `x_code_table_column`;
CREATE TABLE `x_code_table_column`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `comments` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `jdbc_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_pk` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `show_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sort` decimal(10, 0) NULL DEFAULT NULL COMMENT '排序（升序）',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `flag` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '删除标记（0：正常；1：删除）',
  `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_editPage` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编辑字段',
  `isInsertRequiredField` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '插入必须字段 1 非必须0',
  `isUpdateRequiredField` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '插入必须字段 1 非必须0',
  `is_sort` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '是否排序 1排序，0不排序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `gen_table_column_table_id`(`table_id`) USING BTREE,
  INDEX `gen_table_column_name`(`name`) USING BTREE,
  INDEX `gen_table_column_sort`(`sort`) USING BTREE,
  INDEX `gen_table_column_del_flag`(`flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 985 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '表名称' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for x_code_menu
-- ----------------------------
DROP TABLE IF EXISTS `x_code_menu`;
CREATE TABLE `x_code_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单地址',
  `isbutton` int(1) NULL DEFAULT 0 COMMENT '是否按钮 0不是 1是',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `flag` int(1) NULL DEFAULT -1 COMMENT '删除标记 -1删除 1正常',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `fid` bigint(20) NULL DEFAULT -1 COMMENT '父级菜单ID',
  `level` int(11) NULL DEFAULT 1 COMMENT '菜单等级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_code_menu
-- ----------------------------
INSERT INTO `x_code_menu` VALUES (1, '系统管理', NULL, 0, '2018-12-20 21:45:31', '2018-12-20 23:02:55', 1, '系统管理', -1, 1);
INSERT INTO `x_code_menu` VALUES (2, '菜单管理', '/menu', 0, '2018-12-20 21:46:20', '2018-12-20 21:46:23', 1, '菜单管理', 1, 2);
INSERT INTO `x_code_menu` VALUES (3, '调度管理', NULL, 0, '2018-12-20 22:32:27', '2018-12-20 22:39:04', -1, '调度任务', -1, 1);
INSERT INTO `x_code_menu` VALUES (4, '调度任务', '/jobinfo', 0, '2018-12-20 22:56:43', '2018-12-20 22:56:43', -1, '调度任务', 3, 2);
INSERT INTO `x_code_menu` VALUES (5, '调度日志', '/joblog', 0, '2018-12-20 22:57:29', '2018-12-20 22:57:29', -1, '调度日志', 3, 2);
INSERT INTO `x_code_menu` VALUES (7, '系统监控', NULL, 0, '2018-12-20 23:00:54', '2018-12-20 23:00:54', -1, '系统监控', -1, 1);
INSERT INTO `x_code_menu` VALUES (8, 'redis监控', '/redis', 0, '2018-12-20 23:02:02', '2018-12-20 23:02:25', -1, 'redis监控', 7, 2);
INSERT INTO `x_code_menu` VALUES (9, '执行器管理', '/jobgroup', 0, '2018-12-20 23:23:17', '2018-12-20 23:23:17', -1, '执行器管理', 3, 2);
INSERT INTO `x_code_menu` VALUES (10, '字典管理', '/dict', 0, '2018-12-20 23:24:23', '2018-12-20 23:24:23', -1, '字典管理', 1, 2);
INSERT INTO `x_code_menu` VALUES (11, '用户管理', '/user', 0, '2018-12-20 23:25:37', '2018-12-20 23:25:37', 1, '用户管理', 1, 2);
INSERT INTO `x_code_menu` VALUES (12, '角色管理', '/role', 0, '2018-12-20 23:26:10', '2018-12-20 23:26:10', 1, '角色管理', 1, 2);
INSERT INTO `x_code_menu` VALUES (19, '代码生成', NULL, 0, '2020-09-16 01:27:26', '2020-09-16 01:28:16', 1, '代码生成', -1, 1);
INSERT INTO `x_code_menu` VALUES (20, '表配置', '/codeTable', 0, '2020-09-16 01:29:02', '2020-09-16 01:29:02', 1, '表配置', 19, 2);
INSERT INTO `x_code_menu` VALUES (21, '代码生成配置', '/codeScheme', 0, '2020-09-16 22:17:53', '2020-09-16 22:17:53', 1, NULL, 19, 2);

-- ----------------------------
-- Table structure for x_code_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `x_code_menu_role`;
CREATE TABLE `x_code_menu_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `flag` int(1) NULL DEFAULT 1 COMMENT '删除标记 -1删除 1正常',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_menu_id`(`menu_id`) USING BTREE,
  INDEX `idx_role_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单-角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_code_menu_role
-- ----------------------------
INSERT INTO `x_code_menu_role` VALUES (65, 19, 19, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');
INSERT INTO `x_code_menu_role` VALUES (66, 19, 21, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');
INSERT INTO `x_code_menu_role` VALUES (67, 19, 20, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');
INSERT INTO `x_code_menu_role` VALUES (68, 19, 1, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');
INSERT INTO `x_code_menu_role` VALUES (69, 19, 12, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');
INSERT INTO `x_code_menu_role` VALUES (70, 19, 11, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');
INSERT INTO `x_code_menu_role` VALUES (71, 19, 2, '2020-09-16 22:18:03', '2020-09-16 22:18:03', 1, NULL, 'admin_role');

-- ----------------------------
-- Table structure for x_code_role
-- ----------------------------
DROP TABLE IF EXISTS `x_code_role`;
CREATE TABLE `x_code_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `flag` int(1) NULL DEFAULT -1 COMMENT '删除标记 -1删除 1正常',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_updatetime`(`update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_code_role
-- ----------------------------
INSERT INTO `x_code_role` VALUES (19, '管理员', '2018-12-19 22:44:55', '2020-09-16 22:18:03', 1, '管理员', 'admin_role');

-- ----------------------------
-- Table structure for x_code_user
-- ----------------------------
DROP TABLE IF EXISTS `x_code_user`;
CREATE TABLE `x_code_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `age` int(11) NULL DEFAULT 18 COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `qq` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `wechart` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `flag` int(11) NULL DEFAULT -1 COMMENT '删除标记 1正常 -1删除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_phone`(`phone`) USING BTREE,
  INDEX `idx_user_updatetime`(`UPDATE_TIME`) USING BTREE,
  INDEX `idx_user_role`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '调度中心用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_code_user
-- ----------------------------
INSERT INTO `x_code_user` VALUES (1, 'admin', '30e229876358062f5d83bc824c81a99e', 18, '199199688@qq.com', '18610000006', NULL, NULL, NULL, '2018-12-19 22:17:19', '2018-12-24 00:22:16', 1, '222', 'admin_role');

SET FOREIGN_KEY_CHECKS = 1;
