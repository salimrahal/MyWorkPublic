<?php
include("dbInfo.php");
//echo"helo";
$id=$_GET['id'];
mysql_connect(localhost,$user,$password);
mysql_select_db($database) or die( "Unable to select database");
$query=" SELECT * FROM userIp WHERE id='$id'";
$result=mysql_query($query);
$num=mysql_numrows($result);

mysql_close();

$i=0;
while ($i < $num) {
	$id = mysql_result($result, $i, "id");
	$employeName = mysql_result($result, $i, "employeName");
	$adminUser = mysql_result($result,$i,"adminUser");
	$adminPssd = mysql_result($result,$i,"adminPssd");
	$Ip1 = mysql_result($result,$i,"Ip1");
	$Ip2 = mysql_result($result,$i,"Ip2");
	$comment = mysql_result($result,$i,"Comment");


	?>
<form action="updated.php" method="post">
	<input type="hidden" name="ud_id" value="<? echo $id; ?>">
	<table>
		<tr>
			<td>Employee Full Name:</td>
			<td><input type="text" name="ud_employeName"
				value="<? echo $employeName; ?>" />
			</td>
		</tr>
		<tr>
			<td>adminUser:</td>
			<td><input type="text" name="ud_adminUser"
				value="<? echo $adminUser;?>" />
			</td>
		</tr>
		<tr>
			<td>adminPssd:</td>
			<td><input type="text" name="ud_adminPssd"
				value="<? echo $adminPssd; ?>" />
			</td>
		</tr>
		<tr>
			<td>IP - 1:</td>
			<td><input type="text" name="ud_Ip1" value="<? echo $Ip1; ?>" />
			</td>
		</tr>
		<tr>
			<td>IP - 2:</td>
			<td><input type="text" name="ud_Ip2" value="<? echo $Ip2; ?>" />
			</td>
		</tr>
		<tr>
			<td>Comment:</td>
			<td><input type="text" name="ud_comment" value="<? echo $comment; ?>" />
			</td>
		</tr>
	</table>
	<input type="Submit" value="Update" />
</form>
<?php

++$i;
}
?>