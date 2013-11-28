--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Structure for view `vLocLikes`, used to create a virtual table of Location, Reviews and Users
--

CREATE  VIEW `vLocLikes` AS 
					(SELECT `loc`.`Location_Latitude` AS `Location_Latitude`,`loc`.`Location_Longitude` AS `Location_Longitude`,
							`loc`.`Location_Address` AS `Location_Address`,`loc`.`Location_ID` AS `Location_ID`,`usr`.`User_EmailAddress` AS `User_EmailAddress`,
							`Lik`.`Like_ID` AS `Like_ID`,`Lik`.`Likes` AS `Likes` 
					 FROM ((`Location` `loc` 
					 JOIN `Likes` `Lik`
					 ON((`loc`.`Location_ID` = `Lik`.`Location_ID`))) 
					 JOIN `Users` `usr` 
					 ON((`usr`.`User_EmailAddress` = `Lik`.`User_Email`))));

