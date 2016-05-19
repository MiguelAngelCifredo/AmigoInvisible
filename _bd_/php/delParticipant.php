<?php
require_once "dbms.php";
echo runDML("delete from participant where id_participant=" . $_GET["id_participant"]);
?>