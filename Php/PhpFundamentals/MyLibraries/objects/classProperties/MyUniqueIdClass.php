<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//using static properties for generating unique Id
class MyUniqueIdClass {

    static $idCounter = 0;
    public $uniqueId;

    function __construct() {
        self::$idCounter++;
        $this->uniqueId = self::$idCounter;
    }
/* //Also it works using class name as constructor method 
 *     function MyUniqueIdClass() {
        self::$idCounter++;
        $this->uniqueId = self::$idCounter;
    }
 */
}
$obj1 = new MyUniqueIdClass();
print $obj1->uniqueId."</br>";

$obj2 = new MyUniqueIdClass();
print $obj2->uniqueId."</br>";

?>
