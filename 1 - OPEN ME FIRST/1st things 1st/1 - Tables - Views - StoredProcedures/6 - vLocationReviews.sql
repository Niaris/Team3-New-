--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Structure for view `vLocationReviews`, used to create a virtual table of Location and Reviews
--

CREATE VIEW `vLocationReviews` AS 
							(SELECT `loc`.`Location_Latitude` AS `Location_Latitude`,`loc`.`Location_Longitude` AS `Location_Longitude`,
									`loc`.`Location_Address` AS `Location_Address`,`Rev`.`Review_Comment` AS `Review_Comment`,`Rev`.`Review_Rating` AS `Review_Rating`,
									`Rev`.`Review_Date` AS `Review_Date`,`Rev`.`Review_Time` AS `Review_Time`,`Rev`.`Location_ID` AS `Location_ID`,
									`Rev`.`User_Email` AS `User_Email` 
							FROM (	`Location` `loc` 
							JOIN	`Review` `Rev` 
							ON   ((`loc`.`Location_ID` = `Rev`.`Location_ID`))));
