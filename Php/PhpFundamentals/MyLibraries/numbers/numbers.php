<!--
To change this template, choose Tools | Templates
and open the template in the editor.
Integers can be written in decimal, hexadecimal (prefixed with 0x), and
octal notation (prefixed with 0), and can include +/- signs.
Some examples of integers include
240000
0xABCD
007
-100
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        
        
        $a = 0;
        echo ~$a;//-1
        $b = 0x02;
       // echo $a===$b >> $a;
       // echo "5.0"=="5";//1
        echo"END";
        // Integer
        $int1 = 24000;
        $int2 = 0xABCD;
        $int3 = 007;
        $int4 = -100;
        print "$int1 is " . gettype($int1) . ";$int2 is " . gettype($int2) . ";$int3 is " . gettype($int3) . ";$int4 is " . gettype($int4);

        //type juggling
        $foo = "0";  // $foo is string (ASCII 48)
        $foo += 2;   // $foo is now an integer (2)
        $foo = $foo + 1.3;  // $foo is now a float (3.3)
        $foo = 5 + "10 Little Piggies"; // $foo is integer (15)
        $foo = 5 + "10 Small Pigs";     // $foo is integer (15)
        print "</br>foo: $foo";
        
        //casting
        $boo = 10;
        $boo = (bool)$boo;
        print"</br>casting to bool: \$boo= $boo ".  gettype($boo);
        $boo = (unset)$boo;
        print"</br>unset:: \$boo= $boo ".  gettype($boo);
        
        $doo = 10.22;
        $doo = (double)$doo;
        $doo = (real)$doo;
        print"</br>casting to double: \$doo= $doo ".  gettype($doo);
        $doo = (binary)$doo;
        print"</br>casting to binary: \$doo= $doo ".  gettype($doo);
        print"</br>-------------------------</br>";
        $var = 1 + "-1.3e3";
        echo ' 1 + "-1.3e3='.$var."</br>";//-1299
        echo -1.3e3;
        ?>
    </body>
</html>
