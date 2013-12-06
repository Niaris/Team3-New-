<?php

 /* 
    AddLike is used to add a like based on the User (useremail) and Location (locaitonid)
    Then it will retrieve the response.
 */

 //Creating the Response Array
$response = array();

// Check if required data are passed successfully
if (isset($_POST['locationid']) && isset($_POST['useremail'])) {
	
    //Parameter(s)
	$locationid = $_POST['locationid'];
	$useremail = $_POST['useremail'];
	$like = '1';
	
	// The Database Connection
	require_once __DIR__ . '/db_connect1.php';
	
	// Connecting to the Database
	$db = new DB_CONNECT();
	
	//SQL Select Query to make check based on IF and ELSE statement
	$query="SELECT User_Email, Location_ID FROM Likes WHERE User_Email = '$useremail' AND Location_ID = '$locationid'";
    //Passing the Query above to "selectedResults"
	$selectedResults = mysql_query($query) or die('Error, querry failed');
	
	
    //IF Statement to check if the results are null
	if (mysql_fetch_array($selectedResults) == NULL){
        //IF the results are null then the following SQL Query will add the like
		$insertresult = mysql_query("INSERT INTO Likes (Likes, User_Email, Location_ID) VALUES ('$like', '$useremail', '$locationid')");
		if ($insertresult) {
			// Successfully added the like
			$response["success"] = 1;
			$response["message"] = "Details successfully inserted.";
			
			// Echoing the JSON response
			echo json_encode($response);
		} else {
			// If it the success response is 0 then an error has ccoured
			$response["success"] = 0;
			$response["message"] = "Failed to insert";
			echo json_encode($response);
		}
		
		
		
	} else {
		
			// Or the like already exists!
			$response["success"] = 0;
			$response["message"] = "Like already exists";
			echo json_encode($response);
		
		
	}//Ends else

	

} else {
	//Or required field is missing
	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing";
	
	// Echoing JSON response
	echo json_encode($response);
}
?>
<!-- THIS FORM IS TO TEST THAT THE SCRIPT WORKS BEFORE IMPLEMENTING IT IN THE ANDROID APP -->
	<form action="AddLike.php" method="post">
Location ID: <input type="text" name="locationid">
    UserMail: <input type="text" name="useremail">
<input type="submit">
</form>