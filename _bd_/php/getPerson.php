<?php
require_once "dbms.php";
echo runSelect("select email, name from person where id_person=" . $_GET["id_person"]);
?>