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
        echo "</br>---------path----</br>";
        $subject = "abcdef";
        $pattern = '/^def/';
        preg_match($pattern, substr($subject, 3), $matches, PREG_OFFSET_CAPTURE);
        print_r($matches);
        echo"</br>";
        echo"======================</br>";
        //pattern below using delimiter "()": /Letters/number
        $pattern2 = "(^/([a-z]+)/(\d+)$)i";
        $pattern3 = "(^/([a-z]+)/([a-z]+))i";
        $pattern4 = "(^/([a-z]+)/([a-z\d+]+))i";
        $pattern5 = "(^/([a-z\d+]+)/([a-z\d+]+))i";
        $pattern6 = "(^//(www))i";
        $pattern7 = "(^(www))i";
        $pattern8 = "(^//)i";
        $pattern9 = "(^[a-z])i";
        
        $samplerequesturi1 = "/variable/12345678910";
        $samplerequesturi2 = "en.variable/12345678910";
        $url3 = "/wiki/Wikipedia";
        $url4 = "/wiki/Wikipedia:Introduction";
        $url5 = "//pl.wikipedia.org/wiki/Wikipedia:Lista_wersji_j%C4%99zykowych";
        $url6 = "//pl.wikipedia.org/wiki#top";
        $url7 = "/variable1234/12345678910";
        $url8= "http://www.google.com";
        $url9 = "//www.google.com";
        $url10 = "/wiki/File:David_X._Cohen_by_Gage_Skidmore_2.jpg";
        $url11 = "www.google.com";
        $url12 = "en.wikipedia.com";
        $url13 = "out.html";
        
        //preg_match("(^/([a-z]+)/(\d+)$)i", $samplerequesturi, $out);
        $arr = array($samplerequesturi1, $samplerequesturi2, $url3, $url4, $url5, $url6, $url7, $url8, $url9, $url10, $url11, $url12, $url13);

        echo "</br>";
        /* OUTPUT BELOW   */ 
        foreach ($arr as $value) {
            preg_match($pattern9, $value, $out);    
            print_r($out);
            if (empty($out)) {
                echo "$value array empty";
            } else {
                 echo " $value array not empty";
            }
            echo "</br>";
        }
         /*
         foreach ($arr as $value) {
             echo "$value</br>";
            preg_match($pattern6, $value, $out1);
            preg_match($pattern7, $value, $out2);
            if(!empty($out1) || !empty($out2)){
                print_r($out1."/");
                print_r($out2);
            echo "</br>";
            }          
        }*/
        ?>
    </body>
</html>
