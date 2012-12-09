-- MySQL dump 10.13  Distrib 5.5.28, for Win32 (x86)
--
-- Host: localhost    Database: rate3
-- ------------------------------------------------------
-- Server version	5.5.28

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
-- Table structure for table `algorithm`
--

DROP TABLE IF EXISTS `algorithm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm` (
  `uuid` binary(16) NOT NULL,
  `user_uuid` binary(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` enum('FINGERVEIN') NOT NULL,
  `protocol` enum('FVC2006') NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` text NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm`
--

LOCK TABLES `algorithm` WRITE;
/*!40000 ALTER TABLE `algorithm` DISABLE KEYS */;
/*!40000 ALTER TABLE `algorithm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `algorithm_version`
--

DROP TABLE IF EXISTS `algorithm_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm_version` (
  `uuid` binary(16) NOT NULL,
  `algorithm_uuid` binary(16) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'this column is also used to identify different versions of an algorithm',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm_version`
--

LOCK TABLES `algorithm_version` WRITE;
/*!40000 ALTER TABLE `algorithm_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `algorithm_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benchmark`
--

DROP TABLE IF EXISTS `benchmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `benchmark` (
  `uuid` binary(16) NOT NULL,
  `view_uuid` binary(16) NOT NULL,
  `protocol` enum('FVC2006') NOT NULL,
  `name` varchar(45) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` text NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benchmark`
--

LOCK TABLES `benchmark` WRITE;
/*!40000 ALTER TABLE `benchmark` DISABLE KEYS */;
/*!40000 ALTER TABLE `benchmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `uuid` binary(16) NOT NULL,
  `person_uuid` binary(16) DEFAULT NULL,
  `type` enum('FINGERVEIN') NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `import_tag` varchar(45) NOT NULL COMMENT 'With each import, you must provide a import_tag. It is used for rollback functions.',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_type`
--

DROP TABLE IF EXISTS `device_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_type` (
  `uuid` binary(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` enum('FINGERVEIN') NOT NULL,
  `provider` varchar(45) DEFAULT NULL COMMENT 'for example PKU, YANNAN',
  `version` varchar(45) DEFAULT NULL COMMENT 'for example V3, V5, any text that could distinguish different versions',
  `extra` blob,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_type`
--

LOCK TABLES `device_type` WRITE;
/*!40000 ALTER TABLE `device_type` DISABLE KEYS */;
INSERT INTO `device_type` VALUES ('PÅÈA◊‚ÜÌ\0ˇÒÏπ\\','PKU_FINGERVEIN_V3_DEVICE','FINGERVEIN','Peking University','V3',NULL);
/*!40000 ALTER TABLE `device_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `uuid` binary(16) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `gender` enum('MALE','FEMALE','UNKNOWN') NOT NULL DEFAULT 'UNKNOWN',
  `birth` date DEFAULT NULL,
  `extra` blob,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sample`
--

DROP TABLE IF EXISTS `sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample` (
  `uuid` binary(16) NOT NULL,
  `class_uuid` binary(16) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `file` varchar(256) NOT NULL COMMENT 'this should be a path with UNIX seperator \\''/\\'' to avoid strange behaviors',
  `device_type` binary(16) DEFAULT NULL,
  `import_tag` varchar(45) NOT NULL COMMENT 'With each import, you must provide a import_tag. It is used for rollback functions.',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sample`
--

LOCK TABLES `sample` WRITE;
/*!40000 ALTER TABLE `sample` DISABLE KEYS */;
/*!40000 ALTER TABLE `sample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `uuid` binary(16) NOT NULL,
  `algorithm_version_uuid` binary(16) DEFAULT NULL,
  `benchmark_uuid` binary(16) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `finished` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uuid` binary(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` char(32) NOT NULL COMMENT 'should be a md5 of the actual password',
  `registered` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(128) NOT NULL COMMENT 'Enforce the user to provide an email address and must be unique.',
  `organization` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view` (
  `uuid` binary(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `import_tag` varchar(45) NOT NULL COMMENT 'With each import, you must provide a import_tag. It is used for rollback functions.',
  `type` enum('FINGERVEIN') NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `import_tag_UNIQUE` (`import_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view_sample`
--

DROP TABLE IF EXISTS `view_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view_sample` (
  `view_uuid` binary(16) NOT NULL,
  `sample_uuid` binary(16) NOT NULL,
  PRIMARY KEY (`view_uuid`,`sample_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view_sample`
--

LOCK TABLES `view_sample` WRITE;
/*!40000 ALTER TABLE `view_sample` DISABLE KEYS */;
/*!40000 ALTER TABLE `view_sample` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-09 16:09:53
