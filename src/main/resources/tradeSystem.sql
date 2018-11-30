/*
 Navicat Premium Data Transfer

 Source Server         : 47.93.15.203
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 47.93.15.203:3306
 Source Schema         : tradeSystem

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 30/11/2018 19:36:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for T_ADMIN_ACCOUNT
-- ----------------------------
DROP TABLE IF EXISTS `T_ADMIN_ACCOUNT`;
CREATE TABLE `T_ADMIN_ACCOUNT`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AVATOR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ACCESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_API
-- ----------------------------
DROP TABLE IF EXISTS `T_API`;
CREATE TABLE `T_API`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `API_KEY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `API_SEC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DES` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `EXPIRE` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_ASSET
-- ----------------------------
DROP TABLE IF EXISTS `T_ASSET`;
CREATE TABLE `T_ASSET`  (
  `ASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OUTABLE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INABLE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIN_OUT_AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MAX_OUT_FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIN_OUT_FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OUT_FEERATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ICON` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ROUTE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LINK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ASSET`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_ASSETPAIR_PRICE
-- ----------------------------
DROP TABLE IF EXISTS `T_ASSETPAIR_PRICE`;
CREATE TABLE `T_ASSETPAIR_PRICE`  (
  `ASSET_PAIR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CLOSE_PRICE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID` bigint(30) NULL DEFAULT NULL,
  PRIMARY KEY (`DATE`, `ASSET_PAIR`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_ASSET_PAIR
-- ----------------------------
DROP TABLE IF EXISTS `T_ASSET_PAIR`;
CREATE TABLE `T_ASSET_PAIR`  (
  `PAIR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BASE_CURRENCY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUOTE_CURRENCY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PRICE_DECIMAL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AMOUNT_DECIMAL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MARKET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TRADEABLE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIN_TRADE_ASSETLIMIT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIN_TRADE_AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`PAIR`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_ASSET_PAIR_MARKET
-- ----------------------------
DROP TABLE IF EXISTS `T_ASSET_PAIR_MARKET`;
CREATE TABLE `T_ASSET_PAIR_MARKET`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MARKET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRADEABLE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ORDER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DES` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ID`, `MARKET`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_ASSET_TO_BTC_PRICE
-- ----------------------------
DROP TABLE IF EXISTS `T_ASSET_TO_BTC_PRICE`;
CREATE TABLE `T_ASSET_TO_BTC_PRICE`  (
  `ASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TO_BTC_PRICE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ASSET`, `DATE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_BANNER
-- ----------------------------
DROP TABLE IF EXISTS `T_BANNER`;
CREATE TABLE `T_BANNER`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IMG` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TIPS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ORDER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_CLEARACCOUNTBALANCE
-- ----------------------------
DROP TABLE IF EXISTS `T_CLEARACCOUNTBALANCE`;
CREATE TABLE `T_CLEARACCOUNTBALANCE`  (
  `ClearDate` datetime(6) NULL,
  `Asset` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BuyAmount` decimal(32, 8) NULL DEFAULT NULL,
  `SellAmount` decimal(32, 8) NULL DEFAULT NULL,
  `BeforeBalance` decimal(32, 8) NULL DEFAULT NULL,
  `EndBalance` decimal(32, 8) NULL DEFAULT NULL,
  `BuyFee` decimal(32, 8) NULL DEFAULT NULL,
  `SellFee` decimal(32, 8) NULL DEFAULT NULL,
  `TransferIN` decimal(32, 8) NULL DEFAULT NULL,
  `TransferOut` decimal(32, 8) NULL DEFAULT NULL,
  `Stat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Bonus` decimal(32, 8) NULL DEFAULT NULL,
  PRIMARY KEY (`ClearDate`, `Asset`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_CLEAR_STATE
-- ----------------------------
DROP TABLE IF EXISTS `T_CLEAR_STATE`;
CREATE TABLE `T_CLEAR_STATE`  (
  `CLEAR_STATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CLEARDATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_FEE_FLOW
-- ----------------------------
DROP TABLE IF EXISTS `T_FEE_FLOW`;
CREATE TABLE `T_FEE_FLOW`  (
  `ORDER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ASSET_PAIR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME` datetime(0) NULL DEFAULT NULL,
  `MATCH_FLOW_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `ASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19371 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_FILE
-- ----------------------------
DROP TABLE IF EXISTS `T_FILE`;
CREATE TABLE `T_FILE`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UID` int(11) NULL DEFAULT NULL,
  `SIZE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `FILENAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPLOADDATE` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 340 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_GOOGLE_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `T_GOOGLE_AUTH`;
CREATE TABLE `T_GOOGLE_AUTH`  (
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BACKUP_CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STEPTWO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USERNAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_MATCH_FLOW
-- ----------------------------
DROP TABLE IF EXISTS `T_MATCH_FLOW`;
CREATE TABLE `T_MATCH_FLOW`  (
  `ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SELLSIDE_ORDER_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BUYSIDE_ORDER_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PRICE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INSERT_TIME` datetime(0) NULL DEFAULT NULL,
  `SELLSIDE_ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BUYSIDE_ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ASSET_PAIR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BUYSIDE_FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `SELLSIDE_FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `Match_Flow_BuyOrder_Index`(`BUYSIDE_ORDER_ID`) USING BTREE,
  INDEX `Match_Flow_SellOrder_Index`(`SELLSIDE_ORDER_ID`) USING BTREE,
  INDEX `Idx_timestamp`(`INSERT_TIME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_RATE_LIMIT
-- ----------------------------
DROP TABLE IF EXISTS `T_RATE_LIMIT`;
CREATE TABLE `T_RATE_LIMIT`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RATE_LIMIT_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INTERVAL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LIMIT` int(255) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_REFERRAL
-- ----------------------------
DROP TABLE IF EXISTS `T_REFERRAL`;
CREATE TABLE `T_REFERRAL`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `USER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USER_INVITED_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USER_NAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_STATE_REPORT
-- ----------------------------
DROP TABLE IF EXISTS `T_STATE_REPORT`;
CREATE TABLE `T_STATE_REPORT`  (
  `ASSET_PAIR` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATE` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TOTAL_AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TOTAL_FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ACCOUNT
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ACCOUNT`;
CREATE TABLE `T_USER_ACCOUNT`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LEVEL` int(255) NULL DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TIPS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGISTER_IP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DISCD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AUTH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AUTH_REASON` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ACCOUNT_LEVEL
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ACCOUNT_LEVEL`;
CREATE TABLE `T_USER_ACCOUNT_LEVEL`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LEVEL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BUY_FEERATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SELL_FEERATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WITHDRAW_QUOTA` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DES` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AUDIT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1 需要审核 0 不需要',
  `TRADE_AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HOLDING` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_BTC
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_BTC`;
CREATE TABLE `T_USER_ASSET_BTC`  (
  `ID` int(11) NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `TOTAL_AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `AIVILIABLE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LOCK_VERSION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `LIQUIDATION_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_CHANGE_FLOW
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_CHANGE_FLOW`;
CREATE TABLE `T_USER_ASSET_CHANGE_FLOW`  (
  `ID` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `FROM_ADDRESS` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `TIME` datetime(0) NULL DEFAULT NULL,
  `TRANSFER_SIDE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LOCK_VERSION` int(10) NOT NULL,
  `ASSET` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `TO_ADDRESS` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N',
  `UPDATE_TIME` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_CHANGE_FLOW_HISTORY
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_CHANGE_FLOW_HISTORY`;
CREATE TABLE `T_USER_ASSET_CHANGE_FLOW_HISTORY`  (
  `ID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `DATE` date NOT NULL,
  `TRANSFER_SIDE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ASSET` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `UPDATE_TIME` datetime(0) NULL,
  PRIMARY KEY (`ID`, `DATE`, `ASSET`, `TRANSFER_SIDE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20249 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_ETH
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_ETH`;
CREATE TABLE `T_USER_ASSET_ETH`  (
  `ID` int(11) NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `TOTAL_AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `AIVILIABLE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LOCK_VERSION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `LIQUIDATION_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_MOX
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_MOX`;
CREATE TABLE `T_USER_ASSET_MOX`  (
  `ID` int(11) NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `TOTAL_AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `AIVILIABLE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LOCK_VERSION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `LIQUIDATION_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_NEO
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_NEO`;
CREATE TABLE `T_USER_ASSET_NEO`  (
  `ID` int(11) NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `TOTAL_AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `AIVILIABLE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LOCK_VERSION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `LIQUIDATION_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ASSET_USDT
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ASSET_USDT`;
CREATE TABLE `T_USER_ASSET_USDT`  (
  `ID` int(11) NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `TOTAL_AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `AIVILIABLE` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LOCK_VERSION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `LIQUIDATION_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_AUTH_ASSET
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_AUTH_ASSET`;
CREATE TABLE `T_USER_AUTH_ASSET`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ID_NUMBER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ID_ISSUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ID_EXPIRE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ADDRESS_MATCH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CITY2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVINCE2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ZIP2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STREET2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HOUSE_NUMBER2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSPORT_COVER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSPORT_INFO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSPORT_MAN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_AUTH_BASE
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_AUTH_BASE`;
CREATE TABLE `T_USER_AUTH_BASE`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRST_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAST_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIDDLE_NAME1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIDDLE_NAME2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SEX` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BIRTHDAY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COUNTRY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CITY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVINCE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ZIP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STREET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HOUSE_NUMBER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ADDRESS_PHOTO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_AUTH_CERTIFICATE
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_AUTH_CERTIFICATE`;
CREATE TABLE `T_USER_AUTH_CERTIFICATE`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INVEST_SOURCE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EARNING` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INVEST_EXPECT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_AUTH_COMPANY
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_AUTH_COMPANY`;
CREATE TABLE `T_USER_AUTH_COMPANY`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `COMPANY_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGIST_TIME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGIST_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CITY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVINCE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ZIP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STREET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HOUSE_NUMBER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COMPANY_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CITY1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVINCE1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ZIP1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STREET1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COMPANY_NUM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HOUSE_NUMBER1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TAX_NUMBER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHECK_WWW` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WWW` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INVEST_SOURCE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VOLUME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COMPANY_PHOTE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COMPANY_MAN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DIRECTOR_PHOTE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_AUTH_CONTACT
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_AUTH_CONTACT`;
CREATE TABLE `T_USER_AUTH_CONTACT`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRST_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAST_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIDDLE_NAME1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MIDDLE_NAME2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SEX` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CONTACT_TITLE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EMPLOYMENT_LETTER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSPORT_COVER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSPORT_INFO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSPORT_MAN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_BONUS
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_BONUS`;
CREATE TABLE `T_USER_BONUS`  (
  `ASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `Trade_AMOUNT` decimal(32, 8) NULL DEFAULT NULL,
  `Bonus` decimal(32, 8) NOT NULL,
  `CLEARDATE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NOT_START',
  PRIMARY KEY (`ASSET`, `ACCOUNT`, `CLEARDATE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_CANCEL_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_CANCEL_ORDER`;
CREATE TABLE `T_USER_CANCEL_ORDER`  (
  `ORDER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CANCEL_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ORDER`;
CREATE TABLE `T_USER_ORDER`  (
  `ORDER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORDER_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ASSET_PAIR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORDER_SIDE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORDER_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SURVIVE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PRICE` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STATE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REMAIN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ASSET_LIMIT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_VERSION` int(10) NOT NULL DEFAULT 0,
  `ORDER_FEE_RATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `MATCH_MONEY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ORDER_FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `SOURCE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DEDUCTION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `DEDUCTION_AMOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `DEDUCTION_COST` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ORDER_ID`) USING BTREE,
  INDEX `order_account_index`(`ACCOUNT`) USING BTREE,
  INDEX `idx_deduction`(`DEDUCTION`) USING BTREE,
  INDEX `id_status`(`STATE`) USING BTREE,
  INDEX `idx_Update_time`(`UPDATE_TIME`) USING BTREE,
  INDEX `idx_order_time`(`ORDER_TIME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_REBATE
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_REBATE`;
CREATE TABLE `T_USER_REBATE`  (
  `ASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `AMOUNT` decimal(32, 8) NOT NULL,
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BASE_AMOUNT` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CLEARDATE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STATE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NOT_START',
  PRIMARY KEY (`ASSET`, `ACCOUNT`, `CLEARDATE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_SECRET
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_SECRET`;
CREATE TABLE `T_USER_SECRET`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `QUESTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ANSWER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_TRADEAMOUNT
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_TRADEAMOUNT`;
CREATE TABLE `T_USER_TRADEAMOUNT`  (
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TOTALAMOUNT` decimal(32, 8) NULL DEFAULT NULL,
  `CLEARDATE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BALANCE` decimal(32, 8) NULL DEFAULT NULL,
  `CURLEVEL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AMOUNTGAP` decimal(32, 8) NULL DEFAULT NULL,
  `BALANCEGAP` decimal(32, 8) NULL DEFAULT NULL,
  `TODAYAMOUNT` decimal(32, 8) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`, `CLEARDATE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_TRANSFERIN_ADDR_BTC
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_TRANSFERIN_ADDR_BTC`;
CREATE TABLE `T_USER_TRANSFERIN_ADDR_BTC`  (
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ADDR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATETIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_TRANSFERIN_ADDR_ETH
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_TRANSFERIN_ADDR_ETH`;
CREATE TABLE `T_USER_TRANSFERIN_ADDR_ETH`  (
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ADDR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATETIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_TRANSFERIN_ADDR_USDT
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_TRANSFERIN_ADDR_USDT`;
CREATE TABLE `T_USER_TRANSFERIN_ADDR_USDT`  (
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ADDR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATETIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_TRANSOUT_ADDRESS
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_TRANSOUT_ADDRESS`;
CREATE TABLE `T_USER_TRANSOUT_ADDRESS`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `ASSET` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `ADDRESS` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `VERSION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for T_USER_TRANSOUT_FLOW
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_TRANSOUT_FLOW`;
CREATE TABLE `T_USER_TRANSOUT_FLOW`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FROM_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TIME` datetime(0) NULL DEFAULT NULL,
  `ASSET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ACCOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TO_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AMOUNT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N',
  `FEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LINK` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for P_AccountLevelUpdate
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_AccountLevelUpdate`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `P_AccountLevelUpdate`(clear_date VARCHAR ( 8 ) )
BEGIN#Routine body goes here...
	DECLARE
		accountid VARCHAR ( 24 ) DEFAULT '';
	DECLARE
		balance DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		btctradeamount DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		today_amount DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		accountlevel int  DEFAULT 0;
	DECLARE
		nextlvtradeamountgap DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		nextlvbalancegap  DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		next_day VARCHAR ( 8 );
	DECLARE
		last_day VARCHAR ( 8 );
	DECLARE
		last_30_day VARCHAR ( 8 );
	DECLARE
		Today_day VARCHAR ( 10 );
/*标识事务错误*/
	DECLARE
		err INT DEFAULT 0;
