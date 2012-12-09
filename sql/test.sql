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
  `generator` varchar(45) NOT NULL,
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
INSERT INTO `class` VALUES ('>E��]J;���Z�q�','�fT�|�-��>��V','FINGERVEIN','2010-03-25 02:02:47','20121209test'),('`�{wOɚ��q���','W\Z(��ߧ0����.T','FINGERVEIN','2010-09-14 02:19:30','20121209test'),('S]�LM��)#&�z8','W\Z(��ߧ0����.T','FINGERVEIN','2010-09-14 02:20:05','20121209test'),('\Z�G��D���7}�+b','�e�ߧ0����.T','FINGERVEIN','2010-09-13 00:36:32','20121209test'),('6��e�\ZB��?��﫣5','��9�|�-��>��V','FINGERVEIN','2010-03-19 09:58:57','20121209test'),('?C�6jfJM��4�`��','�fT�|�-��>��V','FINGERVEIN','2010-03-25 02:01:34','20121209test'),('C��0%A��p�눤�','W\Z(��ߧ0����.T','FINGERVEIN','2010-09-14 02:19:12','20121209test'),('NX�B�M}�ԧ�e@','�c�\\|�-��>��V','FINGERVEIN','2009-09-24 02:55:06','20121209test'),('WW�~1KEĪ��=�','�c�\\|�-��>��V','FINGERVEIN','2009-09-24 02:54:19','20121209test'),('W~H]�H=�����3','��9�|�-��>��V','FINGERVEIN','2010-03-19 09:57:12','20121209test'),('_�����@p�N��P#�(','�tD|�-��>��V','FINGERVEIN','2009-11-09 05:29:04','20121209test'),('��ך�6K��v��#�1�','�\'�1n/�3�@�m','FINGERVEIN','2011-09-21 22:44:10','20121209test'),('����B@o��a�3','��9�|�-��>��V','FINGERVEIN','2010-03-19 09:59:56','20121209test'),('�K�06}Jl�M*�$ِ{','�UK|�-��>��V','FINGERVEIN','2009-03-12 06:53:37','20121209test'),('��l�G�@]����ʝ9�','W\Z(��ߧ0����.T','FINGERVEIN','2010-09-14 02:19:49','20121209test'),('ȝ��P�K��:iù�','�c�\\|�-��>��V','FINGERVEIN','2009-09-24 02:55:30','20121209test'),('���B	�f��P','�a�|�-��>��V','FINGERVEIN','2009-03-11 10:02:40','20121209test'),('��*��M_���I�d[H','�e�ߧ0����.T','FINGERVEIN','2010-09-13 00:37:20','20121209test'),('�\'�UJ��~1��+�\\','�fT�|�-��>��V','FINGERVEIN','2010-03-25 02:00:55','20121209test'),('�p�\0��A �O�d\'�v�','�tD|�-��>��V','FINGERVEIN','2009-11-09 05:28:05','20121209test');
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
INSERT INTO `device_type` VALUES ('P��A���\0���\\','PKU_FINGERVEIN_V3_DEVICE','FINGERVEIN','Peking University','V3',NULL);
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
INSERT INTO `person` VALUES ('�e�ߧ0����.T','','MALE',NULL,NULL),('W\Z(��ߧ0����.T','','MALE',NULL,NULL),('�UK|�-��>��V','','FEMALE',NULL,NULL),('�a�|�-��>��V','','MALE',NULL,NULL),('�c�\\|�-��>��V','','FEMALE',NULL,NULL),('�fT�|�-��>��V','','FEMALE',NULL,NULL),('�tD|�-��>��V','','FEMALE',NULL,NULL),('��9�|�-��>��V','','FEMALE',NULL,NULL),('�\'�1n/�3�@�m','','FEMALE',NULL,NULL);
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
INSERT INTO `sample` VALUES ('��;X�@��f#�','WW�~1KEĪ��=�','2010-05-12 09:34:22','bioverify/00811119_V1/2010-05-12_17-34-22.bmp','P��A���\0���\\','20121209test'),('�# %I8�F�Y�o��','�\'�UJ��~1��+�\\','2011-04-08 00:05:48','bioverify/00825190_V1/2011-04-08_08-05-48.bmp','P��A���\0���\\','20121209test'),('0�L��8�K�AL�','C��0%A��p�눤�','2010-09-14 10:12:00','bioverify/1000016909_V1/2010-09-14_18-12-00.bmp','P��A���\0���\\','20121209test'),('��hN�R�$Wp�','�K�06}Jl�M*�$ِ{','2010-04-09 10:45:58','bioverify/00721024_V3/2010-04-09_18-45-58.bmp','P��A���\0���\\','20121209test'),('\n)#\\��D\Z�Ȭ]��','6��e�\ZB��?��﫣5','2010-03-24 09:39:56','bioverify/90908135_V3/2010-03-24_17-39-56.bmp','P��A���\0���\\','20121209test'),('\nI�4�F=�uey�{%','6��e�\ZB��?��﫣5','2010-03-21 02:37:45','bioverify/90908135_V3/2010-03-21_10-37-45.bmp','P��A���\0���\\','20121209test'),('\nmEpI��naБ)6','�\'�UJ��~1��+�\\','2011-03-13 23:50:25','bioverify/00825190_V1/2011-03-14_07-50-25.bmp','P��A���\0���\\','20121209test'),('\Z��o�O��HHׯ��','`�{wOɚ��q���','2012-03-05 10:23:02','bioverify/1000016909_V2/2012-03-05_18-23-02.bmp','P��A���\0���\\','20121209test'),('�N	Z�C��z��L(','C��0%A��p�눤�','2010-11-20 00:50:03','bioverify/1000016909_V1/2010-11-20_08-50-03.bmp','P��A���\0���\\','20121209test'),('=�<ȁF��>_�@���','`�{wOɚ��q���','2012-03-13 10:14:47','bioverify/1000016909_V2/2012-03-13_18-14-47.bmp','P��A���\0���\\','20121209test'),('ʞE��F܈),3��s','`�{wOɚ��q���','2012-03-11 00:05:58','bioverify/1000016909_V2/2012-03-11_08-05-58.bmp','P��A���\0���\\','20121209test'),('��A�CN����K|','���B	�f��P','2011-03-12 07:23:43','bioverify/00804709_V4/2011-03-12_15-23-43.bmp','P��A���\0���\\','20121209test'),('贜~I{�ŵx�¤�','�K�06}Jl�M*�$ِ{','2010-04-09 10:06:41','bioverify/00721024_V3/2010-04-09_18-06-41.bmp','P��A���\0���\\','20121209test'),('�]}��I�������','�K�06}Jl�M*�$ِ{','2010-03-27 23:55:47','bioverify/00721024_V3/2010-03-28_07-55-47.bmp','P��A���\0���\\','20121209test'),('�NsAŒ�;���','���B	�f��P','2011-03-18 23:48:49','bioverify/00804709_V4/2011-03-19_07-48-49.bmp','P��A���\0���\\','20121209test'),('+����G��J��Ț�','WW�~1KEĪ��=�','2010-05-27 09:06:02','bioverify/00811119_V1/2010-05-27_17-06-02.bmp','P��A���\0���\\','20121209test'),('�iX�@����z��z','��ך�6K��v��#�1�','2011-10-18 23:33:12','bioverify/1110303124_V3/2011-10-19_07-33-12.bmp','P��A���\0���\\','20121209test'),('��nI��rD��o�','��l�G�@]����ʝ9�','2010-09-19 23:24:07','bioverify/1000016909_V3/2010-09-20_07-24-07.bmp','P��A���\0���\\','20121209test'),('�ǐmC �)s�ۆl(','?C�6jfJM��4�`��','2011-04-05 01:35:35','bioverify/00825190_V3/2011-04-05_09-35-35.bmp','P��A���\0���\\','20121209test'),('\Z��|�@�zP}*�X�','��l�G�@]����ʝ9�','2010-09-23 10:15:25','bioverify/1000016909_V3/2010-09-23_18-15-25.bmp','P��A���\0���\\','20121209test'),('E�82A\n�����z','6��e�\ZB��?��﫣5','2010-04-01 23:45:58','bioverify/90908135_V3/2010-04-02_07-45-58.bmp','P��A���\0���\\','20121209test'),('>Έz�C��\Z�ewF�','����B@o��a�3','2010-03-29 12:53:58','bioverify/90908135_V4/2010-03-29_20-53-59.bmp','P��A���\0���\\','20121209test'),('W,C��C��ሷ���','_�����@p�N��P#�(','2010-04-21 06:07:18','bioverify/00924002_V4/2010-04-21_14-07-18.bmp','P��A���\0���\\','20121209test'),('���*Ģ�xi�Д�','��ך�6K��v��#�1�','2011-10-11 23:31:22','bioverify/1110303124_V3/2011-10-12_07-31-22.bmp','P��A���\0���\\','20121209test'),('�b�?Bд����\rKe','�\'�UJ��~1��+�\\','2011-04-13 12:42:15','bioverify/00825190_V1/2011-04-13_20-42-15.bmp','P��A���\0���\\','20121209test'),(' h.��rC��@�E�','W~H]�H=�����3','2010-03-26 10:19:29','bioverify/90908135_V2/2010-03-26_18-19-29.bmp','P��A���\0���\\','20121209test'),('!v�NyD���U�[��','_�����@p�N��P#�(','2010-11-23 12:54:54','bioverify/00924002_V4/2010-11-23_20-54-54.bmp','P��A���\0���\\','20121209test'),('!�ۤ�K���y�p-�)','\Z�G��D���7}�+b','2010-09-20 09:31:26','bioverify/1000016275_V1/2010-09-20_17-31-26.bmp','P��A���\0���\\','20121209test'),('\"F%��aL\n���t\\�x}','W~H]�H=�����3','2010-03-31 10:43:06','bioverify/90908135_V2//2010-03-31_18-43-06.bmp','P��A���\0���\\','20121209test'),('\"r�\Z�I+�ڬ�b���','WW�~1KEĪ��=�','2010-05-30 09:39:51','bioverify/00811119_V1/2010-05-30_17-39-52.bmp','P��A���\0���\\','20121209test'),('#�[�s$M��2���','NX�B�M}�ԧ�e@','2010-05-09 09:52:17','bioverify/00811119_V3/2010-05-09_17-52-17.bmp','P��A���\0���\\','20121209test'),('&Du2�@d�Fs6z��','�K�06}Jl�M*�$ِ{','2010-04-06 12:07:06','bioverify/00721024_V3/2010-04-06_20-07-06.bmp','P��A���\0���\\','20121209test'),('&��C\\�L\'�Q+C��7','���B	�f��P','2011-03-06 04:53:26','bioverify/00804709_V4/2011-03-06_12-53-26.bmp','P��A���\0���\\','20121209test'),('(-���N���ϫ�X=6','\Z�G��D���7}�+b','2011-03-03 23:45:28','bioverify/1000016275_V1/2011-03-04_07-45-28.bmp','P��A���\0���\\','20121209test'),('(���_O��1�`W�^','ȝ��P�K��:iù�','2010-05-25 09:17:59','bioverify/00811119_V4/2010-05-25_17-17-59.bmp','P��A���\0���\\','20121209test'),(')FrMF@��;ʿ	N�','��ך�6K��v��#�1�','2011-10-11 10:24:45','bioverify/1110303124_V3/2011-10-11_18-24-45.bmp','P��A���\0���\\','20121209test'),(')�M��C������{','��ך�6K��v��#�1�','2011-09-27 23:42:01','bioverify/1110303124_V3/2011-09-28_07-42-01.bmp','P��A���\0���\\','20121209test'),('+8��בF�����W	O','����B@o��a�3','2010-03-27 13:13:52','bioverify/90908135_V4/2010-03-27_21-13-52.bmp','P��A���\0���\\','20121209test'),('+�9�Bɪu��d�k','W~H]�H=�����3','2010-03-26 10:19:39','bioverify/90908135_V2/2010-03-26_18-19-39.bmp','P��A���\0���\\','20121209test'),(',�B���Dδ��a~+�','�\'�UJ��~1��+�\\','2011-04-20 00:11:49','bioverify/00825190_V1/2011-04-20_08-11-49.bmp','P��A���\0���\\','20121209test'),('.j�;�L���6�$��','C��0%A��p�눤�','2011-03-02 09:43:33','bioverify/1000016909_V1/2011-03-02_17-43-33.bmp','P��A���\0���\\','20121209test'),('/3�\n�N���<�\'��>','ȝ��P�K��:iù�','2010-05-06 23:48:08','bioverify/00811119_V4/2010-05-07_07-48-08.bmp','P��A���\0���\\','20121209test'),('/S8/�A�E��ަ!_','�K�06}Jl�M*�$ِ{','2010-04-08 09:15:53','bioverify/00721024_V3/2010-04-08_17-15-53.bmp','P��A���\0���\\','20121209test'),('2�UOIEO��7҅;�','����B@o��a�3','2010-03-28 00:47:22','bioverify/90908135_V4/2010-03-28_08-47-22.bmp','P��A���\0���\\','20121209test'),('4g+)��LX�����^','?C�6jfJM��4�`��','2011-03-12 11:47:43','bioverify/00825190_V3/2011-03-12_19-47-43.bmp','P��A���\0���\\','20121209test'),('6WnUj�B	�/4O��c','`�{wOɚ��q���','2012-03-12 10:13:52','bioverify/1000016909_V2/2012-03-12_18-13-52.bmp','P��A���\0���\\','20121209test'),('8S|P]�Lt�Dr��Xv�','�K�06}Jl�M*�$ِ{','2010-04-08 09:55:21','bioverify/00721024_V3/2010-04-08_17-55-21.bmp','P��A���\0���\\','20121209test'),('9\\�aE@Z�\nQ{)�f�','��ך�6K��v��#�1�','2011-10-11 08:57:25','bioverify/1110303124_V3/2011-10-11_16-57-25.bmp','P��A���\0���\\','20121209test'),(';���X�Ix�9GK����','C��0%A��p�눤�','2011-03-06 12:25:16','bioverify/1000016909_V1/2011-03-06_20-25-16.bmp','P��A���\0���\\','20121209test'),(';���nA~�}3Ru}�','>E��]J;���Z�q�','2011-04-24 09:11:13','bioverify/00825190_V4/2011-04-24_17-11-13.bmp','P��A���\0���\\','20121209test'),('>�-��RJӄK�})','NX�B�M}�ԧ�e@','2010-05-06 08:42:51','bioverify/00811119_V3/2010-05-06_16-42-51.bmp','P��A���\0���\\','20121209test'),('D�u��N���ky88�Q','>E��]J;���Z�q�','2011-03-26 11:22:09','bioverify/00825190_V4/2011-03-26_19-22-09.bmp','P��A���\0���\\','20121209test'),('D.#i�FI������/K�','�K�06}Jl�M*�$ِ{','2010-04-09 00:23:43','bioverify/00721024_V3/2010-04-09_08-23-43.bmp','P��A���\0���\\','20121209test'),('I��V[eM�Hڌ�a:|','6��e�\ZB��?��﫣5','2010-03-29 12:53:47','bioverify/90908135_V3/2010-03-29_20-53-47.bmp','P��A���\0���\\','20121209test'),('L@b��CF&�~��q�','��l�G�@]����ʝ9�','2010-09-30 09:06:46','bioverify/1000016909_V3/2010-09-30_17-06-46.bmp','P��A���\0���\\','20121209test'),('OF{݃�@��Za#��','WW�~1KEĪ��=�','2010-05-30 09:37:39','bioverify/00811119_V1/2010-05-30_17-37-39.bmp','P��A���\0���\\','20121209test'),('PS���L���0�x�','�K�06}Jl�M*�$ِ{','2010-04-03 23:57:19','bioverify/00721024_V3/2010-04-04_07-57-19.bmp','P��A���\0���\\','20121209test'),('Q����Eu�Q����','��ך�6K��v��#�1�','2011-09-29 23:49:49','bioverify/1110303124_V3/2011-09-30_07-49-49.bmp','P��A���\0���\\','20121209test'),('S\\��{I��[9T񱭉','?C�6jfJM��4�`��','2011-12-02 13:14:43','bioverify/00825190_V3/2011-12-02_21-14-43.bmp','P��A���\0���\\','20121209test'),('U�1N�G��L�W5�','���B	�f��P','2011-03-15 10:22:00','bioverify/00804709_V4/2011-03-15_18-22-00.bmp','P��A���\0���\\','20121209test'),('V:��*CM���<1�ha','�p�\0��A �O�d\'�v�','2010-03-31 12:41:51','bioverify/00924002_V3//2010-03-31_20-41-51.bmp','P��A���\0���\\','20121209test'),('V�6�i6H��}�}�DbQ','ȝ��P�K��:iù�','2010-05-09 09:54:09','bioverify/00811119_V4/2010-05-09_17-54-09.bmp','P��A���\0���\\','20121209test'),('YK.|�AI��R%��o','_�����@p�N��P#�(','2010-04-25 07:18:19','bioverify/00924002_V4/2010-04-25_15-18-20.bmp','P��A���\0���\\','20121209test'),('Y�N���F�jF=K','?C�6jfJM��4�`��','2011-04-13 11:30:05','bioverify/00825190_V3/2011-04-13_19-30-05.bmp','P��A���\0���\\','20121209test'),('[��[�\ZN���iqĩ��','`�{wOɚ��q���','2012-03-08 10:01:30','bioverify/1000016909_V2/2012-03-08_18-01-30.bmp','P��A���\0���\\','20121209test'),('\\��\"�GׂejG93��','W~H]�H=�����3','2010-03-28 11:38:47','bioverify/90908135_V2/2010-03-28_19-38-47.bmp','P��A���\0���\\','20121209test'),('\\��GiyL����gs��','�\'�UJ��~1��+�\\','2011-03-16 00:13:43','bioverify/00825190_V1/2011-03-16_08-13-43.bmp','P��A���\0���\\','20121209test'),(']�Ę;�M&�V�E�|��','���B	�f��P','2011-03-13 07:42:35','bioverify/00804709_V4/2011-03-13_15-42-35.bmp','P��A���\0���\\','20121209test'),('^2�%]�JZ����Ee8�','S]�LM��)#&�z8','2010-10-19 10:18:35','bioverify/1000016909_V4/2010-10-19_18-18-35.bmp','P��A���\0���\\','20121209test'),('^r�`�EJr���Q�','>E��]J;���Z�q�','2011-04-10 12:51:51','bioverify/00825190_V4/2011-04-10_20-51-51.bmp','P��A���\0���\\','20121209test'),('_X��wG�a�����','?C�6jfJM��4�`��','2011-04-10 10:50:31','bioverify/00825190_V3/2011-04-10_18-50-31.bmp','P��A���\0���\\','20121209test'),('a���E��$Q:��4','����B@o��a�3','2010-03-21 03:25:41','bioverify/90908135_V4/2010-03-21_11-25-41.bmp','P��A���\0���\\','20121209test'),('e��POr�����1fO','��l�G�@]����ʝ9�','2010-09-26 22:46:53','bioverify/1000016909_V3/2010-09-27_06-46-53.bmp','P��A���\0���\\','20121209test'),('fNM�P4I�,�;iAK�','���B	�f��P','2011-03-13 09:20:26','bioverify/00804709_V4/2011-03-13_17-20-26.bmp','P��A���\0���\\','20121209test'),('g�2-NJ��W���Ǉ','\Z�G��D���7}�+b','2010-09-20 08:51:36','bioverify/1000016275_V1/2010-09-20_16-51-36.bmp','P��A���\0���\\','20121209test'),('g���o�G���%���','WW�~1KEĪ��=�','2010-05-30 09:38:14','bioverify/00811119_V1/2010-05-30_17-38-14.bmp','P��A���\0���\\','20121209test'),('iUpP4�@v�K�q��','W~H]�H=�����3','2010-03-25 00:24:38','bioverify/90908135_V2/2010-03-25_08-24-39.bmp','P��A���\0���\\','20121209test'),('i_	:��Bѐ�3���','>E��]J;���Z�q�','2011-04-25 08:26:19','bioverify/00825190_V4/2011-04-25_16-26-19.bmp','P��A���\0���\\','20121209test'),('m��.-Hk�n9~,9','6��e�\ZB��?��﫣5','2010-03-19 10:00:14','bioverify/90908135_V3/2010-03-19_18-00-14.bmp','P��A���\0���\\','20121209test'),('o�SqCR�l�\']�o�','NX�B�M}�ԧ�e@','2010-05-10 10:44:43','bioverify/00811119_V3/2010-05-10_18-44-44.bmp','P��A���\0���\\','20121209test'),('p$�mT�M춆u���\'�','?C�6jfJM��4�`��','2011-11-01 00:23:09','bioverify/00825190_V3/2011-11-01_08-23-09.bmp','P��A���\0���\\','20121209test'),('s)j3G����L�','_�����@p�N��P#�(','2010-04-21 04:19:05','bioverify/00924002_V4/2010-04-21_12-19-05.bmp','P��A���\0���\\','20121209test'),('u�ِ�0J�0���d','>E��]J;���Z�q�','2011-04-22 00:11:58','bioverify/00825190_V4/2011-04-22_08-11-58.bmp','P��A���\0���\\','20121209test'),('u�@:I���\\��5`�','>E��]J;���Z�q�','2011-04-21 08:05:01','bioverify/00825190_V4/2011-04-21_16-05-01.bmp','P��A���\0���\\','20121209test'),('v�qi�J���hP��','NX�B�M}�ԧ�e@','2010-05-09 09:54:31','bioverify/00811119_V3/2010-05-09_17-54-31.bmp','P��A���\0���\\','20121209test'),('v.�%�I⡄z���','?C�6jfJM��4�`��','2011-04-13 12:42:06','bioverify/00825190_V3/2011-04-13_20-42-06.bmp','P��A���\0���\\','20121209test'),('yfC�Mݬ�3�l0Ng','�\'�UJ��~1��+�\\','2011-03-13 23:50:16','bioverify/00825190_V1/2011-03-14_07-50-16.bmp','P��A���\0���\\','20121209test'),('zVGEq>H�y��:\Zr�','WW�~1KEĪ��=�','2010-05-30 09:39:37','bioverify/00811119_V1/2010-05-30_17-39-37.bmp','P��A���\0���\\','20121209test'),('|�pt\'5DѴ��ߍG|�','�\'�UJ��~1��+�\\','2011-03-07 10:45:52','bioverify/00825190_V1/2011-03-07_18-45-52.bmp','P��A���\0���\\','20121209test'),('a\"� �@�Y��{\'��','>E��]J;���Z�q�','2011-04-26 10:28:22','bioverify/00825190_V4/2011-04-26_18-28-22.bmp','P��A���\0���\\','20121209test'),('�^���D��}�T���','�p�\0��A �O�d\'�v�','2010-03-19 08:40:50','bioverify/00924002_V3/2010-03-19_16-40-50.bmp','P��A���\0���\\','20121209test'),('�6n��K���c�\\','S]�LM��)#&�z8','2011-03-19 10:49:57','bioverify/1000016909_V4/2011-03-19_18-49-57.bmp','P��A���\0���\\','20121209test'),('�X�\Z`�M1������X�','��ך�6K��v��#�1�','2011-10-19 09:38:03','bioverify/1110303124_V3/2011-10-19_17-38-03.bmp','P��A���\0���\\','20121209test'),('���\\L�B�\'R��NS�','����B@o��a�3','2010-03-27 10:45:42','bioverify/90908135_V4/2010-03-27_18-45-42.bmp','P��A���\0���\\','20121209test'),('��.BXGU����5~�','���B	�f��P','2011-03-13 09:20:44','bioverify/00804709_V4/2011-03-13_17-20-44.bmp','P��A���\0���\\','20121209test'),('�d�i�F▼1\Z��v�','��*��M_���I�d[H','2010-09-19 10:11:09','bioverify/1000016275_V3/2010-09-19_18-11-09.bmp','P��A���\0���\\','20121209test'),('�l5�D�$��8ΐ','ȝ��P�K��:iù�','2010-05-07 09:35:05','bioverify/00811119_V4/2010-05-07_17-35-05.bmp','P��A���\0���\\','20121209test'),('���2�Cx�w�(�[[','�p�\0��A �O�d\'�v�','2010-03-29 09:45:14','bioverify/00924002_V3/2010-03-29_17-45-14.bmp','P��A���\0���\\','20121209test'),('������F͊��u�@g	','W~H]�H=�����3','2010-03-26 11:01:43','bioverify/90908135_V2/2010-03-26_19-01-43.bmp','P��A���\0���\\','20121209test'),('������D�P|8�{�','WW�~1KEĪ��=�','2010-05-30 09:40:49','bioverify/00811119_V1/2010-05-30_17-40-49.bmp','P��A���\0���\\','20121209test'),('��ꠉI�����!�s�','��*��M_���I�d[H','2010-09-21 08:05:20','bioverify/1000016275_V3/2010-09-21_16-05-20.bmp','P��A���\0���\\','20121209test'),('��e��uO�����U+�','��l�G�@]����ʝ9�','2010-09-24 12:34:55','bioverify/1000016909_V3/2010-09-24_20-34-55.bmp','P��A���\0���\\','20121209test'),('�B��^HЏ��ʤ','_�����@p�N��P#�(','2010-11-24 08:29:46','bioverify/00924002_V4/2010-11-24_16-29-46.bmp','P��A���\0���\\','20121209test'),('�+ӮrI��gF��','����B@o��a�3','2010-03-24 10:12:02','bioverify/90908135_V4/2010-03-24_18-12-03.bmp','P��A���\0���\\','20121209test'),('�{����I��\Z�XV��V','\Z�G��D���7}�+b','2010-09-27 08:45:57','bioverify/1000016275_V1/2010-09-27_16-45-57.bmp','P��A���\0���\\','20121209test'),('������@۾b����','NX�B�M}�ԧ�e@','2010-05-07 09:36:15','bioverify/00811119_V3/2010-05-07_17-36-15.bmp','P��A���\0���\\','20121209test'),('��c8HbB��6h��D','`�{wOɚ��q���','2012-03-09 00:38:22','bioverify/1000016909_V2/2012-03-09_08-38-22.bmp','P��A���\0���\\','20121209test'),('�5�8��E�â�P ','�K�06}Jl�M*�$ِ{','2010-04-10 09:08:04','bioverify/00721024_V3/2010-04-10_17-08-04.bmp','P��A���\0���\\','20121209test'),('�����F���OCj�?','S]�LM��)#&�z8','2011-03-25 11:58:23','bioverify/1000016909_V4/2011-03-25_19-58-23.bmp','P��A���\0���\\','20121209test'),('��0A\ndL��,b�]��','NX�B�M}�ԧ�e@','2010-05-10 10:44:20','bioverify/00811119_V3/2010-05-10_18-44-20.bmp','P��A���\0���\\','20121209test'),('�����MA\0����0@p','C��0%A��p�눤�','2011-03-07 10:23:51','bioverify/1000016909_V1/2011-03-07_18-23-51.bmp','P��A���\0���\\','20121209test'),('��[���Hխ䜖i���','ȝ��P�K��:iù�','2010-05-06 23:48:50','bioverify/00811119_V4/2010-05-07_07-48-50.bmp','P��A���\0���\\','20121209test'),('�4W\"`F<��*��_�','NX�B�M}�ԧ�e@','2010-05-06 08:43:55','bioverify/00811119_V3/2010-05-06_16-43-55.bmp','P��A���\0���\\','20121209test'),('�J�\Z��C����n/h','��l�G�@]����ʝ9�','2010-09-22 22:54:27','bioverify/1000016909_V3/2010-09-23_06-54-27.bmp','P��A���\0���\\','20121209test'),('��\"y��DV��=���{\Z','`�{wOɚ��q���','2011-05-05 23:00:16','bioverify/1000016909_V2/2011-05-06_07-00-16.bmp','P��A���\0���\\','20121209test'),('���&#L0�m���K','6��e�\ZB��?��﫣5','2010-03-29 10:43:20','bioverify/90908135_V3/2010-03-29_18-43-20.bmp','P��A���\0���\\','20121209test'),('���}�;K���b�','6��e�\ZB��?��﫣5','2010-03-23 12:05:17','bioverify/90908135_V3/2010-03-23_20-05-17.bmp','P��A���\0���\\','20121209test'),('��Y~{IN��45���Q','�p�\0��A �O�d\'�v�','2010-03-30 23:52:30','bioverify/00924002_V3//2010-03-31_07-52-30.bmp','P��A���\0���\\','20121209test'),('�3\Z�%LɆA~�q��l','\Z�G��D���7}�+b','2010-09-21 08:07:31','bioverify/1000016275_V1/2010-09-21_16-07-31.bmp','P��A���\0���\\','20121209test'),('�S�!� F������W','_�����@p�N��P#�(','2010-11-03 08:09:48','bioverify/00924002_V4/2010-11-03_16-09-48.bmp','P��A���\0���\\','20121209test'),('�K�	D�J˥o�!&��','NX�B�M}�ԧ�e@','2010-05-10 10:44:22','bioverify/00811119_V3/2010-05-10_18-44-22.bmp','P��A���\0���\\','20121209test'),('����Ip�N�%\r��','��*��M_���I�d[H','2010-09-21 08:04:41','bioverify/1000016275_V3/2010-09-21_16-04-41.bmp','P��A���\0���\\','20121209test'),('�Ղ^�iJ9�����\Z�','>E��]J;���Z�q�','2011-04-25 10:09:05','bioverify/00825190_V4/2011-04-25_18-09-05.bmp','P��A���\0���\\','20121209test'),('��=���B|�+��:;y','`�{wOɚ��q���','2012-03-06 10:11:31','bioverify/1000016909_V2/2012-03-06_18-11-31.bmp','P��A���\0���\\','20121209test'),('��@�x�Eģ��^z�','��l�G�@]����ʝ9�','2010-09-29 09:48:14','bioverify/1000016909_V3/2010-09-29_17-48-14.bmp','P��A���\0���\\','20121209test'),('���o6�C��m�ئ','ȝ��P�K��:iù�','2010-05-25 09:17:52','bioverify/00811119_V4/2010-05-25_17-17-52.bmp','P��A���\0���\\','20121209test'),('�j$�d~C�d\'���*1','W~H]�H=�����3','2010-03-21 23:44:09','bioverify/90908135_V2/2010-03-22_07-44-09.bmp','P��A���\0���\\','20121209test'),('��{���A�N�gN@�','�K�06}Jl�M*�$ِ{','2010-04-06 13:07:47','bioverify/00721024_V3/2010-04-06_21-07-47.bmp','P��A���\0���\\','20121209test'),('������DJ��P`�9 <','���B	�f��P','2011-03-13 09:20:33','bioverify/00804709_V4/2011-03-13_17-20-33.bmp','P��A���\0���\\','20121209test'),('������BǾЦ�Fj�X','S]�LM��)#&�z8','2011-03-21 10:14:30','bioverify/1000016909_V4/2011-03-21_18-14-30.bmp','P��A���\0���\\','20121209test'),('�Yy�>I!�\nܛY��','��*��M_���I�d[H','2010-09-19 00:59:28','bioverify/1000016275_V3/2010-09-19_08-59-28.bmp','P��A���\0���\\','20121209test'),('�<���@J���\n�����','��l�G�@]����ʝ9�','2010-09-28 10:13:54','bioverify/1000016909_V3/2010-09-28_18-13-54.bmp','P��A���\0���\\','20121209test'),('�c���CD��|�b���','\Z�G��D���7}�+b','2011-03-08 08:50:49','bioverify/1000016275_V1/2011-03-08_16-50-49.bmp','P��A���\0���\\','20121209test'),('�P^ժ\'A��IŎ�} ','?C�6jfJM��4�`��','2011-03-26 11:22:02','bioverify/00825190_V3/2011-03-26_19-22-02.bmp','P��A���\0���\\','20121209test'),('�����@B�������>�','S]�LM��)#&�z8','2011-03-20 10:20:07','bioverify/1000016909_V4/2011-03-20_18-20-07.bmp','P��A���\0���\\','20121209test'),('��[\'�NH\0�nk)�O�','C��0%A��p�눤�','2010-09-15 23:07:56','bioverify/1000016909_V1/2010-09-16_07-07-56.bmp','P��A���\0���\\','20121209test'),('���U�@m�ك��5ҋ','>E��]J;���Z�q�','2011-04-10 10:50:39','bioverify/00825190_V4/2011-04-10_18-50-39.bmp','P��A���\0���\\','20121209test'),('�=��9$AԼ�c� ','\Z�G��D���7}�+b','2011-03-12 01:11:07','bioverify/1000016275_V1/2011-03-12_09-11-07.bmp','P��A���\0���\\','20121209test'),('�����\ZA����9FQ�k','��ך�6K��v��#�1�','2011-10-16 23:51:52','bioverify/1110303124_V3/2011-10-17_07-51-52.bmp','P��A���\0���\\','20121209test'),('�g�JoL$�Fp_\r�','?C�6jfJM��4�`��','2011-04-03 09:59:28','bioverify/00825190_V3/2011-04-03_17-59-28.bmp','P��A���\0���\\','20121209test'),('��%�\0LN̰��\Za.Z','ȝ��P�K��:iù�','2010-05-06 08:43:12','bioverify/00811119_V4/2010-05-06_16-43-12.bmp','P��A���\0���\\','20121209test'),('�5�\0�C��8ڗ;���','_�����@p�N��P#�(','2010-03-25 09:42:02','bioverify/00924002_V4/2010-03-25_17-42-02.bmp','P��A���\0���\\','20121209test'),('�x:�xBn��=z�Nb','6��e�\ZB��?��﫣5','2010-03-24 10:11:51','bioverify/90908135_V3/2010-03-24_18-11-51.bmp','P��A���\0���\\','20121209test'),('�ձ.qIj�Ya��V\n','C��0%A��p�눤�','2010-09-16 09:10:11','bioverify/1000016909_V1/2010-09-16_17-10-11.bmp','P��A���\0���\\','20121209test'),('�g�YlM{���','S]�LM��)#&�z8','2010-10-17 23:06:31','bioverify/1000016909_V4/2010-10-18_07-06-31.bmp','P��A���\0���\\','20121209test'),('�FfQ\rAv���<P_�','��*��M_���I�d[H','2010-09-21 08:05:42','bioverify/1000016275_V3/2010-09-21_16-05-42.bmp','P��A���\0���\\','20121209test'),('��L8��]�`�','���B	�f��P','2011-03-15 09:01:02','bioverify/00804709_V4/2011-03-15_17-01-02.bmp','P��A���\0���\\','20121209test'),('��x�Gȯ)�3J�','�p�\0��A �O�d\'�v�','2010-04-03 04:37:49','bioverify/00924002_V3/2010-04-03_12-37-49.bmp','P��A���\0���\\','20121209test'),('����\ngFȣ\\�O��','S]�LM��)#&�z8','2011-03-28 10:18:38','bioverify/1000016909_V4/2011-03-28_18-18-38.bmp','P��A���\0���\\','20121209test'),('Ï4��8H�����6��S','�p�\0��A �O�d\'�v�','2010-03-27 00:49:12','bioverify/00924002_V3/2010-03-27_08-49-12.bmp','P��A���\0���\\','20121209test'),('à�m\"�I\'�iq�P�U�','��ך�6K��v��#�1�','2011-10-09 23:34:36','bioverify/1110303124_V3/2011-10-10_07-34-36.bmp','P��A���\0���\\','20121209test'),('���8BBJ��o�.��v','WW�~1KEĪ��=�','2010-05-30 09:38:22','bioverify/00811119_V1/2010-05-30_17-38-22.bmp','P��A���\0���\\','20121209test'),('�*����C���B��]','�\'�UJ��~1��+�\\','2011-04-07 12:55:48','bioverify/00825190_V1/2011-04-07_20-55-48.bmp','P��A���\0���\\','20121209test'),('����:H/�S�kt�','ȝ��P�K��:iù�','2010-05-06 08:43:33','bioverify/00811119_V4/2010-05-06_16-43-33.bmp','P��A���\0���\\','20121209test'),('�>�pE���p5�ٛ','6��e�\ZB��?��﫣5','2010-03-21 02:37:33','bioverify/90908135_V3/2010-03-21_10-37-33.bmp','P��A���\0���\\','20121209test'),('�*���lO�����g�E','��*��M_���I�d[H','2010-09-21 08:05:05','bioverify/1000016275_V3/2010-09-21_16-05-05.bmp','P��A���\0���\\','20121209test'),('��\\�XuD����^n��I','����B@o��a�3','2010-04-01 23:46:21','bioverify/90908135_V4/2010-04-02_07-46-21.bmp','P��A���\0���\\','20121209test'),('�R`ں�M\"�i�\r�<+@','C��0%A��p�눤�','2010-09-15 09:44:34','bioverify/1000016909_V1/2010-09-15_17-44-34.bmp','P��A���\0���\\','20121209test'),('�^�0:�M�����;	֏','ȝ��P�K��:iù�','2010-05-07 09:35:46','bioverify/00811119_V4/2010-05-07_17-35-46.bmp','P��A���\0���\\','20121209test'),('�ET��I�Tu�(=_','��*��M_���I�d[H','2010-09-19 23:33:22','bioverify/1000016275_V3/2010-09-20_07-33-22.bmp','P��A���\0���\\','20121209test'),('��ezĳI����*��e�','S]�LM��)#&�z8','2011-03-23 09:37:34','bioverify/1000016909_V4/2011-03-23_17-37-34.bmp','P��A���\0���\\','20121209test'),('υ$�A�BC�m�����','_�����@p�N��P#�(','2010-11-27 01:02:54','bioverify/00924002_V4/2010-11-27_09-02-54.bmp','P��A���\0���\\','20121209test'),('ϑ��C~���	�_','�p�\0��A �O�d\'�v�','2010-03-23 23:27:22','bioverify/00924002_V3/2010-03-24_07-27-22.bmp','P��A���\0���\\','20121209test'),('���Bc�[ĭF��)','�p�\0��A �O�d\'�v�','2010-04-01 09:31:13','bioverify/00924002_V3//2010-04-01_17-31-13.bmp','P��A���\0���\\','20121209test'),('ѰQ�q�G{����E|7','�\'�UJ��~1��+�\\','2011-04-20 00:11:58','bioverify/00825190_V1/2011-04-20_08-11-58.bmp','P��A���\0���\\','20121209test'),('��1�Jܷ7\n�*�W�','WW�~1KEĪ��=�','2010-05-30 09:41:42','bioverify/00811119_V1/2010-05-30_17-41-42.bmp','P��A���\0���\\','20121209test'),('Ӻ�v\nO���LH<�','��*��M_���I�d[H','2010-09-18 11:44:24','bioverify/1000016275_V3/2010-09-18_19-44-24.bmp','P��A���\0���\\','20121209test'),('������G_�l>֋Ө','_�����@p�N��P#�(','2010-04-24 01:02:51','bioverify/00924002_V4/2010-04-24_09-02-51.bmp','P��A���\0���\\','20121209test'),('۽P�pA���팔R�a','��l�G�@]����ʝ9�','2010-09-29 22:52:49','bioverify/1000016909_V3/2010-09-30_06-52-49.bmp','P��A���\0���\\','20121209test'),('��b�¹C���ĸ�K5Q','W~H]�H=�����3','2010-03-26 11:02:08','bioverify/90908135_V2/2010-03-26_19-02-08.bmp','P��A���\0���\\','20121209test'),('�ј��D��X��:��!','\Z�G��D���7}�+b','2010-09-21 09:04:32','bioverify/1000016275_V1/2010-09-21_17-04-32.bmp','P��A���\0���\\','20121209test'),('��L3D�8{���','>E��]J;���Z�q�','2011-04-13 23:52:46','bioverify/00825190_V4/2011-04-14_07-52-46.bmp','P��A���\0���\\','20121209test'),('�/��RI���;$��','���B	�f��P','2011-03-18 23:48:40','bioverify/00804709_V4/2011-03-19_07-48-40.bmp','P��A���\0���\\','20121209test'),('���^˼Ls���@�H�','W~H]�H=�����3','2010-03-28 08:49:28','bioverify/90908135_V2/2010-03-28_16-49-28.bmp','P��A���\0���\\','20121209test'),('�YX�H��O�M>&�','\Z�G��D���7}�+b','2010-09-27 08:46:05','bioverify/1000016275_V1/2010-09-27_16-46-05.bmp','P��A���\0���\\','20121209test'),('�*i>9�H\"��pz~�4','��*��M_���I�d[H','2010-09-19 08:44:34','bioverify/1000016275_V3/2010-09-19_16-44-34.bmp','P��A���\0���\\','20121209test'),('�@L�avE+���x�a��','����B@o��a�3','2010-03-21 02:38:27','bioverify/90908135_V4/2010-03-21_10-38-27.bmp','P��A���\0���\\','20121209test'),('�i��q�G���an���','S]�LM��)#&�z8','2011-03-17 13:07:46','bioverify/1000016909_V4/2011-03-17_21-07-46.bmp','P��A���\0���\\','20121209test'),('�׸�oFi���C�o46','WW�~1KEĪ��=�','2010-05-30 09:40:00','bioverify/00811119_V1/2010-05-30_17-40-00.bmp','P��A���\0���\\','20121209test'),('�^(��No��F��','��ך�6K��v��#�1�','2011-10-18 10:24:24','bioverify/1110303124_V3/2011-10-18_18-24-24.bmp','P��A���\0���\\','20121209test'),('�lYz�B�L8K���','C��0%A��p�눤�','2010-09-17 12:41:22','bioverify/1000016909_V1/2010-09-17_20-41-22.bmp','P��A���\0���\\','20121209test'),('�1�ƁCO���)�G','��*��M_���I�d[H','2010-09-18 09:06:12','bioverify/1000016275_V3/2010-09-18_17-06-12.bmp','P��A���\0���\\','20121209test'),('�tTN�ZO��d�Ff���','C��0%A��p�눤�','2011-03-06 23:50:18','bioverify/1000016909_V1/2011-03-07_07-50-18.bmp','P��A���\0���\\','20121209test'),('���hUzHZ�=�i&F�','�p�\0��A �O�d\'�v�','2010-03-31 11:12:27','bioverify/00924002_V3//2010-03-31_19-12-27.bmp','P��A���\0���\\','20121209test'),('�	R_`AЪWp560A�','S]�LM��)#&�z8','2011-03-29 10:18:04','bioverify/1000016909_V4/2011-03-29_18-18-04.bmp','P��A���\0���\\','20121209test'),('�W�tK�����','W~H]�H=�����3','2010-03-26 11:01:55','bioverify/90908135_V2/2010-03-26_19-01-55.bmp','P��A���\0���\\','20121209test'),('���H�����d]','\Z�G��D���7}�+b','2010-09-28 23:38:57','bioverify/1000016275_V1/2010-09-29_07-38-57.bmp','P��A���\0���\\','20121209test'),('�\'+��I@�v��b$`�','?C�6jfJM��4�`��','2011-03-23 10:25:51','bioverify/00825190_V3/2011-03-23_18-25-51.bmp','P��A���\0���\\','20121209test'),('�؂���N����?c�Ձ','����B@o��a�3','2010-03-23 00:26:22','bioverify/90908135_V4/2010-03-23_08-26-22.bmp','P��A���\0���\\','20121209test'),('�.���NO��ep}��','6��e�\ZB��?��﫣5','2010-04-01 23:46:09','bioverify/90908135_V3/2010-04-02_07-46-09.bmp','P��A���\0���\\','20121209test'),('��.xt@1�J����5�','_�����@p�N��P#�(','2010-11-25 00:17:48','bioverify/00924002_V4/2010-11-25_08-17-48.bmp','P��A���\0���\\','20121209test'),('�%|0io@��9RS�D��','NX�B�M}�ԧ�e@','2010-05-07 09:35:45','bioverify/00811119_V3/2010-05-07_17-35-45.bmp','P��A���\0���\\','20121209test'),('����\ZKAK�f�n���[','`�{wOɚ��q���','2012-03-11 03:36:38','bioverify/1000016909_V2/2012-03-11_11-36-38.bmp','P��A���\0���\\','20121209test'),('����F��A�{4�T�','�p�\0��A �O�d\'�v�','2010-04-07 08:46:29','bioverify/00924002_V3/2010-04-07_16-46-29.bmp','P��A���\0���\\','20121209test'),('�/�`�aJҮcS�U���','�\'�UJ��~1��+�\\','2011-04-07 00:15:47','bioverify/00825190_V1/2011-04-07_08-15-47.bmp','P��A���\0���\\','20121209test'),('�O�$�MC����4�:�','ȝ��P�K��:iù�','2010-05-09 09:53:48','bioverify/00811119_V4/2010-05-09_17-53-49.bmp','P��A���\0���\\','20121209test'),('�q|\Z�)L8�9�|��V�','`�{wOɚ��q���','2012-02-29 13:05:51','bioverify/1000016909_V2/2012-02-29_21-05-51.bmp','P��A���\0���\\','20121209test'),('��LqM�����;','��l�G�@]����ʝ9�','2010-09-22 12:56:37','bioverify/1000016909_V3/2010-09-22_20-56-37.bmp','P��A���\0���\\','20121209test'),('��\n�\09I=��Wl�x0','NX�B�M}�ԧ�e@','2010-05-06 23:48:28','bioverify/00811119_V3/2010-05-07_07-48-29.bmp','P��A���\0���\\','20121209test'),('�6�FJۀ:�q�1�b','����B@o��a�3','2010-03-29 00:26:12','bioverify/90908135_V4/2010-03-29_08-26-12.bmp','P��A���\0���\\','20121209test');
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
  `generator` varchar(45) NOT NULL COMMENT 'With each import, you must provide a import_tag. It is used for rollback functions.',
  `type` enum('FINGERVEIN') NOT NULL,
  `generated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES ('ݢ�3�A��Ϊ�f','ViewByImportTag-20121209test','GenerateByImportTagGenerator','FINGERVEIN','2012-12-09 12:54:44');
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
INSERT INTO `view_sample` VALUES ('ݢ�3�A��Ϊ�f','��;X�@��f#�'),('ݢ�3�A��Ϊ�f','�# %I8�F�Y�o��'),('ݢ�3�A��Ϊ�f','0�L��8�K�AL�'),('ݢ�3�A��Ϊ�f','��hN�R�$Wp�'),('ݢ�3�A��Ϊ�f','\n)#\\��D\Z�Ȭ]��'),('ݢ�3�A��Ϊ�f','\nI�4�F=�uey�{%'),('ݢ�3�A��Ϊ�f','\nmEpI��naБ)6'),('ݢ�3�A��Ϊ�f','\Z��o�O��HHׯ��'),('ݢ�3�A��Ϊ�f','�N	Z�C��z��L('),('ݢ�3�A��Ϊ�f','=�<ȁF��>_�@���'),('ݢ�3�A��Ϊ�f','ʞE��F܈),3��s'),('ݢ�3�A��Ϊ�f','��A�CN����K|'),('ݢ�3�A��Ϊ�f','贜~I{�ŵx�¤�'),('ݢ�3�A��Ϊ�f','�]}��I�������'),('ݢ�3�A��Ϊ�f','�NsAŒ�;���'),('ݢ�3�A��Ϊ�f','+����G��J��Ț�'),('ݢ�3�A��Ϊ�f','�iX�@����z��z'),('ݢ�3�A��Ϊ�f','��nI��rD��o�'),('ݢ�3�A��Ϊ�f','�ǐmC �)s�ۆl('),('ݢ�3�A��Ϊ�f','\Z��|�@�zP}*�X�'),('ݢ�3�A��Ϊ�f','E�82A\n�����z'),('ݢ�3�A��Ϊ�f','>Έz�C��\Z�ewF�'),('ݢ�3�A��Ϊ�f','W,C��C��ሷ���'),('ݢ�3�A��Ϊ�f','���*Ģ�xi�Д�'),('ݢ�3�A��Ϊ�f','�b�?Bд����\rKe'),('ݢ�3�A��Ϊ�f',' h.��rC��@�E�'),('ݢ�3�A��Ϊ�f','!v�NyD���U�[��'),('ݢ�3�A��Ϊ�f','!�ۤ�K���y�p-�)'),('ݢ�3�A��Ϊ�f','\"F%��aL\n���t\\�x}'),('ݢ�3�A��Ϊ�f','\"r�\Z�I+�ڬ�b���'),('ݢ�3�A��Ϊ�f','#�[�s$M��2���'),('ݢ�3�A��Ϊ�f','&Du2�@d�Fs6z��'),('ݢ�3�A��Ϊ�f','&��C\\�L\'�Q+C��7'),('ݢ�3�A��Ϊ�f','(-���N���ϫ�X=6'),('ݢ�3�A��Ϊ�f','(���_O��1�`W�^'),('ݢ�3�A��Ϊ�f',')FrMF@��;ʿ	N�'),('ݢ�3�A��Ϊ�f',')�M��C������{'),('ݢ�3�A��Ϊ�f','+8��בF�����W	O'),('ݢ�3�A��Ϊ�f','+�9�Bɪu��d�k'),('ݢ�3�A��Ϊ�f',',�B���Dδ��a~+�'),('ݢ�3�A��Ϊ�f','.j�;�L���6�$��'),('ݢ�3�A��Ϊ�f','/3�\n�N���<�\'��>'),('ݢ�3�A��Ϊ�f','/S8/�A�E��ަ!_'),('ݢ�3�A��Ϊ�f','2�UOIEO��7҅;�'),('ݢ�3�A��Ϊ�f','4g+)��LX�����^'),('ݢ�3�A��Ϊ�f','6WnUj�B	�/4O��c'),('ݢ�3�A��Ϊ�f','8S|P]�Lt�Dr��Xv�'),('ݢ�3�A��Ϊ�f','9\\�aE@Z�\nQ{)�f�'),('ݢ�3�A��Ϊ�f',';���X�Ix�9GK����'),('ݢ�3�A��Ϊ�f',';���nA~�}3Ru}�'),('ݢ�3�A��Ϊ�f','>�-��RJӄK�})'),('ݢ�3�A��Ϊ�f','D�u��N���ky88�Q'),('ݢ�3�A��Ϊ�f','D.#i�FI������/K�'),('ݢ�3�A��Ϊ�f','I��V[eM�Hڌ�a:|'),('ݢ�3�A��Ϊ�f','L@b��CF&�~��q�'),('ݢ�3�A��Ϊ�f','OF{݃�@��Za#��'),('ݢ�3�A��Ϊ�f','PS���L���0�x�'),('ݢ�3�A��Ϊ�f','Q����Eu�Q����'),('ݢ�3�A��Ϊ�f','S\\��{I��[9T񱭉'),('ݢ�3�A��Ϊ�f','U�1N�G��L�W5�'),('ݢ�3�A��Ϊ�f','V:��*CM���<1�ha'),('ݢ�3�A��Ϊ�f','V�6�i6H��}�}�DbQ'),('ݢ�3�A��Ϊ�f','YK.|�AI��R%��o'),('ݢ�3�A��Ϊ�f','Y�N���F�jF=K'),('ݢ�3�A��Ϊ�f','[��[�\ZN���iqĩ��'),('ݢ�3�A��Ϊ�f','\\��\"�GׂejG93��'),('ݢ�3�A��Ϊ�f','\\��GiyL����gs��'),('ݢ�3�A��Ϊ�f',']�Ę;�M&�V�E�|��'),('ݢ�3�A��Ϊ�f','^2�%]�JZ����Ee8�'),('ݢ�3�A��Ϊ�f','^r�`�EJr���Q�'),('ݢ�3�A��Ϊ�f','_X��wG�a�����'),('ݢ�3�A��Ϊ�f','a���E��$Q:��4'),('ݢ�3�A��Ϊ�f','e��POr�����1fO'),('ݢ�3�A��Ϊ�f','fNM�P4I�,�;iAK�'),('ݢ�3�A��Ϊ�f','g�2-NJ��W���Ǉ'),('ݢ�3�A��Ϊ�f','g���o�G���%���'),('ݢ�3�A��Ϊ�f','iUpP4�@v�K�q��'),('ݢ�3�A��Ϊ�f','i_	:��Bѐ�3���'),('ݢ�3�A��Ϊ�f','m��.-Hk�n9~,9'),('ݢ�3�A��Ϊ�f','o�SqCR�l�\']�o�'),('ݢ�3�A��Ϊ�f','p$�mT�M춆u���\'�'),('ݢ�3�A��Ϊ�f','s)j3G����L�'),('ݢ�3�A��Ϊ�f','u�ِ�0J�0���d'),('ݢ�3�A��Ϊ�f','u�@:I���\\��5`�'),('ݢ�3�A��Ϊ�f','v�qi�J���hP��'),('ݢ�3�A��Ϊ�f','v.�%�I⡄z���'),('ݢ�3�A��Ϊ�f','yfC�Mݬ�3�l0Ng'),('ݢ�3�A��Ϊ�f','zVGEq>H�y��:\Zr�'),('ݢ�3�A��Ϊ�f','|�pt\'5DѴ��ߍG|�'),('ݢ�3�A��Ϊ�f','a\"� �@�Y��{\'��'),('ݢ�3�A��Ϊ�f','�^���D��}�T���'),('ݢ�3�A��Ϊ�f','�6n��K���c�\\'),('ݢ�3�A��Ϊ�f','�X�\Z`�M1������X�'),('ݢ�3�A��Ϊ�f','���\\L�B�\'R��NS�'),('ݢ�3�A��Ϊ�f','��.BXGU����5~�'),('ݢ�3�A��Ϊ�f','�d�i�F▼1\Z��v�'),('ݢ�3�A��Ϊ�f','�l5�D�$��8ΐ'),('ݢ�3�A��Ϊ�f','���2�Cx�w�(�[['),('ݢ�3�A��Ϊ�f','������F͊��u�@g	'),('ݢ�3�A��Ϊ�f','������D�P|8�{�'),('ݢ�3�A��Ϊ�f','��ꠉI�����!�s�'),('ݢ�3�A��Ϊ�f','��e��uO�����U+�'),('ݢ�3�A��Ϊ�f','�B��^HЏ��ʤ'),('ݢ�3�A��Ϊ�f','�+ӮrI��gF��'),('ݢ�3�A��Ϊ�f','�{����I��\Z�XV��V'),('ݢ�3�A��Ϊ�f','������@۾b����'),('ݢ�3�A��Ϊ�f','��c8HbB��6h��D'),('ݢ�3�A��Ϊ�f','�5�8��E�â�P '),('ݢ�3�A��Ϊ�f','�����F���OCj�?'),('ݢ�3�A��Ϊ�f','��0A\ndL��,b�]��'),('ݢ�3�A��Ϊ�f','�����MA\0����0@p'),('ݢ�3�A��Ϊ�f','��[���Hխ䜖i���'),('ݢ�3�A��Ϊ�f','�4W\"`F<��*��_�'),('ݢ�3�A��Ϊ�f','�J�\Z��C����n/h'),('ݢ�3�A��Ϊ�f','��\"y��DV��=���{\Z'),('ݢ�3�A��Ϊ�f','���&#L0�m���K'),('ݢ�3�A��Ϊ�f','���}�;K���b�'),('ݢ�3�A��Ϊ�f','��Y~{IN��45���Q'),('ݢ�3�A��Ϊ�f','�3\Z�%LɆA~�q��l'),('ݢ�3�A��Ϊ�f','�S�!� F������W'),('ݢ�3�A��Ϊ�f','�K�	D�J˥o�!&��'),('ݢ�3�A��Ϊ�f','����Ip�N�%\r��'),('ݢ�3�A��Ϊ�f','�Ղ^�iJ9�����\Z�'),('ݢ�3�A��Ϊ�f','��=���B|�+��:;y'),('ݢ�3�A��Ϊ�f','��@�x�Eģ��^z�'),('ݢ�3�A��Ϊ�f','���o6�C��m�ئ'),('ݢ�3�A��Ϊ�f','�j$�d~C�d\'���*1'),('ݢ�3�A��Ϊ�f','��{���A�N�gN@�'),('ݢ�3�A��Ϊ�f','������DJ��P`�9 <'),('ݢ�3�A��Ϊ�f','������BǾЦ�Fj�X'),('ݢ�3�A��Ϊ�f','�Yy�>I!�\nܛY��'),('ݢ�3�A��Ϊ�f','�<���@J���\n�����'),('ݢ�3�A��Ϊ�f','�c���CD��|�b���'),('ݢ�3�A��Ϊ�f','�P^ժ\'A��IŎ�} '),('ݢ�3�A��Ϊ�f','�����@B�������>�'),('ݢ�3�A��Ϊ�f','��[\'�NH\0�nk)�O�'),('ݢ�3�A��Ϊ�f','���U�@m�ك��5ҋ'),('ݢ�3�A��Ϊ�f','�=��9$AԼ�c� '),('ݢ�3�A��Ϊ�f','�����\ZA����9FQ�k'),('ݢ�3�A��Ϊ�f','�g�JoL$�Fp_\r�'),('ݢ�3�A��Ϊ�f','��%�\0LN̰��\Za.Z'),('ݢ�3�A��Ϊ�f','�5�\0�C��8ڗ;���'),('ݢ�3�A��Ϊ�f','�x:�xBn��=z�Nb'),('ݢ�3�A��Ϊ�f','�ձ.qIj�Ya��V\n'),('ݢ�3�A��Ϊ�f','�g�YlM{���'),('ݢ�3�A��Ϊ�f','�FfQ\rAv���<P_�'),('ݢ�3�A��Ϊ�f','��L8��]�`�'),('ݢ�3�A��Ϊ�f','��x�Gȯ)�3J�'),('ݢ�3�A��Ϊ�f','����\ngFȣ\\�O��'),('ݢ�3�A��Ϊ�f','Ï4��8H�����6��S'),('ݢ�3�A��Ϊ�f','à�m\"�I\'�iq�P�U�'),('ݢ�3�A��Ϊ�f','���8BBJ��o�.��v'),('ݢ�3�A��Ϊ�f','�*����C���B��]'),('ݢ�3�A��Ϊ�f','����:H/�S�kt�'),('ݢ�3�A��Ϊ�f','�>�pE���p5�ٛ'),('ݢ�3�A��Ϊ�f','�*���lO�����g�E'),('ݢ�3�A��Ϊ�f','��\\�XuD����^n��I'),('ݢ�3�A��Ϊ�f','�R`ں�M\"�i�\r�<+@'),('ݢ�3�A��Ϊ�f','�^�0:�M�����;	֏'),('ݢ�3�A��Ϊ�f','�ET��I�Tu�(=_'),('ݢ�3�A��Ϊ�f','��ezĳI����*��e�'),('ݢ�3�A��Ϊ�f','υ$�A�BC�m�����'),('ݢ�3�A��Ϊ�f','ϑ��C~���	�_'),('ݢ�3�A��Ϊ�f','���Bc�[ĭF��)'),('ݢ�3�A��Ϊ�f','ѰQ�q�G{����E|7'),('ݢ�3�A��Ϊ�f','��1�Jܷ7\n�*�W�'),('ݢ�3�A��Ϊ�f','Ӻ�v\nO���LH<�'),('ݢ�3�A��Ϊ�f','������G_�l>֋Ө'),('ݢ�3�A��Ϊ�f','۽P�pA���팔R�a'),('ݢ�3�A��Ϊ�f','��b�¹C���ĸ�K5Q'),('ݢ�3�A��Ϊ�f','�ј��D��X��:��!'),('ݢ�3�A��Ϊ�f','��L3D�8{���'),('ݢ�3�A��Ϊ�f','�/��RI���;$��'),('ݢ�3�A��Ϊ�f','���^˼Ls���@�H�'),('ݢ�3�A��Ϊ�f','�YX�H��O�M>&�'),('ݢ�3�A��Ϊ�f','�*i>9�H\"��pz~�4'),('ݢ�3�A��Ϊ�f','�@L�avE+���x�a��'),('ݢ�3�A��Ϊ�f','�i��q�G���an���'),('ݢ�3�A��Ϊ�f','�׸�oFi���C�o46'),('ݢ�3�A��Ϊ�f','�^(��No��F��'),('ݢ�3�A��Ϊ�f','�lYz�B�L8K���'),('ݢ�3�A��Ϊ�f','�1�ƁCO���)�G'),('ݢ�3�A��Ϊ�f','�tTN�ZO��d�Ff���'),('ݢ�3�A��Ϊ�f','���hUzHZ�=�i&F�'),('ݢ�3�A��Ϊ�f','�	R_`AЪWp560A�'),('ݢ�3�A��Ϊ�f','�W�tK�����'),('ݢ�3�A��Ϊ�f','���H�����d]'),('ݢ�3�A��Ϊ�f','�\'+��I@�v��b$`�'),('ݢ�3�A��Ϊ�f','�؂���N����?c�Ձ'),('ݢ�3�A��Ϊ�f','�.���NO��ep}��'),('ݢ�3�A��Ϊ�f','��.xt@1�J����5�'),('ݢ�3�A��Ϊ�f','�%|0io@��9RS�D��'),('ݢ�3�A��Ϊ�f','����\ZKAK�f�n���['),('ݢ�3�A��Ϊ�f','����F��A�{4�T�'),('ݢ�3�A��Ϊ�f','�/�`�aJҮcS�U���'),('ݢ�3�A��Ϊ�f','�O�$�MC����4�:�'),('ݢ�3�A��Ϊ�f','�q|\Z�)L8�9�|��V�'),('ݢ�3�A��Ϊ�f','��LqM�����;'),('ݢ�3�A��Ϊ�f','��\n�\09I=��Wl�x0'),('ݢ�3�A��Ϊ�f','�6�FJۀ:�q�1�b');
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

-- Dump completed on 2012-12-09 21:03:40
