<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
//the two string need to be declared in PHP, 
//otherwise there is no to string representation in case we print the $obj.

class Person {

    function __construct($name) {
        $this->name = $name;
    }
    private $name;
    /*
     * __toString() should return the string representation of the object, and
when defined, the print command will call it and print the returned string
     * The __toString() method is currently only called by the print and echo
language constructs. In the future, they will probably also be called by com-
mon string operations, such as string concatenation and explicit casting to
string.

     */
function __toString() {
    return $this->name;
}
}

$obj = new Person("Andi Gutmans");
print $obj;//out: Andi Gutmans
?>
