<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Example 1</title>
    </head>
    <body>
        <?php
        /* If it is April 1st, we show a quote */
        if (date('md' == '0401')) {
            echo 'A bookstore is one of the only pieces of evidence we have ' .
            'that people are still thinking. <i>Jerry Seinfeld</i>';
        } else {
            echo 'Good morning!';
        }
        ?>
    </body>
</html>
