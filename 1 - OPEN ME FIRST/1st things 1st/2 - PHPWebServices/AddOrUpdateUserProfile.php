<?php
  /* 
    AddOrUpdateUserProfile is used to add or update a user pofile 
    Then it will retrieve the response.
 */

  //Creating the Response Array
$response = array();
 
// Check if required data are passed successfully
if (isset($_POST['name']) && isset($_POST['useremail'])) {
 
    //Parameter(s)
    $name = $_POST['name'];
    $interest = $_POST['interest'];
    $useremail = $_POST['useremail'];
   
   
    // The Database Connection
    require_once __DIR__ . '/db_connect1.php';
 
    // Connecting to the Database
    $db = new DB_CONNECT();
    
    //SQL Select Query to make check based on IF and ELSE statement
     $query="SELECT * FROM UserProfile WHERE User_Google_Account = '$useremail'";
     //Passing the Query above to "selectedResults"
     $selectedResults = mysql_query($query) or die('Error, querry failed');
   
    
      //IF Statement to check if the results are null
     if (mysql_fetch_array($selectedResults) == NULL){
           //IF the results are null then the following SQL Query will add the UserProfile
          $insertresult = mysql_query("INSERT INTO UserProfile(User_Name, User_Interests, User_Google_Account) VALUES ('$name', ' $interest', '$useremail')");
                if ($insertresult) {
                // Successfully added User Profile
                  $response["success"] = 1;
                 $response["message"] = "Details successfully inserted.";
 
                  // Echoing JSON response
                  echo json_encode($response);
               } else {
                 // If it the success response is 0 then an error has ccoured
                $response["success"] = 0;
                $response["message"] = "Failed to insert";
                echo json_encode($response);
                 }
                            
                   
      
     } else {
         //Else statement to execute query that it will instead Update the User Profile based on the User's google account (email)
           $updateresult = mysql_query("UPDATE UserProfile SET User_Name='$name', User_Interests= '$interest', User_Google_Account='$useremail' WHERE User_Google_Account='$useremail'");
                  
        if ($updateresult){
        // Successfully added User Profile
        $response["success"] = 1;
        $response["message"] = "Details successfully updated.";

         // Echoing JSON response
        echo json_encode($response);
    } else {
          // If it the success response is 0 then an error has ccoured
        $response["success"] = 0;
        $response["message"] = "Failed to update";
        echo json_encode($response);
    }
                       
                 
     }//Ends else

    

} else {
    // Or Required Fields are missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // Echoing JSON response
    echo json_encode($response);
}
?>
<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
	<form action="AddOrUpdateUserProfile.php" method="post">
User Name: <input type="text" name="name">
Interest: <input type="text" name="interest">
    UserMail: <input type="text" name="useremail">
<input type="submit">
</form>