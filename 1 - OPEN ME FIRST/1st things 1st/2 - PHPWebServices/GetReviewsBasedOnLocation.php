<?php
 /* 
    The GetReviewsBasedOnLocation is used to retrieve all the reviews based on the locaiton ID that will be passed from the android applicaition.
    Then it will retireve the data as a JSON array and return them to the app
 */

//The Database Connection
require_once __DIR__ . '/db_connect1.php';
 
// connecting to DB
$db = new DB_CONNECT();
//Parameter(s)
  $locationid = $_GET['locationid'];

//SQL Query
    $sql = "SELECT Review_Comment, Review_Rating, Review_Date, Review_Time, User_Email FROM vLocationReviews WHERE Location_ID = '$locationid'"; 
//Result of MySQL Query
    $result = mysql_query($sql);
//Declaring A Json Array
    $json = array();
 
//If statement to check if the query is not empty
    if(mysql_num_rows($result)){

        //While loop to fetch every row of the query
        while($row=mysql_fetch_assoc($result)){

            //Populatign the json array
            $json['review'][]=$row;
        }//End while

    }//End if

//Close Connection
mysql_close($con);

//Echo the array
echo json_encode($json); 
?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="GetReviewsBasedOnLocation.php" method="get">
Location ID: <input type="text" name="locationid">
<input type="submit">
</form>
