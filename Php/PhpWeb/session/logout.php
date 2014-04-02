<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>logout</title>
    </head>
    <body>
        <?php
        session_start();
        $_SESSION = array();
        session_destroy();
        header('Location: http://localhost/PhpWeb/session/login.php');
        ?>
    </body>
</html>
