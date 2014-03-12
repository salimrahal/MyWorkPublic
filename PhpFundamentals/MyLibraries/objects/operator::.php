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

        Class Insurance {

            function clsName() {
                echo get_class($this);
            }
            
            static function statFucn() {
                echo "static funtion</br>";
            }

        }
$cl = new Insurance();
$cl ->clsName();
Insurance::clsName();
//output: InsuranceInsurance 
$cl->statFucn();
Insurance::statFucn();

        ?>
    </body>
</html>
