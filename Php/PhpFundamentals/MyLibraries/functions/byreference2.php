<!--
To change this template, choose Tools | Templates
and open the template in the editor.
http://www.php.net/manual/en/language.references.pass.php
-The following examples of passing by reference are invalid

-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        echo"---------------The following examples of passing by reference are invalid:--------------------";

        function foo(&$var) {
            $var++;
            echo $var . "</br>";
        }

        function bar() { // Note the missing &
            $a = 5;
            return $a;
        }

        foo(bar()); // Produces fatal error since PHP 5.0.5
        // No error normal behavior: on php 5.4
        foo($a = 5); // Expression, not variable
        
       // Produces fatal error: Fatal error: Only variables can be passed by reference
       // foo(5);

        Function fn(&$var) {
            $var = $var - ($var / 10 * 5);
            Return $var;
        }
#fatal error: Fatal error: Only variables can be passed by reference
      //  Echo fn(100);
        ?>
    </body>
</html>
