<!--
To change this template, choose Tools | Templates
and open the template in the editor.

http://www.php.net/manual/en/language.constants.php
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>

        <?php
        define('MIN_VALUE', '0.0');   // RIGHT - Works OUTSIDE of a class definition.
        define('MAX_VALUE', '1.0');   // RIGHT - Works OUTSIDE of a class definition.
//const MIN_VALUE = 0.0;         WRONG - Works INSIDE of a class definition.
//const MAX_VALUE = 1.0;         WRONG - Works INSIDE of a class definition.

        class Constants {
            //define('MIN_VALUE', '0.0');  WRONG - Works OUTSIDE of a class definition.
            //define('MAX_VALUE', '1.0');  WRONG - Works OUTSIDE of a class definition.

            const MIN_VALUE = 0.0;      // RIGHT - Works INSIDE of a class definition.
            const MAX_VALUE = 1.0;      // RIGHT - Works INSIDE of a class definition.

            public static function getMinValue() {
                return self::MIN_VALUE;
            }

            public static function getMaxValue() {
                return self::MAX_VALUE;
            }

        }

        #Example 1:
        $min = Constants::MIN_VALUE;
        $max = Constants::MAX_VALUE;

#Example 2:
        $min = Constants::getMinValue();
        $max = Constants::getMaxValue();

        print MIN_VALUE;
        ?>
    </body>
</html>