/*达到一定数量就进行提交，计数器*/
	DECLARE
		counts INT DEFAULT 0;
/*标识是否回滚过*/
	DECLARE
		isrollback INT DEFAULT 0;
/*游标遍历时，作为判断是否遍历完全部记录的标记*/
	DECLARE
		done INTEGER DEFAULT 0;
/*获取临时表该任务的数据*/

	DECLARE
		cur CURSOR FOR SELECT
		c1.ACCOUNT,
		c1.TOTAL_AMOUNT as balance,
		COALESCE(c2.btc_trade_amount,0)+COALESCE(last_30_amount,0) as totalamount,
		c3.`LEVEL`,
		COALESCE(c2.btc_trade_amount,0) as todayamount
	FROM
		T_USER_ASSET_BTC c1
		LEFT JOIN (
		SELECT
			ACCOUNT,
			sum( COALESCE ( trade_amount * TO_BTC_PRICE, 0 ) ) AS btc_trade_amount 
		FROM
			(
			SELECT
				a1.BUYSIDE_ACCOUNT AS ACCOUNT,
				substr( a1.ASSET_PAIR, 1, 3 ) AS asset,
				sum( a1.AMOUNT * PRICE ) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= last_day 
				AND a1.INSERT_TIME < next_day
			GROUP BY
				a1.BUYSIDE_ACCOUNT,
				substr( ASSET_PAIR, 1, 3 ) UNION
			SELECT
				a1.SELLSIDE_ACCOUNT AS ACCOUNT,
				substr( a1.ASSET_PAIR, 5, 3 ) AS asset,
				sum( a1.AMOUNT ) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= last_day
				AND a1.INSERT_TIME < next_day
			GROUP BY
				a1.SELLSIDE_ACCOUNT,
				substr( ASSET_PAIR, 5, 3 ) 
			) b1
			LEFT JOIN T_ASSET_TO_BTC_PRICE b2 ON b1.asset = b2.ASSET 
			AND b2.date = Today_day
		GROUP BY
			ACCOUNT 
		) c2 ON c1.ACCOUNT = c2.ACCOUNT
		left join T_USER_ACCOUNT c3
		on c1.ACCOUNT=c3.username
		left join (
		select account,sum(todayamount) as last_30_amount from T_USER_TRADEAMOUNT where cleardate>=last_30_day
		group by account
		)c4
		on c1.account=c4.account
		;
		
		/* 出现错误，设置为1，只要发生异常就回滚*/
	DECLARE
	CONTINUE HANDLER FOR SQLEXCEPTION 
		SET err = 1;
