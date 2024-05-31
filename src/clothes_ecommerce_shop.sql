/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100427
 Source Host           : localhost:3306
 Source Schema         : clothes_ecommerce_shop

 Target Server Type    : MySQL
 Target Server Version : 100427
 File Encoding         : 65001

 Date: 31/05/2024 17:31:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `street` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ward` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_default` bit(1) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `created_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `created_by`(`created_by` ASC) USING BTREE,
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` bit(1) NULL DEFAULT b'1',
  `created_at` datetime NULL DEFAULT NULL,
  `created_by` int NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `updated_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 'T-Shirt3', b'1', '2024-04-10 00:00:00', NULL, '2024-05-31 00:07:53', NULL);
INSERT INTO `category` VALUES (2, 'Baby-Tee1', b'1', '2024-04-09 00:00:00', NULL, '2024-05-29 20:28:35', NULL);
INSERT INTO `category` VALUES (3, 'Polo', b'1', '2024-04-09 00:00:00', NULL, '2024-04-09 00:00:00', NULL);
INSERT INTO `category` VALUES (4, 'Dress-Shirt', b'1', '2024-04-09 00:00:00', NULL, '2024-04-09 00:00:00', NULL);
INSERT INTO `category` VALUES (5, 'Jecket', b'1', '2024-04-09 00:00:00', NULL, '2024-04-09 00:00:00', NULL);
INSERT INTO `category` VALUES (6, 'Hoodie', b'1', '2024-04-09 00:00:00', NULL, '2024-04-09 00:00:00', NULL);
INSERT INTO `category` VALUES (7, 'pants', b'1', '2024-04-09 00:00:00', NULL, '2024-04-09 00:00:00', NULL);
INSERT INTO `category` VALUES (8, 'Quần jean', b'0', '2024-04-09 00:00:00', NULL, '2024-05-30 13:50:38', NULL);
INSERT INTO `category` VALUES (20, 'Quần đùi1', b'1', '2024-05-31 15:27:25', NULL, '2024-05-31 15:27:38', NULL);
INSERT INTO `category` VALUES (21, 'quan thun2', b'1', '2024-05-31 15:59:30', NULL, '2024-05-31 15:59:45', NULL);
INSERT INTO `category` VALUES (22, 'Ao dep', b'1', '2024-05-31 16:43:52', NULL, '2024-05-31 16:43:52', NULL);
INSERT INTO `category` VALUES (23, 'Áo jean', b'1', '2024-05-31 16:50:46', NULL, '2024-05-31 16:50:46', NULL);

-- ----------------------------
-- Table structure for color
-- ----------------------------
DROP TABLE IF EXISTS `color`;
CREATE TABLE `color`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `color_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of color
-- ----------------------------
INSERT INTO `color` VALUES (1, 'Đen', NULL);
INSERT INTO `color` VALUES (2, 'Ghi', NULL);
INSERT INTO `color` VALUES (3, 'Xanh ve chai', NULL);
INSERT INTO `color` VALUES (4, 'Trắng', NULL);
INSERT INTO `color` VALUES (5, 'Be', NULL);
INSERT INTO `color` VALUES (6, 'Xám khói', NULL);
INSERT INTO `color` VALUES (7, 'Nâu', NULL);
INSERT INTO `color` VALUES (8, 'Xám chì', NULL);
INSERT INTO `color` VALUES (9, 'Xanh rêu', NULL);

-- ----------------------------
-- Table structure for color_size
-- ----------------------------
DROP TABLE IF EXISTS `color_size`;
CREATE TABLE `color_size`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NULL DEFAULT NULL,
  `color_id` int NOT NULL,
  `size_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  INDEX `color_id`(`color_id` ASC) USING BTREE,
  INDEX `size_id`(`size_id` ASC) USING BTREE,
  CONSTRAINT `color_size_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `color_size_ibfk_2` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `color_size_ibfk_3` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 510 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of color_size
