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
// Show all errors.
        error_reporting(E_ALL);

        class beers {

            const softdrink = 'rootbeer';

            public static $ale = 'ipa';

        }

        $rootbeer = 'A & W';
        $ipa = 'Alexander Keith\'s';

// This works; outputs: I'd like an A & W
        echo "I'd like an {${beers::softdrink}}</br>";

// This works too; outputs: I'd like an Alexander Keith's
        echo "I'd like an {${beers::$ale}}</br>";
        $x = 10;
        $$x = "success";
        echo '$x=' . "{$x}"; //10
        echo '${x}=' . "${x}"; //10
        echo '${x}=' . "{$$x}"; //success
        echo '${x}=' . "${$x}"; //success
        echo '${x}=' . "${10}"; //success
        echo '${x}=' . "$$x"; //$10
//---------------------example----------------------
        function curlyExample() {
            echo"<br>-------------indirect reference------------------</br>";
//Which of the following is not a correct way of printing 'ABS Ltd, Sydney'? 
            $company = 'ABS Ltd';
            $$company = ', Sydney';

            echo '$company $$company=' . "$company $$company";
            echo"<br>------";
            echo '$company {$$company}=' . "$company {$$company}";
            echo"<br>------";
            echo '$company ${\'ABS Ltd\'}=' . "$company ${'ABS Ltd'}";
            echo"<br>------";
            echo '$company ${$company}=' . "$company ${$company}";
        }
        curlyExample();
        ?>

    </body>
</html>
