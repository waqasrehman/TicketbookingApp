<?php

require("include/config.php");

if (!empty($_POST)) {

    $query = " SELECT * FROM movies WHERE  Title    = :SearchData  or Genre = :SearchData  or year = :SearchData"  ;
    
    $query_params = array(
        ':SearchData' => $_POST['SearchData'],


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
        $json["Title"] = $rows["Title"];
        $json["Image"] = $rows["Image"];
        $json["rating"]= $rows["rating"];
        $json["Price"] = $rows["Price"];
        $json["Description"] = $rows["Description"];
        $json["Venue"] = $rows["Venue"];
        $json["Available"] = $rows["Available"];
        $json["Genre"] = $rows["Genre"];



        array_push ($datareturn,$json);

            

            }
     echo stripcslashes(json_encode($datareturn, JSON_PRETTY_PRINT));



        




    }




} else {



}

?>