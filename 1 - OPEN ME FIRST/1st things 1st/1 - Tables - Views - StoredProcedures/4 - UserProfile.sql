--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Table structure for table `UserProfile`, used to store the user profile
--

CREATE TABLE IF NOT EXISTS `UserProfile` (
  `User_Profile_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(50) DEFAULT NULL,
  `User_Interests` varchar(320) DEFAULT NULL,
  `User_Google_Account` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`User_Profile_ID`),
  UNIQUE KEY `User_Google_Account` (`User_Google_Account`),
  UNIQUE KEY `User_Name` (`User_Name`)
);


--
-- Constraints for table `UserProfile` FK's
--
ALTER TABLE `UserProfile`
  ADD CONSTRAINT `UserProfile_ibfk_1` FOREIGN KEY (`User_Google_Account`) REFERENCES `Users` (`User_EmailAddress`);
