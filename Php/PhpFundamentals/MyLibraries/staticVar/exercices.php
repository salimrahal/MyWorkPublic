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
        //valid static declaration
        static $varb = array(1, 'val', 3);
        static $varb = 1000;
        static $varb = "string";
        const CONSTA = 10;
        static $varc = CONSTA;
        // Invalid static declaration: Syntax error: all below
        //static $varb = 1+(2 * 90);//using expression: not allowed
        //static $varb = sqrt(81);//using function: not allowed
         // static $v+arb = new Object();//using object : not allowed
        //static $varb = "Sa"."lim";//using expression: not allowed
        //static $varb = 10 - 155;//using expression: not allowed
         class Object{
         }
        ?>
    </body>
</html>
