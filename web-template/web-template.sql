/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : 192.168.1.10:3306
 Source Schema         : web_template

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 20/06/2023 22:04:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0未删除，1-已删除）',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限编码',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code` ASC) USING BTREE COMMENT '权限编码唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('379d7459ae85f7bd4d848e4b8ac470b0', NULL, '2023-06-18 19:56:14', NULL, '2023-06-18 20:01:15', 0, '路由管理-查询', 'system:router:query', '系统管理中路由管理的查询列表权限');
INSERT INTO `t_permission` VALUES ('4824f94af1ba2d890e77b8978730ab4d', NULL, '2023-06-18 19:57:20', NULL, '2023-06-18 20:09:23', 0, '路由管理-状态', 'system:router:status', '系统管理中路由管理的修改状态权限');
INSERT INTO `t_permission` VALUES ('48daa42a171f4af7ade1e66b66899b9b', NULL, '2023-06-18 19:28:18', NULL, '2023-06-18 19:28:41', 0, '权限管理-删除', 'system:permission:del', '系统管理中权限管理的删除权限');
INSERT INTO `t_permission` VALUES ('62cf88701f66eef280c80cb680e8bc83', NULL, '2023-06-20 17:19:36', NULL, '2023-06-20 17:19:36', 0, '用户管理-新增', 'system:user:add', '系统管理中用户管理的新增权限');
INSERT INTO `t_permission` VALUES ('641026c3ed3afb772e485b63f01ef871', NULL, '2023-06-18 20:05:25', NULL, '2023-06-18 20:05:25', 0, '角色管理-新增', 'system:role:add', '系统管理中角色管理的新增权限');
INSERT INTO `t_permission` VALUES ('73bd0f9273dd889cc79b7f61a33b9354', NULL, '2023-06-18 19:26:04', NULL, '2023-06-18 19:26:04', 0, '权限管理-查询', 'system:permission:query', '系统管理中权限管理的查询列表权限');
INSERT INTO `t_permission` VALUES ('7674a387c978b9e41dbdf1e480cdcdd1', NULL, '2023-06-18 19:56:32', NULL, '2023-06-18 20:01:46', 0, '路由管理-编辑', 'system:router:edit', '系统管理中路由管理的编辑权限\n');
INSERT INTO `t_permission` VALUES ('7aa1218ecb5f78b84c672045918cd686', NULL, '2023-06-18 20:08:46', NULL, '2023-06-18 20:08:46', 0, '角色管理-状态', 'system:role:status', '系统管理中角色管理的修改状态权限');
INSERT INTO `t_permission` VALUES ('81fcb6bb0a6d2588c5b28b6fa8f967b8', NULL, '2023-06-20 17:18:33', NULL, '2023-06-20 17:18:33', 0, '用户管理-查询', 'system:user:query', '系统管理中用户管理的查询列表权限');
INSERT INTO `t_permission` VALUES ('8c6ac303d8709dcc5370cf4bf239e985', NULL, '2023-06-18 19:55:49', NULL, '2023-06-18 20:00:45', 0, '路由管理-新增', 'system:router:add', '系统管理中路由管理的新增权限');
INSERT INTO `t_permission` VALUES ('8e2203248e70c5d7889bde881754ef54', NULL, '2023-06-18 19:57:02', NULL, '2023-06-18 20:02:17', 0, '路由管理-删除', 'system:router:del', '系统管理中路由管理的删除权限');
INSERT INTO `t_permission` VALUES ('a5f9ced7be44f7620dd1b6a048fdcd5d', NULL, '2023-06-18 20:04:35', NULL, '2023-06-18 20:04:35', 0, '角色管理-删除', 'system:role:del', '系统管理中角色管理的删除权限');
INSERT INTO `t_permission` VALUES ('b703e783535ccca5f9c28fcd9e5e8f45', NULL, '2023-06-20 17:22:54', NULL, '2023-06-20 17:22:54', 0, '用户管理-重置密码', 'system:user:resetPwd', '系统管理中用户管理的重置密码权限');
INSERT INTO `t_permission` VALUES ('bce92abf0733b8e4a113998a263a98f3', NULL, '2023-06-20 17:20:13', NULL, '2023-06-20 17:20:13', 0, '用户管理-编辑', 'system:user:edit', '系统管理中用户管理的编辑权限');
INSERT INTO `t_permission` VALUES ('be4b0f8a5604bd0b98ba89bf6b7cdef3', NULL, '2023-06-18 20:06:10', NULL, '2023-06-18 20:06:10', 0, '角色管理-编辑', 'system:role:edit', '系统管理中角色管理的编辑权限\n');
INSERT INTO `t_permission` VALUES ('c501bf85727ca12ceb3d38bccd009671', NULL, '2023-06-18 19:27:56', NULL, '2023-06-18 19:28:30', 0, '权限管理-编辑', 'system:permission:edit', '系统管理中权限管理的编辑权限');
INSERT INTO `t_permission` VALUES ('cde8a17f2a5d8c0f4599512a8d86a7c6', NULL, '2023-06-20 17:20:52', NULL, '2023-06-20 17:20:52', 0, '用户管理-删除', 'system:user:del', '系统管理中用户管理的删除权限');
INSERT INTO `t_permission` VALUES ('cf6a7f4eacde3bee08c5f81592b06789', NULL, '2023-06-18 20:07:12', NULL, '2023-06-20 17:18:48', 0, '角色管理-查询', 'system:role:query', '系统管理中角色管理的查询列表权限');
INSERT INTO `t_permission` VALUES ('e8b9be7173948e41f710d356bddfe279', NULL, '2023-06-18 19:27:13', NULL, '2023-06-18 19:27:27', 0, '权限管理-新增', 'system:permission:add', '系统管理中权限管理的新增权限\n');
INSERT INTO `t_permission` VALUES ('fc2aeb74718a1d2f949a4c556d5ecff3', NULL, '2023-06-20 17:21:49', NULL, '2023-06-20 17:21:49', 0, '用户管理-状态', 'system:user:status', '系统管理中用户管理的修改状态权限');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0未删除，1-已删除）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '角色描述',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色标识',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '启用状态（默认启用）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code` ASC) USING BTREE COMMENT '角色编码唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('80af1ff16e92fe1b4bab4ca3fd64799c', '王玉星', '2023-06-08 20:49:42', '王玉星', '2023-06-08 20:49:42', 0, '普通管理员', '普通管理角色', 'common_role', 1);
INSERT INTO `t_role` VALUES ('b93682e6781df977a5f641d3711e6296', '王玉星', '2023-06-09 15:50:08', '王玉星', '2023-06-18 19:23:10', 0, '通用人员', '通用人员', 'common', 1);
INSERT INTO `t_role` VALUES ('c114ad97d1aa8d5ec81d7c67cd689313', '王玉星', '2023-06-05 21:38:40', '王玉星', '2023-06-20 17:27:39', 0, '超级管理员', '超级管理员', 'admin', 1);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `permission_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限Id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_permission_id`(`permission_id` ASC) USING BTREE COMMENT '权限Id索引',
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE COMMENT '角色Id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('17118f9c674fa6a0052c73d46a4c00c3', '81fcb6bb0a6d2588c5b28b6fa8f967b8', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('180c2fd1e99ec8a998b181b0ca4041a1', '7674a387c978b9e41dbdf1e480cdcdd1', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('1bfb34eb6bb37e86f57a4f437c580548', '62cf88701f66eef280c80cb680e8bc83', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('239bf00a54828da4260797a91312daa0', 'cf6a7f4eacde3bee08c5f81592b06789', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('288b3d4ad1dadae3b468069dc69b8bd1', '641026c3ed3afb772e485b63f01ef871', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('42ece587aff1121d4bb2a1a111917963', 'be4b0f8a5604bd0b98ba89bf6b7cdef3', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('6085d41634726237234e872733ba6538', 'cde8a17f2a5d8c0f4599512a8d86a7c6', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('64cfa69c9c4dd04ce499f4c7d2f897d4', '379d7459ae85f7bd4d848e4b8ac470b0', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('656e1af13884840ea3f881cc1c7607ca', '8e2203248e70c5d7889bde881754ef54', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('69c59b606addbe9a3667613b0024cbb7', '48daa42a171f4af7ade1e66b66899b9b', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('81669e5df0f223c8654d84adf40d1c44', 'c501bf85727ca12ceb3d38bccd009671', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('848db1620dce190748c277cc11a51b3e', '7aa1218ecb5f78b84c672045918cd686', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('9ca5288cc148b3eb345f685245de4c48', 'b703e783535ccca5f9c28fcd9e5e8f45', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('bb42cf1c8812eeb23082dbe11660d2cd', '4824f94af1ba2d890e77b8978730ab4d', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('c21280a0396260d44e25b3b7e7e32394', 'e8b9be7173948e41f710d356bddfe279', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('c96fe5c6068dafc7dbd1aa380190245a', 'bce92abf0733b8e4a113998a263a98f3', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('d1341a696bf887e55c92ab91ced6082f', 'a5f9ced7be44f7620dd1b6a048fdcd5d', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('e6d37bb8eb132f8b9acc600eac52f45f', '73bd0f9273dd889cc79b7f61a33b9354', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('f9be35a7753b55654b74488642dd899e', 'fc2aeb74718a1d2f949a4c556d5ecff3', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_role_permission` VALUES ('fe3d3ca19bb025ba93d7c175e81c4b26', '8c6ac303d8709dcc5370cf4bf239e985', 'c114ad97d1aa8d5ec81d7c67cd689313');

