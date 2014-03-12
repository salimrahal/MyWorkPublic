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
        include 'session.inc';
        # Retreiving Session Variables
        $b = $GLOBALS['b'];
       echo  '</br>  $GLOBALS[\'b\']='.$GLOBALS['b'];//undefined
       echo '</br>$_SESSION[\'b\']='.$_SESSION['b'];//3
        ?>
    </body>
</html>
