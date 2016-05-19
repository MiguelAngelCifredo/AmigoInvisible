<?php

function abrirConexion () {
	/*
	Conexión con Hostinger [asd.hol.es]
	DataBase:	u140081066_amigo
	Usuario:	u140081066_cm
	Password:	cm2016
	*/

//$conn = new mysqli('mysql.hostinger.es', 'u140081066_cm', 'cm2016', 'u140081066_amigo');
 $conn = new mysqli('localhost', 'root', '', 'amigo');

	$conn->set_charset("utf8");
	$conn->autocommit(TRUE);
	return $conn;
}

function cerrarConexion($conn) {
	$conn = null;
	return null;
}

function runSelect($query){
	$conn = abrirConexion();
	$result = $conn->query($query);
	cerrarConexion($conn);
	$datos = array();
	while($reg = $result->fetch_object()){
		$datos[] = $reg;
	}
	return json_encode($datos);
}

function runDML($query){
	$conn = abrirConexion();
	$conn->query($query);
	cerrarConexion($conn);
}

/*
function getRecord ($stmt) {
	$data=null;
	foreach($stmt as $row) { $data=$row; }
	return $data;		
}
*/
function getFieldPhoto($query) {
	$conn = abrirConexion();
	$result = $conn->query($query);
	cerrarConexion($conn);
	$reg = $result->fetch_object();
	return $reg->photo;
}

?>