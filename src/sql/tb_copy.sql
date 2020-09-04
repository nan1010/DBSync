/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-09-04 15:23:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_copy
-- ----------------------------
DROP TABLE IF EXISTS `tb_copy`;
CREATE TABLE `tb_copy` (
  `id` bigint(20) NOT NULL,
  `data_type` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `data` varchar(4096) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `JRMGAH_1` (`id`),
  UNIQUE KEY `VRBMPR_2` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
