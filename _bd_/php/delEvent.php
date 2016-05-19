<?php
require_once "dbms.php";
echo runDML("delete from participant where id_event=" . $_GET["id_event"]);
echo runDML("delete from event where id_event=" . $_GET["id_event"]);
?>