-- ----------------------------
INSERT INTO `color_size` VALUES (25, 3, 6, 1, 3);
INSERT INTO `color_size` VALUES (26, 3, 7, 1, 3);
INSERT INTO `color_size` VALUES (27, 3, 8, 1, 3);
INSERT INTO `color_size` VALUES (28, 3, 9, 1, 3);
INSERT INTO `color_size` VALUES (29, 3, 4, 2, 3);
INSERT INTO `color_size` VALUES (31, 3, 5, 2, 3);
INSERT INTO `color_size` VALUES (32, 3, 6, 2, 3);
INSERT INTO `color_size` VALUES (33, 3, 7, 2, 3);
INSERT INTO `color_size` VALUES (34, 3, 4, 3, 3);
INSERT INTO `color_size` VALUES (35, 3, 5, 3, 3);
INSERT INTO `color_size` VALUES (36, 3, 6, 3, 3);
INSERT INTO `color_size` VALUES (37, 3, 7, 3, 0);
INSERT INTO `color_size` VALUES (38, 4, 1, 1, 0);
INSERT INTO `color_size` VALUES (39, 4, 2, 1, 0);
INSERT INTO `color_size` VALUES (40, 4, 3, 1, 0);
INSERT INTO `color_size` VALUES (41, 4, 1, 2, 0);
INSERT INTO `color_size` VALUES (42, 4, 2, 2, 0);
INSERT INTO `color_size` VALUES (43, 4, 3, 2, 0);
INSERT INTO `color_size` VALUES (44, 4, 1, 3, 0);
INSERT INTO `color_size` VALUES (45, 4, 2, 3, 0);
INSERT INTO `color_size` VALUES (46, 5, 3, 3, 0);
INSERT INTO `color_size` VALUES (47, 5, 3, 1, 0);
INSERT INTO `color_size` VALUES (48, 5, 4, 1, 0);
INSERT INTO `color_size` VALUES (49, 5, 5, 1, 0);
INSERT INTO `color_size` VALUES (50, 5, 4, 2, 0);
INSERT INTO `color_size` VALUES (51, 5, 5, 2, 0);
INSERT INTO `color_size` VALUES (52, 5, 6, 2, 0);
INSERT INTO `color_size` VALUES (53, 6, 6, 1, 5);
INSERT INTO `color_size` VALUES (54, 6, 7, 1, 0);
INSERT INTO `color_size` VALUES (55, 6, 8, 1, 0);
INSERT INTO `color_size` VALUES (56, 6, 9, 1, 0);
INSERT INTO `color_size` VALUES (57, 6, 4, 2, 0);
INSERT INTO `color_size` VALUES (58, 6, 5, 2, 0);
INSERT INTO `color_size` VALUES (59, 6, 6, 2, 0);
INSERT INTO `color_size` VALUES (60, 6, 7, 2, 0);
INSERT INTO `color_size` VALUES (61, 6, 4, 3, 0);
INSERT INTO `color_size` VALUES (62, 6, 5, 3, 0);
INSERT INTO `color_size` VALUES (63, 6, 6, 3, 0);
INSERT INTO `color_size` VALUES (64, 6, 7, 3, 0);
INSERT INTO `color_size` VALUES (65, 7, 1, 1, 0);
INSERT INTO `color_size` VALUES (66, 7, 2, 1, 0);
INSERT INTO `color_size` VALUES (67, 7, 3, 1, 0);
INSERT INTO `color_size` VALUES (68, 7, 1, 2, 0);
INSERT INTO `color_size` VALUES (69, 7, 2, 2, 0);
INSERT INTO `color_size` VALUES (70, 7, 3, 2, 0);
INSERT INTO `color_size` VALUES (71, 7, 1, 3, 0);
INSERT INTO `color_size` VALUES (72, 7, 2, 3, 0);
INSERT INTO `color_size` VALUES (73, 7, 3, 3, 0);
INSERT INTO `color_size` VALUES (74, 8, 3, 1, 5);
INSERT INTO `color_size` VALUES (75, 8, 4, 1, 5);
INSERT INTO `color_size` VALUES (76, 8, 5, 1, 5);
INSERT INTO `color_size` VALUES (77, 8, 4, 2, 0);
INSERT INTO `color_size` VALUES (78, 8, 5, 2, 0);
INSERT INTO `color_size` VALUES (79, 8, 6, 2, 0);
INSERT INTO `color_size` VALUES (80, 9, 6, 1, 0);
INSERT INTO `color_size` VALUES (81, 9, 7, 1, 0);
INSERT INTO `color_size` VALUES (82, 9, 8, 1, 0);
INSERT INTO `color_size` VALUES (83, 9, 9, 1, 0);
INSERT INTO `color_size` VALUES (84, 9, 4, 2, 0);
INSERT INTO `color_size` VALUES (85, 9, 5, 2, 0);
INSERT INTO `color_size` VALUES (86, 9, 6, 2, 0);
INSERT INTO `color_size` VALUES (87, 9, 7, 2, 0);
INSERT INTO `color_size` VALUES (88, 9, 4, 3, 0);
INSERT INTO `color_size` VALUES (89, 9, 5, 3, 0);
INSERT INTO `color_size` VALUES (90, 9, 6, 3, 0);
INSERT INTO `color_size` VALUES (91, 9, 7, 3, 0);
INSERT INTO `color_size` VALUES (92, 10, 1, 1, 0);
INSERT INTO `color_size` VALUES (93, 10, 2, 1, 0);
INSERT INTO `color_size` VALUES (94, 10, 3, 1, 0);
INSERT INTO `color_size` VALUES (95, 10, 1, 2, 0);
INSERT INTO `color_size` VALUES (96, 10, 2, 2, 0);
INSERT INTO `color_size` VALUES (97, 10, 3, 2, 0);
INSERT INTO `color_size` VALUES (98, 10, 1, 3, 0);
INSERT INTO `color_size` VALUES (99, 10, 2, 3, 0);
INSERT INTO `color_size` VALUES (100, 11, 3, 3, 0);
INSERT INTO `color_size` VALUES (101, 11, 3, 1, 0);
INSERT INTO `color_size` VALUES (102, 11, 4, 1, 0);
INSERT INTO `color_size` VALUES (103, 11, 5, 1, 0);
INSERT INTO `color_size` VALUES (104, 11, 4, 2, 0);
INSERT INTO `color_size` VALUES (105, 11, 5, 2, 0);
INSERT INTO `color_size` VALUES (106, 11, 6, 2, 0);
INSERT INTO `color_size` VALUES (107, 12, 6, 1, 0);
INSERT INTO `color_size` VALUES (108, 12, 7, 1, 0);
INSERT INTO `color_size` VALUES (109, 12, 8, 1, 0);
INSERT INTO `color_size` VALUES (110, 12, 9, 1, 0);
INSERT INTO `color_size` VALUES (111, 12, 4, 2, 0);
INSERT INTO `color_size` VALUES (112, 12, 5, 2, 0);
INSERT INTO `color_size` VALUES (113, 12, 6, 2, 0);
INSERT INTO `color_size` VALUES (114, 12, 7, 2, 0);
INSERT INTO `color_size` VALUES (115, 12, 4, 3, 0);
INSERT INTO `color_size` VALUES (116, 12, 5, 3, 0);
INSERT INTO `color_size` VALUES (117, 12, 6, 3, 0);
INSERT INTO `color_size` VALUES (118, 12, 7, 3, 0);
INSERT INTO `color_size` VALUES (119, 13, 1, 1, 0);
INSERT INTO `color_size` VALUES (120, 13, 2, 1, 0);
INSERT INTO `color_size` VALUES (121, 13, 3, 1, 0);
INSERT INTO `color_size` VALUES (122, 13, 1, 2, 0);
INSERT INTO `color_size` VALUES (123, 13, 2, 2, 0);
INSERT INTO `color_size` VALUES (124, 13, 3, 2, 0);
INSERT INTO `color_size` VALUES (125, 13, 1, 3, 0);
INSERT INTO `color_size` VALUES (126, 13, 2, 3, 0);
INSERT INTO `color_size` VALUES (127, 13, 3, 3, 0);
INSERT INTO `color_size` VALUES (128, 14, 3, 1, 0);
INSERT INTO `color_size` VALUES (129, 14, 4, 1, 0);
INSERT INTO `color_size` VALUES (130, 14, 5, 1, 0);
INSERT INTO `color_size` VALUES (131, 14, 4, 2, 0);
INSERT INTO `color_size` VALUES (132, 14, 5, 2, 0);
INSERT INTO `color_size` VALUES (133, 14, 6, 2, 0);
INSERT INTO `color_size` VALUES (134, 15, 6, 1, 0);
INSERT INTO `color_size` VALUES (135, 15, 7, 1, 0);
INSERT INTO `color_size` VALUES (136, 15, 8, 1, 0);
INSERT INTO `color_size` VALUES (137, 15, 9, 1, 0);
INSERT INTO `color_size` VALUES (138, 15, 4, 2, 0);
INSERT INTO `color_size` VALUES (139, 15, 5, 2, 0);
INSERT INTO `color_size` VALUES (140, 15, 6, 2, 0);
INSERT INTO `color_size` VALUES (141, 15, 7, 2, 0);
INSERT INTO `color_size` VALUES (142, 15, 4, 3, 0);
INSERT INTO `color_size` VALUES (143, 15, 5, 3, 0);
INSERT INTO `color_size` VALUES (144, 15, 6, 3, 0);
INSERT INTO `color_size` VALUES (145, 15, 7, 3, 0);
INSERT INTO `color_size` VALUES (146, 16, 1, 1, 0);
INSERT INTO `color_size` VALUES (147, 16, 2, 1, 0);
INSERT INTO `color_size` VALUES (148, 16, 3, 1, 0);
INSERT INTO `color_size` VALUES (149, 16, 1, 2, 0);
INSERT INTO `color_size` VALUES (150, 16, 2, 2, 0);
INSERT INTO `color_size` VALUES (151, 16, 3, 2, 0);
INSERT INTO `color_size` VALUES (152, 16, 1, 3, 0);
INSERT INTO `color_size` VALUES (153, 16, 2, 3, 0);
INSERT INTO `color_size` VALUES (154, 17, 3, 3, 0);
INSERT INTO `color_size` VALUES (155, 17, 3, 1, 0);
INSERT INTO `color_size` VALUES (156, 17, 4, 1, 0);
INSERT INTO `color_size` VALUES (157, 17, 5, 1, 0);
INSERT INTO `color_size` VALUES (158, 17, 4, 2, 0);
INSERT INTO `color_size` VALUES (159, 17, 5, 2, 0);
INSERT INTO `color_size` VALUES (160, 17, 6, 2, 0);
INSERT INTO `color_size` VALUES (161, 18, 6, 1, 0);
INSERT INTO `color_size` VALUES (162, 18, 7, 1, 0);
INSERT INTO `color_size` VALUES (163, 18, 8, 1, 0);
INSERT INTO `color_size` VALUES (164, 18, 9, 1, 0);
INSERT INTO `color_size` VALUES (165, 18, 4, 2, 0);
INSERT INTO `color_size` VALUES (166, 18, 5, 2, 0);
INSERT INTO `color_size` VALUES (167, 18, 6, 2, 0);
INSERT INTO `color_size` VALUES (168, 18, 7, 2, 0);
INSERT INTO `color_size` VALUES (169, 18, 4, 3, 0);
INSERT INTO `color_size` VALUES (170, 18, 5, 3, 0);
INSERT INTO `color_size` VALUES (171, 18, 6, 3, 0);
INSERT INTO `color_size` VALUES (172, 18, 7, 3, 0);
INSERT INTO `color_size` VALUES (173, 19, 1, 1, 0);
INSERT INTO `color_size` VALUES (174, 19, 2, 1, 0);
INSERT INTO `color_size` VALUES (175, 19, 3, 1, 0);
INSERT INTO `color_size` VALUES (176, 19, 1, 2, 0);
INSERT INTO `color_size` VALUES (177, 19, 2, 2, 0);
INSERT INTO `color_size` VALUES (178, 19, 3, 2, 0);
INSERT INTO `color_size` VALUES (179, 19, 1, 3, 0);
INSERT INTO `color_size` VALUES (180, 19, 2, 3, 0);
INSERT INTO `color_size` VALUES (181, 19, 3, 3, 0);
INSERT INTO `color_size` VALUES (182, 20, 3, 1, 0);
INSERT INTO `color_size` VALUES (183, 20, 4, 1, 0);
INSERT INTO `color_size` VALUES (184, 20, 5, 1, 0);
INSERT INTO `color_size` VALUES (185, 20, 4, 2, 0);
INSERT INTO `color_size` VALUES (186, 20, 5, 2, 0);
INSERT INTO `color_size` VALUES (187, 21, 6, 2, 0);
INSERT INTO `color_size` VALUES (188, 21, 6, 1, 0);
INSERT INTO `color_size` VALUES (189, 21, 7, 1, 0);
INSERT INTO `color_size` VALUES (190, 21, 8, 1, 0);
INSERT INTO `color_size` VALUES (191, 21, 9, 1, 0);
INSERT INTO `color_size` VALUES (192, 21, 4, 2, 0);
INSERT INTO `color_size` VALUES (193, 21, 5, 2, 0);
INSERT INTO `color_size` VALUES (194, 21, 6, 2, 0);
INSERT INTO `color_size` VALUES (195, 21, 7, 2, 0);
INSERT INTO `color_size` VALUES (196, 21, 4, 3, 0);
INSERT INTO `color_size` VALUES (197, 21, 5, 3, 0);
INSERT INTO `color_size` VALUES (198, 21, 6, 3, 0);
INSERT INTO `color_size` VALUES (199, 21, 7, 3, 0);
INSERT INTO `color_size` VALUES (200, 22, 1, 1, 0);
INSERT INTO `color_size` VALUES (201, 22, 2, 1, 0);
INSERT INTO `color_size` VALUES (202, 22, 3, 1, 0);
INSERT INTO `color_size` VALUES (203, 22, 1, 2, 0);
INSERT INTO `color_size` VALUES (204, 22, 2, 2, 0);
INSERT INTO `color_size` VALUES (205, 22, 3, 2, 0);
INSERT INTO `color_size` VALUES (206, 22, 1, 3, 0);
INSERT INTO `color_size` VALUES (207, 22, 2, 3, 0);
INSERT INTO `color_size` VALUES (208, 23, 3, 3, 0);
INSERT INTO `color_size` VALUES (209, 23, 3, 1, 0);
INSERT INTO `color_size` VALUES (210, 23, 4, 1, 0);
INSERT INTO `color_size` VALUES (211, 23, 5, 1, 0);
INSERT INTO `color_size` VALUES (212, 23, 4, 2, 0);
INSERT INTO `color_size` VALUES (213, 23, 5, 2, 0);
INSERT INTO `color_size` VALUES (214, 23, 6, 2, 0);
INSERT INTO `color_size` VALUES (215, 24, 6, 1, 0);
INSERT INTO `color_size` VALUES (216, 24, 7, 1, 0);
INSERT INTO `color_size` VALUES (217, 24, 8, 1, 0);
INSERT INTO `color_size` VALUES (218, 24, 9, 1, 0);
INSERT INTO `color_size` VALUES (219, 24, 4, 2, 0);
INSERT INTO `color_size` VALUES (220, 24, 5, 2, 0);
INSERT INTO `color_size` VALUES (221, 24, 6, 2, 0);
INSERT INTO `color_size` VALUES (222, 24, 7, 2, 0);
INSERT INTO `color_size` VALUES (223, 24, 4, 3, 0);
INSERT INTO `color_size` VALUES (224, 24, 5, 3, 0);
INSERT INTO `color_size` VALUES (225, 24, 6, 3, 0);
INSERT INTO `color_size` VALUES (226, 24, 7, 3, 0);
INSERT INTO `color_size` VALUES (227, 25, 1, 1, 0);
INSERT INTO `color_size` VALUES (228, 25, 2, 1, 0);
INSERT INTO `color_size` VALUES (229, 25, 3, 1, 0);
INSERT INTO `color_size` VALUES (230, 25, 1, 2, 0);
INSERT INTO `color_size` VALUES (231, 25, 2, 2, 0);
INSERT INTO `color_size` VALUES (232, 25, 3, 2, 0);
INSERT INTO `color_size` VALUES (233, 25, 1, 3, 0);
INSERT INTO `color_size` VALUES (234, 25, 2, 3, 0);
INSERT INTO `color_size` VALUES (235, 25, 3, 3, 0);
INSERT INTO `color_size` VALUES (236, 26, 3, 1, 0);
INSERT INTO `color_size` VALUES (237, 26, 4, 1, 0);
INSERT INTO `color_size` VALUES (238, 26, 5, 1, 0);
INSERT INTO `color_size` VALUES (239, 26, 4, 2, 0);
INSERT INTO `color_size` VALUES (240, 26, 5, 2, 0);
INSERT INTO `color_size` VALUES (241, 26, 6, 2, 0);
INSERT INTO `color_size` VALUES (242, 27, 6, 1, 0);
INSERT INTO `color_size` VALUES (243, 27, 7, 1, 0);
INSERT INTO `color_size` VALUES (244, 27, 8, 1, 0);
INSERT INTO `color_size` VALUES (245, 27, 9, 1, 0);
INSERT INTO `color_size` VALUES (246, 27, 4, 2, 0);
INSERT INTO `color_size` VALUES (247, 27, 5, 2, 0);
INSERT INTO `color_size` VALUES (248, 27, 6, 2, 0);
INSERT INTO `color_size` VALUES (249, 27, 7, 2, 0);
INSERT INTO `color_size` VALUES (250, 27, 4, 3, 0);
INSERT INTO `color_size` VALUES (251, 27, 5, 3, 0);
INSERT INTO `color_size` VALUES (252, 27, 6, 3, 0);
INSERT INTO `color_size` VALUES (253, 27, 7, 3, 0);
INSERT INTO `color_size` VALUES (254, 28, 1, 1, 0);
INSERT INTO `color_size` VALUES (255, 28, 2, 1, 0);
INSERT INTO `color_size` VALUES (256, 28, 3, 1, 0);
INSERT INTO `color_size` VALUES (257, 28, 1, 2, 0);
INSERT INTO `color_size` VALUES (258, 28, 2, 2, 0);
INSERT INTO `color_size` VALUES (259, 28, 3, 2, 0);
INSERT INTO `color_size` VALUES (260, 28, 1, 3, 0);
INSERT INTO `color_size` VALUES (261, 28, 2, 3, 0);
INSERT INTO `color_size` VALUES (262, 29, 3, 3, 0);
INSERT INTO `color_size` VALUES (263, 29, 3, 1, 0);
INSERT INTO `color_size` VALUES (264, 29, 4, 1, 0);
INSERT INTO `color_size` VALUES (265, 29, 5, 1, 0);
INSERT INTO `color_size` VALUES (266, 29, 4, 2, 0);
INSERT INTO `color_size` VALUES (267, 29, 5, 2, 0);
INSERT INTO `color_size` VALUES (268, 29, 6, 2, 0);
INSERT INTO `color_size` VALUES (269, 30, 6, 1, 0);
INSERT INTO `color_size` VALUES (270, 30, 7, 1, 0);
INSERT INTO `color_size` VALUES (271, 30, 8, 1, 0);
INSERT INTO `color_size` VALUES (272, 30, 9, 1, 0);
INSERT INTO `color_size` VALUES (273, 30, 4, 2, 0);
INSERT INTO `color_size` VALUES (274, 30, 5, 2, 0);
INSERT INTO `color_size` VALUES (275, 30, 6, 2, 0);
INSERT INTO `color_size` VALUES (276, 30, 7, 2, 0);
INSERT INTO `color_size` VALUES (277, 30, 4, 3, 0);
INSERT INTO `color_size` VALUES (278, 30, 5, 3, 0);
INSERT INTO `color_size` VALUES (279, 30, 6, 3, 0);
INSERT INTO `color_size` VALUES (280, 30, 7, 3, 0);
INSERT INTO `color_size` VALUES (281, 31, 1, 1, 0);
INSERT INTO `color_size` VALUES (282, 31, 2, 1, 0);
INSERT INTO `color_size` VALUES (283, 31, 3, 1, 0);
INSERT INTO `color_size` VALUES (284, 31, 1, 2, 0);
INSERT INTO `color_size` VALUES (285, 31, 2, 2, 0);
INSERT INTO `color_size` VALUES (286, 31, 3, 2, 0);
INSERT INTO `color_size` VALUES (287, 31, 1, 3, 0);
INSERT INTO `color_size` VALUES (288, 31, 2, 3, 0);
INSERT INTO `color_size` VALUES (289, 32, 3, 3, 0);
INSERT INTO `color_size` VALUES (290, 32, 3, 1, 0);
INSERT INTO `color_size` VALUES (291, 32, 4, 1, 0);
INSERT INTO `color_size` VALUES (292, 32, 5, 1, 0);
INSERT INTO `color_size` VALUES (293, 32, 4, 2, 0);
INSERT INTO `color_size` VALUES (294, 32, 5, 2, 0);
INSERT INTO `color_size` VALUES (295, 32, 6, 2, 0);
INSERT INTO `color_size` VALUES (296, 33, 6, 1, 0);
INSERT INTO `color_size` VALUES (297, 33, 7, 1, 0);
INSERT INTO `color_size` VALUES (298, 33, 8, 1, 0);
INSERT INTO `color_size` VALUES (299, 33, 9, 1, 0);
INSERT INTO `color_size` VALUES (300, 33, 4, 2, 0);
INSERT INTO `color_size` VALUES (301, 33, 5, 2, 0);
INSERT INTO `color_size` VALUES (302, 33, 6, 2, 0);
INSERT INTO `color_size` VALUES (303, 33, 7, 2, 0);
INSERT INTO `color_size` VALUES (304, 33, 4, 3, 0);
INSERT INTO `color_size` VALUES (305, 33, 5, 3, 0);
INSERT INTO `color_size` VALUES (306, 33, 6, 3, 0);
INSERT INTO `color_size` VALUES (307, 33, 7, 3, 0);
INSERT INTO `color_size` VALUES (308, 34, 1, 1, 0);
INSERT INTO `color_size` VALUES (309, 34, 2, 1, 0);
INSERT INTO `color_size` VALUES (310, 34, 3, 1, 0);
INSERT INTO `color_size` VALUES (311, 34, 1, 2, 0);
INSERT INTO `color_size` VALUES (312, 34, 2, 2, 0);
INSERT INTO `color_size` VALUES (313, 34, 3, 2, 0);
INSERT INTO `color_size` VALUES (314, 34, 1, 3, 0);
INSERT INTO `color_size` VALUES (315, 34, 2, 3, 0);
INSERT INTO `color_size` VALUES (316, 34, 3, 3, 0);
INSERT INTO `color_size` VALUES (317, 34, 3, 1, 0);
INSERT INTO `color_size` VALUES (318, 34, 4, 1, 0);
INSERT INTO `color_size` VALUES (319, 34, 5, 1, 0);
INSERT INTO `color_size` VALUES (320, 34, 4, 2, 0);
INSERT INTO `color_size` VALUES (321, 34, 5, 2, 0);
INSERT INTO `color_size` VALUES (322, 34, 6, 2, 0);
INSERT INTO `color_size` VALUES (323, 36, 6, 1, 0);
INSERT INTO `color_size` VALUES (324, 36, 7, 1, 0);
INSERT INTO `color_size` VALUES (325, 36, 8, 1, 0);
INSERT INTO `color_size` VALUES (326, 36, 9, 1, 0);
INSERT INTO `color_size` VALUES (327, 36, 4, 2, 0);
INSERT INTO `color_size` VALUES (328, 36, 5, 2, 0);
INSERT INTO `color_size` VALUES (329, 36, 6, 2, 0);
INSERT INTO `color_size` VALUES (330, 36, 7, 2, 0);
INSERT INTO `color_size` VALUES (331, 36, 4, 3, 0);
INSERT INTO `color_size` VALUES (332, 36, 5, 3, 0);
INSERT INTO `color_size` VALUES (333, 36, 6, 3, 0);
INSERT INTO `color_size` VALUES (334, 36, 7, 3, 0);
INSERT INTO `color_size` VALUES (335, 37, 1, 1, 0);
INSERT INTO `color_size` VALUES (336, 37, 2, 1, 0);
INSERT INTO `color_size` VALUES (337, 37, 3, 1, 0);
INSERT INTO `color_size` VALUES (338, 37, 1, 2, 0);
INSERT INTO `color_size` VALUES (339, 37, 2, 2, 0);
INSERT INTO `color_size` VALUES (340, 37, 3, 2, 0);
INSERT INTO `color_size` VALUES (341, 37, 1, 3, 0);
INSERT INTO `color_size` VALUES (342, 37, 2, 3, 0);
INSERT INTO `color_size` VALUES (343, 37, 3, 3, 0);
INSERT INTO `color_size` VALUES (344, 38, 3, 1, 0);
INSERT INTO `color_size` VALUES (345, 38, 4, 1, 0);
INSERT INTO `color_size` VALUES (346, 38, 5, 1, 0);
INSERT INTO `color_size` VALUES (347, 38, 4, 2, 0);
INSERT INTO `color_size` VALUES (348, 38, 5, 2, 0);
INSERT INTO `color_size` VALUES (349, 38, 6, 2, 0);
INSERT INTO `color_size` VALUES (350, 39, 6, 1, 0);
INSERT INTO `color_size` VALUES (351, 39, 7, 1, 0);
INSERT INTO `color_size` VALUES (352, 39, 8, 1, 0);
INSERT INTO `color_size` VALUES (353, 39, 9, 1, 0);
INSERT INTO `color_size` VALUES (354, 39, 4, 2, 0);
INSERT INTO `color_size` VALUES (355, 39, 5, 2, 0);
INSERT INTO `color_size` VALUES (356, 39, 6, 2, 0);
INSERT INTO `color_size` VALUES (357, 39, 7, 2, 0);
INSERT INTO `color_size` VALUES (358, 39, 4, 3, 0);
INSERT INTO `color_size` VALUES (359, 39, 5, 3, 0);
INSERT INTO `color_size` VALUES (360, 39, 6, 3, 0);
INSERT INTO `color_size` VALUES (361, 39, 7, 3, 0);
INSERT INTO `color_size` VALUES (509, 85, 7, 1, 0);

