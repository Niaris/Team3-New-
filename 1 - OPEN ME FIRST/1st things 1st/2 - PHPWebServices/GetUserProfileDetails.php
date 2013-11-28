<?php
require_once __DIR__ . '/db_connect1.php';
 
// connecting to db
$db = new DB_CONNECT();
  $useremail = $_POST['useremail'];


$sql = "SELECT * FROM UserProfile WHERE User_Google_Account =('$useremail') "; 
$result = mysql_query($sql);
$json = array();
 
if(mysql_num_rows($result)){
while($row=mysql_fetch_assoc($result)){
$json['userprofiles'][]=$row;
}
}
mysql_close($con);
echo json_encode($json); 
?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="GetUserProfileDetails.php" method="get">
User Email: <input type="text" name="useremail">
<input type="submit">
</form>