<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * with abstract
 */
class Animal{
      function sound(){
        print "Error: This method should be re-implemented in the children";

    }
}
abstract class AnimalAbs{
    abstract function sound();
}

class Dog extends Animal{
    public function sound() {
        print "wuff</br>";
    }
}

class Cat extends Animal{
    public function sound() {
        print "Miaw</br>";
    }
}
function printTheRightSound($obj) {
    if ($obj instanceof Animal) {
        $obj->sound();
        }
        else {
        print "Error: Passed wrong kind of object";
    }
    print "</br>";
}

$cat = new Cat();
$dog = new Dog();
//$cat->sound();
//$dog->sound();
printTheRightSound($cat);
printTheRightSound($dog);
?>
