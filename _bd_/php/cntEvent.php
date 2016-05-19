<?php
require_once "dbms.php";
echo runSelect("select count(*) total from event");
?>