<!--
To change this template, choose Tools | Templates
and open the template in the editor.
Heredoc text behaves just like a double-quoted string, 
without the double quotes. This means that quotes in a heredoc do not need to be escaped,
but the escape codes listed above can still be used. Variables are expanded, 
but the same care must be taken when expressing complex variables inside a heredoc as with strings. 
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        $str = <<<EOD
Example of string
spanning multiple lines
using heredoc syntax.
EOD;

        /* More complex example, with variables. */

        class foo {

            var $foo;
            var $bar;

            function foo() {
                $this->foo = 'Foo';
                $this->bar = array('Bar1', 'Bar2', 'Bar3');
            }

        }

        $foo = new foo();
        $name = 'MyName';

        echo <<<EOT
My name is "$name". I am printing some $foo->foo.
Now, I am printing some {$foo->bar[1]}.
This should print a capital 'A': \x41
EOT;

//Using double quotes in Heredoc
echo "</br>--------Using double quotes in Heredoc-------------";
echo <<<"FOOBAR"
Hello World!
FOOBAR;

$var = <<<bb
salim is n=miwae daisy rahallll hahhhha
    </br>new line
    new line $name "behhhh double quote"
bb;
echo $var;
?>
        
        
    </body>
</html>
