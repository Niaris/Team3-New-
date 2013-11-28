--
-- Database: `Team3_backup`
--
--

-- --------------------------------------------------------

--
-- Table structure for table `Review`
--

CREATE TABLE IF NOT EXISTS `Review` (
  `Review_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Review_Comment` varchar(255) NOT NULL,
  `Review_Rating` tinyint(5) NOT NULL,
  `Review_ImagePath` varchar(256) NOT NULL,
  `Review_Date` date NOT NULL,
  `Review_Time` time NOT NULL,
  `Location_ID` int(11) NOT NULL,
  `User_Email` varchar(320) NOT NULL,
  PRIMARY KEY (`Review_ID`),
  KEY `Location_ID` (`Location_ID`),
  KEY `User_Email` (`User_Email`)
);

-- ---------------------------------------------------------------
--
-- Constraints for table `Review` FK's
--
ALTER TABLE `Review`
  ADD CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`Location_ID`) REFERENCES `Location` (`Location_ID`),
  ADD CONSTRAINT `Review_ibfk_2` FOREIGN KEY (`User_Email`) REFERENCES `Users` (`User_EmailAddress`);
