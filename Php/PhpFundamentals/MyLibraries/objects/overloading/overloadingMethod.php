<!--
To change this template, choose Tools | Templates
and open the template in the editor.

Overloading

Overloading in PHP provides means to dynamically "create" properties and methods. These dynamic entities are processed via magic methods one can establish in a class for various action types.

The overloading methods are invoked when interacting with properties or methods that have not been declared or are not visible in the current scope. The rest of this section will use the terms "inaccessible properties" and "inaccessible methods" to refer to this combination of declaration and visibility.

All overloading methods must be defined as public. 
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
/*
 *  Overloading methods via the __call() and __callStatic() methods 
 */
        class MethodTest {
/**/
            public function __call($name, $arguments) {
                // Note: value of $name is case sensitive.
                echo "Calling object method '$name' "
                . implode(', ', $arguments) . "\n</br>";
            }
/**/
            /**  As of PHP 5.3.0  */
/**/           public static function __callStatic($name, $arguments) {
                // Note: value of $name is case sensitive.
                echo "Calling static method '$name' "
                . implode(', ', $arguments) . "\n</br>";
            }
/**/
         /*
            public function runTest($var){
                print "runTest method processing..$var</br>";
            }
            
            /**/
        }

        $obj = new MethodTest;
        $obj->runTest('in object context');
        $obj->newTest("param</br>");

        MethodTest::runTest('in static context');  // As of PHP 5.3.0
        ?>
    </body>
</html>
