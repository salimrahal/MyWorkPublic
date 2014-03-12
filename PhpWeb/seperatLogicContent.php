<?php
/* If it is April 1st, we show a quote */
if (date('md' == '0401')) {
    $greeting = 'A bookstore is one of the only pieces of ' .
            'evidence we have that people are still thinking. ' .
            '<i>Jerry Seinfeld</i>';
} else {
    $greeting = 'Good morning!';
}
?>

<html>
    <head><title>Example 3</title></head>
    <body>
        <?php echo $greeting; ?>
    </body>
</html>