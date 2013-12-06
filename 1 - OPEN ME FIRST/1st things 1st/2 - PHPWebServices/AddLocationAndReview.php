
<?php
 /* 
    AddLocationAndReview is used to add a Location and Review or just a Review if the location exists based on the parameters that are passed
    Then it will retrieve the response.
 */

 //Creating the Response Array
$response = array();
 
// Check if required data are passed successfully
if (isset($_POST['name'])) {
        
        //Parameter(s)
        $comment = $_POST['comment'];
		$rating = $_POST['rating']; 
        $name = $_POST['name'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
		$address = $_POST['address'];
        $email = $_POST['email'];
        $locationid = $_POST['locationid'];

   
   
     // The Database Connection
    require_once __DIR__ . '/db_connect1.php';
 
    // Connecting to the Database
    $db = new DB_CONNECT();
    
     //SQL Select Query to make check based on IF and ELSE statement
     $query="SELECT * FROM Location WHERE Location_Latitude = '$latitude' AND Location_Longitude = '$longitude'";
     //Passing the Query above to "selectedResults"
     $selectedResults = mysql_query($query) or die('Error, querry failed');

     //IF Statement to check if the results are null
     if (mysql_fetch_array($selectedResults) == NULL){
         //If the result is null then a call will be made to StoredProcedure that will add both locaiton and review
         $insertresult = mysql_query("call spAddLocAndRev
         ('$name','$latitude','$longitude','$address','$comment','$rating', '$email', '$locationid')");
         
                                     
         if ($insertresult) {
                // Successfully added the passed information
                  $response["success"] = 1;
                 $response["message"] = "Details successfully inserted.";
 
                  // Echoing JSON response
                  echo json_encode($response);
               } else {
                 // If it the success response is 0 then an error has ccoured. Here we response the mysql error as well
                $response["success"] = 0;
                $response["message"] = "Failed to insert" . mysql_error() . " sad";
                echo json_encode($response);
               }

     }
     else {
         //If the results are not null then the same call will be made only this time the SP will just add the review
        $insertresult = mysql_query("call spAddLocAndRev
         ('$name','$latitude','$longitude','$address','$comment','$rating', '$email', '$locationid')");
        
                                     
         if ($insertresult) {
                // Successfully added the passed information
                  $response["success"] = 1;
                 $response["message"] = "Details successfully updated.";
 
                  // Echoing JSON response
                  echo json_encode($response);
               } else {
                 // If it the success response is 0 then an error has ccoured. Here we response the mysql error as well
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

