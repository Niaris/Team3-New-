--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Table structure for table `Likes`, used to store the Likes
--

CREATE TABLE IF NOT EXISTS `Likes` (
  `Like_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Likes` int(11) DEFAULT NULL,
  `User_Email` varchar(320) DEFAULT NULL,
  `Location_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`Like_ID`),
  KEY `User_Email` (`User_Email`),
  KEY `Location_ID` (`Location_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;


--
-- Constraints for table `Likes`
--
ALTER TABLE `Likes`
  ADD CONSTRAINT `Likes_ibfk_1` FOREIGN KEY (`User_Email`) REFERENCES `Users` (`User_EmailAddress`),
  ADD CONSTRAINT `Likes_ibfk_2` FOREIGN KEY (`Location_ID`) REFERENCES `Location` (`Location_ID`);


