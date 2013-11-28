--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Table structure for table `Location`, used to store the registered locations
--

CREATE TABLE IF NOT EXISTS `Location` (
  `Location_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Location_Name` varchar(30) NOT NULL,
  `Location_Latitude` decimal(10,8) NOT NULL,
  `Location_Longitude` decimal(11,8) NOT NULL,
  `Location_Address` varchar(255) NOT NULL,
  PRIMARY KEY (`Location_ID`)
);

-- --------------------------------------------------------