-- ----------------------------
-- Table structure for delivery_status
-- ----------------------------
DROP TABLE IF EXISTS `delivery_status`;
CREATE TABLE `delivery_status`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of delivery_status
-- ----------------------------
INSERT INTO `delivery_status` VALUES (0, 'Đợi lấy hàng');
INSERT INTO `delivery_status` VALUES (1, 'Chưa xác nhận');
INSERT INTO `delivery_status` VALUES (2, 'Đợi lấy hàng');
INSERT INTO `delivery_status` VALUES (3, 'Đã lấy hàng');
INSERT INTO `delivery_status` VALUES (4, 'Đang vận chuyển');
INSERT INTO `delivery_status` VALUES (5, 'Đang giao hàng');
INSERT INTO `delivery_status` VALUES (6, 'Đã giao hàng');

-- ----------------------------
-- Table structure for discount_code
-- ----------------------------
DROP TABLE IF EXISTS `discount_code`;
CREATE TABLE `discount_code`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `discount` decimal(10, 2) NULL DEFAULT NULL,
  `discount_rate` int NULL DEFAULT NULL,
  `start_date` datetime NULL DEFAULT NULL,
  `end_date` datetime NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'active',
  `created_at` datetime NULL DEFAULT current_timestamp,
  `created_by` int NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `updated_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `created_by`(`created_by` ASC) USING BTREE,
  CONSTRAINT `discount_code_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of discount_code
