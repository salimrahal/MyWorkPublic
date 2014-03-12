<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// The scop of a constant is global
//defining global constant: write it upper case (convention)
define("MY_OK", 0);//used outside a class definition
define("MY_ERROR", 1);
$error_code = 1;
if ($error_code == MY_ERROR) {
print("There was an error\n");
}

//constant are as static variable related to the class, not to the instance
class MyColorEnumClass {

    const RED = "red";
    const GREEN = "green";
    const BLUE = "blue";

    static $white = "white";

    function getRedColor() {
        return self::RED;
    }

    function getWhiteColor() {
        return self::$white;
    }

}

print MyColorEnumClass::GREEN . "</br>";
$obj = new MyColorEnumClass();
print $obj->getRedColor() . "</br>";
print $obj->getWhiteColor() . "</br>";
?>
