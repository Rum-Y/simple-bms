/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80041
 Source Host           : localhost:3306
 Source Schema         : trabel-db

 Target Server Type    : MySQL
 Target Server Version : 80041
 File Encoding         : 65001

 Date: 12/05/2025 12:24:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for eb_user
-- ----------------------------
DROP TABLE IF EXISTS `eb_user`;
CREATE TABLE `eb_user`  (
  `uid` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '使用者id',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '使用者賬號',
  `pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '使用者密碼',
  `real_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '真實姓名',
  `birthday` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '生日',
  `card_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '身份證號碼',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '使用者備註',
  `partner_id` int(0) NULL DEFAULT NULL COMMENT '合夥人id',
  `group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '使用者分組id',
  `tag_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '小程序的登录id openid',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '使用者昵稱',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '使用者頭像',
  `phone` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手機號碼',
  `add_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '新增ip',
  `last_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最後一次登錄ip',
  `now_money` decimal(16, 2) UNSIGNED NULL DEFAULT 0.00 COMMENT '使用者餘額 已经弃用 金额数目不正常 主要看冻结的',
  `brokerage_price` decimal(16, 2) NULL DEFAULT 0.00 COMMENT '傭金金額',
  `integral` int(0) NULL DEFAULT 0 COMMENT '使用者剩餘積分',
  `experience` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '使用者剩餘經驗',
  `sign_num` int(0) NULL DEFAULT 0 COMMENT '連續簽到天數',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '1為正常，0為禁止',
  `level` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '等級',
  `spread_uid` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '推廣員id',
  `spread_time` timestamp(0) NULL DEFAULT NULL COMMENT '推廣員關聯時間',
  `user_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '使用者型別',
  `is_promoter` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '是否為推廣員',
  `pay_count` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '使用者購買次數',
  `spread_count` int(0) NULL DEFAULT 0 COMMENT '下級人數',
  `addres` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '詳細地址',
  `adminid` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '管理員編號  0非管理员 1是管理员',
  `login_type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '使用者登陸型別，h5,wechat,routine',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '建立時間',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新時間',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最後一次登錄時間',
  `clean_time` timestamp(0) NULL DEFAULT NULL COMMENT '清除時間',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/0/' COMMENT '推廣等級記錄',
  `subscribe` tinyint(0) NULL DEFAULT 0 COMMENT '是否關注公眾號',
  `subscribe_time` timestamp(0) NULL DEFAULT NULL COMMENT '關注公眾號時間',
  `sex` tinyint(1) NULL DEFAULT 1 COMMENT '性別，0未知，1男，2女，3保密',
  `country` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'CN' COMMENT '國家，中國CN，其他OTHER',
  `promoter_time` timestamp(0) NULL DEFAULT NULL COMMENT '成為分銷員時間',
  `ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邀请成功的用户uid',
  `invite_phone` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邀请人手机号',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在地区',
  `sector` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业领域',
  `isDelete` tinyint(1) NULL DEFAULT 0 COMMENT '0代表不删除  1代表删除',
  `withdrawable` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '可提现金额',
  `freezeMoney` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '冻结中金额\r\n冻结中金额',
  `version` int(0) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  INDEX `spreaduid`(`spread_uid`) USING BTREE,
  INDEX `level`(`level`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `is_promoter`(`is_promoter`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '使用者表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
