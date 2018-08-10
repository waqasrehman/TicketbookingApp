<?php

require("include/config.php");

if (!empty($_POST)) {
  
    if (empty($_POST['username']) || empty($_POST['currentEmail'])|| empty($_POST['newusername'])) {
        
        
        $response["success"] = 0;
        $response["message"] = " Please enter require detail .";
        
        
        die(json_encode($response));
    }
    
  
    $query = " SELECT 1 FROM users WHERE username = :username  AND email = :currentEmail";
    $query_params = array(
        ':username' => $_POST['username'],
        ':currentEmail' => $_POST['currentEmail']
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
        $response["message"] = "username doesnt exist";
    
        
    }
    
    
      $query = "UPDATE users SET username =:newusername WHERE email= :currentEmail";
    

    $query_params = array(
        ':newusername'=>$_POST['newusername'],
        ':currentEmail' => $_POST['currentEmail']

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
    $response["message"] = "username  changed  successfully !";

    echo json_encode($response);
  
    
    
} else {

  

}

?>