-- ----------------------------
-- Table structure for t_router
-- ----------------------------
DROP TABLE IF EXISTS `t_router`;
CREATE TABLE `t_router`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0未删除，1-已删除）',
  `parent_router_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '父级路由Id',
  `path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由路径',
  `component` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由组件',
  `redirect` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '路由名称',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '路由类型（0、目录 1、菜单）',
  `state` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否路由启用状态（0、停用，1、启用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_router
-- ----------------------------
INSERT INTO `t_router` VALUES ('01eac41102049f4c1bbf5e976e1ba9a4', '王玉星', '2023-06-11 19:09:45', '王玉星', '2023-06-13 15:43:23', 0, '1e6d865dfef354a6444dc8020f7b6d97', '/system/role', 'system/role/index', '', 'Role', 1, 1);
INSERT INTO `t_router` VALUES ('1e6d865dfef354a6444dc8020f7b6d97', '王玉星', '2023-06-11 19:02:48', '王玉星', '2023-06-12 09:35:54', 0, '0', '/system', '', '', '', 0, 1);
INSERT INTO `t_router` VALUES ('8590cdf5e76288e35f20d7cb1dc5ad71', '王玉星', '2023-06-12 11:29:14', '王玉星', '2023-06-12 12:51:12', 0, '1e6d865dfef354a6444dc8020f7b6d97', '/system/permission', 'system/permission/index', '', 'Permission', 1, 1);
INSERT INTO `t_router` VALUES ('952c6c6eafc89e0ba1a9136af7678606', '王玉星', '2023-06-11 19:03:59', '王玉星', '2023-06-13 12:53:05', 0, '1e6d865dfef354a6444dc8020f7b6d97', '/system/router', 'system/router/index', '', 'Router', 1, 1);
INSERT INTO `t_router` VALUES ('9c7554940c61664fde947d52967f8ec0', '王玉星', '2023-06-11 19:08:09', '王玉星', '2023-06-13 15:45:22', 0, '1e6d865dfef354a6444dc8020f7b6d97', '/system/user', 'system/user/index', '', 'User', 1, 1);

