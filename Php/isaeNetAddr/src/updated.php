<?php
include("dbInfo.php");

$ud_id=$_POST['ud_id'];
$ud_employeName=$_POST['ud_employeName'];
$ud_adminUser=$_POST['ud_adminUser'];
$ud_adminPssd=$_POST['ud_adminPssd'];
$ud_Ip1=$_POST['ud_Ip1'];
$ud_Ip2=$_POST['ud_Ip2'];
$ud_comment=$_POST['ud_comment'];

mysql_connect(localhost,$user,$password);
//selecting DB should not be called every time i want to execute query
@mysql_select_db($database) or die( "Unable to select database");

$query="UPDATE userIp SET employeName='$ud_employeName', adminUser='$ud_adminUser', adminPssd='$ud_adminPssd', Ip1='$ud_Ip1', Ip2='$ud_Ip2', comment='$ud_comment' WHERE id='$ud_id'";
//echo $query;
mysql_query($query);
echo "Record Updated";
mysql_close();
?>
<br>
<a href="output.php">return</a>