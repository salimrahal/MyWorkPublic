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
        $str = 'apple';

        if (sha1($str) === 'd0be2dc421be4fcd0172e5afceea3970e2f3d940') {
            echo "Would you like a green or red apple?";
            echo count('d0be2dc421be4fcd0172e5afceea3970e2f3d940');
        }

        $checksum = crc32("The quick brown fox jumped over the lazy dog.");
        printf("%u\n", $checksum);
        echo '----------hash length-------</br>';
        $name = "salim";
        echo "</br>sha1 of $name:" . sha1($name);
        echo "</br>md5 of $name:" . md5($name);
        echo "</br>----------------------using crypt function------------------";
#http://www.php.net/manual/en/function.crypt.php

        function encodePassd() {
            $user_input = 'mypassword';
            $hashed_password = crypt('mypassword'); // let the salt be automatically generated

            /* You should pass the entire results of crypt() as the salt for comparing a
              password, to avoid problems when different hashing algorithms are used. (As
              it says above, standard DES-based password hashing uses a 2-character salt,
              but MD5-based hashing uses 12.) */
            if (crypt($user_input, $hashed_password) == $hashed_password) {
                echo "Password verified!";
            }
        }
        ?>
    </body>
</html>
