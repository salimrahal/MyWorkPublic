<!--
To change this template, choose Tools | Templates
and open the template in the editor.
http://php.net/manual/en/language.operators.precedence.php
Operator Precedence

The precedence of an operator specifies how "tightly" it binds two expressions together. For example, in the expression 1 + 5 * 3, the answer is 16 and not 18 because the multiplication ("*") operator has a higher precedence than the addition ("+") operator. Parentheses may be used to force precedence, if necessary. For instance: (1 + 5) * 3 evaluates to 18.

When operators have equal precedence, their associativity decides whether they are evaluated starting from the right, or starting from the left - see the examples below.

The following table lists the operators in order of precedence, with the highest-precedence ones at the top. Operators on the same line have equal precedence, in which case associativity decides the order of evaluation. 
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        $a = 3 * 3 % 5; // (3 * 3) % 5 = 4
        $a = true ? 0 : true ? 1 : 2; // (true ? 0 : true) ? 1 : 2 = 2
        echo true ? 0 : true?1:9;// out: 9
        echo $a."</br>";
        $a = 1;
        $b = 2;
        $a = $b += 3; // $a = ($b += 3) -> $a = 5, $b = 5
// mixing ++ and + produces undefined behavior
        $a = 1;
        echo++$a + $a++; // may print 4 or 5
        ?>
    </body>
</html>
