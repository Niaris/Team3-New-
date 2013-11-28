<?php
 
/*
 * Following code will get User
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/php/database/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET['emailAddress'])) 
{ 

    $email = $_POST['emailAddress'];
 
    // get a User record from table
    $result = mysql_query("SELECT * FROM Users WHERE User_EmailAddress = $email");
 
    if (!empty($result)) 
    {
        // check for empty result
        if (mysql_num_rows($result) > 0) 
        {
 
            $result = mysql_fetch_array($result);
 
            $User = array();
            $User["UserEmailAddress"] = $result["User_EmailAddress"];
            $User["UserID"] = $result["User_ID"];
            $User["UserName"] = $result["User_Fname"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["User"] = array();
 
            array_push($response["User"], $User);
 
            // echoing JSON response
            echo json_encode($response);
        } 
        else 
        {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No User found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } 
    else 
    {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No User found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} 
else 
{
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>