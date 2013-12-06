--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `User_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Fname` char(50) NOT NULL,
  `User_EmailAddress` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`User_ID`),
  UNIQUE KEY `User_EmailAddress` (`User_EmailAddress`)
); 



