<!--
To change this template, choose Tools | Templates
and open the template in the editor.

Desc: Login using SESSION. this scripts include session initialization file (session.inc)
1- If the form is validated then redirect to welcome page index.php
2- otherwise redirect to the login form

-->
<!DOCTYPE html>
<html>
    <?php
    include 'session.inc';

    function check_auth() {
        return 4;
    }
    ?>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        if (isset($_POST['login']) && ($_POST['login'] == 'Log in') &&
                ($uid = check_auth($_POST['email'], $_POST['password']))) {
            /* User successfully logged in, setting cookie */
            $_SESSION['uid'] = $uid;
            //Register the email as Global variable to session             
           // session_register('email');Deprecated
            header('Location: http://localhost/PhpWeb/session/ressource.php');
        } else {
            ?>
           <h1>Log-in</h1>
                <form method="post" action="login.php">
                    <table>
                        <tr><td>E-mail address:</td>
                            <td><input type='text' name='email'/></td></tr>
                        <tr><td>Password:</td>
                            <td><input type='password' name='password'/></td></tr>
                        <tr><td colspan='2'>
                                <input type='submit' name='login' value='Log in'/></td>
                        </tr>
                    </table>
                </form>
            <?php
        }
        ?>
    </body>
</html>
