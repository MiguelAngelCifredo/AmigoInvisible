<?php
require_once "dbms.php";
echo runSelect("select text, description, bought from wish where id_wish=" . $_GET["id_wish"]);
?>