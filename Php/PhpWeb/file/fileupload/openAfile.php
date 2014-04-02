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
        include 'fileContdaent.txt';//if it doesnt exists, the code post a warning and contiue processig
        require 'fileContdaent.txt';//if the file doesnt exists, the code issue awarning and wont continue
        print '</br>';
        $handle = fopen("fileContent.txt", "r");
        print_r($handle); //Resource id #3 

//        try {
//            $handle = fopen("fileContdADent.txt", "r");
//        } catch (Exception $e) {
//            echo $e->getFile();
//        }
        echo "processing continue...";
        ?>

    </body>
</html>
