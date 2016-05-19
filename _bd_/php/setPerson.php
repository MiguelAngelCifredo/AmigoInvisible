<?php
require_once "dbms.php";
$photo = $_POST["photo"] == "0xnull" ? "" : ", photo=" . $_POST["photo"];
$query = "update person set name='" . $_POST["name"] . "', email='" . $_POST["email"] . "'" . $photo . " where id_person=" . $_POST["id_person"];
echo $query;
echo runDML($query);
?>