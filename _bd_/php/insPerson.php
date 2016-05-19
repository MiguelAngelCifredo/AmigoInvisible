<?php
require_once "dbms.php";
if ($_POST["photo"] == "0xnull"){
	$query = "insert into person (id_person, email, name) values ((select max(aux.id_person)+1 from person aux),'" . $_POST["email"] . "','"  . $_POST["name"] . "')";
}else{
	$query = "insert into person (id_person, email, name, photo) values ((select max(aux.id_person)+1 from person aux),'" . $_POST["email"] . "','"  . $_POST["name"] . "',". $_POST["photo"] . ")";
}
echo $query;
echo runDML($query);
?>