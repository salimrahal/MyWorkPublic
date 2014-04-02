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
        /*Implode join an array to a String
         * http://www.php.net/manual/en/function.implode.php
         */
        $array = array('lastname', 'email', 'phone');
        $comma_separated = implode(",", $array);

        echo $comma_separated; // lastname,email,phone
// Empty string when using an empty array:
        var_dump(implode('hello', array())); // string(0) ""
        
        /*
         * Join
         */
        //Join same as implode()
        echo "</br>";
$arr=array("First","Second","Third","Four","....+N");
echo join("Seperator",$arr);
        ?>
    </body>
</html>