/*声明当游标遍历完全部记录后将标志变量置成某个值*/
	DECLARE
		CONTINUE HANDLER FOR NOT FOUND 
		SET done = 1;
	SELECT
		date_format(
				str_to_date( clear_date, '%Y%m%d' ),'%Y-%m-%d' 
		) INTO Today_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL - 1 DAY 
			),
			'%Y%m%d' 
		) INTO next_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL  0 DAY 
			),
			'%Y%m%d' 
		) INTO last_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL  29 DAY 
			),
			'%Y%m%d' 
		) INTO last_30_day;
delete from T_USER_TRADEAMOUNT where cleardate=clear_date;
/*开启事务*/
	START TRANSACTION;
/*打开游标*/
	OPEN cur;
/*使用LOOP循环遍历*/
	out_loop :LOOP
/*将每一条结果对应的字段值赋值给变量*/
		FETCH cur INTO accountid,balance,btctradeamount,accountlevel,today_amount;
		IF	done = 1 THEN
				LEAVE out_loop;
		END IF;
/*如果正式表不存在相同groupId and email记录，添加到正式表*/
/*插入正式表*/
		IF balance >= 100 AND btctradeamount > 200 and accountlevel<4  THEN
				UPDATE T_USER_ACCOUNT SET `LEVEL` = 4 WHERE	ACCOUNT = accountid;
				set nextlvbalancegap=0;
				set nextlvtradeamountgap=0;
				SET accountlevel=4;
