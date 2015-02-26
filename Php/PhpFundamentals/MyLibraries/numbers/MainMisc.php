<?php
require_once './NumbersTest.php';
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Magic ocnstant
echo '<br> begins</br>';
echo __FILE__;
echo '<br>ends</br>';

echo '<br>SAPI name </br>';
echo php_sapi_name();

echo '<br>ends</br>';

//special character inside a quote and double quote
echo '<br> begins</br>';
echo 'single quote \n single ';
echo "double quote \n single ";

echo '<br>ends</br>';

echo '<br>empty is set: begins</br>';
$x=0;
echo empty($x);
echo isset($x);

echo '<br>empty is set: ends</br>';
$test = new NumbersTest();
echo $test->compute();

$res = $test->unsettest();
echo '<br>get type</br>';
echo gettype($res);

$arr = array();
$arr[0]='salim';
$arr[1]='nibal';
$arr[2]='daisy';
$arr[3]='sandy';
echo '<br>for each </br>';
foreach ($arr as $key => $value) {
    echo'['.$key . $value.']';
}
echo '<br>for </br>';
for($i=0;$i<sizeof($arr);$i++){
       echo '['.$i.'-'.$arr[$i].']';
}

