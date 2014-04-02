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

        // Interfaces with constants
        interface a1 {

            const b = 'Interface constant</br>';

        }

// Prints: Interface constant
        echo a1::b;

// This will however not work because it's not allowed to 
// override constants.
        class b implements a1 {

            const b = 'Class constant</br>';
        }
        
        echo b::b;//will not work, empty output
        ?>
    </body>
</html>
