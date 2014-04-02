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
        // here comes some String split functions
        echo'---------explode---------</br>';
        $string = "S.A.L.I.M Hello";
        $arr = explode(".", $string);
        print_r($arr);

        echo'</br>---------str_split: convert string to array---------</br>';
        $str = "Hello Friend";

        $arr1 = str_split($str);
        $arr2 = str_split($str, 3);

        print_r($arr1);//Array ( [0] => H [1] => e [2] => l [3] => l [4] => o [5] => [6] => F [7] => r [8] => i [9] => e [10] => n [11] => d )
        print_r($arr2);//Array ( [0] => Hel [1] => lo [2] => Fri [3] => end )
         echo'</br>---------strLength---------</br>';
        echo $str."-length=". strlen($str);
         echo'</br>--------implode---------</br>';
        echo ($str."=".implode("---", $arr1));//H---e---l---l---o--- ---F---r---i---e---n---d 
        ?>
    </body>
</html>
