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
        /*
         * This example uses the hear basic auth, it open a popup that demand user/pssd
         */
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header('WWW-Authenticate: Basic realm="My Realm"');
            header('HTTP/1.0 401 Unauthorized');
            echo 'Text to send if user hits Cancel button';
            exit;
        } else {
            echo "<p>Hello {$_SERVER['PHP_AUTH_USER']}.</p>";
            echo "<p>You entered {$_SERVER['PHP_AUTH_PW']} as your password.</p>";
           // unset($_SERVER['PHP_AUTH_USER']); //unset the variable
            echo "<p>Hello {$_SERVER['PHP_AUTH_USER']}.</p>";// the var is unset
        }
        ?>
    </body>
</html>