-- ----------------------------

-- ----------------------------
-- Table structure for image_product
-- ----------------------------
DROP TABLE IF EXISTS `image_product`;
CREATE TABLE `image_product`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NULL DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `image_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of image_product
-- ----------------------------
INSERT INTO `image_product` VALUES (2, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (3, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (4, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (5, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (6, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (7, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (8, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (9, 3, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (10, 4, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (11, 5, 'http://localhost:3000/static/media/BigSlider2.4628882c8bc935f00462.webp');
INSERT INTO `image_product` VALUES (30, 85, 'https://i.ibb.co/DWBvjRW/d5027ecbcfd0.webp');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `timeStamp` datetime NULL DEFAULT current_timestamp,
  `user_id` int NOT NULL,
  `action` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for order_details
-- ----------------------------
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `size_id` int NOT NULL,
  `color_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  INDEX `size_id`(`size_id` ASC) USING BTREE,
  INDEX `color_id`(`color_id` ASC) USING BTREE,
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_details_ibfk_3` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_details_ibfk_4` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_details
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `payment_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `total_amount` decimal(10, 2) NOT NULL,
  `discount_code_id` int NULL DEFAULT NULL,
  `delivery_status_id` int NULL DEFAULT NULL,
  `shipping_cost` decimal(10, 2) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT current_timestamp,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `discount_code_id`(`discount_code_id` ASC) USING BTREE,
  INDEX `delivery_status_id`(`delivery_status_id` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`discount_code_id`) REFERENCES `discount_code` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`delivery_status_id`) REFERENCES `delivery_status` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'ADMIN');
INSERT INTO `permission` VALUES (2, 'CUSTOMER');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `thumbnail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `price` decimal(10, 2) NOT NULL,
  `quantity` int NULL DEFAULT 0,
  `sold` int NULL DEFAULT 0,
  `status` bit(1) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT current_timestamp,
  `created_by` int NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `updated_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  INDEX `created_by`(`created_by` ASC) USING BTREE,
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 1, 'Sp1', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', '<p>1212121</p>', 5555.00, 0, 0, b'1', '2024-04-11 23:14:00', NULL, '2024-04-11 23:14:10', NULL);
INSERT INTO `product` VALUES (2, 1, 'Sp1', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', '<p>1212121</p>', 5555.00, 0, 0, b'1', '2024-04-11 23:14:10', NULL, '2024-04-11 23:14:10', NULL);
INSERT INTO `product` VALUES (3, 1, 'Áo Thun Teelab Local Brand Unisex Las Vegas Tshirt TS226', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:42:43', NULL, NULL, NULL);
INSERT INTO `product` VALUES (4, 1, 'Áo Thun Teelab Local Brand Unisex Stitch Detail Tshirt TS224', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:44:28', NULL, NULL, NULL);
INSERT INTO `product` VALUES (5, 1, 'Áo Thun Teelab Local Brand Unisex Goose on Animal Planet Tshirt TS229', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:44:36', NULL, NULL, NULL);
INSERT INTO `product` VALUES (6, 1, 'Áo Babytee Polo Teelab Local Brand Unisex Studio Track Baby BT012', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 250000.00, 0, 0, b'1', '2024-03-08 20:46:29', NULL, '2024-05-30 21:56:24', NULL);
INSERT INTO `product` VALUES (7, 1, 'Áo Thun Baby Tee Teelab butterfly BT003', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 250000.00, 0, 0, b'1', '2024-03-08 20:47:07', NULL, NULL, NULL);
INSERT INTO `product` VALUES (8, 1, 'Áo Babytee Local Brand Teelab Studio Baby Sheep Cute BT013', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 250000.00, 0, 0, b'1', '2024-03-08 20:47:17', NULL, '2024-05-30 21:54:13', NULL);
INSERT INTO `product` VALUES (9, 1, 'Áo Thun Babytee flaming fire BT011', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 250000.00, 0, 0, b'1', '2024-03-08 20:53:55', NULL, NULL, NULL);
INSERT INTO `product` VALUES (10, 1, 'Áo Thun Baby Tee Teelab Studio Tducky BT007', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 250000.00, 0, 0, b'1', '2024-03-08 20:55:13', NULL, NULL, NULL);
INSERT INTO `product` VALUES (11, 1, 'Áo Polo Teelab Local Brand Unisex Power Team Worldwide AP050', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:56:18', NULL, NULL, NULL);
INSERT INTO `product` VALUES (12, 1, 'Áo Polo Teelab Local Brand Unisex Racing Master Polo Shirt AP049', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:56:33', NULL, NULL, NULL);
INSERT INTO `product` VALUES (13, 1, 'Áo Polo Teelab Local Brand Unisex United Waffle Polo Shirt AP046', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:56:47', NULL, NULL, NULL);
INSERT INTO `product` VALUES (14, 1, 'Áo Babytee Polo Teelab Local Brand Unisex Studio Track Baby BT012', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:57:17', NULL, NULL, NULL);
INSERT INTO `product` VALUES (15, 1, 'Áo Polo Teelab Tyrannosaurus AP035', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:57:30', NULL, NULL, NULL);
INSERT INTO `product` VALUES (16, 1, 'Áo Sơ Mi Ngắn Tay Teelab Local Brand Unisex Studio Waffle Shirt SS050', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:58:50', NULL, NULL, NULL);
INSERT INTO `product` VALUES (17, 1, 'Áo Sơ Mi Ngắn Tay Teelab Local Brand Unisex Studio Oxford Shirt SS052', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:59:03', NULL, NULL, NULL);
INSERT INTO `product` VALUES (18, 1, 'Áo Sơ Mi Ngắn Tay Teelab Graffiti Oversize Shirt SS049', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:59:33', NULL, NULL, NULL);
INSERT INTO `product` VALUES (19, 4, 'Áo Sơ Mi Teelab Symbol Basic Logo SS047', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 20:59:54', NULL, NULL, NULL);
INSERT INTO `product` VALUES (20, 4, 'Áo Sơ Mi Đũi Cộc Tay Form Rộng Teelab Local Brand Unisex SS038', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 21:00:16', NULL, NULL, NULL);
INSERT INTO `product` VALUES (21, 5, 'Áo Khoác Gió Teelab Local Brand Unisex Color Block Patchwork Logo Printed Jacket AK107', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:01:36', NULL, NULL, NULL);
INSERT INTO `product` VALUES (22, 5, 'Áo Khoác Gió Teelab Local Brand Unisex Contrast Line Zip-up Jacket AK102', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:01:49', NULL, NULL, NULL);
INSERT INTO `product` VALUES (23, 5, 'Áo khoác Teelab Local Brand Unisex Tweed striped fabric jacket AK098', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:01:50', NULL, NULL, NULL);
INSERT INTO `product` VALUES (24, 5, 'Áo khoác Gió Varsity Teelab Local Brand Unisex Bones holding cards AK101', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:02:04', NULL, NULL, NULL);
INSERT INTO `product` VALUES (25, 5, 'Áo Khoác Teelab Local Brand Unisex Pilot Boxy Fit Bomber AK092', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:02:39', NULL, NULL, NULL);
INSERT INTO `product` VALUES (26, 6, 'Áo Hoodie Teelab Local Brand Unisex wash cupid HD071', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 750000.00, 0, 0, b'1', '2024-03-08 21:03:29', NULL, NULL, NULL);
INSERT INTO `product` VALUES (27, 6, 'Áo Hoodie Teelab Local Brand Unisex \" Mom’s favorite kido \" Hoodie HD081', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:03:39', NULL, NULL, NULL);
INSERT INTO `product` VALUES (28, 6, 'Áo Hoodie Teelab Local Brand Unisex \" TếtLàb \" Sum họp Hoodie HD080', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:03:49', NULL, NULL, NULL);
INSERT INTO `product` VALUES (29, 6, 'Áo Hoodie Teelab Local Brand Unisex Tectonic hoodie HD072', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 550000.00, 0, 0, b'1', '2024-03-08 21:04:07', NULL, NULL, NULL);
INSERT INTO `product` VALUES (30, 6, 'Áo Hoodie Teelab Local Brand Unisex Grey Ombre Washed Full Zip Hoodie HD076', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 750000.00, 0, 0, b'1', '2024-03-08 21:05:23', NULL, NULL, NULL);
INSERT INTO `product` VALUES (31, 7, 'Quần Nỉ Teelab Local Brand Unisex Worldwide Studio PS063', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 400000.00, 0, 0, b'1', '2024-03-08 21:07:22', NULL, NULL, NULL);
INSERT INTO `product` VALUES (32, 7, 'Quần short kaki túi hộp 3 màu Teelab Basic PS060', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 21:07:32', NULL, NULL, NULL);
INSERT INTO `product` VALUES (33, 7, 'Quần Short Dù Túi Hộp Teelab Local Brand Unisex Basic PS059', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 21:07:56', NULL, NULL, NULL);
INSERT INTO `product` VALUES (34, 7, 'Quần Short Kaki Teelab Local Brand Unisex Pocket Basic PS058', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 350000.00, 0, 0, b'1', '2024-03-08 21:08:07', NULL, NULL, NULL);
INSERT INTO `product` VALUES (35, 7, 'Quần Short Kaki Teelab Local Brand Unisex 2 Túi Hộp PS037', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 260000.00, 0, 0, b'1', '2024-03-08 21:08:18', NULL, NULL, NULL);
INSERT INTO `product` VALUES (36, 8, 'Quần Jean Teelab Local Brand Unisex Ripped Wash GP010', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 400000.00, 0, 0, b'1', '2024-03-08 21:11:38', NULL, NULL, NULL);
INSERT INTO `product` VALUES (37, 8, 'Quần Jean Teelab Local Brand Unisex Basic Denim GP008', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 500000.00, 0, 0, b'1', '2024-03-08 21:11:46', NULL, NULL, NULL);
INSERT INTO `product` VALUES (38, 8, 'Quần Jean Teelab Local Brand Unisex Silver Wash Denim GP007', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 500000.00, 0, 0, b'1', '2024-03-08 21:11:53', NULL, NULL, NULL);
INSERT INTO `product` VALUES (39, 8, 'Quần Jean Teelab Local Brand Unisex Ripped Denim GP006', 'https://i.ibb.co/5cYxtDs/01532f62d674.webp', 'You will never be younger than you are at this very moment “Enjoy Your Youth!”\r\n\r\nKhông chỉ là thời trang, TEELAB còn là “phòng thí nghiệm” của tuổi trẻ - nơi nghiên cứu và cho ra đời năng lượng mang tên “Youth”. Chúng mình luôn muốn tạo nên những trải nghiệm vui vẻ, năng động và trẻ trung.\r\nLấy cảm hứng từ giới trẻ, sáng tạo liên tục, bắt kịp xu hướng và phát triển đa dạng các dòng sản phẩm là cách mà chúng mình hoạt động để tạo nên phong cách sống hằng ngày của bạn. Mục tiêu của TEELAB là cung cấp các sản phẩm thời trang chất lượng cao với giá thành hợp lý.\r\nChẳng còn thời gian để loay hoay nữa đâu youngers ơi! Hãy nhanh chân bắt lấy những những khoảnh khắc tuyệt vời của tuổi trẻ. TEELAB đã sẵn sàng trải nghiệm cùng bạn!\r\n\r\n“Enjoy Your Youth”, now!\r\n\r\nHướng dẫn sử dụng sản phẩm Teelab:\r\n- Ngâm áo vào NƯỚC LẠNH có pha giấm hoặc phèn chua từ trong 2 tiếng đồng hồ\r\n- Giặt ở nhiệt độ bình thường, với đồ có màu tương tự.\r\n- Không dùng hóa chất tẩy.\r\n- Hạn chế sử dụng máy sấy và ủi (nếu có) thì ở nhiệt độ thích hợp.\r\n\r\nChính sách bảo hành:\r\n- Miễn phí đổi hàng cho khách mua ở TEELAB trong trường hợp bị lỗi từ nhà sản xuất, giao nhầm hàng, bị hư hỏng trong quá trình vận chuyển hàng.\r\n- Sản phẩm đổi trong thời gian 3 ngày kể từ ngày nhận hàng\r\n- Sản phẩm còn mới nguyên tem, tags và mang theo hoá đơn mua hàng, sản phẩm chưa giặt và không dơ bẩn, hư hỏng bởi những tác nhân bên ngoài cửa hàng sau khi mua hàng.', 500000.00, 0, 0, b'1', '2024-03-08 21:12:03', NULL, NULL, NULL);
INSERT INTO `product` VALUES (85, 4, 'z tétt 1', 'https://i.ibb.co/DWBvjRW/d5027ecbcfd0.webp', '<p>Áo quần</p>', 500000.00, 0, 0, b'1', '2024-05-31 16:50:20', NULL, '2024-05-31 16:50:37', NULL);

-- ----------------------------
-- Table structure for product_promotion
-- ----------------------------
DROP TABLE IF EXISTS `product_promotion`;
CREATE TABLE `product_promotion`  (
  `promotion_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`promotion_id`, `product_id`) USING BTREE,
  INDEX `product_promotion_ibfk_2`(`product_id` ASC) USING BTREE,
  CONSTRAINT `product_promotion_ibfk_1` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_promotion_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_promotion
