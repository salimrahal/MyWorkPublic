<!--
To change this template, choose Tools | Templates
and open the template in the editor.
http://us1.php.net/manual/en/function.filter-var.php
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        #$url = "http://example.php?name=Peter&age=37";//valid
        $url = "/example.php";//false

        if (!filter_var($url, FILTER_VALIDATE_URL, FILTER_FLAG_SCHEME_REQUIRED)) {
            echo "$url URL is not valid";
        } else {
            echo "$url URL is valid";
        }
        
        
        //validate emails
        var_dump(filter_var('bob@example.com', FILTER_VALIDATE_EMAIL));
        var_dump(filter_var('http://example.com', FILTER_VALIDATE_URL, FILTER_FLAG_PATH_REQUIRED));
        ?>
    </body>
</html>
