<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
# Type hinting also works with functions: 

        // An example class
        class MyClass {

            public $var = 'Hello World';

        }
        class MyClass2 {

        }


        /**
         * A test function
         *
         * First parameter must be an object of type MyClass
         */
        function myFunction(MyClass $foo) {
            echo $foo->var;
        }

// Works
        $myclass = new MyClass;
        //$myclass2 = new MyClass2;//run time exception param should be MyClass.
        //myFunction($myclass2);
        myFunction($myclass);
        ?>
    </body>
</html>
