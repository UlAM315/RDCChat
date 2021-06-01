/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.15 : Database - rdcchat
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rdcchat` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rdcchat`;

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `momentId` int(11) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `reviewerId` int(11) DEFAULT NULL,
  KEY `c_m` (`momentId`),
  KEY `c_u` (`reviewerId`),
  CONSTRAINT `c_u` FOREIGN KEY (`reviewerId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `c_m` FOREIGN KEY (`momentId`) REFERENCES `moment` (`momentId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comments` */

/*Table structure for table `friend` */

DROP TABLE IF EXISTS `friend`;

CREATE TABLE `friend` (
  `myId` int(11) NOT NULL DEFAULT '0',
  `friendId` int(11) NOT NULL DEFAULT '0',
  `nickname` varchar(30) DEFAULT NULL,
  `ifBlacklist` char(1) DEFAULT 'N',
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`myId`,`friendId`),
  KEY `f_u2` (`friendId`),
  CONSTRAINT `f_u1` FOREIGN KEY (`myId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `f_u2` FOREIGN KEY (`friendId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `friend` */

insert  into `friend`(`myId`,`friendId`,`nickname`,`ifBlacklist`,`content`) values (18,19,'她是2号，我是1号','N','我们已经是好友啦！'),(19,18,'她是1号，我是2号','N','你好呀！');

/*Table structure for table `groupnoword` */

DROP TABLE IF EXISTS `groupnoword`;

CREATE TABLE `groupnoword` (
  `groupId` int(11) NOT NULL DEFAULT '0',
  `userId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`groupId`,`userId`),
  KEY `gnw_u` (`userId`),
  CONSTRAINT `gnw_u` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `groupnoword` */

/*Table structure for table `groups` */

DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(30) NOT NULL,
  `ownerId` int(11) NOT NULL,
  `avatarImg` varchar(200) NOT NULL,
  PRIMARY KEY (`groupId`),
  KEY `g_u1` (`ownerId`),
  CONSTRAINT `g_u1` FOREIGN KEY (`ownerId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `groups` */

/*Table structure for table `manager` */

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `managerId` int(11) NOT NULL AUTO_INCREMENT,
  `managerName` varchar(30) NOT NULL,
  `managerPassword` varchar(100) NOT NULL,
  PRIMARY KEY (`managerId`),
  UNIQUE KEY `managerName` (`managerName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `manager` */

insert  into `manager`(`managerId`,`managerName`,`managerPassword`) values (1,'管理员1','123456');

/*Table structure for table `moment` */

DROP TABLE IF EXISTS `moment`;

CREATE TABLE `moment` (
  `momentId` int(11) NOT NULL AUTO_INCREMENT,
  `issueTime` varchar(30) DEFAULT NULL,
  `firstImg` varchar(200) DEFAULT NULL,
  `secondImg` varchar(200) DEFAULT NULL,
  `thirdImg` varchar(200) DEFAULT NULL,
  `likeNumber` int(11) DEFAULT '0',
  `ifOpen` char(1) DEFAULT 'Y',
  `content` varchar(200) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`momentId`),
  KEY `m_u` (`userId`),
  CONSTRAINT `m_u` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `moment` */

insert  into `moment`(`momentId`,`issueTime`,`firstImg`,`secondImg`,`thirdImg`,`likeNumber`,`ifOpen`,`content`,`userId`) values (12,'2021-06-01','img/2e090712-f2b0-488c-9b6d-bd59e6c09445.jpg','img/fc5663ca-67be-4195-82df-6f51bd8c779a.jpg','img/1f8e1127-28b0-4eef-b0b7-c1d8e8d0ae76.jpg',0,'Y','今天很开心喔！',18);

/*Table structure for table `momentlike` */

DROP TABLE IF EXISTS `momentlike`;

CREATE TABLE `momentlike` (
  `momentId` int(11) NOT NULL DEFAULT '0',
  `userId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`momentId`,`userId`),
  KEY `ml_u` (`userId`),
  CONSTRAINT `ml_m` FOREIGN KEY (`momentId`) REFERENCES `moment` (`momentId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ml_u` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `momentlike` */

/*Table structure for table `mygroups` */

DROP TABLE IF EXISTS `mygroups`;

CREATE TABLE `mygroups` (
  `myId` int(11) NOT NULL DEFAULT '0',
  `groupId` int(11) NOT NULL DEFAULT '0',
  `myGroupName` varchar(30) DEFAULT NULL,
  `myContent` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`myId`,`groupId`),
  KEY `mg_u2` (`groupId`),
  CONSTRAINT `mg_u1` FOREIGN KEY (`myId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mygroups` */

/*Table structure for table `reporting` */

DROP TABLE IF EXISTS `reporting`;

CREATE TABLE `reporting` (
  `accuserId` int(11) DEFAULT NULL,
  `accusedId` int(11) DEFAULT NULL,
  `message` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `reporting` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL,
  `userPassword` varchar(100) NOT NULL,
  `idNumber` varchar(100) NOT NULL,
  `gender` char(1) DEFAULT '男',
  `telephone` varchar(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `avatarImg` varchar(200) DEFAULT NULL,
  `backgroundImg` varchar(200) DEFAULT NULL,
  `ifBlock` char(1) DEFAULT 'N',
  `activeStatus` char(1) DEFAULT NULL,
  `activeCode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName` (`userName`),
  UNIQUE KEY `idNumber` (`idNumber`),
  UNIQUE KEY `telephone` (`telephone`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`userId`,`userName`,`userPassword`,`idNumber`,`gender`,`telephone`,`email`,`avatarImg`,`backgroundImg`,`ifBlock`,`activeStatus`,`activeCode`) values (18,'lxl1','96e79218965eb72c92a549dd5a330112','1','女','13265359580','2122048586@qq.com','img/94c4d256-16e5-4901-a7ce-3b514c38bd60.jpg','img/bd47c4a8-1f6f-4100-a07a-4f6a70edae50.jpg','N','Y','97a363084f37456a9bc0d377ada48cb3'),(19,'lxl2','96e79218965eb72c92a549dd5a330112','2','女','13265359581','2826516861@qq.com','img/38e88ef1-4a24-4f67-baf5-5f01d9401fdb.jpg','img/4315fa2c-bc0d-431f-a251-c78d94275dc3.jpg','N','Y','d41a61c13a2a4d71b9e68af887ac3a36');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
