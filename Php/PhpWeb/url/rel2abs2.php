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
        require '../AbsoluteUrl/url_to_absolute.php';
        
         echo 'test0='. url_to_absolute("http://en.wikipedia.org/wiki/Portal:Arts", "/wiki/Portal:Culture");
// Output: http://en.wikipedia.org/wiki/wiki/Portal:Culture
         echo "</br>";
        
//url_to_absolute( $baseUrl, $relativeUrl );
                  echo 'test1='. url_to_absolute("http://www.example.com/", ".");
// Output: http://www.example.com/
        echo "</br>";

        echo 'test2='. url_to_absolute("http://www.example.com/dir1/dir2/", "..");
// Output: test2=http://www.example.com/dir1
  echo "</br>";
//en.wikipedia.org/
             echo 'test3='. url_to_absolute("http://en.wikipedia.org/wiki/Portal:Arts", "//en.wikipedia.org/");
// Output: http://en.wikipedia.org/
        echo "</br>";
        
           echo 'test4='. url_to_absolute("http://en.wikipedia.org/wiki/Portal:Arts", "#mw-navigation");
// Output: test4= http://en.wikipedia.org/wiki/Portal:Arts#mw-navigation
        echo "</br>";
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        echo url_to_absolute("http://www.example.com/", "/dir/page.html");
// Output: http://www.example.com/dir/page.html
        echo "</br>";

        echo url_to_absolute("http://www.example.com/dir1/page2.html", "/dir/page.html");
// Output: http://www.example.com/dir/page.html
echo "</br>";
        echo url_to_absolute("http://www.example.com/dir1/page2.html", "dir/page.html" );
// Output: http://www.example.com/dir1/dir/page.html
echo "</br>";
        echo url_to_absolute("http://www.example.com/dir1/dir3/page.html", "../dir/page.html");
// Output: http://www.example.com/dir1/dir/page.html
        ?>
    </body>
</html>
