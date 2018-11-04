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
-- Table structure for table `send_message`
--

DROP TABLE IF EXISTS `send_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `send_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_from` int(11) NOT NULL,
  `id_to` int(11) NOT NULL,
  `id_message` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_from_idx` (`id_from`),
  KEY `id_to_idx` (`id_to`),
  KEY `id_message_idx` (`id_message`),
  CONSTRAINT `id_from` FOREIGN KEY (`id_from`) REFERENCES `users` (`id`),
  CONSTRAINT `id_message` FOREIGN KEY (`id_message`) REFERENCES `message` (`id`),
  CONSTRAINT `id_to` FOREIGN KEY (`id_to`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `send_message`
--

LOCK TABLES `send_message` WRITE;
/*!40000 ALTER TABLE `send_message` DISABLE KEYS */;
INSERT INTO `send_message` VALUES (2,2,1,2),(15,1,3,15),(17,4,2,17),(18,1,4,18),(21,9,4,21),(22,9,2,22),(24,3,4,24),(27,2,3,27),(28,2,3,28),(34,1,9,34),(36,1,2,36),(37,1,9,37),(41,2,4,41),(42,1,9,42),(43,1,4,43),(44,1,2,44),(45,1,3,45),(46,1,3,46),(47,2,1,47),(48,2,1,48),(49,1,9,49),(50,1,2,50),(51,1,3,51),(52,1,9,52),(53,2,1,53),(54,2,1,54),(55,1,2,55),(56,1,2,56),(57,1,2,57),(58,2,1,58),(59,2,9,59),(60,1,2,60),(61,1,2,61),(63,1,9,63),(66,3,4,66),(67,2,1,67),(69,1,3,69),(71,1,2,71),(72,1,2,72),(73,1,3,73),(74,1,4,74),(75,2,9,75);
/*!40000 ALTER TABLE `send_message` ENABLE KEYS */;
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