-- ----------------------------
-- Table structure for t_router_meta
-- ----------------------------
DROP TABLE IF EXISTS `t_router_meta`;
CREATE TABLE `t_router_meta`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `router_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由Id',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由名称',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由图标',
  `extra_icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由额外图标',
  `show_link` tinyint(1) NULL DEFAULT 1 COMMENT '路由是否显示',
  `show_parent` tinyint(1) NULL DEFAULT 1 COMMENT '路由显示父目录',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '是否开启路由组件缓存',
  `frame_src` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内嵌的iframe链接',
  `frame_loading` tinyint(1) NULL DEFAULT NULL COMMENT '是否开启iframe首次加载动画',
  `hidden_tag` tinyint(1) NULL DEFAULT 1 COMMENT '是否添加路由到页签',
  `dynamic_level` int NULL DEFAULT NULL COMMENT '动态路由可打开的最大数量',
  `transition` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '路由页面加载动画',
  `rank` int NULL DEFAULT NULL COMMENT '路由排序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_router_id`(`router_id` ASC) USING BTREE COMMENT '路由Id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_router_meta
-- ----------------------------
INSERT INTO `t_router_meta` VALUES ('01eac41102049f4c1bbf5e976e1ba9a4', '01eac41102049f4c1bbf5e976e1ba9a4', 'menus.hsRole', 'role', '', 1, 1, 0, '', 1, 0, NULL, '{\"enterTransition\":\"animate__bounceInDown\",\"leaveTransition\":\"animate__bounceOutDown\",\"name\":\"左放大进场-右缩小离场\"}', 3);
INSERT INTO `t_router_meta` VALUES ('1e6d865dfef354a6444dc8020f7b6d97', '1e6d865dfef354a6444dc8020f7b6d97', 'menus.hssysManagement', 'flUser', '', 1, 1, 0, '', 1, 1, NULL, '{\"enterTransition\":\"\",\"leaveTransition\":\"\",\"name\":\"\"}', 20);
INSERT INTO `t_router_meta` VALUES ('8590cdf5e76288e35f20d7cb1dc5ad71', '8590cdf5e76288e35f20d7cb1dc5ad71', 'menus.hsPermission', 'role', '', 1, 1, 0, '', 1, 0, NULL, '{\"enterTransition\":\"\",\"leaveTransition\":\"\",\"name\":\"\"}', 0);
INSERT INTO `t_router_meta` VALUES ('952c6c6eafc89e0ba1a9136af7678606', '952c6c6eafc89e0ba1a9136af7678606', 'menus.hsRouter', 'role', '', 1, 1, 0, '', 1, 0, NULL, '{\"enterTransition\":\"animate__bounce\",\"leaveTransition\":\"animate__backOutDown\"}', 1);
INSERT INTO `t_router_meta` VALUES ('9c7554940c61664fde947d52967f8ec0', '9c7554940c61664fde947d52967f8ec0', 'menus.hsUser', 'flUser', '', 1, 1, 0, '', 1, 0, NULL, '{}', 4);

