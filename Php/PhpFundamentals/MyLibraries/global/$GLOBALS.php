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

        function test() {
            $foo = "local variable";

            echo '$foo in global scope: ' . $GLOBALS["foo"] . "</br>";
            echo '$foo in current scope: ' . $foo . "</br>";
        }

        $foo = "Example content";
        test();
        /*
         * OUTPUT:
         * $foo in global scope: Example content
           $foo in current scope: local variable
         */
        
        echo '</br>----'.$GLOBALS['foo'];
        ?>

    </body>
</html>
