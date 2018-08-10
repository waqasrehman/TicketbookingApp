<?php

require("include/config.php");

if (!empty($_POST)) {

    $query = " SELECT username , password FROM users WHERE username = :username And password =:password " ;
    
    $query_params = array(
        ':username' => $_POST['username'],
        ':password' => $_POST['password']

            );
    
    try {
        $statement   = $db->prepare($query);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
        

        $response["success"] = 0;
        $response["message"] = "Database query error";
        die(json_encode($response));
        
    }
    
   // validation set to false initailly 
    $validated_info = false;
    
    $row = $statement->fetch();
    if ($row) {
       


       // password encrytion will be added here later 

        if ($_POST['password'] === $row['password']) {
            $login_successful = true;
        }else{

            $login_successful= false;
        }
    }
    
   
// if login detail match return json success as 1 else 0 and display relevant message 

    if ($login_successful) {
        $response["success"] = 1;
        $response["message"] = "Login successful!";

        die(json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
} else {
    
}

?>