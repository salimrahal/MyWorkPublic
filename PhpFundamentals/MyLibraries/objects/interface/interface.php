<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
interface Loggable {

    function logString();
    function aMethod();
}

class Person implements Loggable {

    private $name, $address, $idNumber, $age;

    function logString() {
        return "class Person: name = $this->name, ID = $this->idNumber\n";
    }

    function aMethod() {
        
    }

}

class Product implements Loggable {

    private $name, $price, $expiryDate;

    function logString() {
        return "class Product: name = $this->name, price = $this->price\n";
    }

    public function aMethod() {
        
    }
    public function aMethod2() {
        
    }

}

function MyLog($obj) {
    if ($obj instanceof Loggable) {
        print $obj->logString();
    } else {
        print "Error: Object doesn’t support Loggable interface\n";
    }
}

$person = new Person();
// ...
$product = new Product();
MyLog($person);
MyLog($product);
?>
