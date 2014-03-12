<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * The below example, checks an object if null it throws a predefined exception,
 *  otherwise it prints the object string representation
 * 
 * Notice that the name Jane isn’t printed, only Bill. This is because the
line throws an exception inside the function, and therefore,
Jane is skipped. In the catch handler, inherited methods such as getFile() are
used to give additional information on where the exception occurred.

 */
class NullHandleException extends Exception {

    function __construct($message) {
        parent::__construct($message);
    }

}

function printObject($obj) {
    if ($obj == NULL) {
        throw new NullHandleException("printObject received NULL object");
    }
    print $obj . "</br>";
}

class MyName {

    function __construct($name) {
        $this->name = $name;
    }

    function __toString() {
        return $this->name;
    }

    private $name;

}

try {
    printObject(new MyName("Bill"));
    printObject(NULL);
    printObject(new MyName("Jane"));
   } 
catch (NullHandleException $exception) {
    print $exception->getMessage();
    print " in file " . $exception->getFile();
    print " on line " . $exception->getLine() . "</br>";
}
 catch (Exception $exception) {
// This won't be reached
}
?>
