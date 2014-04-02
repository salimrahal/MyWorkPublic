<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Desc:
 * In order to minimize the code that it going to be included we use the __autoload().
 * 
 * So long as MyClass.php exists in the classes/ directory inside the docu-
ment root of the web server, the script prints
Hello, World
Realize that MyClass.php was not explicitly included in main.php but
implicitly by the call to __autoload(). You will usually keep the definition of
__autoload() in a file that is included by all of your main script files (similar to
general.inc in this example), and when the amount of classes you use
increases, the savings in code and maintenance will be great.

 */
require_once "general.php";
$obj = new MyClass();
$obj->printHelloWorld();
print "</br>".$_SERVER["DOCUMENT_ROOT"];
?>
