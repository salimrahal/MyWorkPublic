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
        const LOCALHOST = 'localhost';
        const USER = 'root';
        const PASSWORD = 'root';
        /*
         * Returns: 
         * Type:
           resource
           Description:
           a MySQL link identifier on success or FALSE on failure.
         */
        $db_link = mysql_connect(LOCALHOST,USER,PASSWORD);
        if(is_resource($db_link)){
            echo "$db_link is ressource type. get_resource_type()::".get_resource_type($db_link)."</br>";
        }
        
        ?>
    </body>
</html>
