/*
Navicat MySQL Data Transfer

Source Server         : 10.161.172.188
Source Server Version : 50538
Source Host           : 10.161.172.188:3306
Source Database       : source_v3

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2017-05-12 14:31:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for v3unit_jingzongunit
-- ----------------------------
DROP TABLE IF EXISTS `v3unit_jingzongunit`;
CREATE TABLE `v3unit_jingzongunit` (
  `v3_code` varchar(255) NOT NULL COMMENT 'v3编码',
  `mc` varchar(255) DEFAULT NULL COMMENT 'mc',
  `jc` varchar(255) DEFAULT NULL COMMENT 'jc',
  `zt` varchar(255) DEFAULT NULL COMMENT 'zt',
  `jz_code` varchar(255) DEFAULT NULL COMMENT '警综编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of v3unit_jingzongunit
-- ----------------------------
INSERT INTO `v3unit_jingzongunit` VALUES ('520114000000', '贵阳市公安局经济技术开发区分局', '经开分局', '1', '520199000000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114540000', '贵阳市公安局经济技术开发区分局三江派出所', '三江派出所', '1', '520199630000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114030000', '贵阳市公安局经济技术开发区分局治安大队', '治安大队', '1', '520199030000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114020000', '贵州省贵阳市公安局小河区分局刑事经济犯罪侦查大队', '经侦大队', '1', '520199020000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114090000', '贵州省贵阳市公安局经济开发区分局消防大队', '消防大队', '1', '520103X00000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114050100', '贵州省贵阳市公安局经济技术开发区分局刑事侦查大队技术科', '技术科', '1', '520199050100');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114210000', '贵阳市公安局经济技术开发区分局禁毒大队', '禁毒大队', '1', '520199050100');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114160000', '贵阳市公安局经济技术开发区分局戒毒刑拘所', '戒毒所', '1', '5201998B0000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114320000', '贵阳市公安局经济技术开发区分局纪委监察室', '纪委监察室', '1', '520199320000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114080000', '贵阳市公安局经济技术开发区分局网安大队', '网安大队', '1', '520199110000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114180000', '贵阳市公安局经济技术开发区分局法制大队', '法制大队', '1', '520199180000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114520000', '贵阳市公安局经济技术开发区分局平桥派出所', '平桥派出所', '1', '520199610000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114230000', '贵阳市公安局经济技术开发区分局长江路派出所', '长江派出所', '1', '520199650000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114350000', '贵阳市公安局经济技术开发区分局政工监督室', '政工科', '1', '520199350000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114530000', '贵阳市公安局经济技术开发区分局金竹派出所', '金竹派出所', '1', '520199620000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114340000', '贵阳市公安局经济技术开发区分局督察大队', '督察大队', '1', '无');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114510000', '贵阳市公安局经济技术开发区分局黄河路派出所', '黄河派出所', '1', '520199600000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114300000', '贵阳市公安局经济技术开发区分局巡（特）大队', '巡（特）大队', '1', '5201995C0000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114550000', '贵阳市公安局经济技术开发区分局大兴派出所', '大兴派出所', '1', '520199640000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114310000', '贵阳市公安局经济技术开发区分局办公室', '办公室', '1', '520199310000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114050000', '贵阳市公安局经济技术开发区分局刑事侦查大队', '刑侦大队', '1', '520199050000');
INSERT INTO `v3unit_jingzongunit` VALUES ('520114250000', '贵阳市公安局经济技术开发区分局国保大队', '国保大队', '1', '无');
