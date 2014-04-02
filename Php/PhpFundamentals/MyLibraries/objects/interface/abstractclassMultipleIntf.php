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
         interface a1 {

            public function foo();
        }

        interface b1 extends a1 {

            public function baz(Baz $baz);
        }
        interface b2 {

            public function bazb2(Baz $baz);
        }

// This will work
        abstract class c1 implements b1, b2 {

            public function foo() {
                
            }

            public function baz(Baz $baz) {
                
            }

            public function bazb2(Baz $baz) {
                
            }

        }

        ?>
    </body>
</html>
