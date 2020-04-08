/*
Navicat MySQL Data Transfer

Source Server         : MySQL8@Master
Source Server Version : 80019
Source Host           : localhost:3307
Source Database       : o2o

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-08 22:47:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area` (
  `area_id` int NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `priority` int NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `tb_area_area_name_uk` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
INSERT INTO `tb_area` VALUES ('1', '西苑', '1', '2020-04-08 11:27:08', '2020-04-08 11:27:11');
INSERT INTO `tb_area` VALUES ('2', '东苑', '2', '2020-04-08 11:27:13', '2020-04-08 11:27:16');

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line` (
  `line_id` int NOT NULL AUTO_INCREMENT,
  `line_name` varchar(200) DEFAULT NULL,
  `line_link` varchar(200) NOT NULL,
  `line_img` varchar(200) NOT NULL,
  `priority` int DEFAULT NULL,
  `enable_status` int NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------
INSERT INTO `tb_head_line` VALUES ('11', '1', '1', '/upload/item/headtitle/2017061320315746624.jpg', '1', '1', '2020-04-05 15:43:54', '2020-04-05 15:43:56');
INSERT INTO `tb_head_line` VALUES ('12', '2', '2', '/upload/item/headtitle/2017061320371786788.jpg', '2', '1', '2020-04-07 10:04:08', '2020-04-07 10:04:11');
INSERT INTO `tb_head_line` VALUES ('13', '3', '3', '/upload/item/headtitle/2017061320393452772.jpg', '3', '1', '2020-04-07 10:05:12', '2020-04-07 10:05:15');
INSERT INTO `tb_head_line` VALUES ('14', '4', '4', '/upload/item/headtitle/2017061320400198256.jpg', '4', '1', '2020-04-07 10:05:42', '2020-04-07 10:05:45');

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth` (
  `local_auth_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `tb_local_auth_username_uk` (`username`),
  KEY `tb_local_auth_user_id_fk` (`user_id`),
  CONSTRAINT `tb_local_auth_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------

-- ----------------------------
-- Table structure for tb_person_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `profile_img` varchar(1024) DEFAULT NULL,
  `email` varchar(1024) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `enable_status` int NOT NULL DEFAULT '0' COMMENT '0:禁止使用本商城,1:允许使用本商城',
  `user_type` int NOT NULL DEFAULT '1' COMMENT '1:顾客,2:店家,3:超级管理员',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
INSERT INTO `tb_person_info` VALUES ('1', '徐煜东', null, null, '男', '0', '1', '2020-03-26 11:38:52', '2020-03-26 11:38:56');

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int NOT NULL DEFAULT '0',
  `product_category_id` int DEFAULT NULL,
  `shop_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `tb_product_product_category_id_fk` (`product_category_id`),
  KEY `tb_product_shop_id_fk` (`shop_id`),
  CONSTRAINT `tb_product_product_category_id_fk` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `tb_product_shop_id_fk` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES ('1', '小叮当', null, '', null, null, '0', '2020-03-31 11:28:15', '2020-04-05 11:14:16', '1', '2', '2');
INSERT INTO `tb_product` VALUES ('2', '测试商品1', '测试商品1', '测试商品1', '1988', '1655', '1', '2020-03-31 11:49:14', null, '1', '2', '2');
INSERT INTO `tb_product` VALUES ('3', '测试商品2', '测试商品2', '测试商品2', '1987', '1455', '2', '2020-03-31 11:49:14', null, '1', '2', '2');
INSERT INTO `tb_product` VALUES ('5', '测试商品5', 'hhhh', 'upload\\item\\shop\\2\\2020-04-04-16-43-57-62726.jpg', '20', '10', '50', '2020-03-31 16:45:56', '2020-04-06 14:07:57', '0', '3', '2');
INSERT INTO `tb_product` VALUES ('6', '测试商品1', '测试商品1', 'upload\\item\\shop\\2\\2020-03-31-16-50-06-90883.jpg', null, null, '1', '2020-03-31 16:50:07', '2020-03-31 16:50:07', '1', '2', '2');
INSERT INTO `tb_product` VALUES ('7', '测试商品1', '测试商品1', 'upload\\item\\shop\\2\\2020-03-31-18-36-37-39930.jpg', null, null, '1', '2020-03-31 18:36:38', '2020-03-31 18:36:38', '1', '2', '2');
INSERT INTO `tb_product` VALUES ('8', '测试商品1', '测试商品1', 'upload\\item\\shop\\2\\2020-03-31-19-09-05-68373.jpg', null, null, '1', '2020-03-31 19:09:06', '2020-03-31 19:09:06', '1', '2', '2');
INSERT INTO `tb_product` VALUES ('9', '测试商品1', '测试商品1', 'upload\\item\\shop\\2\\2020-03-31-19-38-27-26347.jpg', null, null, '1', '2020-03-31 19:38:27', '2020-03-31 19:38:27', '1', '2', '2');
INSERT INTO `tb_product` VALUES ('10', '测试商品5', '测试商品5', 'upload\\item\\shop\\2\\2020-04-04-12-34-21-35798.jpg', '4', '2', '1', '2020-04-03 16:50:52', '2020-04-04 12:34:21', '1', '3', '2');
INSERT INTO `tb_product` VALUES ('11', '测试店铺3', '2222', 'aaa', '4', '2', '1', '2020-04-03 16:52:56', '2020-04-03 20:23:51', '1', '3', '2');

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
  `product_category_id` int NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(200) NOT NULL,
  `priority` int DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `shop_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_category_id`),
  KEY `tb_product_category_shop_id_pk` (`shop_id`),
  CONSTRAINT `tb_product_category_shop_id_pk` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES ('2', '店铺商品测试类别2', '20', '2020-03-28 10:03:35', '2');
INSERT INTO `tb_product_category` VALUES ('3', '店铺商品测试类别3', '2', '2020-03-28 10:04:44', '2');

-- ----------------------------
-- Table structure for tb_product_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img` (
  `product_img_id` int NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `tb_product_img_product_id_fk` (`product_id`),
  CONSTRAINT `tb_product_img_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
INSERT INTO `tb_product_img` VALUES ('3', '图片1', '测试图片1', '1', '2020-03-31 11:29:09', '1');
INSERT INTO `tb_product_img` VALUES ('4', '图片1', '测试图片1', '1', '2020-03-31 11:29:09', '1');
INSERT INTO `tb_product_img` VALUES ('9', 'upload\\item\\shop\\2\\2020-03-31-16-50-06-89433.jpg', '商品号6的1号图片', '1', '2020-03-31 16:50:07', '6');
INSERT INTO `tb_product_img` VALUES ('10', 'upload\\item\\shop\\2\\2020-03-31-16-50-07-34696.jpg', '商品号6的2号图片', '2', '2020-03-31 16:50:07', '6');
INSERT INTO `tb_product_img` VALUES ('11', 'upload\\item\\shop\\2\\2020-03-31-18-36-37-58717.jpg', '商品号7的1号图片', '1', '2020-03-31 18:36:38', '7');
INSERT INTO `tb_product_img` VALUES ('12', 'upload\\item\\shop\\2\\2020-03-31-18-36-37-65364.jpg', '商品号7的2号图片', '2', '2020-03-31 18:36:38', '7');
INSERT INTO `tb_product_img` VALUES ('13', 'upload\\item\\shop\\2\\2020-03-31-19-09-05-75970.jpg', '商品号8的1号图片', '1', '2020-03-31 19:09:06', '8');
INSERT INTO `tb_product_img` VALUES ('14', 'upload\\item\\shop\\2\\2020-03-31-19-09-05-19942.jpg', '商品号8的2号图片', '2', '2020-03-31 19:09:06', '8');
INSERT INTO `tb_product_img` VALUES ('15', 'upload\\item\\shop\\2\\2020-03-31-19-38-27-26092.jpg', '商品号9的1号图片', '1', '2020-03-31 19:38:28', '9');
INSERT INTO `tb_product_img` VALUES ('16', 'upload\\item\\shop\\2\\2020-03-31-19-38-27-39764.jpg', '商品号9的2号图片', '2', '2020-03-31 19:38:28', '9');
INSERT INTO `tb_product_img` VALUES ('22', 'upload\\item\\shop\\2\\2020-04-04-12-34-21-63810.jpg', '商品号10的1号图片', '1', '2020-04-04 12:34:22', '10');
INSERT INTO `tb_product_img` VALUES ('23', 'upload\\item\\shop\\2\\2020-04-04-12-34-21-37559.jpg', '商品号10的2号图片', '2', '2020-04-04 12:34:22', '10');
INSERT INTO `tb_product_img` VALUES ('25', 'upload\\item\\shop\\2\\2020-04-04-16-43-57-57175.jpg', '商品号5的1号图片', '1', '2020-04-04 16:43:58', '5');

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL COMMENT '店铺创建人',
  `area_id` int DEFAULT NULL,
  `shop_category_id` int DEFAULT NULL,
  `shop_name` varchar(200) NOT NULL,
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int NOT NULL DEFAULT '0',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `tb_shop_owner_id_fk` (`owner_id`),
  KEY `tb_shop_shop_category_id_fk` (`shop_category_id`),
  CONSTRAINT `tb_shop_owner_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `tb_shop_shop_category_id_fk` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES ('2', '1', '1', '14', '正式店铺名称', '测试描述', '正式地址', '13810524086', '\\upload\\item\\shop\\1\\2017091621545314507.jpg', '10', '2020-03-26 11:40:18', '2020-03-27 00:05:16', '1', null);
INSERT INTO `tb_shop` VALUES ('28', '1', '2', '22', '小黄人主题奶茶店', '不接受预定,请直接来电力进行消费', '东苑2号', '13810524086', ' \\upload\\item\\shop\\28\\2017092601041469991.png', '50', '2020-03-26 11:52:30', '2020-03-26 18:27:26', '1', null);
INSERT INTO `tb_shop` VALUES ('29', '1', '1', '22', '暴漫奶茶店', '过来喝喝就知道啦,你是我的奶茶', '西苑1号', '13042465911', '\\upload\\item\\shop\\29\\2017092601054939287.jpg', '40', '2020-04-08 11:19:18', '2020-04-08 11:19:21', '1', null);
INSERT INTO `tb_shop` VALUES ('30', '1', '2', '20', '彪哥大排档', '敢说不好吃吗', '东苑1号', '13050554190', '\\upload\\item\\shop\\30\\2017092601063878278.jpg', '30', '2020-04-08 11:22:04', '2020-04-08 11:22:07', '1', null);
INSERT INTO `tb_shop` VALUES ('31', '1', '2', '20', '威哥大排档', '干掉彪哥大排档', '东苑南路', '15841193190', '\\upload\\item\\shop\\31\\2017092601072177572.jpg', '20', '2020-04-08 11:24:13', '2020-04-08 11:24:15', '1', null);
INSERT INTO `tb_shop` VALUES ('32', '1', '2', '22', '你是我的奶茶', '奶茶店再次来袭', '东苑六路', '15942659124', '\\upload\\item\\shop\\32\\2017092601081463136.jpg', '10', '2020-04-08 11:25:49', '2020-04-08 11:25:51', '1', null);

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category` (
  `shop_category_id` int NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(200) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(200) DEFAULT '',
  `shop_category_img` varchar(200) DEFAULT NULL,
  `priority` int NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `tb_shop_category_parent_id_fk` (`parent_id`),
  CONSTRAINT `tb_shop_category_parent_id_fk` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop_category
-- ----------------------------
INSERT INTO `tb_shop_category` VALUES ('10', '二手市场', '二手商品交易', '/upload/item/shopcategory/2017061223272255687.png', '100', '2020-03-26 11:11:54', '2020-03-26 11:11:57', null);
INSERT INTO `tb_shop_category` VALUES ('11', '美容美发', '美容美发', '/upload/item/shopcategory/2017061223273314635.png', '99', '2020-03-26 11:20:24', '2020-03-26 11:20:26', null);
INSERT INTO `tb_shop_category` VALUES ('12', '美食饮品', '美食饮品', '/upload/item/shopcategory/2017061223274213433.png', '98', '2020-04-06 18:52:56', '2020-04-06 18:52:59', null);
INSERT INTO `tb_shop_category` VALUES ('13', '休闲娱乐', '休闲娱乐', '/upload/item/shopcategory/2017061223275121460.png', '97', '2020-04-06 18:53:01', '2020-04-06 18:53:56', null);
INSERT INTO `tb_shop_category` VALUES ('14', '旧车', '旧车', '/upload/item/shopcategory/2017060420315183203.png', '80', '2020-04-06 18:53:14', '2020-04-06 18:54:04', '10');
INSERT INTO `tb_shop_category` VALUES ('15', '二手书籍', '二手书籍', '/upload/item/shopcategory/2017060420322333745.png', '79', '2020-04-06 18:53:17', '2020-04-06 18:54:06', '10');
INSERT INTO `tb_shop_category` VALUES ('17', '护理', '护理', '/upload/item/shopcategory/2017060420372391702.png', '76', '2020-04-06 18:53:20', '2020-04-06 18:54:09', '11');
INSERT INTO `tb_shop_category` VALUES ('18', '理发', '理发', '/upload/item/shopcategory/2017060420374775350.png', '74', '2020-04-06 18:53:23', '2020-04-06 18:54:11', '11');
INSERT INTO `tb_shop_category` VALUES ('20', '大排档', '大排档', '/upload/item/shopcategory/2017060420460491494.png', '59', '2020-04-06 18:53:28', '2020-04-06 18:54:14', '12');
INSERT INTO `tb_shop_category` VALUES ('22', '奶茶店', '奶茶店', '/upload/item/shopcategory/2017060420464594520.png', '58', '2020-04-06 18:53:31', '2020-04-06 18:54:18', '12');
INSERT INTO `tb_shop_category` VALUES ('24', '密室逃生', '密室逃生', '/upload/item/shopcategory/2017060420500783376.png', '56', '2020-04-06 18:53:34', '2020-04-06 18:54:21', '13');
INSERT INTO `tb_shop_category` VALUES ('25', 'KTV', 'KTV', '/upload/item/shopcategory/2017060420505834244.png', '57', '2020-04-06 18:53:37', '2020-04-06 18:54:26', '13');
INSERT INTO `tb_shop_category` VALUES ('27', '教育培训', '教育培训', '/upload/item/shopcategory/2017061223280082147.png', '96', '2020-04-06 18:53:08', '2020-04-06 18:53:58', null);
INSERT INTO `tb_shop_category` VALUES ('28', '租赁市场', '租赁市场', '/upload/item/shopcategory/2017061223281361578.png', '95', '2020-04-06 18:53:11', '2020-04-06 18:54:00', null);
INSERT INTO `tb_shop_category` VALUES ('29', '程序设计', '程序设计', '/upload/item/shopcategory/2017060421593496807.png', '50', '2020-04-06 18:53:39', '2020-04-06 18:54:29', '27');
INSERT INTO `tb_shop_category` VALUES ('30', '声乐舞蹈', '声乐舞蹈', '/upload/item/shopcategory/2017060421595843693.png', '49', '2020-04-06 18:53:42', '2020-04-06 18:54:32', '27');
INSERT INTO `tb_shop_category` VALUES ('31', '演出道具', '演出道具', '/upload/item/shopcategory/2017060422114076152.png', '45', '2020-04-06 18:53:45', '2020-04-06 18:54:34', '28');
INSERT INTO `tb_shop_category` VALUES ('32', '交通工具', '交通工具', '/upload/item/shopcategory/2017060422121144586.png', '44', '2020-04-06 18:53:48', '2020-04-06 18:54:37', '28');

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `open_id` varchar(200) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `tb_wechat_auth_open_id_uk` (`open_id`),
  KEY `tb_wechat_auth_user_id_fk` (`user_id`),
  CONSTRAINT `tb_wechat_auth_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wechat_auth
-- ----------------------------
