<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class MyClass {

    static $myStaticVariable;
    static $myInitializedStaticVariable = 0;

    function printvar(){
        self::$myInitializedStaticVariable++;
        print "from insdie::".self::$myInitializedStaticVariable;
    }
}
//accessing the static var from outside the class
MyClass::$myInitializedStaticVariable++;
print MyClass::$myInitializedStaticVariable."</br>";
$myclass = new MyClass();
$myclass->printvar();

?>
