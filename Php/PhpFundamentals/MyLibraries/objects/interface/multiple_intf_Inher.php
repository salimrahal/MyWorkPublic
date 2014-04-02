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

        //Example #3 Multiple interface inheritance
        interface a {

            public function foo();
        }

        interface b {

            public function bar();
        }

        interface c extends a, b {

            public function baz();
        }

        class d implements c {

            public function foo() {
                
            }

            public function bar() {
                
            }

            public function baz() {
                
            }

        }
        ?>
    </body>
</html>