/*计数器，每1000条才提交*/
				SET counts = counts + 1;
		ELSEIF balance >= 50 and balance<100
					AND btctradeamount > 100 and btctradeamount<=200  THEN
					if accountlevel<3 THEN
						UPDATE T_USER_ACCOUNT SET `LEVEL` = 3 WHERE	ACCOUNT = accountid;
					end if;
					if balance<100 then 
						set nextlvbalancegap=100 - balance;
					end if; 
					if btctradeamount<200 then 
						set nextlvtradeamountgap=200 - btctradeamount;
					end if; 
					SET accountlevel=3;
/*计数器，每1000条才提交*/
					SET counts = counts + 1;

		ELSEIF balance >= 25 and balance<50
				 AND btctradeamount > 50 and btctradeamount<=100  THEN
				 	if accountlevel<2 THEN
				 UPDATE T_USER_ACCOUNT SET `LEVEL` = 2 WHERE	ACCOUNT = accountid;
				 end if;
/*计数器，每1000条才提交*/
				 SET counts = counts + 1;
				 if balance<50 then 
						set nextlvbalancegap=50 - balance;
				 end if; 
				 if btctradeamount<100 then 
						set nextlvtradeamountgap=100-btctradeamount;
				 end if ;
				 SET accountlevel=2;

		ELSEIF balance >= 10 and balance<25 AND btctradeamount >20  and btctradeamount<=50   THEN
					if accountlevel<1 THEN
						UPDATE T_USER_ACCOUNT SET `LEVEL` = 1 WHERE	ACCOUNT = accountid;
					end if;
					if balance<25 then 
						set nextlvbalancegap=25-balance;
					end if ;
					if btctradeamount<50 then 
						set nextlvtradeamountgap=50-btctradeamount;
					end if ;
					SET accountlevel=1;
					SET counts = counts + 1;
		ELSE   
					if balance<10 then 
						set nextlvbalancegap=10-balance;
					end if ;
					if btctradeamount<20 then 
						set nextlvtradeamountgap=20-btctradeamount;
					end if ;
					SET accountlevel=1;
					SET counts = counts + 1;			
		END IF;
		insert into T_USER_TRADEAMOUNT(account,TOTALAMOUNT,ClearDate,balance,balancegap,amountgap,curlevel,todayamount)
		VALUES(accountid,btctradeamount,clear_date,balance,nextlvbalancegap,nextlvtradeamountgap,accountlevel,today_amount);
	/*计数器，每1000条才提交*/
		SET counts = counts + 1;
