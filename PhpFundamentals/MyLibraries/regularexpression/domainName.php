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
        $samplerequesturi1 = "/variable/12345678910";
        $samplerequesturi2 = "en.variable/12345678910";
        $url3 = "/wiki/Wikipedia";
        $url4 = "/wiki/Wikipedia:Introduction";
        $url5 = "//pl.wikipedia.org/wiki/Wikipedia:Lista_wersji_j%C4%99zykowych";
        $url6 = "//pl.wikipedia.org/wiki#top";
        $url7 = "/variable1234/12345678910";
        $url8 = "http://www.google.com";
        $url9 = "//www.google.com";
        $url10 = "/wiki/File:David_X._Cohen_by_Gage_Skidmore_2.jpg";
        $url11 = "www.google.com";
        $url12 = "en.wikipedia.com";
        $url13 = "out.html";


        $arr = array($samplerequesturi1, $samplerequesturi2, $url3, $url4, $url5, $url6, $url7, $url8, $url9, $url10, $url11, $url12, $url13);

        echo "</br>";
        /* OUTPUT BELOW   */
        foreach ($arr as $value) {
           
            $res = is_valid_domain_name($value);
            echo "$value:";
            print_r($res);
            $res = null;
           
            echo "</br>";
        }

        function is_valid_domain_name($domain_name) {
            return (preg_match("/^([a-z\d](-*[a-z\d])*)(\.([a-z\d](-*[a-z\d])*))*$/i", $domain_name) //valid chars check
                    && preg_match("/^.{1,253}$/", $domain_name) //overall length check
                    && preg_match("/^[^\.]{1,63}(\.[^\.]{1,63})*$/", $domain_name) ); //length of each label
        }
        ?>
    </body>
</html>