-- ----------------------------
-- Table structure for t_router_role
-- ----------------------------
DROP TABLE IF EXISTS `t_router_role`;
CREATE TABLE `t_router_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `router_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由Id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_router_role
-- ----------------------------
INSERT INTO `t_router_role` VALUES ('1d33108020700c59537bc18c4c7e0017', '01eac41102049f4c1bbf5e976e1ba9a4', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_router_role` VALUES ('64a903aec19d09aec561d0d2bd2c7598', '9c7554940c61664fde947d52967f8ec0', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_router_role` VALUES ('8a9afa41c39c08fdb3d54262b90471f4', '952c6c6eafc89e0ba1a9136af7678606', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_router_role` VALUES ('a663c2e113e23cb858b7c56e727a0bda', '8590cdf5e76288e35f20d7cb1dc5ad71', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_router_role` VALUES ('d01aed9a1d48991a4f7678b3bba8e01a', 'e7c452dff4f375e349617fc53ad139a1', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_router_role` VALUES ('e8d74b654fd0b68cc5f8c7c1bf41042e', 'ad3a4ec8bdfecdedd9c334dd6f6e0655', 'c114ad97d1aa8d5ec81d7c67cd689313');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0未删除，1-已删除）',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话号码',
  `status` tinyint NULL DEFAULT 1 COMMENT '用户状态',
  `pass_word` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '头像地址',
  `gender` tinyint(1) NOT NULL DEFAULT 1 COMMENT '性别（1-男，0-女。默认值：1）',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('807e3a0cb28483965748794af4d060da', NULL, '2023-06-19 14:26:54', NULL, '2023-06-20 21:55:15', 0, 'test', '测试用户', NULL, '3333', 'edd', 1, '$2a$10$81wyDegqlwowPOBV.DEvwOd0cGnOlXdbrPjMGQqc9j/XKOAetkHhC', NULL, 1, 'fff');
INSERT INTO `t_user` VALUES ('f7414562a282467081d30760a6732251', 'admin', '2023-06-17 17:49:48', 'admin', '2023-06-20 21:55:12', 0, 'admin', '超级管理员', NULL, '309597117@qq.com', '17384206580', 1, '$2a$10$W2qlRVJm0YtNCGGar5dbKuWJksdkSeer2NAd8mL1F0/phLQe3KBUW', NULL, 1, NULL);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户Id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE COMMENT '用户Id索引',
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE COMMENT '角色Id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('0dc46f8cd7e2d230369af57a19ffe18b', 'f7414562a282467081d30760a6732251', '80af1ff16e92fe1b4bab4ca3fd64799c');
INSERT INTO `t_user_role` VALUES ('32bdcc72b2f8c1b8ded042301862f74d', 'f7414562a282467081d30760a6732251', 'c114ad97d1aa8d5ec81d7c67cd689313');
INSERT INTO `t_user_role` VALUES ('5408ad4550f51f297beb33eef5baf0a2', 'f7414562a282467081d30760a6732251', 'b93682e6781df977a5f641d3711e6296');

SET FOREIGN_KEY_CHECKS = 1;
