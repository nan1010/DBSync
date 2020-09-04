/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-09-04 15:23:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_copy2
-- ----------------------------
DROP TABLE IF EXISTS `tb_copy2`;
CREATE TABLE `tb_copy2` (
  `id` bigint(20) NOT NULL,
  `data_type` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `data` varchar(4096) CHARACTER SET utf8 DEFAULT NULL,
  `data_desc` varchar(4096) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `JRMGAH_1` (`id`),
  UNIQUE KEY `VRBMPR_2` (`id`),
  UNIQUE KEY `USGCVG_2` (`id`),
  UNIQUE KEY `ACPUQU_2` (`id`),
  UNIQUE KEY `FCWUXF_2` (`id`),
  UNIQUE KEY `GLRMDV_2` (`id`),
  UNIQUE KEY `JHKIOD_2` (`id`),
  UNIQUE KEY `GUYYTV_2` (`id`),
  UNIQUE KEY `WZZKDK_2` (`id`),
  UNIQUE KEY `LCIECI_2` (`id`),
  UNIQUE KEY `KYJZDZ_2` (`id`),
  UNIQUE KEY `PHYFOM_2` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
