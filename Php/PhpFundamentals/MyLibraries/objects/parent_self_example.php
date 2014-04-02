<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Ancestor {

    const NAME = "Ancestor";

    function __construct() {
        print "In " . self::NAME . " constructor\n</br>";
    }

}

class Child extends Ancestor {

    const NAME = "Child";

    function __construct() {
        parent::__construct();
        print "In " . self::NAME . " constructor\n</br>";
    }

}
class Mother extends Ancestor{
    private $name;
    function __construct($name) {
        parent::__construct();
        $this->name = $name;
    }
    function getName(){
        return $this->name;
    }
}

$obj = new Child();
$mother = new Mother("Katalina");
echo "get Mother's name:".$mother->getName();
?>
