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
        // put your code here
        $varDouble = 1000.15;
        $varInt = (int) $varDouble;
        print "get type of $varDouble:" . gettype($varDouble) . "; type of $varInt:" . gettype($varInt);
        $var = 1000;
        print "</br>    get type of $var:" . gettype($var);

        $fig = 23;
        $varb1 = (real) $fig;
        $varb2 = (double) $fig;
        #$varb3 = (decimal) $fig;#incorrect casting 
        $varb4 = (bool) $fig;
        ?>
    </body>
</html>
