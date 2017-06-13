-- MySQL dump 10.13  Distrib 5.5.41, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: ceng443
-- ------------------------------------------------------
-- Server version	5.5.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `ceng443`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ceng443` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ceng443`;

--
-- Table structure for table `advertisements`
--

DROP TABLE IF EXISTS `advertisements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisements` (
  `cAdvertisement` varchar(512) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisements`
--

LOCK TABLES `advertisements` WRITE;
/*!40000 ALTER TABLE `advertisements` DISABLE KEYS */;
INSERT INTO `advertisements` VALUES ('Electronics/Arduino.html'),('Electronics/Vaio.html'),('Sports/Adidas.html'),('Sports/Nike.html');
/*!40000 ALTER TABLE `advertisements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hitcounts`
--

DROP TABLE IF EXISTS `hitcounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hitcounts` (
  `cPage` varchar(512) NOT NULL,
  `cCount` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hitcounts`
--

LOCK TABLES `hitcounts` WRITE;
/*!40000 ALTER TABLE `hitcounts` DISABLE KEYS */;
INSERT INTO `hitcounts` VALUES ('Ceng443.html',0),('About.html',0);
/*!40000 ALTER TABLE `hitcounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mimetypes`
--

DROP TABLE IF EXISTS `mimetypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mimetypes` (
  `cFileExtension` varchar(32) NOT NULL,
  `cMimeType` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mimetypes`
--

LOCK TABLES `mimetypes` WRITE;
/*!40000 ALTER TABLE `mimetypes` DISABLE KEYS */;
INSERT INTO `mimetypes` VALUES ('.bmp','image/bmp'),('.xls','application/excel'),('.bin','application/octet-stream'),('.ppt','application/mspowerpoint'),('.mp3','audio/mpeg3'),('.txt','text/plain'),('.doc','application/msword'),('.tiff','image/tiff'),('.html','text/html'),('.gz','application/x-gzip'),('.tar','application/x-tar'),('.cpp','text/x-c'),('.htm','text/html'),('.jpg','image/jpeg'),('.text','text/plain'),('.zip','application/zip'),('.wav','audio/wav'),('.java','text/plain'),('.jpeg','image/jpeg'),('.class','application/java'),('.tex','application/x-tex'),('.tgz','application/x-compressed'),('.avi','video/avi'),('.gzip','application/x-gzip'),('.ico','image/x-icon'),('.exe','application/octet-stream'),('.c','text/plain'),('.tif','image/tiff'),('.js','text/javascript'),('.h','text/plain'),('.latex','application/x-latex'),('.xml','text/xml');
/*!40000 ALTER TABLE `mimetypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statusreasons`
--

DROP TABLE IF EXISTS `statusreasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statusreasons` (
  `cStatusCode` smallint(6) NOT NULL,
  `cReason` varchar(64) NOT NULL,
  PRIMARY KEY (`cStatusCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statusreasons`
--

LOCK TABLES `statusreasons` WRITE;
/*!40000 ALTER TABLE `statusreasons` DISABLE KEYS */;
INSERT INTO `statusreasons` VALUES (200,'OK'),(400,'Bad Request'),(403,'Forbidden'),(404,'Not Found'),(500,'Internal Server Error'),(501,'Not Implemented');
/*!40000 ALTER TABLE `statusreasons` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-06  3:27:56
