
<?php
 
/**
 * A class file to connect to database
 */
class DB_CONNECT {
 
    // Constructor
    function __construct() {
        // Connecting to Database
        $this->connect();
    }
 
    // Destructor
    function __destruct() {
        // Closing db connection
        $this->close();
    }
 
    /**
     * Function to connect with database
     */
    function connect() {
        // Import database connection variables
        require_once __DIR__ . '/db_config1.php';
 
        // Connecting to mysql database
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
 
        // Selecing database
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());
 
        // Returing connection cursor
        return $con;
    }
 
    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysql_close();
    }
 
}
 
?>