/*发生异常，回滚*/
		IF err = 1 THEN
				SET isrollback = 1;
				ROLLBACK;
		ELSE 
				IF counts = 1000 THEN
					COMMIT;
/*达到1000条提交后，重置计数器*/
					SET counts = 0;	
				END IF;
		END IF;	
		END LOOP out_loop;
		COMMIT;
		CLOSE cur;
		
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for P_ClearAccount
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_ClearAccount`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `P_ClearAccount`( clear_date VARCHAR ( 8 ) )
BEGIN
	DECLARE
		c_asset VARCHAR ( 36 );-- 遍历数据结束标志，两层循环用同一个标志，需要特别主意
	DECLARE
		done INT DEFAULT FALSE;
	DECLARE
		TOTALAMOUNT DECIMAL(32,8);
	DECLARE
		my_sql VARCHAR ( 2000 );
	DECLARE
		next_day VARCHAR ( 8 );
	DECLARE
		last_day VARCHAR ( 8 );
	DECLARE curasset CURSOR FOR SELECT asset FROM T_ASSET;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE; 
	Delete from T_CLEARACCOUNTBALANCE where ClearDate=clear_date;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL - 1 DAY 
			),
			'%Y%m%d' 
		) INTO next_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL  1 DAY 
			),
			'%Y%m%d' 
		) INTO last_day;
		
	INSERT INTO T_CLEARACCOUNTBALANCE (
		Asset,
		ClearDate,
		buyamount,
		BuyFee,
		sellamount,
		SellFee,
		transferIn,
		transferout,
		BeforeBalance,
	  Bonus
	) SELECT
	a1.asset,
	clear_date,
	COALESCE(buy_amount,0),
	COALESCE(buy_fee,0),
	COALESCE(sell_amount,0),
	COALESCE(sell_fee,0),
	COALESCE(transferIn,0),
	COALESCE(transferout,0),
	COALESCE(BeforeBalance,0),
	COALESCE(Bonus+rebate_amount,0)
	FROM
		T_ASSET a1
		LEFT JOIN (
		SELECT
			date_format( INSERT_TIME, '%Y%m%d' ) AS clearDate,
			substr( ASSET_PAIR, 1, 3 ) AS asset,
			sum(AMOUNT) as  buy_amount,
			sum( BUYSIDE_fee ) AS buy_fee 
		FROM
			T_MATCH_FLOW 
		WHERE
			INSERT_TIME >= clear_date
			AND INSERT_TIME < next_day 
		GROUP BY
			substr( ASSET_PAIR, 1, 3 ),
			date_format( INSERT_TIME, '%Y%m%d' ) 
		) a2 ON a1.asset = a2.asset
		LEFT JOIN (
		SELECT
			substr( ASSET_PAIR, 5, 3 ) AS asset,
			sum(AMOUNT*price) as sell_amount,
			sum( SELLSIDE_fee ) AS sell_fee,
			date_format( INSERT_TIME, '%Y%m%d' ) AS clearDate 
		FROM
			T_MATCH_FLOW 
		WHERE
			INSERT_TIME >= clear_date
			AND INSERT_TIME < next_day
		GROUP BY
			substr( ASSET_PAIR, 5, 3 ),
			date_format( INSERT_TIME, '%Y%m%d' ) 
		) a3 ON a1.asset = a3.asset
		LEFT JOIN (
		SELECT
			asset,
			sum( amount ) AS transferIn 
		FROM
			T_USER_ASSET_CHANGE_FLOW 
		WHERE
			UPDATE_TIME >= clear_date
			AND UPDATE_TIME < next_day
			AND stat = 'D' 
			AND TRANSFER_SIDE = 'IN' 
		GROUP BY
			asset 
		) a4 ON a1.asset = a4.asset
		LEFT JOIN (
		SELECT
			asset,
			sum( amount ) AS transferout 
		FROM
			T_USER_ASSET_CHANGE_FLOW 
		WHERE
			UPDATE_TIME >= clear_date
			AND UPDATE_TIME < next_day 
			AND stat = 'D' 
			AND TRANSFER_SIDE = 'OUT' 
		GROUP BY
			asset 
		) a5 ON 
		a1.asset = a5.asset
		left join (
		select EndBalance as BeforeBalance,asset from T_CLEARACCOUNTBALANCE where clearDate=last_day
		) a6
		on a1.asset = a6.asset
		left join (select ASSET,sum(Bonus) as Bonus from T_USER_BONUS where CLEARDATE=clear_date group by ASSET ) a7
		on a1.asset = a7.asset
		left join (select ASSET,sum(amount) as rebate_amount from T_USER_REBATE where CLEARDATE=clear_date group by ASSET) a8
		on a1.asset = a8.asset
		;
		OPEN curasset;
	a_loop:LOOP
			FETCH curasset INTO c_asset;
		IF	done THEN
			LEAVE a_loop;
		END IF;
		SET my_sql = concat( 'select sum(TOTAL_AMOUNT) into @param1 from T_USER_ASSET_', c_asset );
		SET @ms = my_sql;
		PREPARE s1 FROM @ms;
		EXECUTE s1;
		DEALLOCATE PREPARE s1;
		SET TOTALAMOUNT = @param1;
		update T_CLEARACCOUNTBALANCE set EndBalance=TOTALAMOUNT where asset =c_asset and ClearDate=clear_date;	
	END LOOP;
	CLOSE curasset;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for P_Super_Rebate
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_Super_Rebate`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `P_Super_Rebate`(clear_date VARCHAR ( 8 ) )
BEGIN#Routine body goes here...
	DECLARE
		accountid VARCHAR ( 24 ) DEFAULT '';
	DECLARE
		TOTALAMOUNT DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		btctradeamount DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		next_day VARCHAR ( 8 );
	DECLARE
		last_day VARCHAR ( 8 );
	DECLARE
		Today_day VARCHAR ( 10 );
