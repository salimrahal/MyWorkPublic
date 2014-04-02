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
        /*
         * Possible  flags:

          FILTER_FLAG_SCHEME_REQUIRED - Requires URL to be an RFC compliant URL (like http://example)
          FILTER_FLAG_HOST_REQUIRED - Requires URL to include host name (like http://www.example.com)
          FILTER_FLAG_PATH_REQUIRED - Requires URL to have a path after the domain name (like www.example.com/example1/test2/)
          FILTER_FLAG_QUERY_REQUIRED - Requires URL to have a query string (like "example.php?name=Peter&age=37")

         */
#http://www.w3schools.com/php/filter_validate_url.asp
#$url = "http://www.example.com";
#$url = "example.php?name=Peter&age=37";
        main();

        function main() {
            //website
            $url1 = "http://www.google.com";
            $url2 = "//www.google.com";
            $url3 = "//www.google.com";
            $url4 = "//en.wikipedia.org/";
            $url5 = "en.wikipedia.org";
            //Path
            $url6 = "/wiki/Wikipedia";
            $url61 = "/wiki/Wikipedia:Introduction";
            $url7 = "//pl.wikipedia.org/wiki/Wikipedia:Lista_wersji_j%C4%99zykowych";
            $url8 = "//wikimediafoundation.org/wiki/Privacy_policy";
            $url9 = "/wiki/File:David_X._Cohen_by_Gage_Skidmore_2.jpg";
            $url10 = "http://safiratech.com/wp-content/uploads/galleries/technical/general/Java and Ubuntu";

            //JS link
            $url11 = "#content";

            //Https:
            $url12 = "https://www.facebook.com/groups/safiratech/";
            $url13 = "//en.m.wikipedia.org/w/index.php?title=Main_Page&title=Main_Page";
            $url14 = "http://en.m.wikipedia.org/w/index.php?title=Main_Page&title=Main_Page";
            $url15 = "mailto://salim.rahal@gmail.com";
            $url16 = "ssh://ulserver.isae.edu.lb";
            $url17 = "http://localhost/slacker/web/sourceSample";
            $url18 = "http://safiratech.com/primefaces-jsf/#respond";
            
            $arr = array($url1, $url2, $url3, $url4, $url5, $url6,$url61, $url7, $url8, $url9, $url10, $url11, $url12, $url13, $url14, $url15, $url16, $url17, $url18);
            
             echo "FILTER_VALIDATE_URL, NO FLAGS:</br>";
            /* OUTPUT BELOW
            
             */
            foreach ($arr as $value) {
                checkValidity($value, null);
            }
            echo "FILTER_FLAG_SCHEME_REQUIRED:</br>";
            /* OUTPUT BELOW
             * http://www.google.com URL is valid
              //www.google.com URL is not valid
              //www.google.com URL is not valid
              //en.wikipedia.org/ URL is not valid
              en.wikipedia.org URL is not valid
              /wiki/Wikipedia URL is not valid
              //pl.wikipedia.org/wiki/Wikipedia:Lista_wersji_j%C4%99zykowych URL is not valid
              //wikimediafoundation.org/wiki/Privacy_policy URL is not valid
              /wiki/File:David_X._Cohen_by_Gage_Skidmore_2.jpg URL is not valid
              http://safiratech.com/wp-content/uploads/galleries/technical/general/Java and Ubuntu URL is not valid
              #content URL is not valid
              https://www.facebook.com/groups/safiratech/ URL is valid
             */
            foreach ($arr as $value) {
                checkValidity($value, FILTER_FLAG_SCHEME_REQUIRED);
            }
            echo "</br>FILTER_FLAG_PATH_REQUIRED:</br>";
            /* OUTPUT BELOW
              http://www.google.com URL is not valid
              //www.google.com URL is not valid
              //www.google.com URL is not valid
              //en.wikipedia.org/ URL is not valid
              en.wikipedia.org URL is not valid
              /wiki/Wikipedia URL is not valid
              //pl.wikipedia.org/wiki/Wikipedia:Lista_wersji_j%C4%99zykowych URL is not valid
              //wikimediafoundation.org/wiki/Privacy_policy URL is not valid
              /wiki/File:David_X._Cohen_by_Gage_Skidmore_2.jpg URL is not valid
              http://safiratech.com/wp-content/uploads/galleries/technical/general/Java and Ubuntu URL is not valid
              #content URL is not valid
              https://www.facebook.com/groups/safiratech/ URL is valid
             */
            foreach ($arr as $value) {
                checkValidity($value, FILTER_FLAG_PATH_REQUIRED);
            }
            
                echo "</br>FILTER_FLAG_QUERY_REQUIRED:</br>";
            /* OUTPUT BELOW
               */
            foreach ($arr as $value) {
                checkValidity($value, FILTER_FLAG_QUERY_REQUIRED);
            }
        }

        /*
         * 
         */

        function checkValidity($url, $flag) {
            if (!filter_var($url, FILTER_VALIDATE_URL, $flag)) {
                echo "$url URL is not valid</br>";
            } else {
                echo "<b>$url URL is valid</br></b>";
            }
        }

        /*
         * URL format:
         *    http://www.xxxxxxxxxx: valid
         *    //www.xxxxxxxxxxxxx
         *    //en.wikipedia.com
         *    www.xxxx.com
         *    #
         *    
         */

        function prepareUrl($urlparam) {
            if (strpos($urlparam, 'http:') !== FALSE) {
                #echo 'Found http, dont append it';
                ;
                if (strpos($urlparam, 'http://') !== FALSE) {
                    #echo 'Found http, dont append it';
                    ;
                } else {
                    $urlparam = "http://" . $urlparam;
                }
            }

            return $urlparam;
        }

        /*
         *  Test URL Validity
         *  
          Invalid URL: google.com; www.google.com; //www.safiratech.com/
          Valid URL: http://google.com | http://www.google.com

         */
        ?> 

    </body>
</html>
