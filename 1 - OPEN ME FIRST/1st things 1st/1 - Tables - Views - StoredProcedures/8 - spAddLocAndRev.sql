--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------

--
-- Procedure spAddLocAndRev, used to store Locations and Reviews. Uses and IF and ELSE statement to determine if the location already exists.
-- It is used by the php Script AddLocationAndReview.php
--

DELIMITER $$

CREATE PROCEDURE `spAddLocAndRev`
(
	IN `name` VARCHAR(30), 
	IN `latitude` DECIMAL(10,8), 
	IN `longitude` DECIMAL(11,8), 
	IN `address` VARCHAR(255), 
	IN `comment` VARCHAR(255), 
	IN `rating` TINYINT(5), 
	IN `email` VARCHAR(320), 
	IN `locationid` INT
)

BEGIN
START TRANSACTION;
IF (locationid = 0)THEN
INSERT INTO Location (Location_Name, Location_Latitude, Location_Longitude, Location_Address)
VALUES
					  (name, latitude, longitude, address);

INSERT INTO Review (Review_Comment, Review_Rating, Review_Date, Review_Time, Location_ID, User_Email)
VALUES
				   (comment, rating, CURDATE(), CURTIME(), (SELECT Location_ID FROM Location WHERE Location_Latitude = latitude AND Location_Longitude = longitude), email);

ELSE 
    INSERT INTO Review (Review_Comment, Review_Rating, Review_Date, Review_Time, Location_ID, User_Email)
VALUES
				   (comment, rating, CURDATE(), CURTIME(),locationid, email);
END IF;
COMMIT;
END$$
