/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : copy

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-09-04 15:22:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_gap_data
-- ----------------------------
DROP TABLE IF EXISTS `tb_gap_data`;
CREATE TABLE `tb_gap_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_type` varchar(50) DEFAULT NULL,
  `data` varchar(4096) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6192 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
DROP TRIGGER IF EXISTS `trig_ins_tb_gap_data`;
DELIMITER ;;
CREATE TRIGGER `trig_ins_tb_gap_data` AFTER INSERT ON `tb_gap_data` FOR EACH ROW BEGIN
 DECLARE value_set VARCHAR(20000);
 set value_set= concat('(',new.id,',',
 concat_hf(new.data_type      ),',',
 concat_hf(new.data        ),')');
INSERT INTO sync_police_config(dest_table,action,action_sql)
values('tb_gap_data','insert',value_set);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trig_update_tb_gap_data`;
DELIMITER ;;
CREATE TRIGGER `trig_update_tb_gap_data` AFTER UPDATE ON `tb_gap_data` FOR EACH ROW BEGIN
 DECLARE value_set VARCHAR(20000);
 set value_set= concat('(',new.id,',',
 concat_hf(new.data_type      ),',',
 concat_hf(new.data        ),')');
INSERT INTO sync_police_config(dest_table,action,action_sql)
values('tb_gap_data','insert',value_set);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trig_del_tb_gap_data`;
DELIMITER ;;
CREATE TRIGGER `trig_del_tb_gap_data` AFTER DELETE ON `tb_gap_data` FOR EACH ROW BEGIN
INSERT INTO sync_police_config(dest_table,action,action_sql)
values('tb_gap_data','delete',old.id);
END
;;
DELIMITER ;
