<form action="insert.php" method="post">
	First Name: <input type="text" name="first"><br> Last Name: <input
		type="text" name="last"><br> Phone: <input type="text" name="phone"><br>
	Mobile: <input type="text" name="mobile"><br> Fax: <input type="text"
		name="fax"><br> E-mail: <input type="text" name="email"><br> Web: <input
		type="text" name="web"><br> <input type="Submit">
</form>
<?php
$username="root";
$password="root";
$database="contactDB";

$first=$_POST['first'];
$last=$_POST['last'];
$phone=$_POST['phone'];
$mobile=$_POST['mobile'];
$fax=$_POST['fax'];
$email=$_POST['email'];
$web=$_POST['web'];

mysql_connect(localhost,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");

$query = "INSERT INTO contacts VALUES ('','$first','$last','$phone','$mobile','$fax','$email','$web')";
mysql_query($query);

mysql_close();
?>
