<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Rectangle {

    public $name = __CLASS__;

}

class Square extends Rectangle {

    public $name = __CLASS__;

}

class Circle {

    public $name = __CLASS__;

}

function checkIfRectangle($shape) {
    if ($shape instanceof Rectangle) {
        print $shape->name;
        print " is a rectangle</br>";
    }
    else{
        print $shape->name;
        print " is NOT rectangle</br>";
    }
}
//using Negation with instance of
function checkIfNotRectangle($shape)
{
if (!($shape instanceof Rectangle)) {
print $shape->name;
print " is not a rectangle\n";
}
}
checkIfRectangle(new Square());
checkIfRectangle(new Circle());

checkIfNotRectangle(new Square());
checkIfNotRectangle(new Circle());


?>
