<?php

require("include/config.php");

if (!empty($_POST)) {
  
    if (empty($_POST['email'])|| empty($_POST['newpassword'])) {
        
        
        $response["success"] = 0;
        $response["message"] = " Please enter require detail .";
        
        
        die(json_encode($response));
    }
    
  
    $query = " SELECT 1 FROM users WHERE email = :email ";
    $query_params = array(
        ':email' => $_POST['email'],
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
    if (!$row) {

         
        $response["success"] = 0;
        $response["message"] = "email doesnt exist";
    
        
    }
    
    
      $query = "UPDATE users SET password =:newpassword WHERE email= :email";
    

    $query_params = array(
        ':newpassword'=>$_POST['newpassword'],
        ':email' => $_POST['email']

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
    $response["message"] = "password  changed  successfully !";

    echo json_encode($response);
  
    
    
} else {

  

}

?>