/*标识事务错误*/
	DECLARE
		err INT DEFAULT 0;
/*达到一定数量就进行提交，计数器*/
	DECLARE
		counts INT DEFAULT 0;
/*标识是否回滚过*/
	DECLARE
		isrollback INT DEFAULT 0;
/*游标遍历时，作为判断是否遍历完全部记录的标记*/
	DECLARE
		done INTEGER DEFAULT 0;
/*获取临时表该任务的数据*/
DELETE from T_USER_REBATE where CLEARDATE=clear_date;

SELECT
		date_format(
				str_to_date( clear_date, '%Y%m%d' ),'%Y-%m-%d' 
		) INTO Today_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL - 1 DAY 
			),
			'%Y%m%d' 
		) INTO next_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL  0 DAY 
			),
			'%Y%m%d' 
		) INTO last_day;
			insert into T_USER_REBATE(
	ASSET,ACCOUNT,AMOUNT,BASE_AMOUNT,DESCRIPTION,UPDATE_TIME,CLEARDATE
	)
	select 'BTC' as ASSET,
	d1.account, coalesce(
	case when d2.total_amount<100000 then 
	TOTAL_FEE*0.2 
	else
	TOTAL_FEE*0.4 
	end,0) as rebate_amount
	,COALESCE(TOTAL_FEE,0) as base_amount
	,'super rebate' as DESCRIPTION
	,CURRENT_TIMESTAMP as UPDATE_TIME
	,clear_date
