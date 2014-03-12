<!--
To change this template, choose Tools | Templates
and open the template in the editor.
http://php.net/manual/en/function.parse-url.php
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>

        <?php
        $url = 'http://username:password@hostname/path?arg=value#anchor';
        $arrayRes = parse_url($url);
        print_r($arrayRes);
       //Array ( [scheme] => http [host] => hostname [user] => username
       //[pass] => password [path] => /path [query] => arg=value [fragment] => anchor ) 
        echo "</br>";
        echo '$arrayRes[\'host\']='.$arrayRes['host'];
        //echo parse_url($url, PHP_URL_PATH);
        
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
             $arrayRes = parse_url($value);
             //print_r("URL: $value /Array:");
             //print_r($arrayRes);
             echo ('$arrayRes[\'host\']='.$arrayRes['host']);
          echo "</br>";
        }
        ?>

    </body>
</html>
