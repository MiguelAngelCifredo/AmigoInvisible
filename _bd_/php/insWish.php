<?php
require_once "dbms.php";
if ($_POST["photo"] == "0xnull"){
	$query = "insert into wish (id_wish, id_person, text, description, bought) values ((select max(w.id_wish)+1 from wish w)," . $_POST["id_person"] . ",'"  . $_POST["text"] . "','". $_POST["description"] . "','N')";
}else{
	$query = "insert into wish (id_wish, id_person, text, description, bought, photo) values ((select max(w.id_wish)+1 from wish w)," . $_POST["id_person"] . ",'"  . $_POST["text"] . "','". $_POST["description"] . "','N', " . $_POST["photo"] . ")";
}	
echo $query;
echo runDML($query);
?>