from (
	SELECT
		c1.USER_INVITED_NAME as account,
		sum(c2.trade_fee_in_BTC) as total_fee 
	FROM
		T_REFERRAL c1
		LEFT JOIN (
		SELECT
			ACCOUNT,
			sum( COALESCE ( trade_amount * TO_BTC_PRICE, 0 ) ) AS trade_fee_in_BTC
		FROM
			(
			SELECT
				a1.BUYSIDE_ACCOUNT AS ACCOUNT,
				substr( a1.ASSET_PAIR, 1, 3 ) AS asset,
				sum( a1.BUYSIDE_FEE ) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date 
				AND a1.INSERT_TIME < next_day
			GROUP BY
				a1.BUYSIDE_ACCOUNT,
				substr( ASSET_PAIR, 1, 3 ) UNION
			SELECT
				a1.SELLSIDE_ACCOUNT AS ACCOUNT,
				substr( a1.ASSET_PAIR, 5, 3 ) AS asset,
				sum( a1.SELLSIDE_FEE ) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date
				AND a1.INSERT_TIME < next_day
			GROUP BY
				a1.SELLSIDE_ACCOUNT,
				substr( ASSET_PAIR, 5, 3 ) 
			) b1
			LEFT JOIN T_ASSET_TO_BTC_PRICE b2 ON b1.asset = b2.ASSET 
			AND b2.date = Today_day
		GROUP BY
			ACCOUNT 
		) c2 ON c1.USER_NAME = c2.ACCOUNT
		group by c1.USER_INVITED_NAME) d1
		left join T_USER_ASSET_BTC d2
		on d1.account=d2.account
		where d1.ACCOUNT is not null	
		;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for P_User_bonus
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_User_bonus`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `P_User_bonus`(clear_date VARCHAR ( 8 ) )
BEGIN#Routine body goes here...
	DECLARE
		accountid VARCHAR ( 24 ) DEFAULT '';
	DECLARE
		TOTAL_FEE DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		btctradeamount DECIMAL ( 32, 8 ) DEFAULT 0;
	DECLARE
		next_day VARCHAR ( 8 );
	DECLARE
		last_day VARCHAR ( 8 );
	DECLARE
		Today_day VARCHAR ( 10 );
/*标识事务错误*/
	DECLARE
		err INT DEFAULT 0;
/*达到一定数量就进行提交，计数器*/
	DECLARE
		counts INT DEFAULT 0;
/*标识是否回滚过*/
	DECLARE
		isrollback INT DEFAULT 0;
/*游标遍历时，作为判断是否遍历完全部记录的标记*/
	DECLARE
		done INTEGER DEFAULT 0;
/*获取临时表该任务的数据*/
DELETE from T_USER_BONUS where CLEARDATE=clear_date;

SELECT
		date_format(
				str_to_date( clear_date, '%Y%m%d' ),'%Y-%m-%d' 
		) INTO Today_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL - 1 DAY 
			),
			'%Y%m%d' 
		) INTO next_day;
	SELECT
		date_format(
			date_sub(
				str_to_date( clear_date, '%Y%m%d' ),
				INTERVAL  0 DAY 
			),
			'%Y%m%d' 
		) INTO last_day;
	
	SELECT
		sum(c1.trade_fee_in_BTC) into TOTAL_FEE 
	FROM (
		SELECT
			sum( COALESCE ( trade_fee * TO_BTC_PRICE, 0 ) ) AS trade_fee_in_BTC
		FROM
			(
			SELECT
				substr( a1.ASSET_PAIR, 1, 3 ) AS asset,
				sum( a1.BUYSIDE_FEE ) AS trade_fee 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date
				AND a1.INSERT_TIME < next_day
			GROUP BY
				substr( ASSET_PAIR, 1, 3 ) UNION
			SELECT
				substr( a1.ASSET_PAIR, 5, 3 ) AS asset,
				sum( a1.SELLSIDE_FEE ) AS trade_fee
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date 
				AND a1.INSERT_TIME < next_day
			GROUP BY
				substr( ASSET_PAIR, 5, 3 ) 
			) b1
			LEFT JOIN T_ASSET_TO_BTC_PRICE b2 ON b1.asset = b2.ASSET 
			AND b2.date = Today_day ) c1;
	
	
	SELECT
		sum(c1.trade_amount_in_BTC) into btctradeamount 
		
	FROM (
		SELECT
			sum( COALESCE ( trade_amount * TO_BTC_PRICE, 0 ) ) AS trade_amount_in_BTC 
		FROM
			(
			SELECT
				substr( a1.ASSET_PAIR, 1, 3 ) AS asset,
				sum( a1.AMOUNT * PRICE ) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date
				AND a1.INSERT_TIME < next_day
			GROUP BY
				substr( ASSET_PAIR, 1, 3 ) UNION
			SELECT
				substr( a1.ASSET_PAIR, 5, 3 ) AS asset,
				sum( a1.AMOUNT ) AS trade_amount
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date 
				AND a1.INSERT_TIME < next_day
			GROUP BY
				substr( ASSET_PAIR, 5, 3 ) 
			) b1
			LEFT JOIN T_ASSET_TO_BTC_PRICE b2 ON b1.asset = b2.ASSET 
			AND b2.date = Today_day ) c1;
	
	insert into T_USER_BONUS(
	ASSET,ACCOUNT,BONUS,TRADE_AMOUNT,UPDATE_TIME,CLEARDATE
	)
	select 'BTC' as ASSET,
	d1.account,
	0.5*TOTAL_FEE*trade_amount_in_BTC/btctradeamount  as rebate_amount,
	COALESCE(trade_amount_in_BTC,0) as TRADE_AMOUNT,
	CURRENT_TIMESTAMP as UPDATE_TIME,
	clear_date
	FROM  (
		SELECT
			ACCOUNT,
			sum( COALESCE ( trade_amount * TO_BTC_PRICE, 0 ) ) AS trade_amount_in_BTC
		FROM
			(
			SELECT
				a1.BUYSIDE_ACCOUNT AS ACCOUNT,
				substr( a1.ASSET_PAIR, 1, 3 ) AS asset,
				sum(  a1.AMOUNT * PRICE) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >= clear_date
				AND a1.INSERT_TIME <  next_day
			GROUP BY
				a1.BUYSIDE_ACCOUNT,
				substr( ASSET_PAIR, 1, 3 ) UNION
			SELECT
				a1.SELLSIDE_ACCOUNT AS ACCOUNT,
				substr( a1.ASSET_PAIR, 5, 3 ) AS asset,
				sum( a1.AMOUNT) AS trade_amount 
			FROM
				T_MATCH_FLOW a1 
			WHERE
				a1.INSERT_TIME >=  clear_date
				AND a1.INSERT_TIME < next_day
			GROUP BY
				a1.SELLSIDE_ACCOUNT,
				substr( ASSET_PAIR, 5, 3 ) 
			) b1
			LEFT JOIN T_ASSET_TO_BTC_PRICE b2 ON b1.asset = b2.ASSET 
			AND b2.date = Today_day
		GROUP BY
			ACCOUNT 
		) d1 
		where d1.ACCOUNT is not null	
		;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for E_AccountLevelUpdate
-- ----------------------------
DROP EVENT IF EXISTS `E_AccountLevelUpdate`;
delimiter ;;
CREATE DEFINER = `root`@`%` EVENT `E_AccountLevelUpdate`
ON SCHEDULE
EVERY '1' DAY STARTS '2018-11-24 00:00:00'
DO call  P_AccountLevelUpdate(DATE_FORMAT(adddate(sysdate(),-1),'%Y%m%d'))
;;
delimiter ;

-- ----------------------------
-- Event structure for E_ClearAccount
-- ----------------------------
DROP EVENT IF EXISTS `E_ClearAccount`;
delimiter ;;
CREATE DEFINER = `root`@`%` EVENT `E_ClearAccount`
ON SCHEDULE
EVERY '1' DAY STARTS '2018-11-04 00:15:00'
ON COMPLETION PRESERVE
DO call  P_clearAccount(DATE_FORMAT(adddate(sysdate(),-1),'%Y%m%d'))
;;
delimiter ;

-- ----------------------------
-- Event structure for E_Super_Rebate
-- ----------------------------
DROP EVENT IF EXISTS `E_Super_Rebate`;
delimiter ;;
CREATE DEFINER = `root`@`%` EVENT `E_Super_Rebate`
ON SCHEDULE
EVERY '1' DAY STARTS '2018-11-24 00:03:00'
DO call  P_Super_Rebate(DATE_FORMAT(adddate(sysdate(),-1),'%Y%m%d'))
;;
delimiter ;

-- ----------------------------
-- Event structure for E_User_bonus
-- ----------------------------
DROP EVENT IF EXISTS `E_User_bonus`;
delimiter ;;
CREATE DEFINER = `root`@`%` EVENT `E_User_bonus`
ON SCHEDULE
EVERY '1' DAY STARTS '2018-11-24 00:05:00'
DO call  P_User_bonus(DATE_FORMAT(adddate(sysdate(),-1),'%Y%m%d'))
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
