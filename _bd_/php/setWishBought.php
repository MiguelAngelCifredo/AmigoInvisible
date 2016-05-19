<?php
require_once "dbms.php";
echo runSelect("update wish set bought='" . $_GET["bought"] . "' where id_wish=" . $_GET["id_wish"]);
?>