-- ----------------------------
INSERT INTO `product_promotion` VALUES (6, 38);
INSERT INTO `product_promotion` VALUES (7, 37);
INSERT INTO `product_promotion` VALUES (7, 38);
INSERT INTO `product_promotion` VALUES (8, 33);
INSERT INTO `product_promotion` VALUES (8, 37);

-- ----------------------------
-- Table structure for promotion
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `discount_rate` int NULL DEFAULT NULL,
  `start_date` datetime NULL DEFAULT NULL,
  `end_date` datetime NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT current_timestamp,
  `created_by` int NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `updated_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `created_by`(`created_by` ASC) USING BTREE,
  CONSTRAINT `promotion_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of promotion
-- ----------------------------
INSERT INTO `promotion` VALUES (2, 'Khuyến mãi mùa đông 3', 'hhaha', 53, '2024-04-17 00:00:00', '2024-05-21 00:00:00', b'1', '2024-04-16 19:04:49', NULL, '2024-05-29 20:28:18', NULL);
INSERT INTO `promotion` VALUES (6, 'Mua dong', 'mua dong da den22', 33, '2024-05-30 00:00:00', '2024-05-31 00:00:00', b'1', '2024-05-31 16:00:09', NULL, '2024-05-31 16:00:28', NULL);
INSERT INTO `promotion` VALUES (7, 'Mua he 3', 'Mua he', 33, '2024-05-30 00:00:00', '2024-05-31 00:00:00', b'1', '2024-05-31 16:44:27', NULL, '2024-05-31 16:44:43', NULL);
INSERT INTO `promotion` VALUES (8, 'Mua ha', 'mua', 33, '2024-05-23 00:00:00', '2024-05-31 00:00:00', b'1', '2024-05-31 16:51:18', NULL, '2024-05-31 16:51:18', NULL);

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `score` int NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT current_timestamp,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of review
-- ----------------------------

