<?php
include("dbInfo.php");
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");
$query="CREATE TABLE userIp 
(id int(6) NOT NULL auto_increment,
employeName varchar(20) NOT NULL,
adminUser varchar(15) NOT NULL,
adminPssd varchar(15) NOT NULL,
Ip1 varchar(20) NOT NULL,
Ip2 varchar(20) default ' ',
comment varchar(30) default ' ',
PRIMARY KEY (id),UNIQUE id (id),KEY id_2 (id))";
mysql_query($query);
mysql_close();
?>