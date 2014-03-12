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
        $tests = array(
            "42",
            1337,
            0x539,
            02471,
            0b10100111001,
            1337e0,
            "not numeric",
            array(),
            9.1
        );

        foreach ($tests as $element) {
            if (is_numeric($element)) {
                echo "'{$element}' is numeric",PHP_EOL;
            } else {
                echo "'{$element}' is NOT numeric", PHP_EOL;
            }
        }
        /*
         * '42' is numeric '1337' is numeric '1337' is numeric '1337' is numeric 
         * '1337' is numeric '1337' is numeric 'not numeric' is NOT numeric 
         * 'Array' is NOT numeric '9.1' is numeric 
         */
        ?>
    </body>
</html>
