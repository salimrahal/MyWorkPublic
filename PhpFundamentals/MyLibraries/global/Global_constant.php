<?php
include 'session.inc';
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//defining global constant: write it upper case (convention)
define("MY_OK", 0);
define("MY_ERROR", 1);
$error_code = 1;
if ($error_code == MY_ERROR) {
    print("There was an error\n");
}


//*********using global keyword
$a = 1;
$b = 2;

function Sum() {
    global $a, $b;
    //$a = 0;$b=0;

    $b = $a + $b;
}

Sum();
echo "</br> b is global = ".$b."</br>";

//**************Example #2 Using $GLOBALS instead of global
$a = 1;
$b = 2;

function Sum1() {
    $GLOBALS['b'] = $GLOBALS['a'] + $GLOBALS['b'];
}

Sum1();
echo '</br> b is added to $GLOBALS  $b= '.$b;
echo  '</br>  $GLOBALS[\'b\']='.$GLOBALS['b'];
//setting variable b to the session
$_SESSION['b']= $b;

?>
<a href="page2.php">page 2</a>
