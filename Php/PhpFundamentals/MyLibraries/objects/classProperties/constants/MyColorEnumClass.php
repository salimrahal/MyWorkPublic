<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Defining constant inside class: const const_name
 * Defining global constant: using Define()
 */
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
