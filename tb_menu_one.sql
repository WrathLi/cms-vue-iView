/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : my_dev

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 15/11/2018 19:37:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_menu_one
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu_one`;
CREATE TABLE `tb_menu_one`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示名称',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'ios-folder-outline' COMMENT '图标',
  `sort_index` int(10) NULL DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu_one
-- ----------------------------
INSERT INTO `tb_menu_one` VALUES (1, 'privilege-mgr', '权限管理', 'ios-folder-outline', 1, 0);

-- ----------------------------
-- Table structure for tb_menu_page
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu_page`;
CREATE TABLE `tb_menu_page`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `one_id` int(11) NULL DEFAULT 0 COMMENT '一级菜单ID',
  `route_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示标题',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'document' COMMENT '图标',
  `sort_index` int(10) NULL DEFAULT 0 COMMENT '排序',
  `hide_page` tinyint(1) NULL DEFAULT 0 COMMENT '隐藏页面',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu_page
-- ----------------------------
INSERT INTO `tb_menu_page` VALUES (1, 1, 'menu-mgr', '菜单页面', 'document', 1, 0, 0);
INSERT INTO `tb_menu_page` VALUES (2, 1, 'role-mgr', '角色管理', 'document', 2, 0, 0);
INSERT INTO `tb_menu_page` VALUES (3, 1, 'role-privilege', '角色权限', 'document', 3, 0, 0);
INSERT INTO `tb_menu_page` VALUES (4, 1, 'user-mgr', '用户管理', 'document', 4, 0, 0);

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `is_super` tinyint(1) NOT NULL DEFAULT 0 COMMENT '超级管理员',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime(0) NULL,
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, 'admin', 1, '管理员', '2018-11-04 18:54:41', 0);

-- ----------------------------
-- Table structure for tb_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_privilege`;
CREATE TABLE `tb_role_privilege`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT 0 COMMENT '角色ID',
  `page_id` int(11) NULL DEFAULT 0 COMMENT '页面ID',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `phone_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'email',
  `create_time` datetime(0) NULL,
  `role_id` int(11) NULL DEFAULT 0,
  `freezed` tinyint(1) NULL DEFAULT 0 COMMENT '冻结',
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (1, 'admin', '管理员', '123456', '111', '11', '2018-11-04 18:55:22', 1, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
