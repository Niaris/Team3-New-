<?php
 /* 
    The GetReviewsBasedOnLocation is used to retrieve all the reviews based on the locaiton ID that will be passed from the android applicaition.
    Then it will retireve the data as a JSON array and return them to the app
 */

//The Database Connection
require_once __DIR__ . '/db_connect1.php';
 
// Connecting to DB
$db = new DB_CONNECT();
//Parameter(s)
  $locationid = $_GET['locationid'];

//SQL Query 
    $sql = "SELECT Review_Comment, (SELECT ROUND(AVG(Review_Rating)) FROM vLocationReviews1  WHERE Location_ID = '$locationid') AS Review_AVG_Rating, Review_Rating, Review_Date, Review_Time, User_Email FROM vLocationReviews1 WHERE Location_ID = '$locationid'"; 
    $sql1 = "SELECT User_EmailAddress FROM vLocLikes WHERE Location_ID ='$locationid'";
    $sql2 = "SELECT SUM(Likes) AS Likes_Sum FROM vLocLikes WHERE Location_ID = '$locationid'";
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


    //Result of MySQL Query2
    $result1 = mysql_query($sql1);
//Declaring A Json Array
    $json1 = array();
 
//If statement to check if the query is not empty
    if(mysql_num_rows($result1)){

        //While loop to fetch every row of the query
        while($row1=mysql_fetch_assoc($result1)){

            //Populatign the json array
            $json['Likes'][]=$row1;
        }//End while

    }//End if

        //Result of MySQL Query3
    $result2 = mysql_query($sql2);
//Declaring A Json Array
    $json2 = array();
 
//If statement to check if the query is not empty
    if(mysql_num_rows($result2)){

        //While loop to fetch every row of the query
        while($row2=mysql_fetch_assoc($result2)){

            //Populatign the json array
            $json['SumLikes'][]=$row2;
        }//End while

    }//End if

//Close Connection
mysql_close($con);

//Echo the array
echo json_encode($json); 
?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="GetRevLikesSumOfLikes.php" method="get">
Location ID: <input type="text" name="locationid">
<input type="submit">
</form>