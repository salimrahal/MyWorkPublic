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
        //hinting scalar
echo"------------------------------------ Hinting scalar cannot be used -------------------------";
function test_int(int $x){
    print spl_object_hash($x);
}
class int {
    
}
$y = "10";
$int = new int();
// Catchable fatal error
test_int($int);//out: hash code: 000000000879d4ef00000000fab401b1
        ?>
    </body>
</html>
