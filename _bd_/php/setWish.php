<?php
require_once "dbms.php";
$photo = $_POST["photo"] == "0xnull" ? "" : ", photo=" . $_POST["photo"];
$query = "update wish set text='" . $_POST["text"] . "', description='" . $_POST["description"] . "'" . $photo . " where id_wish=" . $_POST["id_wish"];
echo $query;
echo runDML($query);
?>