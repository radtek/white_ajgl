-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.5.38 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4991
-- --------------------------------------------------------
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `t_at_fwkzzt`;
CREATE TABLE IF NOT EXISTS `t_at_fwkzzt` (
  `id` varchar(255) NOT NULL,
  `zt_id` varchar(36) DEFAULT NULL,
  `lx` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_fwkzzt 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `t_at_fwkzzt` DISABLE KEYS */;
INSERT INTO `t_at_fwkzzt` (`id`, `zt_id`, `lx`) VALUES
	('10d0fbb9-f968-401b-a402-4adb6fde79c1', 'f29204fd-d6cc-4fb4-a787-1e0f826d1a97', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('1ba4ac06-913c-413c-b547-083b2fe37668', '7f13ee87-94e7-4330-8ef7-04979d3c5b66', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('2a41d8a3-8d54-4d2d-a69c-aa6a03b72d15', '1630a7c0-428b-49cf-a9c9-ac635c7f23dd', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('37043a44-71ec-4c86-b65f-446306fb93c2', '69222415-7c80-4370-a468-96b7e1714294', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('3c055b5f-2386-4b21-8055-478fa82ed32d', 'c1c1cc1e-59e4-4640-9698-b8d7eb05fbda', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('3fa4455f-e8da-44b1-b969-c4a02d86bceb', '9dc6079d-e477-4d6d-8cf2-94c91ec38e07', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('41f8e690-240b-4a8e-90f5-923adc3e6e52', '057691fb-aeb6-40e3-a869-e02bcab7be01', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('540cd1d7-5fba-4501-b186-93fbaea51355', 'e0d96ca7-b357-44e0-a219-b19f3a399f46', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('7de556e4-cb4f-43cc-9393-1b61d8aac3cc', '066d0571-135d-4748-88ea-c66c568a65cd', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('8392f837-0ff3-41c1-b731-3a493484919e', 'd9213a33-2871-4035-a255-3e6ee5a8f500', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('8f57a46b-9aa8-4b14-b9c4-ce03b8418613', '93bc091c-1de6-480e-b5cd-2adcf8c8985d', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('93937e51-b72c-40bc-8e5f-ded1c79d54b5', 'a10957bb-a61c-45e8-b1dc-05df8d0e517f', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('a0e7f8c0-1b5e-445e-995f-2e502431949e', 'cbdafc27-e508-4c6b-9dd9-9c9b9eba02e0', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('a6491f42-d0f2-4630-950d-759096611d66', 'ffa0c6ca-0227-40a6-88e7-7d4bdde49346', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('a6f2d279-8fc1-42e6-9f44-85b382b54a71', '02c1c234-65d3-444b-b9b7-f0d93273df34', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('b0390509-d4da-4850-a0e4-96298ec7a68b', '36a84e27-b216-4c32-ab5d-c7f89d6a9217', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('bb9b3a87-8951-43f7-8e72-e40d3e2a1af0', '0ef2f510-9a57-4f42-a46b-89deb26a638a', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('c1de74e2-9222-4e7a-9089-854a24bbc201', 'd3da0774-bb0a-4a03-8a0a-65ee573e81b0', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('c6e164dd-09c9-45a7-948b-f44648915e77', '0fe4b0c6-d148-41d2-aa19-403ea4477cf6', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('d4536a27-714d-45e9-8745-642f5042fa2b', '28280f4e-5864-4946-a940-bc4401da4ac1', 'com.taiji.pubsec.businesscomponent.organization.model.Account'),
	('fbd1ea59-39b7-41db-b740-7dd4ae491252', '0b35afcc-c488-4240-82a9-0ffd2186d403', 'com.taiji.pubsec.businesscomponent.organization.model.Account');
/*!40000 ALTER TABLE `t_at_fwkzzt` ENABLE KEYS */;


-- 导出  表 ajgl.t_at_fwkzzt_js 结构
DROP TABLE IF EXISTS `t_at_fwkzzt_js`;
CREATE TABLE IF NOT EXISTS `t_at_fwkzzt_js` (
  `id` varchar(255) NOT NULL,
  `zt` varchar(6) DEFAULT NULL,
  `js_id` varchar(255) DEFAULT NULL,
  `fwkzzt_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3497DF4DED5E7A1B` (`fwkzzt_id`),
  KEY `FK3497DF4D4672D7E2` (`js_id`),
  CONSTRAINT `FK3497DF4D4672D7E2` FOREIGN KEY (`js_id`) REFERENCES `t_at_js` (`id`),
  CONSTRAINT `FK3497DF4DED5E7A1B` FOREIGN KEY (`fwkzzt_id`) REFERENCES `t_at_fwkzzt` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_fwkzzt_js 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `t_at_fwkzzt_js` DISABLE KEYS */;
INSERT INTO `t_at_fwkzzt_js` (`id`, `zt`, `js_id`, `fwkzzt_id`) VALUES
	('08db32f9-c5c6-4007-b960-15b5ee99f8ee', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', 'a0e7f8c0-1b5e-445e-995f-2e502431949e'),
	('11a6b581-b740-4558-8c33-1586dbb34b49', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '3c055b5f-2386-4b21-8055-478fa82ed32d'),
	('325b15eb-d4c5-48f4-9fef-477815f8a654', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', 'a6f2d279-8fc1-42e6-9f44-85b382b54a71'),
	('32f8a605-8530-4c52-b44b-b2674ed892a0', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '8392f837-0ff3-41c1-b731-3a493484919e'),
	('3ff6dda2-974c-4ed0-8313-ebc29d9e9229', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', 'a6491f42-d0f2-4630-950d-759096611d66'),
	('45631d04-1120-48aa-a913-2c20a7a245d2', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '3fa4455f-e8da-44b1-b969-c4a02d86bceb'),
	('4cbc4590-b15b-4a17-bd6a-a01f6e153275', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '10d0fbb9-f968-401b-a402-4adb6fde79c1'),
	('6bfbfd74-2894-41c4-ac8f-888fc1bc3f68', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', 'c6e164dd-09c9-45a7-948b-f44648915e77'),
	('883a09b3-2e0c-4173-839c-9b551f9b2cb7', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '540cd1d7-5fba-4501-b186-93fbaea51355'),
	('9c2a8f92-85d1-4080-8e36-e78fb1897581', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '1ba4ac06-913c-413c-b547-083b2fe37668'),
	('afaa56a6-a3e5-4f86-b5a8-bf774d144d45', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', 'bb9b3a87-8951-43f7-8e72-e40d3e2a1af0'),
	('ca75ec71-24ef-4244-b7fb-f257ef7b063e', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', 'd4536a27-714d-45e9-8745-642f5042fa2b'),
	('dae56cf6-fb23-4c52-a4a0-7ce30f01ef95', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '41f8e690-240b-4a8e-90f5-923adc3e6e52'),
	('de96cbf0-b3ba-461d-aefb-d711bff4d2c7', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '93937e51-b72c-40bc-8e5f-ded1c79d54b5'),
	('f98dc25f-1c9d-4b24-a0e2-698708a2a95c', '1', 'b8664509-bafc-4de6-96bd-fcfec106b120', '37043a44-71ec-4c86-b65f-446306fb93c2');
/*!40000 ALTER TABLE `t_at_fwkzzt_js` ENABLE KEYS */;


-- 导出  表 ajgl.t_at_js 结构
DROP TABLE IF EXISTS `t_at_js`;
CREATE TABLE IF NOT EXISTS `t_at_js` (
  `id` varchar(255) NOT NULL,
  `zzjg_id` varchar(50) DEFAULT NULL,
  `jsbm` varchar(50) DEFAULT NULL,
  `jsmc` varchar(30) DEFAULT NULL,
  `zt` varchar(30) DEFAULT NULL,
  `sjc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_js 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_at_js` DISABLE KEYS */;
INSERT INTO `t_at_js` (`id`, `zzjg_id`, `jsbm`, `jsmc`, `zt`, `sjc`) VALUES
	('b8664509-bafc-4de6-96bd-fcfec106b120', NULL, 'xtgly', '系统管理员', '1', '2016-08-09 15:10:06');
/*!40000 ALTER TABLE `t_at_js` ENABLE KEYS */;


-- 导出  表 ajgl.t_at_js_qx 结构
DROP TABLE IF EXISTS `t_at_js_qx`;
CREATE TABLE IF NOT EXISTS `t_at_js_qx` (
  `js_id` varchar(255) NOT NULL,
  `qx_id` varchar(255) NOT NULL,
  KEY `FKB379253CF9678ABB` (`qx_id`),
  KEY `FKB379253C4672D7E2` (`js_id`),
  CONSTRAINT `FKB379253C4672D7E2` FOREIGN KEY (`js_id`) REFERENCES `t_at_js` (`id`),
  CONSTRAINT `FKB379253CF9678ABB` FOREIGN KEY (`qx_id`) REFERENCES `t_at_qx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_js_qx 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `t_at_js_qx` DISABLE KEYS */;
INSERT INTO `t_at_js_qx` (`js_id`, `qx_id`) VALUES
	('b8664509-bafc-4de6-96bd-fcfec106b120', '5e072d09-ce42-4a03-b35f-feafe5db5f7e'),
	('b8664509-bafc-4de6-96bd-fcfec106b120', 'a92c99b8-bf85-47a6-a685-cd61edd87b3a'),
	('b8664509-bafc-4de6-96bd-fcfec106b120', 'dd90e869-b29e-4bd7-ba12-c97bb8418349');
/*!40000 ALTER TABLE `t_at_js_qx` ENABLE KEYS */;


-- 导出  表 ajgl.t_at_qx 结构
DROP TABLE IF EXISTS `t_at_qx`;
CREATE TABLE IF NOT EXISTS `t_at_qx` (
  `id` varchar(255) NOT NULL,
  `qxbm` varchar(36) DEFAULT NULL,
  `qxmc` varchar(36) DEFAULT NULL,
  `qxlx` varchar(36) DEFAULT NULL,
  `sjc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_qx 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_at_qx` DISABLE KEYS */;
INSERT INTO `t_at_qx` (`id`, `qxbm`, `qxmc`, `qxlx`, `sjc`) VALUES
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', 'xtgl', '系统管理', NULL, '2016-08-09 15:07:05'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', 'sawp', '涉案物品', NULL, '2016-08-09 15:06:33'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', 'baqgl', '办案区管理', NULL, '2016-08-09 15:05:50'),
	('f4561834-cf21-4e65-8d44-4107ebbe696d', 'tygn', '通用功能', NULL, '2016-07-19 11:29:19');
/*!40000 ALTER TABLE `t_at_qx` ENABLE KEYS */;


-- 导出  表 ajgl.t_at_qx_zy 结构
DROP TABLE IF EXISTS `t_at_qx_zy`;
CREATE TABLE IF NOT EXISTS `t_at_qx_zy` (
  `qx_id` varchar(255) NOT NULL,
  `zy_id` varchar(255) NOT NULL,
  KEY `FKB3DE10B6F9678ABB` (`qx_id`),
  KEY `FKB3DE10B689DBF2D1` (`zy_id`),
  CONSTRAINT `FKB3DE10B689DBF2D1` FOREIGN KEY (`zy_id`) REFERENCES `t_at_zy` (`id`),
  CONSTRAINT `FKB3DE10B6F9678ABB` FOREIGN KEY (`qx_id`) REFERENCES `t_at_qx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_qx_zy 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_at_qx_zy` DISABLE KEYS */;
INSERT INTO `t_at_qx_zy` (`qx_id`, `zy_id`) VALUES
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '01a43815-67f4-43df-b2d7-2b724f14fd76'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '08b1686b-4271-4c03-a204-7e1ffed01b7e'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '2f0c571b-9abf-4ef4-bd8a-42c11a01ed0d'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '58a064df-52bd-42d4-af30-26a07b65e2ce'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '5a0c2769-a084-4a2d-9ce0-91687d086215'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '7cc710bb-2386-4e9c-b79c-4449f10b9eb7'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', '97305854-1049-4f2a-961a-7ede37aced7c'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', 'ba19ada0-6f8c-497e-b714-7d0e29d11913'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', 'be1ba20d-7bbf-44c4-aa2c-a83cf9cd59a9'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', 'c3ccb041-cdb0-4366-ab40-bb207bbf1d61'),
	('dd90e869-b29e-4bd7-ba12-c97bb8418349', 'f4d2923e-8e25-400c-96cc-17d654043c0f'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', '5227aacf-4549-44ba-9f01-38d0a37f1a16'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', '1f0cdfba-be2c-46f9-94b1-f089da434557'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', '9f852e34-09f7-4d73-8527-2792a16082b0'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', 'c3ddbb48-6cb3-477c-ba01-431471f19986'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', 'c52bf450-45b2-42c9-9b52-748165857f21'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', 'e70cee3a-1cf3-4be7-9e96-a1fb1c555b4c'),
	('a92c99b8-bf85-47a6-a685-cd61edd87b3a', 'f525b3cd-e2ae-45e8-9be5-704d8ebd1c0e'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', 'e974a48f-bee6-4f03-ae0a-e15d68502007'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', '04fabe57-ef5b-490d-8749-1639a0650133'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', '4ca93be6-939a-4fca-a570-b9e6a4f85ab8'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', '506eaaa9-e621-4a56-865a-4c6fe8e4ed66'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', 'd6e2d719-a97e-4f65-8304-65469c0b05d2'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', 'e94ef517-bb42-4944-86e9-4969c4a1fda5'),
	('5e072d09-ce42-4a03-b35f-feafe5db5f7e', 'f70a9e62-77b9-4461-a7cd-ae132e312dcf');
/*!40000 ALTER TABLE `t_at_qx_zy` ENABLE KEYS */;


-- 导出  表 ajgl.t_at_zy 结构
DROP TABLE IF EXISTS `t_at_zy`;
CREATE TABLE IF NOT EXISTS `t_at_zy` (
  `id` varchar(255) NOT NULL,
  `zymc` varchar(36) DEFAULT NULL,
  `zylx` varchar(36) DEFAULT NULL,
  `zydz` varchar(60) DEFAULT NULL,
  `sjc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_at_zy 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_at_zy` DISABLE KEYS */;
INSERT INTO `t_at_zy` (`id`, `zymc`, `zylx`, `zydz`, `sjc`) VALUES
	('01a43815-67f4-43df-b2d7-2b724f14fd76', '办案区管理-进入办案区8小时预警', '1', '/alertRule/*', '2016-08-09 14:45:20'),
	('04fabe57-ef5b-490d-8749-1639a0650133', '部门管理', '1', '/department/*', '2016-08-09 14:41:49'),
	('08b1686b-4271-4c03-a204-7e1ffed01b7e', '办案区管理-违规类型', '1', '/illegalType/*', '2016-08-09 14:41:54'),
	('1f0cdfba-be2c-46f9-94b1-f089da434557', '涉案物品-涉案物品存储位置调整单', '1', '/adjustgmentStorageForm/*', '2016-08-09 14:50:51'),
	('2f0c571b-9abf-4ef4-bd8a-42c11a01ed0d', '办案区管理-讯（询）问室维护', '1', '/askRoom/*', '2016-08-09 14:42:00'),
	('4ca93be6-939a-4fca-a570-b9e6a4f85ab8', '角色管理', '1', '/role/*', '2016-08-09 14:42:07'),
	('506eaaa9-e621-4a56-865a-4c6fe8e4ed66', '单位管理', '1', '/unit/*', '2016-08-09 14:42:14'),
	('5227aacf-4549-44ba-9f01-38d0a37f1a16', '涉案物品-涉案物品台账', '1', '/standingBook/*', '2016-08-09 14:50:58'),
	('58a064df-52bd-42d4-af30-26a07b65e2ce', '办案区管理-其他房间维护', '1', '/otherRoom/*', '2016-08-09 14:42:19'),
	('5a0c2769-a084-4a2d-9ce0-91687d086215', '办案区管理-异常记录查询', '1', '/handlingAreaSupervision/*', '2016-08-09 14:44:51'),
	('7cc710bb-2386-4e9c-b79c-4449f10b9eb7', '办案区管理-办案区督查', '1', '/handlingAreaSupervision/*', '2016-08-09 14:43:56'),
	('97305854-1049-4f2a-961a-7ede37aced7c', '办案区管理-储物架维护', '1', '/locker/*', '2016-08-09 14:42:24'),
	('9f852e34-09f7-4d73-8527-2792a16082b0', '涉案物品-物证保管区维护', '1', '/storageArea/*', '2016-08-09 14:47:17'),
	('ba19ada0-6f8c-497e-b714-7d0e29d11913', '办案区管理-讯（询）问室分配记录', '1', '/askRoomAllocation/*', '2016-08-09 14:43:28'),
	('be1ba20d-7bbf-44c4-aa2c-a83cf9cd59a9', '办案区管理-讯（询）问室分配', '1', '/askRoomAllocation/*', '2016-08-09 14:42:31'),
	('c3ccb041-cdb0-4366-ab40-bb207bbf1d61', '办案区管理-讯（询）问室使用记录列表', '1', '/handlingAreaSupervision/*', '2016-08-09 14:44:27'),
	('c3ddbb48-6cb3-477c-ba01-431471f19986', '涉案物品-涉案物品返还', '1', '/backStorageForm/*', '2016-08-09 14:51:08'),
	('c52bf450-45b2-42c9-9b52-748165857f21', '涉案物品-涉案物品入库', '1', '/storageIn/*', '2016-08-09 14:51:15'),
	('d6e2d719-a97e-4f65-8304-65469c0b05d2', '用户管理', '1', '/userManage/*', '2016-08-09 14:42:36'),
	('e70cee3a-1cf3-4be7-9e96-a1fb1c555b4c', '涉案物品-储物架维护', '1', '/locker/*', '2016-08-09 14:51:34'),
	('e94ef517-bb42-4944-86e9-4969c4a1fda5', '人员管理', '1', '/personManage/*', '2016-08-09 14:42:44'),
	('e974a48f-bee6-4f03-ae0a-e15d68502007', '资源管理', '1', '/resource/*', '2016-08-09 14:35:09'),
	('f4d2923e-8e25-400c-96cc-17d654043c0f', '办案区管理-办案区使用单', '1', '/handlingAreaReceipt/*', '2016-08-09 14:42:53'),
	('f525b3cd-e2ae-45e8-9be5-704d8ebd1c0e', '涉案物品-涉案物品出库', '1', '/storageOut/*', '2016-08-09 14:51:27'),
	('f70a9e62-77b9-4461-a7cd-ae132e312dcf', '权限管理', '1', '/authority/*', '2016-08-09 14:43:01');
/*!40000 ALTER TABLE `t_at_zy` ENABLE KEYS */;


-- 导出  表 ajgl.t_og_bm 结构
DROP TABLE IF EXISTS `t_og_bm`;
CREATE TABLE IF NOT EXISTS `t_og_bm` (
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0BBB087FF68B483` (`id`),
  CONSTRAINT `FKA0BBB087FF68B483` FOREIGN KEY (`id`) REFERENCES `t_og_zzjg` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_og_bm 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_og_bm` DISABLE KEYS */;
INSERT INTO `t_og_bm` (`id`) VALUES
	('21482444-b499-4e43-ad12-e17e1dc0f733'),
	('3a1be42e-ba10-4c8a-8a62-5652b91f487f');
/*!40000 ALTER TABLE `t_og_bm` ENABLE KEYS */;


-- 导出  表 ajgl.t_og_dw 结构
DROP TABLE IF EXISTS `t_og_dw`;
CREATE TABLE IF NOT EXISTS `t_og_dw` (
  `fl` varchar(50) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0BBB0CFFF68B483` (`id`),
  CONSTRAINT `FKA0BBB0CFFF68B483` FOREIGN KEY (`id`) REFERENCES `t_og_zzjg` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_og_dw 的数据：~27 rows (大约)
/*!40000 ALTER TABLE `t_og_dw` DISABLE KEYS */;
INSERT INTO `t_og_dw` (`fl`, `id`) VALUES
	(NULL, '01'),
	('0', '069c4e0f-a006-43d6-a9d7-36f488514c7d'),
	('1', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d'),
	('1', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5'),
	('0', '3f025ed8-d417-45ac-96cd-af383b3fc431'),
	('1', '585fdd1b-4453-443d-8d28-305368c99728'),
	('0', '710ec0e8-fe8a-42da-b4e4-353a380be40d'),
	('1', '76e82277-9ef2-4c01-b223-454048e3db9c'),
	('0', '7b8cac4c-2e89-499c-b83e-63defc1da3dd'),
	('0', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5'),
	('1', 'b01776e5-59a3-4892-ab1c-85e6a676388d'),
	('1', 'b1e2af70-ca70-42fb-a728-6ae56123a981'),
	('0', 'b4f8b72d-1940-43a4-9518-d6606b8b768a'),
	('1', 'd36628c3-d4e4-4143-b6bb-c9f6b89f2aa9'),
	('0', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567'),
	('1', 'e55be91b-f2cc-4de6-80cc-d460e3c92975'),
	('0', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23'),
	('1', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad'),
	('1', 'fc4376c0-ba88-4958-87da-f3498281839b');
/*!40000 ALTER TABLE `t_og_dw` ENABLE KEYS */;


-- 导出  表 ajgl.t_og_ry 结构
DROP TABLE IF EXISTS `t_og_ry`;
CREATE TABLE IF NOT EXISTS `t_og_ry` (
  `id` varchar(255) NOT NULL,
  `jh` varchar(50) DEFAULT NULL,
  `xl` varchar(36) DEFAULT NULL,
  `sfzh` varchar(50) DEFAULT NULL,
  `xm` varchar(36) DEFAULT NULL,
  `mz` varchar(36) DEFAULT NULL,
  `bgdh` varchar(30) DEFAULT NULL,
  `zzmm` varchar(36) DEFAULT NULL,
  `zw` varchar(36) DEFAULT NULL,
  `xb` varchar(36) DEFAULT NULL,
  `zt` varchar(30) NOT NULL,
  `yddh` varchar(30) DEFAULT NULL,
  `qt` varchar(36) DEFAULT NULL,
  `pxh` int(11) DEFAULT '0',
  `sjc` datetime NOT NULL,
  `org_id` varchar(255) DEFAULT NULL,
  `account_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0BBB283C3494FBE` (`org_id`),
  KEY `FKA0BBB2832C559F45` (`account_id`),
  CONSTRAINT `FKA0BBB2832C559F45` FOREIGN KEY (`account_id`) REFERENCES `t_og_zh` (`id`),
  CONSTRAINT `FKA0BBB283C3494FBE` FOREIGN KEY (`org_id`) REFERENCES `t_og_zzjg` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_og_ry 的数据：~381 rows (大约)
/*!40000 ALTER TABLE `t_og_ry` DISABLE KEYS */;
INSERT INTO `t_og_ry` (`id`, `jh`, `xl`, `sfzh`, `xm`, `mz`, `bgdh`, `zzmm`, `zw`, `xb`, `zt`, `yddh`, `qt`, `pxh`, `sjc`, `org_id`, `account_id`) VALUES
	('01a28421-0af7-4091-94e5-3565bdf3e325', '004795', '0', '', '唐义', '0', '', '0', '0', '1', '0', '13037884212', NULL, NULL, '2016-07-29 07:49:08', '01', NULL),
	('02e99c70-b890-4697-93a1-8d060f08772d', '004917', '0', '', '秦毅', '0', '', '0', '0', '1', '0', '13984109090', NULL, NULL, '2016-07-31 16:46:12', '01', NULL),
	('044b3f8c-e9b7-4d2a-9339-25fd0c6262d0', '004926', '0', '', '杨万权', '0', '', '0', '0', '1', '0', '13984885496', NULL, NULL, '2016-08-07 18:57:38', '01', NULL),
	('053c0edd-66e0-43c5-80f0-f1290040bcca', '006471', NULL, NULL, '龙新', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('062d5c50-f784-4eeb-abd5-2fb195ddcafb', '42053', NULL, NULL, '熊江丹', NULL, NULL, NULL, '0', '1', '0', '15086004512', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('0782eb83-fe13-474e-84ad-187ad99849f2', '042024', NULL, NULL, '戈兵', NULL, NULL, NULL, '0', '1', '0', '15985144176', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('0841973b-e98b-446a-bee4-42a69902cf04', '42011', NULL, NULL, '陈宇耕', NULL, NULL, NULL, '0', '1', '0', '18685098532', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('099a7d4d-db0e-4400-8638-527ef3de001c', '042084', NULL, NULL, '张磊', NULL, NULL, NULL, '0', '1', '0', '18275050490', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('0a0c61c7-5be1-4416-b8ea-f199b0f75796', '006411', NULL, NULL, '关恒青', NULL, NULL, NULL, '0', '1', '0', '13985526920', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('0dddc497-f1ad-4b21-b544-8ab47576184d', '006472', NULL, NULL, '陈建国', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('0de02af3-4185-4384-983a-eeb5ea0297e5', '042057', NULL, NULL, '彭彪', NULL, NULL, NULL, '0', '1', '0', '15085979257', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('0eef9cdb-db0f-4075-8501-6607e3a46f72', '035265', NULL, NULL, '江克非', NULL, NULL, NULL, '0', '1', '0', '18985586363', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('0ef98dad-700c-4595-8399-e0572359c45f', '006149', NULL, NULL, '陈昆', NULL, NULL, NULL, '0', '1', '0', '13984187110', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('0f01b652-515a-4c42-a023-b456996d7857', '037102', NULL, NULL, '罗萍', NULL, NULL, NULL, '0', '1', '0', '13984303169', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('12a00427-2080-4b61-a20a-cbe80368b022', '035297', NULL, NULL, '赵华', NULL, NULL, NULL, '0', '1', '0', '13809498006', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('13224c9c-5142-481b-ba33-da5c7df1db5b', '042099', NULL, NULL, '廖忠', NULL, NULL, NULL, '0', '1', '0', '18585429608', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('13f94792-8fc4-44b2-903a-71c385f174a5', '008544', NULL, NULL, '王功贵', NULL, NULL, NULL, '0', '1', '0', '13984803088', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('1427d9c9-e56a-4b80-9acf-6f16be114387', '006464', NULL, NULL, '孟征', NULL, NULL, NULL, '0', '1', '0', '13595080212', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('14429995-f622-4280-ade5-150cad8d9ee7', '037086', NULL, NULL, '李灵江', NULL, NULL, NULL, '0', '1', '0', '13668506894', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('1481b039-1ccb-4508-8ced-e1ba0cf84a7e', '042019', NULL, NULL, '罗文', NULL, NULL, NULL, '0', '1', '0', '13628512190', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('14983a78-0ccf-4fef-9b81-52c00546a1ee', '42079', NULL, NULL, '邓毓', NULL, NULL, NULL, '0', '1', '0', '18285004331', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('150c3765-609f-47e3-a888-cfc83dd4f15d', '042042', NULL, NULL, '陈璐', NULL, NULL, NULL, '0', '1', '0', '18685006205', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('15c3674a-00ae-41e4-8f8c-e9826e92b911', 'jh4', '0', '', '没名', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-17 20:20:33', 'd36628c3-d4e4-4143-b6bb-c9f6b89f2aa9', NULL),
	('16ae88b7-d49a-4c08-88d0-bb2adc8e6f40', '042041', NULL, NULL, '余菊', NULL, NULL, NULL, '0', '1', '0', '15085946654', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('171a7c8c-1569-460b-82ab-b99b7951913d', '042036', NULL, NULL, '吴容欣', NULL, NULL, NULL, '0', '1', '0', '18685193869', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('184d5879-bc6b-4e46-bbea-e36b8b6387a8', '42095', NULL, NULL, '刘金滨', NULL, NULL, NULL, '0', '1', '0', '15285986654', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('19e3d11a-7d30-4947-bc1d-f56835dc6c9e', '004793', '0', '', '赵朋', '0', '', '0', '0', '1', '0', '18286124670', NULL, NULL, '2016-07-29 07:48:51', '01', NULL),
	('1cb297d9-c084-4059-9ffa-c6508fd2c276', '004792', '0', '', '蔡勇', '0', '', '0', '0', '1', '0', '13985155681', NULL, NULL, '2016-07-29 07:48:41', '01', NULL),
	('1cd24dd1-162c-44fa-bbcb-7c49c00b9f55', '42083', NULL, NULL, '彭忠永', NULL, NULL, NULL, '0', '1', '0', '13668504434', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('1f4e6c6d-5d12-4173-9105-9befe2cd4189', '042074 ', NULL, NULL, '蔡辉', NULL, NULL, NULL, '0', '1', '0', '13595093721', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('207c4231-bb1b-49ef-aeb5-83b5abb96318', '042026', NULL, NULL, '王俊', NULL, NULL, NULL, '0', '1', '0', '18302636967', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('208a8963-8ce4-4d41-8ea0-d194a9c71d2d', 'jh5', '0', '', '龙刚', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-22 11:47:58', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('209194a2-0e36-45f8-a0be-bfea6911ec67', '030490', NULL, NULL, '任义前', NULL, NULL, NULL, '0', '1', '0', '18984850276', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('20c1d57c-2030-4192-89d2-505328c123e0', '042007', NULL, NULL, '范海', NULL, NULL, NULL, '0', '1', '0', '18798733747', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('21081023-41c7-40a4-af76-ead2a3098da5', 'jh6', '0', '', '彭忠永', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-22 11:49:24', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('21c20254-801d-468d-83de-f0b7211f6b9a', '035287', NULL, NULL, '潘莉', NULL, NULL, NULL, '0', '1', '0', '13984081617', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('223109cc-1d27-4c63-a34e-4e5a1064030a', '030497', NULL, NULL, '朱文珂', NULL, NULL, NULL, '0', '1', '0', '13765820123', NULL, 0, '2016-04-21 21:05:09', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', NULL),
	('2316b696-f8b5-4507-abf2-07a646f5ef30', '009256', NULL, NULL, '吴燕', NULL, NULL, NULL, '0', '1', '0', '13765000517', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('238b5f27-2a09-4820-8a49-bdb5857d951f', '042035', NULL, NULL, '何建刚', NULL, NULL, NULL, '0', '1', '0', '13688505355', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('23e04b72-a7b2-428b-948b-f7b91878a466', '007267', NULL, NULL, '谢军', NULL, NULL, NULL, '0', '1', '0', '13908508253', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('23ea4954-3821-4f5b-9f02-13d983635cc2', '042087', NULL, NULL, '唐晓娇', NULL, NULL, NULL, '0', '1', '0', '18585018268', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('24df4d02-4386-484d-b4ce-7e77197648df', '035260', NULL, NULL, '张敏', NULL, NULL, NULL, '0', '1', '0', '18984559477', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('24e784fe-d1cd-4c97-b144-e91587d4615f', '006478', NULL, NULL, '王渊', NULL, NULL, NULL, '0', '1', '0', '15985155966', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('25931579-de80-478b-a057-1d081273698d', '37087', NULL, NULL, '王伟', NULL, NULL, NULL, '0', '1', '0', '13984887793', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('26ae042a-ed06-41c5-9f85-397eb331810d', '004963', '0', '', '罗振兴', '0', '', '0', '0', '1', '0', '83831569', NULL, NULL, '2016-08-07 19:07:42', '01', NULL),
	('275d11f5-6e84-462d-9822-3327e29d3288', '042044', NULL, NULL, '吴吉友', NULL, NULL, NULL, '0', '1', '0', '15286003554', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('286da5f7-d6a1-4b44-aef9-81fd44776189', '006426', NULL, NULL, '袁隆', NULL, NULL, NULL, '0', '1', '0', '13984326668', NULL, 0, '2016-04-21 21:05:09', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', NULL),
	('28a17f44-7843-4cc1-adc9-c8b46450821b', '5973', NULL, NULL, '蒋勇', NULL, NULL, NULL, '0', '1', '0', '13985411588', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('28a7db72-8499-42fe-a0b6-626b027f1677', '035249', NULL, NULL, '殷赵军', NULL, NULL, NULL, '0', '1', '0', '13985013481', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('28f0bfec-adb3-4659-9e57-c34ece21be8b', '006405', NULL, NULL, '骆勋', NULL, NULL, NULL, '0', '1', '0', '13608503990', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('2951e298-727d-47c5-8a2c-d9a6f0f98dee', '035273', NULL, NULL, '莫利达', NULL, NULL, NULL, '0', '1', '0', '13984348880', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('29538cc0-e201-4a42-b24f-4f17006e7b65', '037100', NULL, NULL, '李健', NULL, NULL, NULL, '0', '1', '0', '13984033366', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('2977c795-e4c8-4414-97be-9ee77b7b9586', '006423', NULL, NULL, '龙艺', NULL, NULL, NULL, '0', '1', '0', '13984139797', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('29be3575-9726-4bbd-a7f8-c26c1efcbbed', '6410', NULL, NULL, '李克峰', NULL, NULL, NULL, '0', '1', '0', '13965111811', NULL, 0, '2016-04-21 21:05:09', 'b01776e5-59a3-4892-ab1c-85e6a676388d', NULL),
	('2bafd792-5e3d-4d6d-b737-727b402d5085', '042020', NULL, NULL, '石燕飞', NULL, NULL, NULL, '0', '1', '0', '18785156945', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('2c1f56d4-bc08-4dab-b8d2-4ea18438ac07', 'jh7', '0', '', '熊江丹', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-22 11:47:45', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('2c2bf719-beff-417f-acef-9af6585855ef', '037084', NULL, NULL, '刘训澜', NULL, NULL, NULL, '0', '1', '0', '15285593667', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('2c52cfbc-d223-4801-88eb-c37e06d53b69', '042089', NULL, NULL, '潘俊', NULL, NULL, NULL, '0', '1', '0', '18685623997', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('2ca04c8d-fc09-4f05-8815-0ac3e02551cd', '042049', NULL, NULL, '杨万', NULL, NULL, NULL, '0', '1', '0', '18685188654', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('30921ec1-de66-4cbf-8161-9b21440da797', '006461', NULL, NULL, '姚贵芬', NULL, NULL, NULL, '0', '1', '0', '13595165884', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('31230095-14dc-46bc-ae14-a3877c0a47d2', '042034', NULL, NULL, '周松', NULL, NULL, NULL, '0', '1', '0', '18685163065', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('31536186-8c96-4839-958c-7cf6fdbbeee2', '009262', NULL, NULL, '徐兰珍', NULL, NULL, NULL, '0', '1', '0', '13638516171', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('31811893-7e50-44b8-8ffc-e92220be2d23', '006432', NULL, NULL, '姜晓亭', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '710ec0e8-fe8a-42da-b4e4-353a380be40d', NULL),
	('31b26870-36db-41e1-ae6d-e51ab31dff60', '006462', NULL, NULL, '孟庆玥', NULL, NULL, NULL, '0', '1', '0', '13985042290', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('322f88e5-c59e-4d81-a42b-766fcce9fbb9', '006140', NULL, NULL, '高超', NULL, NULL, NULL, '0', '1', '0', '13765009797', NULL, 0, '2016-04-21 21:05:09', '585fdd1b-4453-443d-8d28-305368c99728', NULL),
	('324e3df2-1d76-4033-bee9-40a622a2669c', '008301', NULL, NULL, '李建辉', NULL, NULL, NULL, '0', '1', '0', '13984144633', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('329a437a-994f-44cb-8fff-469644b06fa4', '035269', NULL, NULL, '刘好', NULL, NULL, NULL, '0', '1', '0', '13985577775', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('32f631a2-1cfc-40ac-acc3-100285e337b6', '037089', NULL, NULL, '胡继贺', NULL, NULL, NULL, '0', '1', '0', '15885104167', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('33aa077f-a2f6-41be-8c2b-a2d476758aac', '006047', NULL, NULL, '赵文学', NULL, NULL, NULL, '0', '1', '0', '13985015145', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('34ec7aed-2299-4cd5-b4e9-20e34148fd94', '005299', '0', '', '杨英识', '0', '', '0', '0', '1', '0', '13595022066', NULL, NULL, '2016-08-07 19:08:23', '01', NULL),
	('3562e043-3a6f-4dc2-9958-a3b33890535c', '035281', NULL, NULL, '张毅', NULL, NULL, NULL, '0', '1', '0', '13985586212', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('35ad1531-2575-4526-aead-399be64aa12e', '006397', NULL, NULL, '聂祥运', NULL, NULL, NULL, '0', '1', '0', '13885089636', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('365c83f5-2954-4212-a0e0-04fac1607b7f', '006470', NULL, NULL, '张勇', NULL, NULL, NULL, '0', '1', '0', '13985027011', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('375aab2d-c32c-4dc1-939b-b0eb8c43a232', '042097', NULL, NULL, '梁应泽', NULL, NULL, NULL, '0', '1', '0', '18585431665', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('392ed11d-47aa-4dd1-879c-4ad11c31aed9', '004968', '0', '', '林金根', '0', '', '0', '0', '1', '0', '83831569', NULL, NULL, '2016-08-07 19:08:07', '01', NULL),
	('393174ee-4e7b-43e0-9f20-9ec61caa5cdd', '6409', NULL, NULL, '陈蓓', NULL, NULL, NULL, '0', '1', '0', '13885038302', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('3985471b-29d3-48b2-b50f-87958baba57b', '005923', NULL, NULL, '王辉', NULL, NULL, NULL, '0', '1', '0', '13511950913', NULL, 0, '2016-04-21 21:05:09', '585fdd1b-4453-443d-8d28-305368c99728', NULL),
	('39f1f096-0088-4e8e-90b8-f859493ebd98', '006458', NULL, NULL, '陈波', NULL, NULL, NULL, '0', '1', '0', '15180860669', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('3ba414f0-1cbf-4ab8-80e3-aeabdeaf635a', '009257', NULL, NULL, '王可', NULL, NULL, NULL, '0', '1', '0', '13985522086', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('3bd4c5f8-7998-4dbc-ad03-949f200ce605', '030453', NULL, NULL, '钟华', NULL, NULL, NULL, '0', '1', '0', '13809464088', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('3c9ad654-ba3a-4ed4-a73d-15afad5c5890', '037097', NULL, NULL, '王祥', NULL, NULL, NULL, '0', '1', '0', '13885038116', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('3e01a48b-c17b-4b21-96e6-7c9cba8b7e68', '037092', NULL, NULL, '刘长华', NULL, NULL, NULL, '0', '1', '0', '13595129950', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('3f263a25-c96c-4417-a7db-b31aa8b8b432', '037094', NULL, NULL, '罗福敏', NULL, NULL, NULL, '0', '1', '0', '18984577989', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('3f32c5cf-94ee-4608-ab11-a1b62541fce4', '035263', NULL, NULL, '陈胜宇', NULL, NULL, NULL, '0', '1', '0', '13985455329', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('4082a078-428b-444e-a52d-fa32c9d44d41', '042093', NULL, NULL, '魏正威', NULL, NULL, NULL, '0', '1', '0', '15285138336', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('40b589ca-97c7-47b7-aeec-00660726331e', '042072', NULL, NULL, '赵兴', NULL, NULL, NULL, '0', '1', '0', '15185642608', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('41559e89-a832-4193-8b34-e625852e8f1d', '006392', NULL, NULL, '郑又源', NULL, NULL, NULL, '0', '1', '0', '18085197897', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('429447c5-c047-4eb8-bc13-14606ce04752', '037088', NULL, NULL, '孙大东', NULL, NULL, NULL, '0', '1', '0', '13595173667', NULL, 0, '2016-04-21 21:05:09', 'fc4376c0-ba88-4958-87da-f3498281839b', NULL),
	('44435b3e-8cec-48f8-bc3f-724f760e6880', '6157', NULL, NULL, '伍绍宇', NULL, NULL, NULL, '0', '1', '0', '13985445687', NULL, 0, '2016-04-21 21:05:09', 'b01776e5-59a3-4892-ab1c-85e6a676388d', NULL),
	('4637eb62-0685-4213-9b8f-b0422850eb36', '006136', NULL, NULL, '申友胜', NULL, NULL, NULL, '0', '1', '0', '13595130103', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('477feff5-8c1e-449e-98a0-7ff24f8e6df8', '035245', NULL, NULL, '孙可珂', NULL, NULL, NULL, '0', '1', '0', '13885158558', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('47dfbbb2-3148-4962-aa85-28f906bad640', '035257', NULL, NULL, '王黔', NULL, NULL, NULL, '0', '1', '0', '13985138002', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('485261a8-ca9c-4b72-a862-073985a0ac9f', '042031', NULL, NULL, '周航丞', NULL, NULL, NULL, '0', '1', '0', '18786005034', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('48722d8e-df30-47ab-9277-53c1fe1dd72b', '005955', NULL, NULL, '成珍禹', NULL, NULL, NULL, '0', '1', '0', '18984818180', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('488a4c55-d1e5-4816-800f-1b653d56b27b', '006403', NULL, NULL, '王宇翔', NULL, NULL, NULL, '0', '1', '0', '18985188333', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('4ad4c1e4-b30e-48a0-8a40-8e9db0781efe', '037093', NULL, NULL, '李伟', NULL, NULL, NULL, '0', '1', '0', '13984854444', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('4b699e55-a009-4e22-93cb-615d25c37c00', '42033', NULL, NULL, '龙飞', NULL, NULL, NULL, '0', '1', '0', '13765123202', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('4bf300ca-f15d-42f7-9c39-0a4bf18b1723', '037079', NULL, NULL, '蒋辉', NULL, NULL, NULL, '0', '1', '0', '13618593130', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('4c916c29-38ce-48a7-82d9-2baa8646909e', '035259', NULL, NULL, '李在峰', NULL, NULL, NULL, '0', '1', '0', '13985179795', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('4de42652-f951-4650-bf67-204f380265c6', '004969', '0', '', '邓如山', '0', '', '0', '0', '1', '0', '13518506190', NULL, NULL, '2016-08-07 19:08:13', '01', NULL),
	('4e01df07-86df-4669-a320-9ffc1ba15b5b', '006390', NULL, NULL, '张书军', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '710ec0e8-fe8a-42da-b4e4-353a380be40d', NULL),
	('5005b0a9-6db9-4c3c-bbdd-14552d735239', '035285', NULL, NULL, '丁亮', NULL, NULL, NULL, '0', '1', '0', '18085160777', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('5014029c-ca73-4262-b0a2-83c3ba9e6f0b', '042076', NULL, NULL, '魏峰', NULL, NULL, NULL, '0', '1', '0', '13984111650', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('50b018fa-ad4b-4c16-80b4-cb5ccfed7836', '37580', NULL, NULL, '杨值玉', NULL, NULL, NULL, '0', '1', '0', '15185022688', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('50b23839-8220-4314-b61f-6c028af6d23a', '006424', NULL, NULL, '徐庭龙', NULL, NULL, NULL, '0', '1', '0', '13984155533', NULL, 0, '2016-04-21 21:05:09', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', NULL),
	('53279ac9-be4f-4ebf-a078-81cfc5925b99', '42062', NULL, NULL, '刘箭', NULL, NULL, NULL, '0', '1', '0', '18786750593', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('538606f9-e9f4-48f7-8032-8213ee12845a', '006032', NULL, NULL, '曹刚', NULL, NULL, NULL, '0', '1', '0', '13985492522', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('551a3de3-e1d5-44ec-ab56-e7279a02dd2b', '035274', NULL, NULL, '王斌', NULL, NULL, NULL, '0', '1', '0', '13595033877', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('56864932-435a-4ac7-957e-1ce3d153989a', '037103', NULL, NULL, '段红光', NULL, NULL, NULL, '0', '1', '0', '13765000466', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('58b129c0-0ada-4722-8f64-bd6ed2c6eacd', '006451', NULL, NULL, '刘乙', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('5bae086f-16b4-4bbb-89af-7b07061defc0', '037101', NULL, NULL, '张伶丽', NULL, NULL, NULL, '0', '1', '0', '18085168686', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('5c1bda4c-7704-49da-a8d5-7169a935638b', '006394', NULL, NULL, '王亦林', NULL, NULL, NULL, '0', '1', '0', '13511911266', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('5d10baea-dd62-4447-a396-d59ab9f45e7a', '035275', NULL, NULL, '钱奕霖', NULL, NULL, NULL, '0', '1', '0', '15985192075', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('5d9f21d3-d294-45de-9d65-268b929d2ec8', '037601', NULL, NULL, '周静', NULL, NULL, NULL, '0', '1', '0', '18786659399', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('5e5aab2d-4047-4b60-bb52-f5ab10552a10', '37082', NULL, NULL, '褚建刚', NULL, NULL, NULL, '0', '1', '0', '13628513916', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('5e7e3864-9880-4955-883f-6e8ccecd6b74', 'jh10', '0', '', '刘光平', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-22 11:48:55', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('5e9509f0-cd50-4786-8f0a-1a899ccdbc13', '042013', NULL, NULL, '安智', NULL, NULL, NULL, '0', '1', '0', '18085350983', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('61b4c7b6-6bff-4956-b7a9-ba9a1deecefb', '35241', NULL, NULL, '王勇', NULL, NULL, NULL, '0', '1', '0', '13985111360', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('625195c9-73a6-4cfe-b50b-eac3d03b7e43', '037090', NULL, NULL, '李玉江', NULL, NULL, NULL, '0', '1', '0', '15519038821', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('62832497-fe74-42ec-b969-ba534a6c8858', '030420', NULL, NULL, '陈新年', NULL, NULL, NULL, '0', '1', '0', '13885059399', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('63057fe4-ddb7-48fe-bb20-75e4a431c8a9', '042073', NULL, NULL, '张昕燕', NULL, NULL, NULL, '0', '1', '0', '15185145973', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('64964926-7681-47e7-87f3-67d1f607b27d', '5802', NULL, NULL, '王刚', NULL, NULL, NULL, '0', '1', '0', '13511914927', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('64d51f14-0e8c-44f2-9398-1dd7343b0589', '008457', NULL, NULL, '王小华', NULL, NULL, NULL, '0', '1', '0', '18885160023', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('6522feed-4880-4658-9ee4-77397b8d4562', '004956', '0', '', '罗新安', '0', '', '0', '0', '1', '0', '13985018107', NULL, NULL, '2016-08-07 18:58:07', '01', NULL),
	('6564f375-9165-434f-8d46-0ff3a30b8cbe', '42100', NULL, NULL, '王威', NULL, NULL, NULL, '0', '1', '0', '17785102122', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('664b3c2b-caca-4a77-9291-21186f73212f', '005727', NULL, NULL, '丁辉', NULL, NULL, NULL, '0', '1', '0', '13595026555', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('67951e10-2f96-4bd2-89e7-0bb5b7a69331', '006474', NULL, NULL, '冷丛洪', NULL, NULL, NULL, '0', '1', '0', '13985565735', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('68224e8d-279d-4c5e-9f2b-26d0426e4ef5', '035262', NULL, NULL, '蔡春山', NULL, NULL, NULL, '0', '1', '0', '13809484955', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('6894522a-4a55-4603-8f8d-0fbb80d03f9c', '005825', NULL, NULL, '刘军', NULL, NULL, NULL, '0', '1', '0', '13985555355', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('68b0b6c6-a8d9-492d-b22d-497a34470d6b', '006414', NULL, NULL, '龙爱山', NULL, NULL, NULL, '0', '1', '0', '13985588977', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('6925ddbe-6353-40e6-826d-81a1772e66d3', '042092', NULL, NULL, '王磊', NULL, NULL, NULL, '0', '1', '0', '13648506467', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('6df1d222-2c84-4741-9087-3e3decf7b4ed', '037104', NULL, NULL, '罗吉松', NULL, NULL, NULL, '0', '1', '0', '13985162159', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('6ec70efa-e482-49ab-9bd9-f55beb8e4489', '009910', NULL, NULL, '陈娟', NULL, NULL, NULL, '0', '1', '0', '13608539799', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('7039ab71-66bd-4c86-8f7e-808250ac60a6', '037849', NULL, NULL, '区杰', NULL, NULL, NULL, '0', '1', '0', '15285519858', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('7070ed36-78b4-40d1-a791-9b42a65127f7', '004953', '0', '', '杨松', '0', '', '0', '0', '1', '0', '13283831569', NULL, NULL, '2016-08-07 18:58:00', '01', NULL),
	('7481c5a1-a312-42a0-b041-f5ec1c9944e9', '42029', NULL, NULL, '武志强', NULL, NULL, NULL, '0', '1', '0', '18984311068', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('748779b4-6ddb-49f4-9a60-894f67e95fe8', '004964', '0', '', '王伟伟', '0', '', '0', '0', '1', '0', '13638500704', NULL, NULL, '2016-08-07 19:07:48', '01', NULL),
	('74becddd-960a-487f-950e-5608a635bc18', '035246', NULL, NULL, '唐家庚', NULL, NULL, NULL, '0', '1', '0', '13595165699', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('75a4668e-34f1-478d-9e12-fccf4aa592e6', '006447', NULL, NULL, '盖雷', NULL, NULL, NULL, '0', '1', '0', '15985127622', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('75df1390-3943-495b-8763-d1b1d1908d01', '006137', NULL, NULL, '杨荣', NULL, NULL, NULL, '0', '1', '0', '15286024010', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('76315024-8dc9-4c3a-8898-c6ef5859c5de', '042055', NULL, NULL, '蒋宇丹', NULL, NULL, NULL, '0', '1', '0', '13639001439', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('76848421-e0f3-4f5e-8538-86bd5ec05a88', '006453', NULL, NULL, '石华', NULL, NULL, NULL, '0', '1', '0', '13885153211', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('77bae380-62ea-42db-a587-45cdafceb08e', '42081', NULL, NULL, '陈毅', NULL, NULL, NULL, '0', '1', '0', '18798601838', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('782cf065-eca3-4598-b853-6a97665e7d48', '004967', '0', '', '余建明', '0', '', '0', '0', '1', '0', '83831569', NULL, NULL, '2016-08-07 19:07:54', '01', NULL),
	('79513371-73cd-4556-ac5c-3368efd9ad9e', '037080', NULL, NULL, '冯建军', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('79f78a1a-3aeb-430d-a58b-092ab7153004', '006457', NULL, NULL, '杨黔筑', NULL, NULL, NULL, '0', '1', '0', '13984863016', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('7a6e2091-feea-4f2b-9ac4-935f6f89f29d', '006439', NULL, NULL, '何意', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('7b1b35a3-a78f-4e74-9ee5-221699ddb463', '6401', NULL, NULL, '贺文君', NULL, NULL, NULL, '0', '1', '0', '15180811997', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('7c055285-bdc1-4c0c-b13d-9dd08f22bbb5', '006467', NULL, NULL, '林健', NULL, NULL, NULL, '0', '1', '0', '13511911528', NULL, 0, '2016-04-21 21:05:09', 'fc4376c0-ba88-4958-87da-f3498281839b', NULL),
	('7d7900d3-4ce0-418d-b9b6-b6ca7ba8a744', '042038', NULL, NULL, '顾培玲', NULL, NULL, NULL, '0', '1', '0', '15180829344', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('7edad144-5bec-4001-89f0-4e54f555b969', '035272', NULL, NULL, '马骏', NULL, NULL, NULL, '0', '1', '0', '13985448264', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('7fff0552-7822-4d9f-bb39-af530b127557', '035255', NULL, NULL, '易景祥', NULL, NULL, NULL, '0', '1', '0', '13985555007', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('804e698b-f1c6-4482-8ab3-269d8af95859', '0006450', '0', '', '聂祥敏', '0', '', '0', '0', '2', '0', '13885130430', NULL, NULL, '2016-07-29 07:46:38', '01', NULL),
	('8051925d-17c1-490e-866d-7962e860ef66', '042091', NULL, NULL, '陈翔燕', NULL, NULL, NULL, '0', '1', '0', '18690745991', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('80683302-1865-4196-a430-0d8072875b91', '035251', NULL, NULL, '廖一龙', NULL, NULL, NULL, '0', '1', '0', '13985566345', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('8169c140-8b13-460f-b1ec-e64346911c67', '35289', NULL, NULL, '王顺', NULL, NULL, NULL, '0', '1', '0', '13985111360', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('81ef2e05-6924-418e-87ff-a21cf80b200c', '006408', NULL, NULL, '刘献红', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('823c0b92-2fc1-4909-8f8d-17ef1e51089a', '042046', NULL, NULL, '唐莉', NULL, NULL, NULL, '0', '1', '0', '13885051261', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('83ce362e-04f5-4edb-ba78-120ca11867a4', '035282', NULL, NULL, '赵光宇', NULL, NULL, NULL, '0', '1', '0', '13368611936', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('83d980ac-272b-4063-9802-091f79401e2a', '4954', NULL, NULL, '倪奎', NULL, NULL, NULL, '0', '1', '0', '13518506180', NULL, 0, '2016-04-21 21:05:09', 'b01776e5-59a3-4892-ab1c-85e6a676388d', NULL),
	('85ea0d5e-7e7e-4577-a360-913a5e01c875', '006151', NULL, NULL, '涂利军', NULL, NULL, NULL, '0', '1', '0', '13985058071', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('863efa91-4f3d-4978-bdc5-65898f834b12', '042054', NULL, NULL, '李苑', NULL, NULL, NULL, '0', '1', '0', '15186955003', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('86945308-8139-49ab-986d-9170f982022d', '042023', NULL, NULL, '冯桂林', NULL, NULL, NULL, '0', '1', '0', '18085196500', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('86b80799-4abb-4d4e-a08c-ed6c7bef7417', '006111', NULL, NULL, '沈灵良', NULL, NULL, NULL, '0', '1', '0', '18798870274', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('872e1c5e-8f45-431d-8e0b-b65c16c3fee3', '42030', NULL, NULL, '曾麒麟', NULL, NULL, NULL, '0', '1', '0', '13908502227', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('874f7015-35cb-447b-ac7a-dbd7df4908c8', '042025', NULL, NULL, '刘兆源', NULL, NULL, NULL, '0', '1', '0', '15885046627', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('879aabb9-0757-4f8c-af97-e73a5bbdc9c8', '30452', NULL, NULL, '吴隽', NULL, NULL, NULL, '0', '1', '0', '15085977866', NULL, 0, '2016-04-21 21:05:09', 'b01776e5-59a3-4892-ab1c-85e6a676388d', NULL),
	('882b7388-da1e-4af4-97ab-ef843362837c', '1231231', '0', '123123', '人人', '0', '123123', '0', '0', '1', '0', '123123', NULL, NULL, '2016-08-07 19:15:19', 'b01776e5-59a3-4892-ab1c-85e6a676388d', NULL),
	('88512b24-f81a-4cee-b267-7b07bc05c3ea', '042048', NULL, NULL, '罗瑞', NULL, NULL, NULL, '0', '1', '0', '15285113625', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('8875b8b2-8943-4ce3-b212-433a4e4f5a14', '042016', NULL, NULL, '雷坤', NULL, NULL, NULL, '0', '1', '0', '13984826677', NULL, 0, '2016-04-21 21:05:09', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', NULL),
	('88b7a845-d452-495c-abca-57904634ed12', '006446', NULL, NULL, '蒋远海', NULL, NULL, NULL, '0', '1', '0', '13984330235', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('89874e4b-52a1-4d2a-a150-c0a82720f96a', '030486', NULL, NULL, '李鑫', NULL, NULL, NULL, '0', '1', '0', '13398512291', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('8a0c5199-8d37-4697-b017-e10c5ff4c54a', '005582', '0', '', '文发春', '0', '', '0', '0', '1', '0', '13985120068', NULL, NULL, '2016-08-07 19:08:29', '01', NULL),
	('8a1447bb-4674-48c3-8de3-a1b8f68af0f5', '见习民警1', NULL, NULL, '梁桂超', NULL, NULL, NULL, '0', '1', '0', '18786017286', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('8a421eaa-3d51-4e84-87cf-73fbb5c2c2a0', '006425', NULL, NULL, '姜勋强', NULL, NULL, NULL, '0', '1', '0', '13984360006', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('8a72f21d-9458-4c3c-9638-4487d49415b5', '006449', NULL, NULL, '张劲勇', NULL, NULL, NULL, '0', '1', '0', '13985517016', NULL, 0, '2016-04-21 21:05:09', 'fc4376c0-ba88-4958-87da-f3498281839b', NULL),
	('8d32e52e-9c76-4a1e-a6a4-927493dce013', '见习民警2', NULL, NULL, '徐琛', NULL, NULL, NULL, '0', '1', '0', '18608567560', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('8e3b795f-6183-4f5a-bfde-65951075638e', '006141', NULL, NULL, '蒋波', NULL, NULL, NULL, '0', '1', '0', '13985162002', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('8e89ff57-5534-4d47-a0d4-dbf7424444e8', '035283', NULL, NULL, '班华', NULL, NULL, NULL, '0', '1', '0', '13765085997', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('8ea400c3-b7ae-4ea8-ba14-78f0fe0925e3', '042021', NULL, NULL, '杨洋', NULL, NULL, NULL, '0', '1', '0', '13595178928', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('8f23b3aa-e0f1-4d5e-890f-bd64c6fb38d5', '042009', NULL, NULL, '冯川', NULL, NULL, NULL, '0', '1', '0', '13118508333', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('918b462c-78b3-4cb7-9dbb-65574b592a20', '005922', NULL, NULL, '刘军', NULL, NULL, NULL, '0', '1', '0', '13885090977', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('920e6f13-e787-4971-bb6a-8c8a07cdfcbd', '35253', NULL, NULL, '石林', NULL, NULL, NULL, '0', '1', '0', '13608584334', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('9231075c-ae77-4900-98f2-d30b6b3d7235', '030451', NULL, NULL, '王妮丽', NULL, NULL, NULL, '0', '1', '0', '18984573107', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('92af6e7c-297f-429c-aca0-c19d72db0339', '37074', NULL, NULL, '廖全勇', NULL, NULL, NULL, '0', '1', '0', '13668508868', NULL, 0, '2016-04-21 21:05:09', 'b01776e5-59a3-4892-ab1c-85e6a676388d', NULL),
	('942760c4-c814-45ed-9dd1-2d36caad2a16', '042058', NULL, NULL, '袁诗梅', NULL, NULL, NULL, '0', '1', '0', '13885007484', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('9462017e-ddec-4601-bd9d-8c04051adefe', '004925', '0', '', '王晨星', '0', '', '0', '0', '1', '0', '13984062100', NULL, NULL, '2016-08-07 18:57:07', '01', NULL),
	('96527513-a5ce-4b55-abe0-fbd68fda4373', '009258', NULL, NULL, '王健', NULL, NULL, NULL, '0', '1', '0', '13985113563', NULL, 0, '2016-04-21 21:05:09', '585fdd1b-4453-443d-8d28-305368c99728', NULL),
	('9673c899-e500-449f-b208-61ceaf682590', '035276', NULL, NULL, '熊远明', NULL, NULL, NULL, '0', '1', '0', '13511911511', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('96864930-b77b-4e99-bb70-9b0b9a6d1d4a', '006466', NULL, NULL, '向顺华', NULL, NULL, NULL, '0', '1', '0', '13511930502', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('96b4766c-3b07-4e99-bd49-7d211c606a16', '035290', NULL, NULL, '陈鹍', NULL, NULL, NULL, '0', '1', '0', '13985123765', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('986bcb3f-73a0-4693-a8a7-3d46cd855a2a', '004957', '0', '', '唐堂', '0', '', '0', '0', '1', '0', '83831569', NULL, NULL, '2016-08-07 19:05:04', '01', NULL),
	('999faf37-77d1-43c7-b17a-7b8a0a5a7734', '035261', NULL, NULL, '薄军', NULL, NULL, NULL, '0', '1', '0', '13027813222', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('99e55daf-3b0c-405f-8e1d-dfea1284786a', '042069', NULL, NULL, '吴昌才', NULL, NULL, NULL, '0', '1', '0', '15285910887', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('9a94922e-2093-41de-be1f-5a88e740a36d', '042037', NULL, NULL, '龙宇', NULL, NULL, NULL, '0', '1', '0', '15985136814', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('9a9beb51-a51c-489f-9e63-492e162a3977', '037083', NULL, NULL, '杨涛', NULL, NULL, NULL, '0', '1', '0', '15885077219', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('9b67e053-d5e8-4210-b086-493d1628db68', '006134', NULL, NULL, '龚天雨', NULL, NULL, NULL, '0', '1', '0', '15285122467', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('9c7fd8e7-4aef-495b-a5ca-902d8ec3e621', '42098', NULL, NULL, '吴华东', NULL, NULL, NULL, '0', '1', '0', '18798059322', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('9df98b77-e91b-48b6-b1f4-86f2075ea8ee', '004961', '0', '', '张宇辉', '0', '', '0', '0', '1', '0', '13985423998', NULL, NULL, '2016-08-07 19:07:26', '01', NULL),
	('9e5d2cbd-435b-48e2-89bc-2c28a1877baa', '42028', NULL, NULL, '王斐', NULL, NULL, NULL, '0', '1', '0', '18508510166', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('9ec3eee2-6014-4cd6-81d1-fed9344ac8c8', '042004', NULL, NULL, '任大河', NULL, NULL, NULL, '0', '1', '0', '15885094335', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('9f32a897-4b5f-4853-9377-817be8f3dbf1', '006455', NULL, NULL, '张稀国', NULL, NULL, NULL, '0', '1', '0', '13985151413', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('9f59ae50-c039-4845-a9d4-e7a5200e5202', '042080', NULL, NULL, '邵崑', NULL, NULL, NULL, '0', '1', '0', '18984333863', NULL, 0, '2016-04-21 21:05:09', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', NULL),
	('9ff19614-49ac-4009-9943-7db7d01f3940', '42014', NULL, NULL, '王飞', NULL, NULL, NULL, '0', '1', '0', '13595072248', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('a0f78335-49de-4325-8da6-50eeca460b00', '037091', NULL, NULL, '杨国军', NULL, NULL, NULL, '0', '1', '0', '13639104966', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('a109984e-a1c0-43d9-8b3f-da80cba6c1ad', '004962', '0', '', '于林根', '0', '', '0', '0', '1', '0', '83831569', NULL, NULL, '2016-08-07 19:07:35', '01', NULL),
	('a11bbc3a-99e7-459e-9025-596f01f50f61', '004794', '0', '', '杨婵娟', '0', '', '0', '0', '1', '0', '15286098567', NULL, NULL, '2016-07-29 07:49:00', '01', NULL),
	('a4fafeaf-efdf-47f2-8770-bd4e31827e02', '35355', NULL, NULL, '裴放', NULL, NULL, NULL, '0', '1', '0', '13037837835', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('a780a78c-4c89-4d79-9a03-ca377dcaecb5', '035264', NULL, NULL, '陈佾红', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('a7981fc8-671f-4752-9833-923af8c98f6d', '42086', NULL, NULL, '黄焱林', NULL, NULL, NULL, '0', '1', '0', '15208639259', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('a7adf650-8a68-4127-ba00-a379ec0b0272', '042045', NULL, NULL, '李枫', NULL, NULL, NULL, '0', '1', '0', '13908517634', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('a86e64ef-37d0-451a-9f1e-902c2b499285', '035258', NULL, NULL, '黄彬', NULL, NULL, NULL, '0', '1', '0', '13985515333', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('abc0f144-bb72-4b91-8568-7835852d3959', '009387', NULL, NULL, '齐玉芳', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '710ec0e8-fe8a-42da-b4e4-353a380be40d', NULL),
	('ac037893-eefc-4abc-8b27-ec6018a9b9d0', '042056', NULL, NULL, '刘波', NULL, NULL, NULL, '0', '1', '0', '13765082634', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('ac170c46-929e-46c8-a3f8-e14daa96fb44', '004798', '0', '', '杜美', '0', '', '0', '0', '1', '0', '15285159509', NULL, NULL, '2016-07-29 07:49:14', '01', NULL),
	('ac2afe70-d23d-48ff-8953-8dad74718091', '037085', NULL, NULL, '胡明发', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('ac703880-ee20-4163-af53-baeee568b24d', '004888', '0', '', '涂晓梅', '0', '', '0', '0', '1', '0', '13378500607', NULL, NULL, '2016-07-29 07:49:22', '01', NULL),
	('acc48e23-00c7-4bd5-8ee0-019cdc44132d', '042063', NULL, NULL, '刘明明', NULL, NULL, NULL, '0', '1', '0', '13984395011', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('adb0a41b-fe58-4600-9c9e-ae19287737c9', '009910', NULL, NULL, '张应笠', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('b05a71be-5dd8-46db-966e-f36a7f640d6e', '42071', NULL, NULL, '舒瑛', NULL, NULL, NULL, '0', '1', '0', '18690727881', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('b0a9a263-91b4-48f1-88f0-9d8b69383c33', '006477', NULL, NULL, '杜军', NULL, NULL, NULL, '0', '1', '0', '13985057045', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('b1e631d1-af78-4575-a876-2a531b172baf', '035279', NULL, NULL, '张浩', NULL, NULL, NULL, '0', '1', '0', '13885044588', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('b48490e4-0d5c-481a-8e59-679d4ccfc8cf', '042010', NULL, NULL, '刘德林', NULL, NULL, NULL, '0', '1', '0', '13984143322', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('b5462039-4853-405f-8527-be69c653a46c', '006445', NULL, NULL, '陈阳福', NULL, NULL, NULL, '0', '1', '0', '13885135497', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('b5c0aeac-c42a-4008-87bc-6916ec835421', '037076', NULL, NULL, '王万军', NULL, NULL, NULL, '0', '1', '0', '13809428104', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('b62416bf-e0b5-4ea0-8d37-d732de3dd434', '042094', NULL, NULL, '邵帅', NULL, NULL, NULL, '0', '1', '0', '18285170937', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('b68d5354-b5fe-4f2f-9a08-bd3aef1797a3', '042061', NULL, NULL, '简成润', NULL, NULL, NULL, '0', '1', '0', '15085954008', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('b75533cd-b339-4b78-9fcb-b421b70f9b24', '035294', NULL, NULL, '王俊芳', NULL, NULL, NULL, '0', '1', '0', '13984116480', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('b8561ea9-0520-46f5-9c56-b3974bb89f5a', '006084', NULL, NULL, '万晓东', NULL, NULL, NULL, '0', '1', '0', '13595001001', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('b8d913ba-7344-45e3-81fc-a152b3581d3c', '006075', NULL, NULL, '葛伟', NULL, NULL, NULL, '0', '1', '0', '18984091806', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('bb017bf0-f881-47a9-99b7-c7f11b5cf3cc', '009259', NULL, NULL, '任大东', NULL, NULL, NULL, '0', '1', '0', '13985025596', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('bbcacf10-99e7-4f75-b757-0d97bef01719', '006456', NULL, NULL, '谷彦君', NULL, NULL, NULL, '0', '1', '0', '18185090555', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('bbf58f58-fd5f-4762-9b0c-b42e4e8b0bdf', '006421', NULL, NULL, '武长文', NULL, NULL, NULL, '0', '1', '0', '13339609177', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('bc143357-ccfb-40f7-849b-3586c92feeab', '042078', NULL, NULL, '骆小峰', NULL, NULL, NULL, '0', '1', '0', '18585436300', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('bc448454-a4b8-44ed-860f-822d93707ce2', '042047', NULL, NULL, '兰岚', NULL, NULL, NULL, '0', '1', '0', '18084254855', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('bc63719e-9dd0-4c93-834c-4f2aaa656cb7', '006475', NULL, NULL, '杨发坤', NULL, NULL, NULL, '0', '1', '0', '13511928833', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('bd13696d-b469-4baf-b4fd-04e40188452d', '42077', NULL, NULL, '刘光平', NULL, NULL, NULL, '0', '1', '0', '18286171802', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('bdb5d8d9-360f-4943-9f8c-07e09f614790', 'jh19', '0', '', '朱坤', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-22 11:49:39', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('bed59704-91ba-4e32-85cd-e3b5709aee3b', '042001', NULL, NULL, '石玮', NULL, NULL, NULL, '0', '1', '0', '13765139735', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('bed7fc51-77f3-459d-82a7-4e98b911563c', '037078', NULL, NULL, '郑海', NULL, NULL, NULL, '0', '1', '0', '13885085510', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('bf4f16d7-e0ea-4ab9-a5f6-585bc9bf962c', '042005', NULL, NULL, '付强', NULL, NULL, NULL, '0', '1', '0', '15285506507', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('bfb8d1ec-2139-466b-88ac-b83c95b439c4', '042022', NULL, NULL, '苑建波', NULL, NULL, NULL, '0', '1', '0', '18885120004', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('bfce90a3-fd59-4779-9f7b-e308bcd3ef05', '042006', NULL, NULL, '洪一峰', NULL, NULL, NULL, '0', '1', '0', '15185000717', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('c01a669d-ae94-4361-a20b-8817db0774f8', '042070', NULL, NULL, '陈东吴', NULL, NULL, NULL, '0', '1', '0', '13985476807', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('c05cda0f-cc72-4a6d-92bb-af025e507199', '035256', NULL, NULL, '蔡剑', NULL, NULL, NULL, '0', '1', '0', '18985025797', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('c0632cb3-d836-4926-90ef-877744a8eee7', '005852', NULL, NULL, '关露阳', NULL, NULL, NULL, '0', '1', '0', '18786755919', NULL, 0, '2016-04-21 21:05:09', 'fc4376c0-ba88-4958-87da-f3498281839b', NULL),
	('c17bc469-fd1d-4b91-ba1e-14a740caaf42', '006452', NULL, NULL, '万钧', NULL, NULL, NULL, '0', '1', '0', '13885106675', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('c1b981d5-82b3-47df-afc6-417922a6351e', '004959', '0', '', '刘辉', '0', '', '0', '0', '1', '0', '13608507606', NULL, NULL, '2016-08-07 19:07:21', '01', NULL),
	('c1dbe084-120a-4a81-b7f2-7f2064889a7c', '035225', NULL, NULL, '李晓琴', NULL, NULL, NULL, '0', '1', '0', '18685114977', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('c1f69409-0df5-4e29-b7ca-d56c779ffde5', '006389', NULL, NULL, '蔡鹏', NULL, NULL, NULL, '0', '1', '0', '13984891053', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('c28a8238-d5d9-476c-a210-e56cece8c63e', '037156', NULL, NULL, '张沏', NULL, NULL, NULL, '0', '1', '0', '15285060766', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('c3dc060b-6bc4-477c-8098-c06137257d19', '006132', NULL, NULL, '沈涧', NULL, NULL, NULL, '0', '1', '0', '13985170123', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('c450b9ca-1763-44ad-b847-afd25e4ef0ee', '42043', NULL, NULL, '张锃谅', NULL, NULL, NULL, '0', '1', '0', '13312273585', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('c469c6af-99a2-4f64-b399-a3451694e3bd', '035280', NULL, NULL, '张辛', NULL, NULL, NULL, '0', '1', '0', '13885157162', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('c48139ee-e467-40dd-9b0d-4c96a4f2544d', '004966', '0', '', '张鲁', '0', '', '0', '0', '1', '0', '13885012168', NULL, NULL, '2016-08-07 19:08:01', '01', NULL),
	('c6e64de8-632a-4c00-9f5a-b399fa1ef147', '37077', NULL, NULL, '龙刚', NULL, NULL, NULL, '0', '1', '0', '13984013659', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('c88dc069-0625-4f4b-b90c-e4723176cd7b', '035250', NULL, NULL, '张军', NULL, NULL, NULL, '0', '1', '0', '13984007345', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('c94b79a5-d295-42f2-b0e1-7c0d4f785d75', '004752', '0', '', '郭军', '0', '', '0', '0', '1', '0', '13765166600', NULL, NULL, '2016-07-29 07:48:25', '01', NULL),
	('c9d25671-f0c7-45ca-8daf-73357668709d', '006448', NULL, NULL, '王晓乐', NULL, NULL, NULL, '0', '1', '0', '13985027002', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('c9d4cedc-3bb2-4367-b34b-8f705a1fcc90', 'jh21', '0', '', '项伟', '0', '', '0', '0', '1', '0', '13585001983', NULL, NULL, '2016-03-17 17:34:00', 'd36628c3-d4e4-4143-b6bb-c9f6b89f2aa9', NULL),
	('ca211ae1-975e-4eba-9ded-2b9d8260266e', '006404', NULL, NULL, '郑海燕', NULL, NULL, NULL, '0', '1', '0', '13398512292', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('cadfd380-ce9d-48f3-bbce-e2cae9226c95', 'jh23', '0', '', '刘箭', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-22 11:49:08', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('cc5ef81d-f699-4301-839c-a7048551a726', '042018', NULL, NULL, '刘彦君', NULL, NULL, NULL, '0', '1', '0', '13885339177', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('cd8634fa-dca9-4a0d-bbe1-dcbaf1459534', '42008', NULL, NULL, '孙林', NULL, NULL, NULL, '0', '1', '0', '18585081007', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('cfc23a80-fb5b-48af-8dc3-659934826c33', '042003', NULL, NULL, '何杰', NULL, NULL, NULL, '0', '1', '0', '13765078899', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('d064d942-c5c8-45af-a727-9abe5b4a322b', '35242', NULL, NULL, '崔鹏', NULL, NULL, NULL, '0', '1', '0', '13518510618', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('d14eaac4-f985-4246-a272-634f8181f60e', '042067', NULL, NULL, '邓贤', NULL, NULL, NULL, '0', '1', '0', '15085933360', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('d32ef095-777c-43d3-9595-6e90ecef3dc7', '35278', NULL, NULL, '张斌', NULL, NULL, NULL, '0', '1', '0', '13885003378', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('d436791a-3009-4a23-8846-a64c858f3ba6', '035252', NULL, NULL, '阮期敏', NULL, NULL, NULL, '0', '1', '0', '13885047395', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('d4dc1021-5dc8-4e6a-8566-1dc05b8a2b6e', '35243', NULL, NULL, '李超', NULL, NULL, NULL, '0', '1', '0', '13339607866', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('d526d795-291c-4be1-8dd1-f3e8bc6a1cb4', '004958', '0', '', '王洪利', '0', '', '0', '0', '1', '0', '13885010385', NULL, NULL, '2016-08-07 19:07:14', '01', NULL),
	('d66d50c0-456a-44c4-8ec9-e8762cfc7d65', '042068', NULL, NULL, '蔡珊', NULL, NULL, NULL, '0', '1', '0', '18685058932', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('d7713ffa-1f0e-43fa-bd03-eebdc80a8c2e', '42012', NULL, NULL, '陈劲鹏', NULL, NULL, NULL, '0', '1', '0', '18984073393', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('d8a5a5e3-fdfb-487f-ab2d-e31e76ba9b09', '006395', NULL, NULL, '郭冰', NULL, NULL, NULL, '0', '1', '0', '13985107806', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('d8df7efb-54da-4c49-89c5-5aa2618a4f03', '004753', '0', '', '刘馨镁', '0', '', '0', '0', '1', '0', '18685004223', NULL, NULL, '2016-07-29 07:48:32', '01', NULL),
	('d935f77c-8774-4865-b5d1-6f21cbb86293', '035296', NULL, NULL, '张淑玉', NULL, NULL, NULL, '0', '1', '0', '13885127605', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('d9673ca0-d535-4f49-9a52-603862fae63a', '009911', NULL, NULL, '苟冰', NULL, NULL, NULL, '0', '1', '0', '13765005512', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('dbfc49e5-1392-46d4-a71f-62eff338305c', '006402', NULL, NULL, '王钟', NULL, NULL, NULL, '0', '1', '0', '13985458989', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('dc1efb93-9368-4423-b58b-abe3d8f921ed', '042050', NULL, NULL, '朱岱', NULL, NULL, NULL, '0', '1', '0', '18212000622', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('dc791589-5118-470d-bce6-52faa510b708', '042090', NULL, NULL, '吴易学', NULL, NULL, NULL, '0', '1', '0', '13985577601', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('dc89f625-48c3-4f88-8692-14897416efa3', '042002', NULL, NULL, '王应魁', NULL, NULL, NULL, '0', '1', '0', '13765017609', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('dc9de729-1197-4db9-8735-f79cfc4a30c8', '35291', NULL, NULL, '付安政', NULL, NULL, NULL, '0', '1', '0', '13595053677', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('ddc13bea-23c2-45c0-a3cc-58ce31c414fe', '009263', NULL, NULL, '黄承林', NULL, NULL, NULL, '0', '1', '0', '13618579118', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('de772d9e-3700-4457-ba70-f4b2dec3df89', '004970', '0', '', '潘伟俊', '0', '', '0', '0', '1', '0', '13518506170', NULL, NULL, '2016-08-07 19:08:18', '01', NULL),
	('de88436f-21b3-4cb5-ade7-dea5cfaea4d6', 'jh25', '0', '', '张零', '0', '', '0', '0', '1', '0', '', NULL, NULL, '2016-03-18 20:57:14', 'd36628c3-d4e4-4143-b6bb-c9f6b89f2aa9', NULL),
	('e040e44f-ff2c-4d23-899f-07374e54043b', '042052', NULL, NULL, '陈进', NULL, NULL, NULL, '0', '1', '0', '13639105191', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('e05224f4-3371-474c-8e0d-378ede04b92a', '006422', NULL, NULL, '周晓勇', NULL, NULL, NULL, '0', '1', '0', '13511911288', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('e2029012-2122-4696-ba04-f50264cd1720', '042017', NULL, NULL, '毛靖', NULL, NULL, NULL, '0', '1', '0', '13984331373', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('e35612aa-6def-47d6-ad80-be728ba07c80', '006386', NULL, NULL, '张薇', NULL, NULL, NULL, '0', '1', '0', '13984140677', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('e5153518-7584-4d23-8201-b8e31853ee2f', '006469', NULL, NULL, '徐广生', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('e85fe85e-fa63-49b9-b194-696beb9c4319', '006433', NULL, NULL, '周卫', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '3f025ed8-d417-45ac-96cd-af383b3fc431', NULL),
	('e8f60ee6-9650-4341-b655-47c29d4700ee', '006463', NULL, NULL, '刘大兴', NULL, NULL, NULL, '0', '1', '0', '83831569', NULL, 0, '2016-04-21 21:05:09', '710ec0e8-fe8a-42da-b4e4-353a380be40d', NULL),
	('e991017f-c820-46a7-8983-94097f4c5deb', '035271', NULL, NULL, '刘晓亚', NULL, NULL, NULL, '0', '1', '0', '13985445000', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('eb24de06-097f-466d-b45e-8932acaf3fb2', '006431', NULL, NULL, '王晓俊', NULL, NULL, NULL, '0', '1', '0', '13985034098', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('eb59e67f-1c71-4cb3-b616-2a16d1de71cb', '006468', NULL, NULL, '孙煜', NULL, NULL, NULL, '0', '1', '0', '13511907111', NULL, 0, '2016-04-21 21:05:09', '069c4e0f-a006-43d6-a9d7-36f488514c7d', NULL),
	('ebf486ae-c1ce-4d48-bcc6-1dad5c3b1ad4', '042059', NULL, NULL, '袁楠', NULL, NULL, NULL, '0', '1', '0', '17785811060', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('ec4d4cb7-437f-4bbc-ab91-e8b88fc2a89f', '009260', NULL, NULL, '邓广祥', NULL, NULL, NULL, '0', '1', '0', '13885002596', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('ee64d618-a3a1-40ac-a6f8-16c5a38f82ac', '005845', NULL, NULL, '潘和峻', NULL, NULL, NULL, '0', '1', '0', '13985131755', NULL, 0, '2016-04-21 21:05:09', '90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', NULL),
	('ef445192-7509-49b1-bbd5-4708531399eb', '42085', NULL, NULL, '朱坤', NULL, NULL, NULL, '0', '1', '0', '15286010103', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('f0161f2c-bfec-455d-a16b-a93694c4b28a', '006398', NULL, NULL, '周娟', NULL, NULL, NULL, '0', '1', '0', '13608502778', NULL, 0, '2016-04-21 21:05:09', 'e5be6a62-4cbe-468e-9cf4-205b3abaea23', NULL),
	('f05ea9a9-a61c-4aab-abb4-d0beea8e9513', '006007', NULL, NULL, '杨勇', NULL, NULL, NULL, '0', '1', '0', '13885007007', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('f17deae9-9979-448b-873a-86a46d41be47', '006092', NULL, NULL, '王永新', NULL, NULL, NULL, '0', '1', '0', '13595109997', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('f2ec2296-c9a3-41c8-b1c1-a4ac81980738', '4755', NULL, NULL, '项伟', NULL, NULL, NULL, '0', '1', '0', '13595081886', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('f3850b1a-c64b-4420-9cbb-d483b569e9df', '042066', NULL, NULL, '彭樱姿', NULL, NULL, NULL, '0', '1', '0', '18275130088', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('f3a84518-c37e-4f2d-a3ac-d07b3f2c6a06', '037081', NULL, NULL, '卜小强', NULL, NULL, NULL, '0', '1', '0', '13984803039', NULL, 0, '2016-04-21 21:05:09', '3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', NULL),
	('f62c6b92-6fd8-42dc-be5c-f26cc2107697', '009917', NULL, NULL, '李萍', NULL, NULL, NULL, '0', '1', '0', '13984349777', NULL, 0, '2016-04-21 21:05:09', '585fdd1b-4453-443d-8d28-305368c99728', NULL),
	('f73dd9fb-8c35-4ad5-9448-4e0d311efb59', '035377', NULL, NULL, '范燕玲', NULL, NULL, NULL, '0', '1', '0', '15885077708', NULL, 0, '2016-04-21 21:05:09', '0b3d22e9-a71f-4216-b98b-39fd6ef6899d', NULL),
	('f7da1aee-1e17-44c7-b0e9-16d86192b158', '006419', NULL, NULL, '冯琪', NULL, NULL, NULL, '0', '1', '0', '15208518866', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('f81bdf3d-cf63-4381-8016-c9255ccc352b', '035268', NULL, NULL, '刘翀', NULL, NULL, NULL, '0', '1', '0', '13809483288', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('f885fe07-d290-4e8f-aa91-9989723c6579', '004751', '0', '', '马宗祥', '0', '', '0', '0', '1', '0', '18685133869', NULL, NULL, '2016-07-29 07:48:16', '01', NULL),
	('f89b3b21-efa4-467e-afe6-dc5641e0f113', '042082', NULL, NULL, '刘凡', NULL, NULL, NULL, '0', '1', '0', '15285982131', NULL, 0, '2016-04-21 21:05:09', '01', NULL),
	('f944b98d-a813-4529-ab01-8ab77bc62884', '042065', NULL, NULL, '李知君', NULL, NULL, NULL, '0', '1', '0', '18585031547', NULL, 0, '2016-04-21 21:05:09', 'f1218a34-d2aa-43da-a9cf-1475a15dc3ad', NULL),
	('f9704fce-9269-4394-8c39-18c8aca05592', '42032', NULL, NULL, '黄康龙', NULL, NULL, NULL, '0', '1', '0', '18798781691', NULL, 0, '2016-04-21 21:05:09', 'e55be91b-f2cc-4de6-80cc-d460e3c92975', NULL),
	('f9be9e06-b273-4de8-850a-908063e3cf4a', '009912', NULL, NULL, '罗华林', NULL, NULL, NULL, '0', '1', '0', '13985599323', NULL, 0, '2016-04-21 21:05:09', 'b4f8b72d-1940-43a4-9518-d6606b8b768a', NULL),
	('fbbf5897-63ad-4d32-98d7-83bd5dd62844', '035257', NULL, NULL, '朱海涛', NULL, NULL, NULL, '0', '1', '0', '13908511678', NULL, 0, '2016-04-21 21:05:09', '7b8cac4c-2e89-499c-b83e-63defc1da3dd', NULL),
	('fcbf237d-e7dd-4099-9f4a-6ad9611b8bd6', '042043', NULL, NULL, '钟菲', NULL, NULL, NULL, '0', '1', '0', '13098511966', NULL, 0, '2016-04-21 21:05:09', 'e1048cb4-01fb-4dd6-a668-048cdf3a4567', NULL),
	('fd8026df-e233-40b7-aa23-79199adb235a', '042064', NULL, NULL, '罗臣', NULL, NULL, NULL, '0', '1', '0', '15519032013', NULL, 0, '2016-04-21 21:05:09', '76e82277-9ef2-4c01-b223-454048e3db9c', NULL),
	('xnry01', 'xn1', NULL, NULL, '虚拟人员', NULL, '', NULL, NULL, NULL, '0', NULL, NULL, 0, '2016-02-24 12:00:00', 'xndw01', NULL),
	('xnry02', 'admin', NULL, NULL, '超级管理员', NULL, '', NULL, NULL, NULL, '0', NULL, NULL, 0, '2016-02-24 12:00:00', '01', NULL);
/*!40000 ALTER TABLE `t_og_ry` ENABLE KEYS */;


-- 导出  表 ajgl.t_og_zh 结构
DROP TABLE IF EXISTS `t_og_zh`;
CREATE TABLE IF NOT EXISTS `t_og_zh` (
  `id` varchar(255) NOT NULL,
  `zhm` varchar(50) DEFAULT NULL,
  `sxsj` datetime DEFAULT NULL,
  `bz` varchar(50) DEFAULT NULL,
  `mm` varchar(50) DEFAULT NULL,
  `yxsj` datetime DEFAULT NULL,
  `zt` varchar(10) DEFAULT NULL,
  `sjc` datetime DEFAULT NULL,
  `ry_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ry_id` (`ry_id`),
  KEY `FKA0BBB36AAF974B5D` (`ry_id`),
  CONSTRAINT `FKA0BBB36AAF974B5D` FOREIGN KEY (`ry_id`) REFERENCES `t_og_ry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_og_zh 的数据：~44 rows (大约)
/*!40000 ALTER TABLE `t_og_zh` DISABLE KEYS */;
INSERT INTO `t_og_zh` (`id`, `zhm`, `sxsj`, `bz`, `mm`, `yxsj`, `zt`, `sjc`, `ry_id`) VALUES
	('02c1c234-65d3-444b-b9b7-f0d93273df34', 'liuyanjun', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:42:00', '1', '2016-08-09 15:12:19', 'cc5ef81d-f699-4301-839c-a7048551a726'),
	('057691fb-aeb6-40e3-a869-e02bcab7be01', 'chenkun', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:39:11', '1', '2016-08-09 15:11:49', '96b4766c-3b07-4e99-bd49-7d211c606a16'),
	('0ef2f510-9a57-4f42-a46b-89deb26a638a', 'chenxinnian', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:37:49', '1', '2016-08-09 15:11:16', '62832497-fe74-42ec-b969-ba534a6c8858'),
	('0fe4b0c6-d148-41d2-aa19-403ea4477cf6', 'shenlingliang', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:38:46', '1', '2016-08-09 15:11:38', '86b80799-4abb-4d4e-a08c-ed6c7bef7417'),
	('28280f4e-5864-4946-a940-bc4401da4ac1', 'zhengguangxiang', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:42:18', '1', '2016-08-09 15:14:26', 'ec4d4cb7-437f-4bbc-ab91-e8b88fc2a89f'),
	('4ff06de7-a52c-44da-afae-a4cb371ac79f', 'administrator', NULL, NULL, '123', NULL, '1', '2016-08-07 21:25:31', 'xnry02'),
	('69222415-7c80-4370-a468-96b7e1714294', 'xulanzhen', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:36:15', '1', '2016-08-09 15:10:41', '31536186-8c96-4839-958c-7cf6fdbbeee2'),
	('9d211d3d-6ede-4c98-a0dc-005fd18ec21f', 'xiangwei', '9999-01-01 00:00:00', '', '123456', '2016-03-17 17:34:04', '1', '2016-03-17 17:34:24', 'c9d4cedc-3bb2-4367-b34b-8f705a1fcc90'),
	('9dc6079d-e477-4d6d-8cf2-94c91ec38e07', 'liuchong', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:42:40', '1', '2016-08-09 15:12:50', 'f81bdf3d-cf63-4381-8016-c9255ccc352b'),
	('a10957bb-a61c-45e8-b1dc-05df8d0e517f', 'zhangqi', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:40:51', '1', '2016-08-09 15:12:08', 'c28a8238-d5d9-476c-a210-e56cece8c63e'),
	('ac32b1bd-4a0d-4152-a773-26c7d67d3c9e', '60028', '9999-01-01 00:00:00', '', '123456', '2016-03-17 20:20:36', '1', '2016-03-17 20:20:57', '15c3674a-00ae-41e4-8f8c-e9826e92b911'),
	('bd954a01-da01-4380-b3ab-e288e1869bba', 'ZL', '9999-01-01 00:00:00', '', '123456', '2016-03-18 20:57:17', '1', '2016-03-18 20:57:36', 'de88436f-21b3-4cb5-ade7-dea5cfaea4d6'),
	('c1c1cc1e-59e4-4640-9698-b8d7eb05fbda', 'wangqian', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:37:28', '1', '2016-08-09 15:11:05', '47dfbbb2-3148-4962-aa85-28f906bad640'),
	('c9680ebb-c333-4c92-8b91-f495a3d7ce48', 'xunizh', '9999-01-01 00:00:00', '', '123456', '2016-03-17 20:09:09', '1', NULL, 'xnry01'),
	('cbdafc27-e508-4c6b-9dd9-9c9b9eba02e0', 'shenyousheng', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:36:58', '1', '2016-08-09 15:10:53', '4637eb62-0685-4213-9b8f-b0422850eb36'),
	('e0d96ca7-b357-44e0-a219-b19f3a399f46', 'tulijun', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:38:18', '1', '2016-08-09 15:11:28', '85ea0d5e-7e7e-4577-a360-913a5e01c875'),
	('f29204fd-d6cc-4fb4-a787-1e0f826d1a97', 'luochen', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:43:36', '1', '2016-08-09 15:14:00', 'fd8026df-e233-40b7-aa23-79199adb235a'),
	('ffa0c6ca-0227-40a6-88e7-7d4bdde49346', 'rendadong', '2099-01-01 00:00:00', '', '123456', '2016-08-09 10:40:10', '1', '2016-08-09 14:21:08', 'bb017bf0-f881-47a9-99b7-c7f11b5cf3cc');
/*!40000 ALTER TABLE `t_og_zh` ENABLE KEYS */;


-- 导出  表 ajgl.t_og_zzjg 结构
DROP TABLE IF EXISTS `t_og_zzjg`;
CREATE TABLE IF NOT EXISTS `t_og_zzjg` (
  `id` varchar(255) NOT NULL,
  `bm` varchar(50) NOT NULL,
  `mc` varchar(200) NOT NULL,
  `bgdz` varchar(200) DEFAULT NULL,
  `cz` varchar(30) DEFAULT NULL,
  `bz` varchar(250) DEFAULT NULL,
  `jc` varchar(50) DEFAULT NULL,
  `zt` varchar(50) DEFAULT NULL,
  `lxdh` varchar(30) DEFAULT NULL,
  `sjc` datetime DEFAULT NULL,
  `sjzzjg_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK609CD1B9A950914E` (`sjzzjg_id`),
  CONSTRAINT `FK609CD1B9A950914E` FOREIGN KEY (`sjzzjg_id`) REFERENCES `t_og_zzjg` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_og_zzjg 的数据：~29 rows (大约)
/*!40000 ALTER TABLE `t_og_zzjg` DISABLE KEYS */;
INSERT INTO `t_og_zzjg` (`id`, `bm`, `mc`, `bgdz`, `cz`, `bz`, `jc`, `zt`, `lxdh`, `sjc`, `sjzzjg_id`) VALUES
	('01', '01', '贵阳市公安局经济技术开发区分局', '贵州省贵阳市经济技术开发区桐荫路12号', NULL, NULL, '经开公安分局', '1', '0851-83847110', '2016-07-31 23:16:30', NULL),
	('069c4e0f-a006-43d6-a9d7-36f488514c7d', '0101', '贵阳市公安局经济技术开发区分局三江派出所', '贵州省贵阳市经济技术开发区开发大道109号', '', '', '三江派出所', '1', '0851-83832110', '2016-08-08 09:56:54', '01'),
	('0b3d22e9-a71f-4216-b98b-39fd6ef6899d', '00013', '贵阳市公安局经济技术开发区分局治安管理大队', '贵州省贵阳市经济技术开发区桐荫路12号', '', '', '治安大队', '1', '0851-83801351', '2016-08-08 09:56:32', '01'),
	('3cdc025c-dcaf-47f8-a2fd-6c9f6e1b18d5', '00012', '贵阳市公安局经济技术开发区分局禁毒大队', '贵州省贵阳市经济技术开发区桐荫路12号', '', '', '禁毒大队', '1', '0851-83807622', '2016-08-08 09:56:26', '01'),
	('3f025ed8-d417-45ac-96cd-af383b3fc431', '00005', '贵阳市公安局经济技术开发区分局强制隔离戒毒所', '贵州省贵阳市经济技术开发区桐荫路12号', '', '', '戒毒所', '1', '0851-83830867', '2016-08-08 09:55:40', '01'),
	('585fdd1b-4453-443d-8d28-305368c99728', '00009', '贵阳市公安局经济技术开发区分局网安大队', '贵州省贵阳市经济技术开发桐荫路12号', '', '', '网安大队', '1', '15722122069', '2016-08-08 09:56:01', '01'),
	('710ec0e8-fe8a-42da-b4e4-353a380be40d', '00006', '贵阳市公安局经济技术开发区分局行政拘留所', '贵州省贵阳市经济技术开发区桐荫路12号', '', '', '拘留所', '1', '0851-83830467', '2016-08-08 09:55:45', '01'),
	('76e82277-9ef2-4c01-b223-454048e3db9c', '00010', '贵阳市公安局经济技术开发区分局法制大队', '贵州省贵阳市经济技术开发区桐荫路12号', '', '联系电话：\n0851-86798411； 0851-86798413；\n0851-86798410； 0851-83830110', '法制大队', '1', '0851-80798411', '2016-08-08 09:56:09', '01'),
	('7b8cac4c-2e89-499c-b83e-63defc1da3dd', '0105', '贵阳市公安局经济技术开发区分局平桥派出所', '贵州省贵阳市经济技术开发区中曹路44号', '', '', '平桥派出所', '1', '0851-83832757', '2016-08-08 09:58:02', '01'),
	('90b2efdf-3e66-4c8d-92ba-33fe5320bdb5', '0104', '贵阳市公安局经济技术开发区分局长江路派出所', '贵州省贵阳市小河区珠江路66号', '', '', '长江派出所', '1', '0851-83818110', '2016-08-08 09:57:53', '01'),
	('b01776e5-59a3-4892-ab1c-85e6a676388d', '00007', '贵阳市公安局经济技术开发区分局反恐大队', '贵州省贵阳市经济技术开发区桐荫路12号', '', 'qweqwe', '反恐大队', '1', '', '2016-08-08 09:55:49', '01'),
	('b1e2af70-ca70-42fb-a728-6ae56123a981', '0113', '测试（贵阳）', '', '', '', '测试（贵阳）', '1', '', '2016-08-08 09:58:36', '01'),
	('b4f8b72d-1940-43a4-9518-d6606b8b768a', '0106', '贵阳市公安局经济技术开发区分局金竹派出所', '贵州省贵阳市经济技术开发区金竹镇金溪路', '', '', '金竹派出所', '1', '0851-83762560', '2016-08-08 09:58:08', '01'),
	('d36628c3-d4e4-4143-b6bb-c9f6b89f2aa9', '0108', '测试(北京)', '85183832757', '85183832757', '', '测试(北京)', '1', '85183832757', '2016-08-08 09:58:19', '01'),
	('e1048cb4-01fb-4dd6-a668-048cdf3a4567', '0102', '贵阳市公安局经济技术开发区分局黄河派出所', '贵州省贵阳市经济开发区浦江路178号', '', '', '黄河派出所', '1', '0851-83848803', '2016-08-08 09:56:59', '01'),
	('e55be91b-f2cc-4de6-80cc-d460e3c92975', '0107', '贵阳市公安局经济技术开发区分局巡（特）警大队', '贵州省贵阳市经济技术开发区浦江路178号', '', '', '巡（特）大队', '1', '0851-83858110', '2016-08-08 09:58:13', '01'),
	('e5be6a62-4cbe-468e-9cf4-205b3abaea23', '5201142100', '贵阳市公安局经济技术开发区分局大兴派出所', '贵州省贵阳市经济技术开发区清水江路东段211号', '', '', '大兴派出所', '1', '0851-83481110', '2016-08-08 09:58:44', '01'),
	('f1218a34-d2aa-43da-a9cf-1475a15dc3ad', '00011111', '贵阳市公安局经济技术开发区分局刑事侦查大队', '贵州省贵阳市经济技术开发区桐荫路12号', '', '', '刑侦大队', '1', '0851-86798492', '2016-08-08 09:56:13', '01'),
	('fc4376c0-ba88-4958-87da-f3498281839b', '00008', '贵阳市公安局经济技术开发区分局国保大队', '贵州省贵阳市经济技术开发区桐荫路12号', '', '', '国保大队', '1', '0851-83809651', '2016-08-08 09:55:56', '01'),
	('xndw01', '02', '虚拟单位', '', '', '', '虚拟单位', '1', '', '2016-02-23 23:21:35', NULL);
/*!40000 ALTER TABLE `t_og_zzjg` ENABLE KEYS */;


-- 导出  表 ajgl.t_og_zzjg_t_og_ry 结构
DROP TABLE IF EXISTS `t_og_zzjg_t_og_ry`;
CREATE TABLE IF NOT EXISTS `t_og_zzjg_t_og_ry` (
  `t_og_zzjg_id` varchar(255) NOT NULL,
  `persons_id` varchar(255) NOT NULL,
  UNIQUE KEY `persons_id` (`persons_id`),
  KEY `FK35FB61FDE8A3C0C9` (`t_og_zzjg_id`),
  KEY `FK35FB61FDD19A0286` (`persons_id`),
  CONSTRAINT `FK35FB61FDD19A0286` FOREIGN KEY (`persons_id`) REFERENCES `t_og_ry` (`id`),
  CONSTRAINT `FK35FB61FDE8A3C0C9` FOREIGN KEY (`t_og_zzjg_id`) REFERENCES `t_og_zzjg` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_og_zzjg_t_og_ry 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_og_zzjg_t_og_ry` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_og_zzjg_t_og_ry` ENABLE KEYS */;


-- 导出  表 ajgl.t_zdlx 结构
DROP TABLE IF EXISTS `t_zdlx`;
CREATE TABLE IF NOT EXISTS `t_zdlx` (
  `id` varchar(255) NOT NULL,
  `fl` varchar(24) DEFAULT NULL,
  `bm` varchar(50) NOT NULL,
  `bz` varchar(150) DEFAULT NULL,
  `mc` varchar(36) NOT NULL,
  `zt` varchar(36) NOT NULL,
  `sjc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_zdlx 的数据：~25 rows (大约)
/*!40000 ALTER TABLE `t_zdlx` DISABLE KEYS */;
INSERT INTO `t_zdlx` (`id`, `fl`, `bm`, `bz`, `mc`, `zt`, `sjc`) VALUES
	('0000000001', '系统管理', 'wxszt', '', '使用状态', '1', '2016-07-29 08:15:24'),
	('0000000002', '系统管理', 'zt', '', '状态', '1', '2016-07-29 08:15:33'),
	('0000000003', '系统管理', 'xb', '', '性别', '1', '2016-07-29 08:15:37'),
	('0000000004', '案件管理', 'ajly', '', '案件理由', '1', '2016-07-29 08:15:48'),
	('0000000005', '案件管理', 'zjlx', '', '证件类型', '1', '2016-07-29 08:15:57'),
	('0000000006', '案件管理', 'sydzt', '', '使用单状态', '1', '2016-07-29 08:16:06'),
	('0000000007', '系统管理', 'sf', '', '是否', '1', '2016-07-29 08:16:14'),
	('0000000008', '案件管理', 'bgcs', '', '保管措施', '1', '2016-07-29 08:16:24'),
	('0000000009', '活动管理', 'hdlx', '', '活动类型', '1', '2016-07-29 08:16:35'),
	('0000000010', '案件管理', 'wpzt', '', '物品状态', '1', '2016-07-29 08:16:40'),
	('0000000011', '房间管理', 'fjlx', '房间管理', '房间类型', '1', '2016-06-26 21:23:01'),
	('0000000012', '活动管理', 'qthdlx', '活动管理', '其他活动类型', '1', '2016-06-26 21:23:01'),
	('0000000013', '预警管理', 'yjgz', '预警管理', '预警规则', '1', '2016-06-26 21:23:01'),
	('0000000014', '预警管理', 'yjfs', '预警管理', '预警方式', '1', '2016-06-26 21:23:01'),
	('0000000015', '预警管理', 'cffs', '预警管理', '触发方式', '1', '2016-06-26 21:23:01'),
	('0000000016', '预警管理', 'wglx', '预警管理', '违规类型', '1', '2016-06-26 21:23:01'),
	('0000000017', '系统管理', 'zylx', '系统管理', '资源类型', '1', '2016-07-28 23:33:49'),
	('0000000018', '系统管理', 'xl', '系统管理', '学历', '1', '2016-07-28 23:59:38'),
	('0000000019', '系统管理', 'mz', '系统管理', '民族', '1', '2016-07-29 00:04:55'),
	('0000000020', '系统管理', 'zzmm', '系统管理', '政治面貌', '1', '2016-07-29 00:09:49'),
	('0000000021', '系统管理', 'zw', '系统管理', '职务', '1', '2016-07-29 00:17:20'),
	('0000000022', '系统管理', 'zzzt', '系统管理', '在职状态', '1', '2016-07-29 07:42:35'),
	('0000000023', '系统管理', 'dwsx', '系统管理', '单位属性', '1', '2016-07-29 08:01:30'),
	('0000000024', '涉案物品管理', 'sawpxz', '', '涉案物品性质', '1', '2016-07-29 08:01:30'),
	('0000000025', '涉案物品管理', 'cklx', '', '出库类型', '1', '2016-07-29 08:01:30'),
	('f7856ac8-ec02-43de-948e-e91efaf22bf8', '系统管理', 'cedy', '123123', '测试单元', '1', '2016-07-31 16:30:22');
/*!40000 ALTER TABLE `t_zdlx` ENABLE KEYS */;


-- 导出  表 ajgl.t_zdx 结构
DROP TABLE IF EXISTS `t_zdx`;
CREATE TABLE IF NOT EXISTS `t_zdx` (
  `id` varchar(255) NOT NULL,
  `bm` varchar(36) NOT NULL,
  `bz` varchar(150) DEFAULT NULL,
  `mc` varchar(50) NOT NULL,
  `sx` int(11) DEFAULT NULL,
  `zt` varchar(36) NOT NULL,
  `sjc` datetime NOT NULL,
  `zdlx_id` varchar(255) NOT NULL,
  `sjzdx_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK68FAC4350BBE766` (`zdlx_id`),
  KEY `FK68FAC43D19428FE` (`sjzdx_id`),
  CONSTRAINT `FK68FAC4350BBE766` FOREIGN KEY (`zdlx_id`) REFERENCES `t_zdlx` (`id`),
  CONSTRAINT `FK68FAC43D19428FE` FOREIGN KEY (`sjzdx_id`) REFERENCES `t_zdx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  ajgl.t_zdx 的数据：~87 rows (大约)
/*!40000 ALTER TABLE `t_zdx` DISABLE KEYS */;
INSERT INTO `t_zdx` (`id`, `bm`, `bz`, `mc`, `sx`, `zt`, `sjc`, `zdlx_id`, `sjzdx_id`) VALUES
	('0000000001001', '0', '', '空闲', 1, '1', '2016-06-26 21:23:01', '0000000001', NULL),
	('0000000001002', '2', '12312', '不可用', 3, '1', '2016-07-30 11:26:48', '0000000001', NULL),
	('0000000001003', '1', '', '使用中', 2, '1', '2016-07-29 08:29:07', '0000000001', NULL),
	('0000000002001', '0', '', '停用', 2, '1', '2016-06-26 21:23:01', '0000000002', NULL),
	('0000000002002', '1', '', '启用', 1, '1', '2016-06-26 21:23:01', '0000000002', NULL),
	('0000000003001', '1', '', '男', 1, '1', '2016-06-26 21:23:01', '0000000003', NULL),
	('0000000003002', '2', '', '女', 2, '1', '2016-06-26 21:23:01', '0000000003', NULL),
	('0000000003003', '0', '', '未知', 3, '1', '2016-06-26 21:23:01', '0000000003', NULL),
	('0000000004001', '0', '', '投案自首', 1, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004002', '1', '', '治安传唤', 2, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004003', '2', '', '继续盘问', 3, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004004', '3', '', '刑事传唤', 4, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004005', '4', '', '拘传', 5, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004006', '5', '', '取保候审', 6, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004007', '6', '', '监视拘捕', 7, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004008', '7', '', '逮捕', 8, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004009', '8', '', '被害人', 9, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004010', '9', '', '证人', 10, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000004011', '10', '', '其他', 11, '1', '2016-06-26 21:23:01', '0000000004', NULL),
	('0000000005001', '0', '', '身份证', 1, '1', '2016-06-26 21:23:01', '0000000005', NULL),
	('0000000005002', '1', '', '军官证', 2, '1', '2016-06-26 21:23:01', '0000000005', NULL),
	('0000000005003', '2', '', '护照', 3, '1', '2016-06-26 21:23:01', '0000000005', NULL),
	('0000000006001', '0', '', '进行中', 1, '1', '2016-06-26 21:23:01', '0000000006', NULL),
	('0000000006002', '1', '', '已完成', 2, '1', '2016-06-26 21:23:01', '0000000006', NULL),
	('0000000007001', '0', '', '否', 1, '1', '2016-06-26 21:23:01', '0000000007', NULL),
	('0000000007002', '1', '', '是', 2, '1', '2016-06-26 21:23:01', '0000000007', NULL),
	('0000000008001', '0', '', '暂存', 1, '1', '2016-06-26 21:23:01', '0000000008', NULL),
	('0000000008002', '1', '', '代保管', 2, '1', '2016-06-26 21:23:01', '0000000008', NULL),
	('0000000009001', '0', '', '讯问', 1, '1', '2016-06-26 21:23:01', '0000000009', NULL),
	('0000000009002', '1', '', '询问', 2, '1', '2016-06-26 21:23:01', '0000000009', NULL),
	('0000000009003', '2', '', '外单位借用', 3, '1', '2016-06-26 21:23:01', '0000000009', NULL),
	('0000000010001', '0', '', '在库', 1, '1', '2016-06-26 21:23:01', '0000000010', NULL),
	('0000000010002', '1', '', '离库', 2, '1', '2016-06-26 21:23:01', '0000000010', NULL),
	('0000000010003', '2', '', '本次返还', 3, '1', '2016-06-26 21:23:01', '0000000010', NULL),
	('0000000011001', '0', '', '问询室', 1, '1', '2016-06-26 21:23:01', '0000000011', NULL),
	('0000000011002', '1', '', '休息室', 2, '1', '2016-06-26 21:23:01', '0000000011', NULL),
	('0000000011003', '2', '', '活动室', 3, '1', '2016-06-26 21:23:01', '0000000011', NULL),
	('0000000012001', '0', '', '等候', 1, '1', '2016-06-26 21:23:01', '0000000012', NULL),
	('0000000012002', '1', '', '休息', 2, '1', '2016-06-26 21:23:01', '0000000012', NULL),
	('0000000012003', '2', '', '饮食', 3, '1', '2016-06-26 21:23:01', '0000000012', NULL),
	('0000000012004', '3', '', '卫生间', 4, '1', '2016-06-26 21:23:01', '0000000012', NULL),
	('0000000013001', '0', '', '传唤到期预警', 1, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000013002', '1', '', '刑拘到期预警', 2, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000013003', '2', '', '延长刑拘到期预警', 3, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000013004', '3', '', '取保到期预警', 4, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000013005', '4', '', '监视居住到期预警', 5, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000013006', '5', '', '逮捕后移送起诉预警', 6, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000013007', '6', '', '进入办案区8小时预警', 7, '1', '2016-06-26 21:23:01', '0000000013', NULL),
	('0000000014001', '0', '', '系统消息提示', 1, '1', '2016-06-26 21:23:01', '0000000014', NULL),
	('0000000014002', '1', '', 'PDA', 2, '1', '2016-06-26 21:23:01', '0000000014', NULL),
	('0000000014003', '2', '', '短信', 3, '1', '2016-06-26 21:23:01', '0000000014', NULL),
	('0000000015001', '0', '', '动作触发', 1, '1', '2016-06-26 21:23:01', '0000000015', NULL),
	('0000000015002', '1', '', '系统触发', 2, '1', '2016-06-26 21:23:01', '0000000015', NULL),
	('0000000016001', '0', '', '单人审讯1', 1, '1', '2016-06-26 21:23:01', '0000000016', NULL),
	('0000000016002', '1', '', '单人审讯2', 2, '1', '2016-06-26 21:23:01', '0000000016', NULL),
	('0000000016003', '2', '', '单人审讯3', 3, '1', '2016-06-26 21:23:01', '0000000016', NULL),
	('0000000016004', '3', '', '单人审讯4', 4, '1', '2016-06-26 21:23:01', '0000000016', NULL),
	('0000000016005', '4', '', '其他异常情况', 5, '1', '2016-06-26 21:23:01', '0000000016', NULL),
	('0000000017001', '0', '', '按钮', 1, '1', '2016-07-28 23:34:25', '0000000017', NULL),
	('0000000017002', '1', '', '链接', 2, '1', '2016-07-28 23:35:19', '0000000017', NULL),
	('0000000018001', '0', '', '大学本科', 1, '1', '2016-07-29 00:00:50', '0000000018', NULL),
	('0000000018002', '1', '', '专科', 2, '1', '2016-07-29 00:01:48', '0000000018', NULL),
	('0000000018003', '2', '', '硕士', 3, '1', '2016-07-29 00:02:29', '0000000018', NULL),
	('0000000019001', '0', '', '汉族', 1, '1', '2016-07-29 00:05:28', '0000000019', NULL),
	('0000000019002', '1', '', '满族', 2, '1', '2016-07-29 00:06:10', '0000000019', NULL),
	('0000000019003', '2', '', '傣族', 3, '1', '2016-07-29 00:06:37', '0000000019', NULL),
	('0000000019004', '3', '', '维吾尔族', 4, '1', '2016-07-29 00:07:42', '0000000019', NULL),
	('0000000020001', '0', '', '群众', 1, '1', '2016-07-29 00:10:36', '0000000020', NULL),
	('0000000020002', '1', '', '团员', 2, '1', '2016-07-29 00:11:52', '0000000020', NULL),
	('0000000020003', '2', '', '预备党员', 3, '1', '2016-07-29 00:14:40', '0000000020', NULL),
	('0000000020004', '3', '', '党员', 4, '1', '2016-07-29 00:15:15', '0000000020', NULL),
	('0000000021001', '0', '', '局长', 1, '1', '2016-07-29 00:18:07', '0000000021', NULL),
	('0000000021002', '1', '', '副局长', 2, '1', '2016-07-29 00:18:33', '0000000021', NULL),
	('0000000021003', '2', '', '科员', 3, '1', '2016-07-29 00:19:08', '0000000021', NULL),
	('0000000022001', '0', '', '在职', 1, '1', '2016-07-29 07:44:26', '0000000022', NULL),
	('0000000022002', '1', '', '离职', 2, '1', '2016-07-29 07:44:55', '0000000022', NULL),
	('0000000023001', '0', '', '配警单位', 1, '1', '2016-07-29 08:02:10', '0000000023', NULL),
	('0000000023002', '1', '', '业务部门', 2, '1', '2016-07-29 08:02:48', '0000000023', NULL),
	('0000000024001', '0', '', '作案工具', 1, '1', '2016-07-29 08:02:48', '0000000024', NULL),
	('0000000024002', '1', '', '赃物', 2, '1', '2016-07-29 08:02:48', '0000000024', NULL),
	('0000000024003', '2', '', '物证', 3, '1', '2016-07-29 08:02:48', '0000000024', NULL),
	('0000000025001', '0', '', '随案移交', 1, '1', '2016-07-29 08:02:48', '0000000025', NULL),
	('0000000025002', '1', '', '责令发还', 2, '1', '2016-07-29 08:02:48', '0000000025', NULL),
	('0000000025003', '2', '', '罚没', 3, '1', '2016-07-29 08:02:48', '0000000025', NULL),
	('0000000025004', '3', '', '上交', 4, '1', '2016-07-29 08:02:48', '0000000025', NULL),
	('0000000025005', '4', '', '借出', 5, '1', '2016-07-29 08:02:48', '0000000025', NULL),
	('0000000025006', '5', '', '其他', 6, '1', '2016-07-29 08:02:48', '0000000025', NULL);

