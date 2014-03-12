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
        $sapi_type = php_sapi_name();
        echo 'php_sapi_name()='.php_sapi_name()."</br>";
        if (substr($sapi_type, 0, 3) == 'cgi') {
            echo "You are using CGI PHP\n";
        } else {
            echo "You are not using CGI PHP\n";
        }
        ?>
    </body>
</html>
