
<?php
/******************* Data Type*******************/
const SALIM = salim;

$a_bool = TRUE;   // a boolean
$a_bool = SALIM;   // a boolean
$a_bool = TRUE;   // a boolean$a_bool = TRUE;   // a boolean

$a_str = "foo";  // a string
$a_str2 = 'foo';  // a string
$an_int = 12;     // an integer

echo gettype($a_bool); // prints out:  boolean 
?>
</br>
<?php
echo gettype($a_str);  // prints out:  string
// If this is an integer, increment it by four
if (is_int($an_int)) {
    $an_int += 4;
}

if ($a_bool) {
    echo 'yes its true';
} else {
    echo 'yes its true';
}

// If $a_bool is a string, print it out
// (does not print out anything)
//if else
if (is_string($a_bool)) {
    echo "String: $a_bool";
} else {
    echo "String: $a_bool";
}
?>
</br>
<?php
echo"<br>-------------------------------</br>";
//indirect reference to variable
$name = "john"; //declaring a variable
$$name = "registered user";
print_r($john . '</br>');

/* * ****************************Managing Variables ******************************** */
$first_name = 'Salim';
$last_name = "rahal";
$father_name = "antoine";

if (isset($first_name)) {
    print '$first_name is set</br>';
} elseif (!isset($first_name)) {
    print '$first_name is not set</br>';
}
//checking multiple param: var1 & var2 & varn
if (isset($first_name, $last_name, $father_name)) {
    print 'vars are set</br>';
} else {
    print 'vars are not';
}

/******isset() NULL variable*******/
echo"<br>-------------------------------</br>";
$varN=NULL;
if(isset($varN)){print "\$varN: $varN is set";}
else{print"\$varN: $varN is not set\n";}

// Unset($var): it reinistializes the var
$name = "John Doe";
$name2 = "daw Jhons";
unset($name,$name2);
if (isset($name,$name2)) {
    print '$name is set';
}
/*empty():may be used to check if a variable has not been
declared or its value is false.
 */
//$var = FALSE;
$var = 'sisi';
if (empty($var)) {
//This code prints an error message if $name doesn’t contain a value that
//evaluates to true.
print 'Error: Forgot to specify a value for $var';
}
if(isset($var)){
    print_r("$var is already set");  
    }
/******Super Global Variables***************
 * $_GET[]
 * $_POST[]
 * $_COOKIE[]
 * $_ENV[]
 * $_SEVER[]
 * 
 */
    $file_path = __file;   // a boolean
    print "</br>-----------------".$file_path;

?>