-- ----------------------------
-- Table structure for size
-- ----------------------------
DROP TABLE IF EXISTS `size`;
CREATE TABLE `size`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of size
-- ----------------------------
INSERT INTO `size` VALUES (1, 'M');
INSERT INTO `size` VALUES (2, 'L');
INSERT INTO `size` VALUES (3, 'XL');

-- ----------------------------
-- Table structure for slider
-- ----------------------------
DROP TABLE IF EXISTS `slider`;
CREATE TABLE `slider`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `created_by` int NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `updated_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of slider
-- ----------------------------
INSERT INTO `slider` VALUES (1, 'hehe.png', 'Summer', b'1', NULL, NULL, NULL, NULL);
INSERT INTO `slider` VALUES (2, 'hih.jpg', 'Winter', b'1', NULL, NULL, NULL, NULL);
INSERT INTO `slider` VALUES (3, 'huhu.png', 'Spring', b'0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `permission_id` int NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `user_info_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_info_id`(`user_info_id` ASC) USING BTREE,
  INDEX `permission_id`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_info_id`) REFERENCES `user_information` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (8, 'Huy', '$2a$10$1HZa17HVVe7XFbO1PI/5xuoyHi0Qk6aPJ46h8HYtNXVicW0yRVCTW', 1, b'1', 14);
