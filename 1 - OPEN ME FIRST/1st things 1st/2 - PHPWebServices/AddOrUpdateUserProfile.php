<?php
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['name']) && isset($_POST['useremail'])) {
 
    $name = $_POST['name'];
    $interest = $_POST['interest'];
    $useremail = $_POST['useremail'];
   
   
    // include db connect class
    require_once __DIR__ . '/db_connect1.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    
   
     $query="SELECT * FROM UserProfile WHERE User_Google_Account = '$useremail'";
     $selectedResults = mysql_query($query) or die('Error, querry failed');
   
    

     if (mysql_fetch_array($selectedResults) == NULL){
          $insertresult = mysql_query("INSERT INTO UserProfile(User_Name, User_Interests, User_Google_Account) VALUES ('$name', ' $interest', '$useremail')");
                if ($insertresult) {
                // successfully inserted into database
                  $response["success"] = 1;
                 $response["message"] = "Details successfully inserted.";
 
                  // echoing JSON response
                  echo json_encode($response);
               } else {
                 // failed to insert row
                $response["success"] = 0;
                $response["message"] = "Failed to insert";
                echo json_encode($response);
                 }
                            
                   
      
     } else {
           $updateresult = mysql_query("UPDATE UserProfile SET User_Name='$name', User_Interests= '$interest', User_Google_Account='$useremail' WHERE User_Google_Account='$useremail'");
                  
                      if ($updateresult){
        $response["success"] = 1;
        $response["message"] = "Details successfully updated.";
         // echoing JSON response
        echo json_encode($response);
    } else {
         // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Failed to update";
        echo json_encode($response);
    }
                       
                 
     }//Ends else

    

} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>
	<form action="AddOrUpdateUserProfile.php" method="post">
User Name: <input type="text" name="name">
Interest: <input type="text" name="interest">
    UserMail: <input type="text" name="useremail">
<input type="submit">
</form>