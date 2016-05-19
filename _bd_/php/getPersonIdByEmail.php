<?php
require_once "dbms.php";
echo runSelect("select id_person from person where email='" . $_GET["email"] . "'");
?>