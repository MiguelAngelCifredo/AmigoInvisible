<?php
require_once "dbms.php";
$query = "select participant.id_participant, person.id_person, person.email, person.name, participant.friend from participant, person where participant.id_event=" . $_GET["id_event"] . " and participant.id_person=person.id_person order by person.name";
echo runSelect($query);
?>