
<?php

// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['name'])) {
    //&& isset( $_POST['rating'] ) && isset( $_POST['publisher']
     //&& isset($_POST['name']) && isset( $_POST['latitude'] ) && isset( $_POST['longitude'] ) && isset( $_POST['address'] && isset['email'] ) 

        $comment = $_POST['comment'];
		$rating = $_POST['rating']; 
        $name = $_POST['name'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
		$address = $_POST['address'];
        $email = $_POST['email'];
        $locationid = $_POST['locationid'];

   
   
    // include db connect class
    require_once __DIR__ . '/db_connect1.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    
   
     $query="SELECT * FROM Location WHERE Location_Latitude = '$latitude' AND Location_Longitude = '$longitude'";
     $selectedResults = mysql_query($query) or die('Error, querry failed');

     if (mysql_fetch_array($selectedResults) == NULL){
         $insertresult = mysql_query("call spAddLocAndRev
         ('$name','$latitude','$longitude','$address','$comment','$rating', '$email', '$locationid')");
         //'$name','$latitude','$longitude','$address','$comment','$rating', '$email'
                                     
         if ($insertresult) {
                // successfully inserted into database
                  $response["success"] = 1;
                 $response["message"] = "Details successfully inserted.";
 
                  // echoing JSON response
                  echo json_encode($response);
               } else {
                 // failed to insert row
                $response["success"] = 0;
                $response["message"] = "Failed to insert" . mysql_error() . " sad";
                echo json_encode($response);
               }

     }
     else {
        $insertresult = mysql_query("call spAddLocAndRev
         ('$name','$latitude','$longitude','$address','$comment','$rating', '$email', '$locationid')");
         //'$name','$latitude','$longitude','$address','$comment','$rating', '$email'
                                     
         if ($insertresult) {
                // successfully inserted into database
                  $response["success"] = 1;
                 $response["message"] = "Details successfully updated.";
 
                  // echoing JSON response
                  echo json_encode($response);
               } else {
                 // failed to insert row
                $response["success"] = 0;
                $response["message"] = "Failed to insert" . mysql_error() . " sad";
                echo json_encode($response);
               }
     }
}

     ?>

<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="AddLocationAndReview.php" method="post">
Comment: <input type="text" name="comment">
    Rating: <input type="text" name="rating">
    Name: <input type="text" name="name">
    Latitude: <input type="text" name="latitude">
    Longitude: <input type="text" name="longitude">
    Address: <input type="text" name="address">
    Email: <input type="text" name="email">
    ID: <input type="text" name="locationid">
<input type="submit">
</form>

