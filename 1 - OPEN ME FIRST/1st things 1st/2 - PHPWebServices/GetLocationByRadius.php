<?php
 /* 
    GetLocationByRadius is used to retrieve all the nearby location based on the user's current location (latitude and longitude) that is passed from the android application.
    Then it will retireve the data as a JSON array and return them to the app
 */

//The Database Connection
require_once __DIR__ . '/db_connect1.php';
 
// connecting to the DB
$db = new DB_CONNECT();
//Parameter(s)
  $latitude = $_GET['latitude'];
  $longitude = $_GET['longitude'];

//SLQ call to the Stored Procedure
$sql = ("call spGetLocationByRadius('$latitude','$longitude')");
//Result of MySQL Query
$result = mysql_query($sql);
//Declaring a json array
$json = array();
 
//If statement to check if the query is not empty
    if(mysql_num_rows($result)){

        //While loop to fetch every row of the query
        while($row=mysql_fetch_assoc($result)){

            //Populatign the json array
            $json['userprofiles'][]=$row;
        }//End while
    }//End if

//Close Connection
mysql_close($con);

//Echo the array
echo json_encode($json); 
?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="GetLocationByRadius.php" method="get">
Latitude: <input type="text" name="latitude">
Longitude: <input type="text" name="longitude">
<input type="submit">
</form>