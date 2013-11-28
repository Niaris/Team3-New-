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

--
-- Dumping data for table `Users` for testing purposes. This will ensure that there are registered
-- Users in the application whithout the need of runnig it with
-- two different mobiles/emulators.
--

INSERT INTO `Users` (`User_Fname`, `User_EmailAddress`) VALUES
('Ellís Carvalho', 'ellisvcarvalho@gmail.com'),
('rufai ahmed', 'rufaiahmed5@gmail.com'),
('putaniaris', 'nono@hotmai.com'),
('Redwan Sulaimani', 'r.s.hamoh@googlemail.com'),
('Chara Ioannou', 'charaioannou.ci@gmail.com'),
('Maria Solomonidou', 'msolomonidou12@googlemail.com');

