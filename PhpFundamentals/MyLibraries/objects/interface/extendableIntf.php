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

        //Example #2 Extendable Interfaces
        interface a {

            public function foo();
        }

        interface b extends a {

            public function baz(Baz $baz);
        }

// This will work
        class c implements b {

            public function foo() {
                
            }

            public function baz(Baz $baz) {
                
            }

        }

// This will not work and result in a fatal error
// Fatal error: Declaration of d::baz() must be compatible with b::baz(Baz $baz)
        class d implements b {

            public function foo() {
                
            }

            public function baz(Foo $foo) {
                
            }

        }
        ?>
    </body>
</html>
