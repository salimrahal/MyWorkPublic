<?php
$username="root";
$password="root";
$database="contactDB";

mysql_connect(localhost,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="SELECT * FROM contacts";
$result=mysql_query($query);

$num=mysql_numrows($result);

mysql_close();

//echo "<b><center>Database Output</center></b><br><br>";
?>
<table border="0" cellspacing="2" cellpadding="2">
<tr>
<th><font face="Arial, Helvetica, sans-serif">Name</font></th>
<th><font face="Arial, Helvetica, sans-serif">Phone</font></th>
<th><font face="Arial, Helvetica, sans-serif">Mobile</font></th>
<th><font face="Arial, Helvetica, sans-serif">Fax</font></th>
<th><font face="Arial, Helvetica, sans-serif">E-mail</font></th>
<th><font face="Arial, Helvetica, sans-serif">Website</font></th>
</tr>

<?php
if($num == 0){
	echo "no data to display";
}
else{
	$i=0;
	while ($i < $num) {
		
	    $id = mysql_result($result, $i, "id");
		$first=mysql_result($result,$i,"first");
		$last=mysql_result($result,$i,"last");
		$phone=mysql_result($result,$i,"phone");
		$mobile=mysql_result($result,$i,"mobile");
		$fax=mysql_result($result,$i,"fax");
		$email=mysql_result($result,$i,"email");
		$web=mysql_result($result,$i,"web");
		?>
	
	<tr>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $first." ".$last; ?>
		</font></td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $phone; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $mobile; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><? echo $fax; ?> </font>
		</td>
		<td><font face="Arial, Helvetica, sans-serif"><a
				href="mailto:<? echo $email; ?>">E-mail</a> </font></td>
		<td><font face="Arial, Helvetica, sans-serif"><a
				href="<? echo $web; ?>">Website</a> </font></td>
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