<!--
To change this template, choose Tools | Templates
and open the template in the editor.
Comparisons of $x with PHP functions Expression 	
              gettype() empty() is_null() isset() boolean : if($x)
$x = ""; 	string 	TRUE 	FALSE 	TRUE 	FALSE
$x = null 	NULL 	TRUE 	TRUE 	FALSE 	FALSE
var $x; 	NULL 	TRUE 	TRUE 	FALSE 	FALSE
$x is undefined NULL 	TRUE 	TRUE 	FALSE 	FALSE
$x = array(); 	array 	TRUE 	FALSE 	TRUE 	FALSE
$x = false; 	boolean TRUE 	FALSE 	TRUE 	FALSE
$x = true; 	boolean FALSE 	FALSE 	TRUE 	TRUE
$x = 1; 	integer FALSE 	FALSE 	TRUE 	TRUE
$x = 42; 	integer FALSE 	FALSE 	TRUE 	TRUE
$x = 0; 	integer TRUE 	FALSE 	TRUE 	FALSE
$x = -1; 	integer FALSE 	FALSE 	TRUE 	TRUE
$x = "1"; 	string 	FALSE 	FALSE 	TRUE 	TRUE
$x = "0"; 	string 	TRUE 	FALSE 	TRUE 	FALSE
$x = "-1"; 	string 	FALSE 	FALSE 	TRUE 	TRUE
$x = "php"; 	string 	FALSE 	FALSE 	TRUE 	TRUE
$x = "true"; 	string 	FALSE 	FALSE 	TRUE 	TRUE
$x = "false"; 	string 	FALSE 	FALSE 	TRUE 	TRUE
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        // put your code here
        $arr = array(
            "",
            "null",
            null,
            array(),
            false,
            true,
            0,
            "0",
            1,
            42,
            -1,
            "1",
            "php",
            "true",
            "false"            
        );
        foreach ($arr as $var) {
            if (empty($var)) {
                echo "'$var' is empty</br>";
            } else {
                echo "'$var' is NOT empty</br>";
            }
        }
        /*
         * ********   Output  ***************
        '' is empty
        'null' is NOT empty
        '' is empty
        'Array' is empty
        '' is empty
        '1' is NOT empty
        '0' is empty
        '0' is empty
        '1' is NOT empty
        '42' is NOT empty
        '-1' is NOT empty
        '1' is NOT empty
        'php' is NOT empty
        'true' is NOT empty
        'false' is NOT empty
         * 
         */
        $var1 = (string)false;
        echo "</br>casting [false] to string [$var1]";
        $a = array();
        echo "</br>array() is set:".isset($a);
        ?>
    </body>
</html>
