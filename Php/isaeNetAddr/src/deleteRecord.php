<?php
$ud_id=$_GET['id'];
//echo $ud_id;
include('dbInfo.php');
mysql_connect(localhost,$user,$password);
//selecting DB should not be called every time i want to execute query
@mysql_select_db($database) or die( "Unable to select database");

$query="delete from userIp WHERE id='$ud_id'";
//echo $query;
mysql_query($query);
echo "Record Deleted";
mysql_close();
?>
<br>
<a href="output.php">return</a>