<?php
require_once "dbms.php";
$query = "select friend from participant where id_event=" . $_GET["id_event"] . " and id_person=" . $_GET["id_person"];
echo runSelect($query);
?>