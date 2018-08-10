<?php

require("include/config.php");

if (!empty($_POST)) {
  
    if (empty($_POST['username']) || empty($_POST['email'])|| empty($_POST['password'])) {
        
        
        $response["success"] = 0;
        $response["message"] = " Please enter require detail .";
        
        
        die(json_encode($response));
    }
    
  
    $query = " SELECT 1 FROM users WHERE username = :user";
    $query_params = array(
        ':user' => $_POST['username']
    );
    
    try {

        $statement   = $db->prepare($query);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {

        $response["success"] = 0;
        $response["message"] = "Database Error 1. Please Try Again!";
;
        die(json_encode($response));
    }
    

    $row = $statement->fetch();
    if ($row) {
       
        
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this username is already in use";
        die(json_encode($response));
    }
    
    
    $query = "INSERT INTO users( username, email , password ) VALUES ( :user, :eml, :pass ) ";
    

    $query_params = array(
        ':user' => $_POST['username'],
        ':eml'=>$_POST['email'],
        ':pass' => $_POST['password']
    );
    
    try {
        $statement   = $db->prepare($query);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
      
        $ex->getMessage();
        
        $response["success"] = 0;
        $response["message"] = "Database Error 2. Please Try Again!";

        die(json_encode($response));
    }
    
  
    $response["success"] = 1;
    $response["message"] = "Username Successfully Added!";

    echo json_encode($response);
    
  
    
    
} else {

}

?>