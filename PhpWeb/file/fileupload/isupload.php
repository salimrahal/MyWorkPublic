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
        /***SCRIPT NOT OPERATIONEL YET
         * http://php.net/manual/en/function.is-uploaded-file.php
         * *******/
        if (is_uploaded_file($_FILES['userfile']['tmp_name'])) {
            echo "File " . $_FILES['userfile']['name'] . " uploaded successfully.\n";
            echo "Displaying contents\n";
            readfile($_FILES['userfile']['tmp_name']);
        } else {
            echo "Possible file upload attack: ";
            echo "filename '" . $_FILES['userfile']['tmp_name'] . "'.";
        }
        ?>
    </body>
</html>
