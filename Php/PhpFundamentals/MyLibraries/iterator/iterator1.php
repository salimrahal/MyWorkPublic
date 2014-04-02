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
        $fruits = array(
            "apple" => "yummy",
            "orange" => "ah ya, nice",
            "grape" => "wow, I love it!",
            "plum" => "nah, not me"
        );
        $obj = new ArrayObject($fruits);
        $it = $obj->getIterator();

// How many items are we iterating over?
        echo "Iterating over: " . $obj->count() . " values\n";

// Iterate over the values in the ArrayObject:
        while ($it->valid()) {
            echo $it->key() . "=" . $it->current() . "\n";
            $it->next();
        }

// The good thing here is that it can be iterated with foreach loop

        foreach ($it as $key => $val)
            echo $key . ":" . $val . "\n";
        
/* Outputs something like */
/*
 Iterating over: 4 values
apple=yummy
orange=ah ya, nice
grape=wow, I love it!
plum=nah, not me
 
 */
        ?>
    </body>
</html>
