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
        
               /**
        * helper to append text to a file
         *
         * @static
         * @param $file string
         * @param $text string
         */
        function fappend($file, $text) {
            $fh = fopen($file, 'a');
            fwrite($fh, $text);
            fclose($fh);
        }
        #Example #1 Reading a file line by line
        function filePrint(){
        $handle = fopen("inputfile.txt", "r");
        if ($handle) {
            while (($buffer = fgets($handle, 4096)) !== false) {
                echo $buffer;
            }   
            if (!feof($handle)) {
                echo "Error: unexpected fgets() fail\n";
            }
            fclose($handle);
        }
        }
        filePrint();
        fappend("inputfile.txt", "appended text bla bla");
        echo "</br>";
        filePrint();
        ?>
    </body>
</html>
