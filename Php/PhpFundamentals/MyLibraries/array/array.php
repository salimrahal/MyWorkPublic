<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/********* Array bool**********/
$arr0 = new ArrayObject();
#return true
if($arr0){print "the array Object contains at least one element</br>";}
else{print"this array Object doesnt contains anything</br>";}


$arr = array();
//$arr[0]="kiki";
#returns false
if($arr){print "the array contains at least one element</br>";}
else{print"this array doesnt contains anything</br>";}

/*****constructing key/value array *****/
$array = array("name1","name2","name3");
$array2 = array("nom"=>"carlos","prenom"=>"garcia","age"=>"32");
$array3 = array("nom"=>"Daisy","prenom"=>"Rahal","age"=>"11");
//NESTED Array() that contains two person information
$arrayData = array($array2,$array3);
print_r($arrayData);
print "</br>";
/*****Creating/reading/Modifiying array*********/
//Creating array
$arr1 = array(1, 2, 3);

$arr2[0] = 1;
$arr2[1] = 2;
$arr2[2] = 3;
print_r($arr1);
print_r($arr2);
//Modiying
$arr1[0] = 10;
print_r($arr1);
//reading Value
print "reading value=$arr1[0]</br>";

/*********Special Notation arr[]=value; automatic index***/
$arr3 = array(1, 2, 3);
$arr4[] =1;
$arr4[] =2;
$arr4[] =3;
print_r($arr4);

$arr5 = array("name" => "John", "age" => 28);
$arr6["name"] = "John";
$arr6["age"] = 28;
if ($arr5 == $arr6) {
print '$arr5 and $arr6 are the same' . "</br>";
}

/*********Reading array values********/
print $arr5["name"];
if ($arr5["age"] < 35) {
print " is quite young</br>";
}
/****** Accessing Nested Arrays (or Multi-Dimensional Arrays)*******/
$arr7 = array(1=>array("name"=>"kiki","age"=>20),array("name"=>"koukou","age"=>22));
//OR
$arr7[1]= array("name"=>"kiki","age"=>20);
$arr7[2]= array("name"=>"koukou","age"=>22);
//OR
$arr7[1]["name"]= "kiki";
$arr7[1]["age"]= "20";
$arr7[1]["name"]= "koukou";
$arr7[1]["age"]= "22";
//Output
print $arr7[2]["name"];// prints Koukou
/****Traversing Arrays Using foreach****/
$array8 = array("kiki","koukou","koko","nana");
foreach ($array8 as $value) {
    print $value."</br>";
}
foreach ($array8 as $key => $value) {
print "#$key = $value </br>";    
}
//Array - adding attribute
$people = array(1 => array("name" => "John", "age" => 28), array("name" => "Barbara", "age" => 67));
//My loop

foreach ($people as $key => $value) {
    if($value["age"] <= 35){
        $people[$key]["age group"]="young";
    }
    else{
        $people[$key]["age group"]="old";
    }
}
print_r($people);
print("</br>");
//Passing by Reference in order to modify an array: Ebook loop - MORE READABLE
foreach ($people as &$person) {
    if($person["age"] <= 35){
        $person["age group"]="young";
        //$person["name"]="Salimooooo";      
    }
    else{
        $person["age group"]="old";
    }
}
print_r($people);
/**** Traversing array using list and each */
$players = array("John", "Barbara", "Bill", "Nancy");
reset($players);//init a pointer
while (list($key, $val) = each($players)) {
print "#$key = $val</br>";
}

$ages = array("John" => 28, "Barbara" => 67);
reset($ages);
$person = each($ages);
print $person["key"];
print " is of age ";
print $person["value"];
print "<br>-----------------</br>";
$list = list($var1,$var2) = array("John", "Barbara", "Bill", "Nancy");
print_r($list);
?>