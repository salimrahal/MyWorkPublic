<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Cat {
    function miau() {
        print "miau";
   }
}

class Dog {
    function wuff() {
        print "wuff";
    }
}

function printTheRightSound($obj) {
    if ($obj instanceof Cat) {
        $obj->miau();
    } else if ($obj instanceof Dog) {
        $obj->wuff();
    } else {
        print "Error: Passed wrong kind of object";
    }
    print "\n";
}

printTheRightSound(new Cat());
printTheRightSound(new Dog());
?>
