<?php
/*
* GetUserProfileDetails.php retrieves all the User Profile Details based on the User Email 
*/

//The Database Connection
require_once __DIR__ . '/db_connect1.php';
 
// Connecting to db
$db = new DB_CONNECT();
//Parameter
  $useremail = $_GET['useremail'];

//SQL Querythat will retrieve all the user profile details based on the google account (user email)
$sql = "SELECT * FROM UserProfile WHERE User_Google_Account =('$useremail') "; 
//Adding the result into a JSON Array
$result = mysql_query($sql);
$json = array();
 
//If statement to check if there is a result
if(mysql_num_rows($result)){
    //While loop to retrieve everything that has the email that is passed
while($row=mysql_fetch_assoc($result)){
//Populating the JSON Array
$json['userprofiles'][]=$row;
}
}
//Echoing the JSON Array
echo json_encode($json); 
?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="GetUserProfileDetails.php" method="get">
User Email: <input type="text" name="useremail">
<input type="submit">
</form>