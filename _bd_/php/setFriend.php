<?php
require_once "dbms.php";
$query = "update participant set friend=" . $_POST["friend"] . " where id_participant=" . $_POST["id_participant"];
echo $query;
echo runDML($query);
?>