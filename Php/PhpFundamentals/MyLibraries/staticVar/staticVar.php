<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//Static Variables:: A static variable exists only in a local function scope
function do_something() {
    static $first_time = TRUE; //initialized once
    if (first_time) {
        print("excuted </br>");
    }
// Execute the function's main logic every time the function is called
    print("main process1...</br>");
}

do_something();
do_something();
do_something();

function test() {
    $a = 0;
    echo $a . "</br>";
    $a++;
}

test();
test();
/*
 * Now, $a is initialized only in first call of function 
 * and every time the test() function 
 * is called it will print the value of $a and increment it. 
 */

function testV2() {
    static $a = 0;
    echo $a . "</br>";
    $a++;
}

test();
test();
testV2();
testV2();
testV2();
testV2();

//Example #6 Static variables with recursive functions
function testRecursive() {
    static $count = 0;
    $count++;
    echo "testRecursive count= "+$count;
    if ($count < 10) {
        testRecursive();
    }
    $count--;
}
testRecursive();
?>
