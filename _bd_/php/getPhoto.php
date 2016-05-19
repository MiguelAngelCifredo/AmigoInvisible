<?php
require_once "dbms.php";
$respuesta = getFieldPhoto("select photo from " . $_GET["source"] . " where id_" . $_GET["source"] . "=" . $_GET["id"]);
//header('Content-type:image/gif');
//echo $respuesta;
echo(base64_encode($respuesta));
?>