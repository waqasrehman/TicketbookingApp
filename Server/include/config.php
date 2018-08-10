
<?php

$host = "localhost";
    $dbname = "";    
   $username = "";
    $password = "";


    $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');

try{


$db = new PDO("mysql:host={$host}; dbname={$dbname}; charset=utf8",$username,$password, $options);



}catch(Exception $ex){

	die("failed to connect to database:".$ex->getMessage());

}



$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);



?>