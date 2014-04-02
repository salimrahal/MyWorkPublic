<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Example Desc:
 * Although PHP is not a strictly typed language in which you would need to
declare what type your variables are, it does allow you (if you wish) to specify
the class you are expecting in your function’s or method’s parameters.

 */

class MyClass {

    protected $name;

    function __construct($name) {
        $this->name = $name;
    }

    function printName() {
        print $this->name;
    }

}

class OtherClass {

    protected $name;

    function __construct($name) {
        $this->name = $name;
    }

    function printName() {
        print $this->name;
    }

}
//old version
function onlyWantMyClassObjects($obj) {
    if (!($obj instanceof MyClass)) {
        die("Only objects of type MyClass can be sent to this
function");
    }
    print "processing....</br>";
}
//Same as the above funtion but putting the hints in function parameter
function onlyWantMyClassObjectsV2(MyClass $obj) {
    print "processingV2....</br>";
}
//main code
$myclass = new MyClass("MySISI");
$otherclass = new OtherClass();
onlyWantMyClassObjectsV2($myclass);
onlyWantMyClassObjectsV2($otherclass);//having error in apache log, wrong instance passed as parameter.
?>
