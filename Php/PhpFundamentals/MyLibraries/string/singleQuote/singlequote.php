<!--
To change this template, choose Tools | Templates
and open the template in the editor.

Single quoted

The simplest way to specify a string is to enclose it in single quotes (the character ').

To specify a literal single quote, escape it with a backslash (\). 
To specify a literal backslash, double it (\\).
All other instances of backslash will be treated as a literal backslash: 
this means that the other escape sequences you might be used to, such as \r or \n,
will be output literally as specified rather than having any special meaning. 
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        echo 'this is a simple string';

        echo 'You can also have embedded newlines in 
strings this way as it is
okay to do';

// Outputs: Arnold once said: "I'll be back"
        echo 'Arnold once said: "I\'ll be back"';

// Outputs: You deleted C:\*.*?
        echo 'You deleted C:\\*.*?';

// Outputs: You deleted C:\*.*?
        echo 'You deleted C:\*.*?';

// Outputs: This will not expand: \n a newline
        echo 'This will not expand: \n a newline';

// Outputs: Variables do not $expand $either
        echo 'Variables do not $expand $either';
        ?>
    </body>
</html>
