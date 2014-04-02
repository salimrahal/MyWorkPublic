<!--
To change this template, choose Tools | Templates
and open the template in the editor.
str_replace:
The string or array being searched and replaced on, otherwise known as the haystack. 
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        include_once  $_SERVER["DOCUMENT_ROOT"] . "/PhpProject2/globals.php";
        //str_replace($search, $replace, $subject)
        //replace string
        print str_replace("s", "k", "salim");// out : kalim
        echo HTML_SEPARATOR;
        //replace array
        print_r(str_replace("s", "k", array("s","s","s")));// out : Array ( [0] => k [1] => k [2] => k ) 
        echo HTML_SEPARATOR;
        /*
         *
         * //replace boolean
        print_r(str_replace(true, false, true));// out : 
        echo HTML_SEPARATOR;
            //replace integer
        print_r(str_replace(100, 99, 1001));// out : 
        echo HTML_SEPARATOR;
         
         */ 
        ?>
    </body>
</html>
