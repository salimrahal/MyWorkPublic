<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Input validation</title>
    </head>
    <body>
        <?php
        /**/
        if (!isset($_GET['prod_id'])) {
            die("Error, product ID was not set");
        }        
        /* */
        $product_id = (int) $_GET['prod_id'];
        echo $product_id;
        ?>
    </body>
</html>
