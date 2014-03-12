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
        echo __LINE__;
echo __FILE__;
//echo __PRETTY_FUNCTION__;//doesnt exist
echo __CLASS__;

function fn(){
    echo "__METHOD__=".__METHOD__;
}
fn();

        ?>
    </body>
</html>
