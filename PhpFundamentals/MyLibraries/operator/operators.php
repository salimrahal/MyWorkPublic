<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
// Multiplication by boolean
$varT = 1*TRUE;
$varF = 1*FALSE;
echo "$varT $varF </br>";

//Multiplication by string
$num1 = 1*"200";
$num2 = 1*"salim";
$num3 = "sadasdsa"*"salim";
echo $num1 ." ".$num2 ." ".$num3."</br>";// output: 200 0 0

//modulus
$num4 = 16 % 4;
$num5 = 20 % 3;
echo $num4 ." ".$num5 ."</br>";//out: 0 2

$num6 = 20 % 2;//pair
$num7 = 21 % 2;//impair
echo "pair=".$num6 ." impair:".$num7."</br>";//out: pair=0 impair:1

//division
$num8 = 10/1855;
echo "10 divide 1855="."$num8"."</br>";//out:10 divide 1855=0.0053908355795148

//Composite Assignment
$counter = 3;
$counter += 2;
echo $counter . "</br>";
$counter *= 2;
echo $counter . "</br>";

//By-Reference Assignment Operator: =&
$name = "john";
$name_alias =& $name;
$name_alias = "john rahal";
echo $name ."</br>";//out: john rahal

//Assignement By value
$name1 = "john";
$name_alias = $name1;
$name_alias = "john rahal";
echo $name1."</br>";//out: john

//modifying value
$name1="new val";
echo $name1."</br>";//out: john

/**** comparison operator *********/
if(1=="1"){print "1==\"1\" return true</br>";}
if(1==="1"){print "1===\"1\" return true</br>";}
else{print "1===\"1\" return false</br>";}
if(1===1){print "1===1 return true</br>";}
/****Logical Operators*/
if(true || true || false){print "true || true || false :: returns true</br>";}
if(true && true && false){print "true && true && false :: impossible statment true";}
else{print "true && true && false :: returns false</br>";}
/**Bitwise?????**/
if(123&13){print "WTF</br>";}
//Negation
$killed = false;
if(!$killed){print "follow him...</br>";}

/** Increment/Decrement Operators **/
$num9 = 10;
print $num9++."</br>";// post-increment //out:10
print ++$num9."</br>";// pre-increment  //out: 12
print $num9--."</br>";// post-decrement old value:12, new value: 11, it prints the old value//out:12
print --$num9."</br>";// pre-decrement  //it print the new value:11-1 =10:: out: 10 

//Post incre
$num10 = 5;
$num11 = $num10++;// post-increment, $num11 is assigned $num10's original➥value
print "PostIncre: ". $num10."</br>";
// this will print the value of $num10, which is now 6
print $num11."</br>";// this will print the value of $num11, which is the➥original value of $num10, thus, 5

//Pre Incre
$num12 = 5;
$num13 = ++$num12;// pre-increment, $num13 is assigned $num12's➥incremented value
print "PreIncre: ".$num12."</br>";// this will print the value of $num12, which is now 6
print $num13."</br>";// this will print the value of $num13, which is the➥same as the value of $num12, thus, 6

/**Cast**/
$str = "5";
$num = (int) $str;
print "gettype(".$num.")".gettype($num)."</br>";//Int
$num = (bool)$str;
print "gettype(".$num.")".gettype($num)."</br>";//bool
$num = (bool)NULL;
print "gettype(".$num.")".gettype($num)."</br>";//bool
$num = NULL;
print "gettype(".$num.")".gettype($num)."</br>";//NULL

//casting NUmbers Int -->
$num14 = 10.58;
$numInt = (integer)$num14;
print "original type:".gettype($num14)." to ".  gettype($numInt).$numInt."</br>";//out: 10

//ternary operator:: truth_expr ? expr1 : expr2
$a = 9;
$msg = isset($a)? "\$a is set</br>": "\$a is not set</br>";
print $msg;
echo"</br>--------------casting String to int----------------";
//
$varC = (int)"salim";
echo "</br>(int)\"salim\"=$varC";

?>