<?php
require_once "dbms.php";
echo runDML("delete from wish where id_wish=" . $_GET["id_wish"]);
?>