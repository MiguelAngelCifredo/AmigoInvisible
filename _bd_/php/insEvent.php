<?php
require_once "dbms.php";
if ($_POST["photo"] == "0xnull"){
	$query = "insert into event (id_event, name, date, place, max_price, min_price, id_admin) values ((select max(aux.id_event)+1 from event aux),'" . $_POST["name"] . "','"  . $_POST["date"] . "','"  . $_POST["place"] . "',"  . $_POST["max_price"] .",0," . $_POST["id_admin"] . ")";
}else{
	$query = "insert into event (id_event, name, date, place, max_price, min_price, id_admin, photo) values ((select max(aux.id_event)+1 from event aux),'" . $_POST["name"] . "','"  . $_POST["date"] . "','"  . $_POST["place"] . "',"  . $_POST["max_price"] .",0," . $_POST["id_admin"] . "," . $_POST["photo"] . ")";
}
echo $query;
echo runDML($query);
?>