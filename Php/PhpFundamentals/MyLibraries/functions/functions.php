<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//predefined function
$length = strlen("john");
print $length."</br>";

//passing functiona as argument
$length = strlen(strlen("john"));
print $length."</br>";

//User-Defined Functions: return by Value
function square($x){
    return $x*$x;
}
print "square of 4 is ".  square(4)."</br>";

//Function Scope
function updateVar(){
    $var =5;
}
$var = 4;
print$var."</br>";//4
print updateVar();
print $var."</br>";//4

function updateVarG(){
    //to access/modify the global variable we use $globals['var_name']
    $GLOBALS['var'] = 500;
    //using global keyword not recommended.
    //global $var;
}
$var = 4;
print $var."</br>";//4
print updateVarG();
print $var."</br>";//4

//Return by Value
function get_global_variable_value($name){
    return $GLOBALS[$name];
}
$num = 4;
$value = get_global_variable_value("num");//cloning a new variable with the same value
$num = 10;  
print "return by value=".$value."</br>";//print 4

//Return by Reference: PAY ATTENTION on the & is added in two places: function definition and function call.
function &get_global_variable_ref($name){
     return $GLOBALS[$name];
   
}
$num = 4;
$value =& get_global_variable_ref("num");//cloning a new variable with the same value
$num = 10;
print "return by ref=".$value."</br>";//print 10
$value = 11;
print "return by ref=".$num."</br>";//print 11

//By-Value Parameters
function powV2($x, $y)
{
print "calu..";
}
$c = 3;
powV2(2*4, $c);

//By reference parameter
function squareV2(&$x){
    $x = $x * $x; 
}
$x = 10;
squareV2($x);
print "By ref Param=".$x;

//default value:
function increment ($x, $num1 = 100)
{
   $x = $x +$num1; 
   print "increment x=".$x;
   print "</br>func_num_args()=".func_num_args();
}
$x = 33;
increment($x);
increment($x, 1000);
print("</br>");
?>
