<!--
To change this template, choose Tools | Templates
and open the template in the editor.

NB: is not wise to use cookie to login or to store the UID in the web browser
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        //turning on output buffering which is needed to send cookies (or other headers) after you output data
        ob_start();
         function check_auth() {
        return 4;
    }
        ?>
    <html>
        <head><title>Login</title></head>
        <body>
            <?php
            if (isset($_POST['login']) && ($_POST['login'] == 'Log in') &&
                    //($uid = check_auth($_POST['email'], $_POST['password']))) {
                    ($uid = check_auth($_POST['email'], $_POST['password']))) {
                /* User successfully logged in, setting cookie */
                setcookie('uid', $uid, time() + 14400, '/');
                header('Location: http://localhost/PhpWeb/cookies/ressource.php');
                exit();
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
            
            /**delete a cookie
             * 
             setcookie('uid', '', time() - 86400, '/');
             header('Location: http://kossu/examples/login.php');
             * 
             * 
             * 
             */
            ?>
        </body>
    </html>
