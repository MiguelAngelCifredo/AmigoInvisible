<?php
require_once "dbms.php";
$query = "update event set photo=" . $_POST["photo"] . " where id_event=" . $_POST["id_event"];
echo $query;
echo runDML($query);
?>