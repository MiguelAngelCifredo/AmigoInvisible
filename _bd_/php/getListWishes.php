<?php
require_once "dbms.php";
echo runSelect("select id_wish, text, description, bought from wish where id_person=" . $_GET["id_person"] . " order by bought desc, text asc");
?>