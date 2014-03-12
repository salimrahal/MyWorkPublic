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
       $str = " Sa lim ";
       echo "--trim</br>--";
       echo "[".trim($str)."]";
       echo "--</br>Leading trim: ltrim</br>--";
       $str = " Salim ";
       echo "[".ltrim($str)."]";
       
       echo "--</br>End white spece: rtrim</br>--";
       $str = " Salim ";
       echo "[".rtrim($str)."]";
       ?>
    </body>
</html>