INSERT INTO `user` VALUES (10, 'huyhiep1907', '$2a$10$RQwZfQSdSfZrTZpy0pfbTOPh/RNM88T1lFQAhCewIChpvrIQKUsD.', 1, b'1', 16);

-- ----------------------------
-- Table structure for user_information
-- ----------------------------
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT current_timestamp,
  `updated_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES (1, 'huyhiep', 'hieppro221992@gmail.copm', '0869991', 'Vai luon1', NULL, '2024-04-16 22:00:39', '2024-04-17 17:35:14');
INSERT INTO `user_information` VALUES (14, 'HuyNguyen', 'huy@gmail.com', '0823173127', '', 'https://i.ibb.co/8KjrZ8S/b10217bb1b34.webp', '2024-05-31 15:23:52', '2024-05-31 15:39:37');
INSERT INTO `user_information` VALUES (16, NULL, 'huyhiep1907@gmail.com', NULL, NULL, NULL, '2024-05-31 15:43:28', NULL);

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `color_id` int NOT NULL,
  `size_id` int NOT NULL,
  `import_price` decimal(10, 2) NULL DEFAULT NULL,
  `quantity` int NOT NULL,
  `created_at` datetime NULL DEFAULT current_timestamp,
  `created_by` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  INDEX `color_id`(`color_id` ASC) USING BTREE,
  INDEX `size_id`(`size_id` ASC) USING BTREE,
  INDEX `created_by`(`created_by` ASC) USING BTREE,
  CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_ibfk_2` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_ibfk_3` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES (1, 3, 6, 1, 500000.00, 3, '2024-05-03 14:43:17', NULL);
INSERT INTO `warehouse` VALUES (2, 1, 2, 3, 50.00, 100, '2024-05-10 16:24:23', NULL);
INSERT INTO `warehouse` VALUES (3, 1, 2, 3, 50.00, 100, '2024-05-10 16:31:24', NULL);
INSERT INTO `warehouse` VALUES (4, 3, 6, 1, 50.00, 100, '2024-05-10 16:34:36', NULL);
INSERT INTO `warehouse` VALUES (20, 2, 2, 2, 500000.00, 10, '2024-05-31 16:51:40', NULL);

SET FOREIGN_KEY_CHECKS = 1;
