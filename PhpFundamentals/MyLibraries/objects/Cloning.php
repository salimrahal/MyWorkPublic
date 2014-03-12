<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Cloning Object:
 * When creating an object (using the new keyword), the returned value is a han-
  dle to an object or, in other words, the id number of the object. This is unlike
  PHP 4, where the value was the object itself. This doesn’t mean that the syn-
  tax for calling methods or accessing properties has changed, but the copying
  semantics of objects have changed.

 */

class MyClass {

    public $var = 1;

}

//assignment by reference
$obj1 = new MyClass();
$obj2 = $obj1; //the handle (id number) is passed from obj1 to obj2.
$obj2->var = 2;
print $obj1->var . "</br>"; // the variable changes since the are the same object: out: 2
//cloning keyword: new instance
$obj2 = clone $obj1;
$obj2->var = 10;
print "using cloning:" . $obj1->var . "</br>"; // out: 2


//using __clone() method: After the new cloned object is created the __clone() is called to initialize some
// ressource passed by reference from the original object to the cloned object
class MyFile {

    function setFileName($file_name) {
        $this->file_name = $file_name;
    }

    function openFileForReading() {
        $this->file_handle = fopen($this->file_name, "r");
    }
    
//if the the file_handle is opened then reopen it for the new cloned object.    
    function __clone() {
        if ($this->file_handle) {
            $this->file_handle = fopen($this->file_name, "r");
        }
    }

    private $file_name;
    private $file_handle = NULL;

}

?>
