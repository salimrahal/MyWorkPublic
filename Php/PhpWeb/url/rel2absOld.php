<!--
To change this template, choose Tools | Templates
and open the template in the editor.

href="relative.php"
href="/absolute1.php"
href="./relative.php"
href="../relative.php"
href="//absolutedomain.org"
href="." relative
href=".." relative
href="../" relative
href="./" relative
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
 * Not good function, doenst work when passing '.' as current dir
 */
        function rel2abs($rel, $base) {
            if (strpos($rel, "//") === 0) {
                return "http:" . $rel;
            }
            /* return if  already absolute URL */
            if (parse_url($rel, PHP_URL_SCHEME) != '')
                return $rel;
            /* queries and  anchors */
            if ($rel[0] == '#' || $rel[0] == '?')
                return $base . $rel;
            /* parse base URL  and convert to local variables:
              $scheme, $host,  $path */
            //extract(parse_url($base));
            $arrayUrl = parse_url($base);
            //echo 'host='.$arrayUrl['host'].' path ='. $arrayUrl['path'];
            $host = $arrayUrl['host'];
            $path = $arrayUrl['path'];
            $scheme = $arrayUrl['scheme'];
            /* remove  non-directory element from path */
            $path = preg_replace('#/[^/]*$#', '', $path);
            /* destroy path if  relative url points to root */
            if ($rel[0] == '/')
                $path = '';
            /* dirty absolute  URL */
            $abs = "$host$path/$rel";
            /* replace '//' or  '/./' or '/foo/../' with '/' */
            $re = array('#(/\.?/)#', '#/(?!\.\.)[^/]+/\.\./#');
            for ($n = 1; $n > 0; $abs = preg_replace($re, '/', $abs, -1, $n)) {
                
            }
            /* absolute URL is  ready! */
            return $scheme . '://' . $abs;
        }

                echo 'test1='. rel2abs(".", "http://www.example.com/");
// Output: http://www.example.com/dir/page.html
        echo "</br>";
        
        echo rel2abs("/dir/page.html", "http://www.example.com/");
// Output: http://www.example.com/dir/page.html
        echo "</br>";

        echo rel2abs("/dir/page.html", "http://www.example.com/dir1/page2.html");
// Output: http://www.example.com/dir/page.html
echo "</br>";
        echo rel2abs("dir/page.html", "http://www.example.com/dir1/page2.html");
// Output: http://www.example.com/dir1/dir/page.html
echo "</br>";
        echo rel2abs("../dir/page.html", "http://www.example.com/dir1/dir3/page.html");
// Output: http://www.example.com/dir1/dir/page.html

        ?>
    </body>
</html>
