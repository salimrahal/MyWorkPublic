<!--
To change this template, choose Tools | Templates
and open the template in the editor.
Bitwise Operators Example 	Name 	Result
$a & $b 	And 	Bits that are set in both $a and $b are set.
$a | $b 	Or (inclusive or) 	Bits that are set in either $a or $b are set.
$a ^ $b 	Xor (exclusive or) 	Bits that are set in $a or $b but not both are set.
~ $a 	Not 	Bits that are set in $a are not set, and vice versa.
$a << $b 	Shift left 	Shift the bits of $a $b steps to the left (each step means "multiply by two")
$a >> $b 	Shift right 	Shift the bits of $a $b steps to the right (each step means "divide by two") 
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        echo 10 << 2;//40
        echo 10 >> 2;//2 not 2.5
         echo "</br>";
        $var = 10 >> 2+1.5;
        echo $var;
        ?>
    </body>
</html>
