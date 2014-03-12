<!--
To change this template, choose Tools | Templates
and open the template in the editor.

Desc: this ressource represent every page that should have login check before entering the code

-->
<!DOCTYPE html>
<html>
    <?php
    include 'session.inc';
    ?>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ressource</title>
    </head>
    <body>
        <?php
        //if the uid is not set in the SEssion global array then redirect to the index
        check_login();
       echo "UID:".$_SESSION['uid'];
       echo "email session variable:".$_SESSION['email'];
       echo "session_cache_expire()=".session_cache_expire();
       /*
        * 
0 = PHP_SESSION_DISABLED
1 = PHP_SESSION_NONE
2 = PHP_SESSION_ACTIVE
        */
       echo 'session_status()='.  session_status();
        ?>
        <h1>Private Info</h1>
        <p>bla bla bla</p>
       
        </br>
        <a href="http://localhost/PhpWeb/session/logout.php">logout</a>
    </body>
</html>
