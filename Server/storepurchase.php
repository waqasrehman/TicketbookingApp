<?php

require("include/config.php");

if (!empty($_POST)) {
  
    if (empty($_POST['title']) || empty($_POST['venue'])|| empty($_POST['username'])||empty($_POST['price'])) {
        
        
        $response["success"] = 0;
        $response["message"] = "data error";
        
        
        die(json_encode($response));
    }
    
  
    
    
    $query = "INSERT INTO purchase( username, title , venue, price ) VALUES ( :username, :title, :venue,:price ) ";

    

    $query_params = array(
        ':username' => $_POST['username'],
        ':venue'=>$_POST['venue'],
        ':price' => $_POST['price'],
        ':title' =>$_POST["title"],
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
    $response["message"] = "purchase  Successfully Added!";

    echo json_encode($response);


    $query2 ="UPDATE movies SET Available  =Available-1  WHERE Venue = :venue and Available > 0";

    $query_params = array(
        ':venue'=>$_POST['venue'],
      
    );
    
    try {
        $statement   = $db->prepare($query2);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
      
        $ex->getMessage();
        
        $response["success"] = 0;
        $response["message"] = "Database Error 2. Please Try Again!";

        die(json_encode($response));
    }
    
  
    $response["success"] = 1;
    $response["message"] = " ticket Available updated!";

    echo json_encode($response);
  
    
    
} else {

}

?>