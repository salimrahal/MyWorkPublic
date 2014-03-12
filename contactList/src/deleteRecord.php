<?php
$ud_id=$_GET['id'];
//echo $ud_id;
$username="root";
$password="root";
$database="contactDB";
mysql_connect(localhost,$username,$password);
//selecting DB should not be called every time i want to execute query
@mysql_select_db($database) or die( "Unable to select database");

$query="delete from contacts WHERE id='$ud_id'";
//echo $query;
mysql_query($query);
echo "Record Deleted";
mysql_close();
?>