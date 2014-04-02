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

        function fn(&$var) {
            $var = $var - ($var / 10 * 5);
            return $var;
        }

        //echo fn(100);//! ) Fatal error: Only variables can be passed by reference in 
        ///home/salim/public_html/PhpProject2/MyLibraries/functions/byreference.php on line 19
        $var = 100;
        echo fn($var); //50
        echo "$var"; //50
        $a = 10;
        echo 'Value of a = $a</br>';

        //default value
        function fn1($var1 = 10) {
            echo "</br>fn1" . $var1;
        }
        fn1();  
        /*********** Return by reference test ************/
        echo "</br>******** Return by reference test ***********</br>";
        function &printReturnParam(&$var) {
            echo"</br> fn2/param=".$var;         
            return $var;
        }
        function &fn3(){
           $var = 900;
            return $var;
        }
        //assign the return param to a new variable
        $var2 = 100;
        $var3 = printReturnParam($var2);
        $var3++;    
        echo '</br>$var2/$var3'.$var2."/$var3";
        
        //assign the return variable to an alias
        $var4 = 200;
        $varRef =& printReturnParam($var4);
        // same variables
        $varRef++;
        #$varRef is alias of $var4 : $var4/$varRef 201/201 
        echo '</br>$varRef is alias of $var4 : $var4/$varRef '.$var4."/$varRef";
        
        #a function call can be passed as parameter to another function
         $varRef = printReturnParam(fn3());
        ?>
    </body>
</html>
