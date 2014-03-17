<?php
include("dbInfo.php");

mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT * FROM userIp";
$result=mysql_query($query);

$num=mysql_numrows($result);

mysql_close();

//echo "<b><center>Database Output</center></b><br><br>";
?>
<html>

<body id="index-content">
 <header>
   <link rel="stylesheet" type="text/css" href="background.css" /> 
   </header>

<a href="insert.php">Add user</a>
<table border="0" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">Employee Name</font></th>
<th><font face="Arial, Helvetica, sans-serif">AdminUser</font></th>
<th><font face="Arial, Helvetica, sans-serif">AdminPssd</font></th>
<th><font face="Arial, Helvetica, sans-serif">Ip1</font></th>
<th><font face="Arial, Helvetica, sans-serif">Ip2</font></th>
<th><font face="Arial, Helvetica, sans-serif">Comment</font></th>
</tr>

<?php
if($num == 0){
	echo "no data to display";
}
else{
	$i =0;
	while ($i < $num) {
		$id = mysql_result($result, $i, "id");
	    $employeName = mysql_result($result, $i, "employeName");
		$adminUser = mysql_result($result,$i,"adminUser");
		$adminPssd = mysql_result($result,$i,"adminPssd");
		$Ip1 = mysql_result($result,$i,"Ip1");
		$Ip2 = mysql_result($result,$i,"Ip2");
		$comment = mysql_result($result,$i,"Comment");
		?>
	<tr>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $employeName; ?>
		</font></td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $adminUser; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $adminPssd; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $Ip1; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $Ip2; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $comment; ?> </font>
		</td>
			<td><font face="Arial, Helvetica, sans-serif"><a
				href="populateRecord.php?id=<?php echo $id?>">Update</a></font></td>
	    <td><font face="Arial, Helvetica, sans-serif"><a
				href="deleteRecord.php?id=<?php echo $id?>">Delete</a></font></td>
	</tr>
	
	<?
	$i++;
	}
		
}
?>
</table>
</body>
</html>