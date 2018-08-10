<?php

require("include/config.php");

if (!empty($_POST)) {

    $query = " SELECT * FROM purchase WHERE username = :username  " ;
    
    $query_params = array(
        ':username' => $_POST['username'],


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
    
    $result = $statement->fetchAll();

    if($result){

            $datareturn =array();

            foreach($result as $rows){
        $json = array();
        $json["title"] = $rows["title"];
        $json["venue"]= $rows["venue"];
        $json["price"] = $rows["price"];



        array_push ($datareturn,$json);

            

            }
     echo stripcslashes(json_encode($datareturn, JSON_PRETTY_PRINT));



        




    }




} else {

}

?>