<?php
$ud_id=$_POST['ud_id'];
$ud_first=$_POST['ud_first'];
$ud_last=$_POST['ud_last'];
$ud_phone=$_POST['ud_phone'];
$ud_mobile=$_POST['ud_mobile'];
$ud_fax=$_POST['ud_fax'];
$ud_email=$_POST['ud_email'];
$ud_web=$_POST['ud_web'];

$username="root";
$password="root";
$database="contactDB";
mysql_connect(localhost,$username,$password);
//selecting DB should not be called every time i want to execute query
@mysql_select_db($database) or die( "Unable to select database");

$query="UPDATE contacts SET first='$ud_first', last='$ud_last', phone='$ud_phone', mobile='$ud_mobile', fax='$ud_fax', email='$ud_email', web='$ud_web' WHERE id='$ud_id'";
//echo $query;
mysql_query($query);
echo "Record Updated";
mysql_close();
?>