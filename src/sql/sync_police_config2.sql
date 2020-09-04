/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : copy

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-09-04 15:23:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sync_police_config2
-- ----------------------------
DROP TABLE IF EXISTS `sync_police_config2`;
CREATE TABLE `sync_police_config2` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `dest_table` varchar(64) DEFAULT NULL,
  `action` varchar(16) DEFAULT NULL,
  `action_sql` varchar(20000) DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=67273 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
