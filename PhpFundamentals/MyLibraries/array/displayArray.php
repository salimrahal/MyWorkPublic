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
        $array = array(array(141, 151, 161), 2, 3, array(101, 202, 303));
        #You want to display all the values in the array. The correct way is:

        function DisplayArray($array) {
            foreach ($array as $value) {
                if (is_array($value)) {
                    DisplayArray($value);
                } else {
                    echo $value . "<br>";
                }
            }
        }
        DisplayArray($array);
        ?>
    </body>
</html>
