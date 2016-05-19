<?php
require_once "dbms.php";
$query = "insert into participant (id_participant, id_event, id_person) values ((select max(aux.id_participant)+1 from participant aux)," . $_POST["id_event"] . ","  . $_POST["id_person"] . ")";
echo $query;
echo runDML($query);
?>