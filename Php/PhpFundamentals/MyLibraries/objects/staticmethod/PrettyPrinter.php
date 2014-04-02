<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Invoking static method: Class_Name::static_method()
 */
class PrettyPrinter {

    static function printHelloWorld() {
        print "Hello, World";
        self::printNewline();
    }

    static function printNewline() {
        print "</BR>";
    }
}
//we can call static metho without creating instance
PrettyPrinter::printHelloWorld();

//using instance to invoke static method
$prettyPrinterInst = new PrettyPrinter();
print "invoking from instance</br>";
$prettyPrinterInst->printHelloWorld();
?>