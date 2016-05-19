<?php
require_once "dbms.php";
$query = "select id_event, name, date, place, max_price, min_price, id_admin from event where id_event in ( select id_event from participant where id_person=" . $_GET["id_person"] .")";
echo runSelect($query);
?>