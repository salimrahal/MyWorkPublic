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
        class Auth {

            function Auth() {
                mysql_connect('localhost', 'root', 'root');
                mysql_select_db('my_own_bookshop');
            }

            public function addUser($email, $password) {
                $q = '
INSERT INTO users(email, passwd)
VALUES ("' . $email . '", "' . sha1($password) . '")
';
                mysql_query($q);
            }

            public function authUser($email, $password) {
                $q = '
SELECT * FROM users
WHERE email="' . $email . '"
AND passwd ="' . sha1($password) . '"
';
                $r = mysql_query($q);
                if (mysql_num_rows($r) == 1) {
                    return TRUE;
                } else {
                    return FALSE;
                }
            }
        }
        ?>
    </body>
</html>
