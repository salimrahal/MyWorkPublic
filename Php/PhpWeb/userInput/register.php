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
        <h1>Registration</h1>
        <?php
        //only show the form if it's the first time the user submit this form
        if (!isset($_POST['register']) || ($_POST['register'] != 'Register')) {
            ?>
            <form method="post" action="register.php">
                <table>
                    <tr><td>E-mail address:</td>
                        <td><input type='text' name='email'/></td></tr>
                    <tr><td>First name:</td>
                        <td><input type='text' name='first_name'/></td></tr>
                    <tr><td>Last name:</td>
                        <td><input type='text' name='last_name'/></td></tr>
                    <tr><td>Password:</td>
                        <td><input type='password' name='password'/></td></tr>
                    <tr>
                        <td colspan='2'>
                            <input type='submit' name='register' value='Register'/>
                        </td>
                    </tr>
                </table>
            </form>
            <?php
        } else {//This piece of code is executed if the form was filled out
            ?>
            E-mail: <?php echo $_POST['email']; ?><br />
            Name: <?php echo $_POST['first_name'] . ' ' . $_POST['last_name']; ?><br />
            Password: <?php echo $_POST['password']; ?><br />
            <?php
        }
        ?>
    </body>
</html>
