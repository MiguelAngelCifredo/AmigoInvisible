<?php
require_once "dbms.php";
echo runSelect("select count(*) total from participant where id_event=" . $_GET["id_event"] . " and id_person=" . $_GET["id_person"] );
?>