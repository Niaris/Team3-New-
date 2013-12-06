<?php
 /* 
    GetFavourites is used to retrieve the locations that a specific user has liked.
    Then it will retrieve the data as a JSON array and return them to the app
 */

//The Database Connection
require_once __DIR__ . '/db_connect1.php';
 
// connecting to the DB
$db = new DB_CONNECT();
//Parameter(s)
  $useremail = $_GET['useremail'];

//SQL call to the Stored Procedure
$sql = ("SELECT Location_Address, Location_Name, Location_ID FROM vLocLikes WHERE User_EmailAddress = '$useremail'");
//Result of MySQL Query
$result = mysql_query($sql);
//Declaring a json array
$json = array();
 
//If statement to check if the query is not empty
    if(mysql_num_rows($result)){

        //While loop to fetch every row of the query
        while($row=mysql_fetch_assoc($result)){

            //Populatign the json array
            $json['Favourites'][]=$row;
        }//End while
    }//End if


//Echo the array
echo json_encode($json); 
?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="GetFavourites.php" method="get">
User Email: <input type="text" name="useremail">
<input type="submit">
</form>