-- MySQL dump 10.13  Distrib 5.1.73, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: udptrafficDB
-- ------------------------------------------------------
-- Server version	5.1.73

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
-- Table structure for table `Ports`
--

DROP TABLE IF EXISTS `Ports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ports` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `portNum` smallint(5) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `portindex` (`portNum`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ports`
--

LOCK TABLES `Ports` WRITE;
/*!40000 ALTER TABLE `Ports` DISABLE KEYS */;
INSERT INTO `Ports` VALUES (2,5094,'f'),(3,5095,'f'),(4,5096,'f'),(5,5097,'f'),(6,5098,'f'),(7,5099,'f'),(8,5100,'f'),(9,5101,'f'),(10,5102,'f'),(11,5103,'f'),(12,5104,'f'),(13,5105,'f'),(14,5106,'f'),(15,5107,'f');
/*!40000 ALTER TABLE `Ports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_result`
--

DROP TABLE IF EXISTS `test_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(36) DEFAULT NULL,
  `customerName` varchar(40) DEFAULT NULL,
  `publicIp` varchar(16) DEFAULT NULL,
  `codec` varchar(8) DEFAULT NULL,
  `testLength` smallint(5) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `uploadPacketLost` smallint(4) DEFAULT NULL,
  `uploadLatencyPeak` smallint(4) DEFAULT NULL,
  `uploadLatencyAvg` smallint(4) DEFAULT NULL,
  `uploadJitterPeak` smallint(4) DEFAULT NULL,
  `uploadJitterAvg` smallint(4) DEFAULT NULL,
  `downloadPacketLost` smallint(4) DEFAULT NULL,
  `downloadLatencyPeak` smallint(4) DEFAULT NULL,
  `downloadLatencyAvg` smallint(4) DEFAULT NULL,
  `downloadJitterPeak` smallint(4) DEFAULT NULL,
  `downloadJitterAvg` smallint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_result`
--

LOCK TABLES `test_result` WRITE;
/*!40000 ALTER TABLE `test_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_result` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-28 21:14:23
