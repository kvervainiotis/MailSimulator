-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: kostas_verve_project1
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_data` varchar(255) NOT NULL,
  `date` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (2,'hi admin','2018-09-07 17:17:47'),(15,'hi','2018-09-15 16:15:29'),(17,'hello krocare','2018-09-14 12:11:25'),(18,'pou sai re ace??','2018-09-14 15:29:15'),(21,'dsakdas','2018-09-15 14:10:45'),(22,'ji kroc','2018-09-15 14:12:00'),(24,'das','2018-09-15 14:12:41'),(27,'naoks','2018-09-15 14:14:25'),(28,'leo','2018-09-15 14:14:45'),(34,'frikarismene','2018-09-15 16:18:53'),(36,'teting','2018-09-15 18:36:13'),(37,'hisad','2018-09-15 19:12:21'),(41,'hi ace','2018-09-17 10:14:22'),(42,'fdasfda','2018-09-17 10:24:32'),(43,'12331212','2018-09-17 10:24:39'),(44,'test file','2018-09-17 10:44:26'),(45,'jdtest','2018-09-17 10:51:39'),(46,'kwstaras testarei','2018-09-17 10:52:37'),(47,'test file','2018-09-17 10:54:50'),(48,'hi again','2018-09-17 10:55:18'),(49,'test file','2018-09-17 11:02:56'),(50,'test file','2018-09-17 11:03:50'),(51,'testing','2018-09-17 11:03:57'),(52,'fjaldhfjofjdsa;fjaeof adf ;jlasfo ;efjae fealh fesaig fea lfaefiaiul fha ','2018-09-17 11:04:07'),(53,'hi testing my file manipulation','2018-09-17 11:06:17'),(54,'jdisaf fdas','2018-09-17 11:09:19'),(55,'jdsa','2018-09-17 11:11:25'),(56,'ofds','2018-09-17 11:13:18'),(57,'12fdsgsf','2018-09-17 11:13:22'),(58,'hia dmin','2018-09-17 11:14:12'),(59,'jdilsafasf','2018-09-17 11:14:18'),(60,'sad','2018-09-17 11:18:10'),(61,'df','2018-09-17 11:18:45'),(63,'das','2018-09-17 11:19:47'),(66,'sa','2018-09-17 14:30:56'),(67,'kjdas','2018-09-17 12:29:31'),(69,'hi leo','2018-09-17 13:29:46'),(71,'pou sai man','2018-09-17 14:18:23'),(72,'hji','2018-09-17 14:21:08'),(73,'kdasfa','2018-09-17 14:21:15'),(74,'fda','2018-09-17 14:22:01'),(75,'hou','2018-09-17 14:27:46');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-17 16:39:57
