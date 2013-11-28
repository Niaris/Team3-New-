--
-- Database: `Team3_backup`
--

-- --------------------------------------------------------
DELIMITER $$
--
-- Procedure spGetLocationByRadius, used to retrieve the Locations that are nearby the user based on the user's current location by a radius of 5 kilometers.
-- It is used by the php Script GetLocationByRadius.php
--
CREATE PROCEDURE `spGetLocationByRadius`
	(
    latitude decimal(10,8),
    longitude decimal (11,8)
    )

SELECT *, 
	( 6371 * acos( cos( radians(latitude) )
                  * cos( radians( Location_Latitude ) )
                  * cos( radians( Location_Longitude )
                        - radians(longitude) )
                  + sin( radians(latitude) )
                  * sin( radians( Location_Latitude ) ) ) ) 
AS distance
FROM Location
HAVING distance <= 5 
ORDER BY distance$$

