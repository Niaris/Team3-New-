<?php
 
/*
 * AddUsers.php will add a user to the database.
 */
 
  //Creating the Response Array
$response = array();
 
// Check if required data are passed successfully
if (isset($_POST['name']) && isset($_POST['emailAddress'])) {
 
    //Parameter(s)
    $name = $_POST['name'];
    $email = $_POST['emailAddress'];
   
   
    // The Database Connection
    require_once __DIR__ . '/db_connect1.php';
 

    // Connecting to the Database
    $db = new DB_CONNECT();

    //SQL Select Query to make check based on IF and ELSE statement
    $query="SELECT * FROM Users WHERE User_EmailAddress = '$email'";
     //Passing the Query above to "selectedResults"
     $selectedResults = mysql_query($query) or die('Error, querry failed');

       //IF Statement to check if the results are null
        if (mysql_fetch_array($selectedResults) == NULL){
               //IF the results are null then the following SQL Query will add the User
              $result = mysql_query("INSERT INTO Users(User_Fname, User_EmailAddress) VALUES('$name', '$email')");
 
                 
                if ($result) {
                // Successfully added the User 
                $response["success"] = 1;
                $response["message"] = "User successfully added.";
 
                // Echoing JSON response
                echo json_encode($response);
        } else {
                // If it the success response is 0 then an error has ccoured
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred.";
 
                // Echoing JSON response
                echo json_encode($response);
         }
             } else {
                 // Or User already exists
                  $response1["success"] = 0;
                    $response1["message"] = "User Exists.";
                     echo json_encode($response1);
             }
                } else {
                    // Or the required fields are missing
                    $response["success"] = 0;
                    $response["message"] = "Required field(s) is missing";
 
                    // Echoing JSON response
                    echo json_encode($response);
                }
?>
<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
<form action="AddUsers.php" method="post">
Name: <input type="text" name="name">
Email: <input type="text" name="emailAddress">
<input type="submit">
</form>
	