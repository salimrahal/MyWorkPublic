<!--
To change this template, choose Tools | Templates
and open the template in the editor.

String conversion to numbers

When a string is evaluated in a numeric context, the resulting value and type are determined as follows.

If the string does not contain any of the characters '.', 'e', or 'E' and the numeric value fits into integer type limits (as defined by PHP_INT_MAX), the string will be evaluated as an integer. 
In all other cases it will be evaluated as a float.

The value is given by the initial portion of the string. 
If the string starts with valid numeric data, this will be the value used. Otherwise, the value will be 0 (zero). Valid numeric data is an optional sign, followed by one or more digits (optionally containing a decimal point), followed by an optional exponent. The exponent is an 'e' or 'E' followed by one or more digits.

-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        $foo = 1 + "10.5";                // $foo is float (11.5)
        $foo = 1 + "-1.3e3";              // $foo is float (-1299)
        $foo = 1 + "bob-1.3e3";           // $foo is integer (1)
        $foo = 1 + "bob3";                // $foo is integer (1)
        $foo = 1 + "10 Small Pigs";       // $foo is integer (11)
        $foo = 4 + "10.2 Little Piggies"; // $foo is float (14.2)
        $foo = "10.0 pigs " + 1;          // $foo is float (11)
        $foo = "10.0 pigs " + 1.0;        // $foo is float (11)     
        ?>
    </body>
</html>
