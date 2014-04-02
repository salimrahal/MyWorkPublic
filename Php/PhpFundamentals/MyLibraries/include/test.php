<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
include "error_codes.php";
//using absolute URL
include $_SERVER["DOCUMENT_ROOT"] . "/PhpProject2/MyLibraries/include/generic.php";

//print variables
print 'My error='.$MY_ERROR." , My_ok=".$MY_OK;
//using absolute reference
print $_SERVER["DOCUMENT_ROOT"];
print "GenericVal=".$Generic_val."</br>";

//Eval(): evaluate string to php script
$str = '$var = 5;';
eval($str);
print $var;


?>
