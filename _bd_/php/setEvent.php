<?php
require_once "dbms.php";
$photo = $_POST["photo"] == "0xnull" ? "" : ", photo=" . $_POST["photo"];
$query = "update event set name='" . $_POST["name"] . "', date='" . $_POST["date"] . "', place='" . $_POST["place"] . "', max_price=" . $_POST["max_price"] . $photo . " where id_event=" . $_POST["id_event"];
echo $query;
echo runDML($query);
?>