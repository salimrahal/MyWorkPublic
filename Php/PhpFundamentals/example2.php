 <?php echo 'if you want to serve XHTML or XML documents, do it like this'; ?>
 <script language="php">
        echo '<br>some editors (like FrontPage) don\'t
              like processing instructions<br>';
    </script>

<? echo 'this is the simplest, an SGML processing instruction'; ?>
<!--<?= expression ?> This is a shortcut for "<? echo expression ?>

<% echo 'You may optionally use ASP-style tags'; %>
    <%= $variable; # This is a shortcut for "<% echo . . ." %>
-->


<?php
    echo 'This is a test'; // This is a one-line c++ style comment
    /* This is a multi line comment
       yet another line of comment */
    echo 'This is yet another test';
    echo 'One Final Test'; # This is a one-line shell-style comment
?>

<?php
// as of PHP 5.4
$array = [
    "foo" => "bar",
    "bar" => "foo",
];
foreach($array as $elem){ ?>
Value: <?=$elem?>

<? } ?>
?>