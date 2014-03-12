<?
include("dbInfo.php");
$employeName=$_POST['employeName'];
$adminUser=$_POST['adminUser'];
$adminPssd=$_POST['adminPssd'];
$Ip1=$_POST['Ip1'];
$Ip2=$_POST['Ip2'];
$comment=$_POST['comment'];
if(isset($employeName) && isset($adminUser) && isset($adminPssd) && isset($Ip1))
{
 mysql_connect(localhost,$user,$password);
 @mysql_select_db($database) or die( "Unable to select database");
 $query = "INSERT INTO userIp(employeName,adminUser,adminPssd,Ip1,Ip2,comment)
 VALUES ('$employeName','$adminUser','$adminPssd','$Ip1','$Ip2','$comment')";
// echo $query;
 mysql_query($query);

 mysql_close();
}
else{
	echo"<b>New Entry</b>";
}
?>

<form action="insert.php" method="post">
	<table>
		<tr>
			<td>Employee Full Name:</td>
			<td><input type="text" name="employeName"/>
			</td>
		</tr>
		<tr>
			<td>adminUser:</td>
			<td><input type="text" name="adminUser"/>
			</td>
		</tr>
		<tr>
			<td>adminPssd:</td>
			<td><input type="text" name="adminPssd"/>
			</td>
		</tr>
		<tr>
			<td>IP - 1:</td>
			<td><input type="text" name="Ip1"/>
			</td>
		</tr>
		<tr>
			<td>IP - 2:</td>
			<td><input type="text" name="Ip2"/>
			</td>
		</tr>
		<tr>
			<td>Comment:</td>
			<td><input type="text" name="comment">
			</td>
		</tr>
	</table>
	<input type="Submit" value="Submit">
</form>

<br>
<a href="output.php">return</a>
