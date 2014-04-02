<?php

/* * ************** Boolean ****************** */
$numerator = 1;
$denominator = 5;
if ($denominator) {
    /* Perform calculation */
    print "Perform calculation\n";
} else {
    print "The denominator needs to be a non-zero number\n";
}

/* * ******* Array bool********* */
$arr = array();
//$arr[0]="kiki";
if ($arr) {
    print "the array contains at least one element";
} else {
    print"this array doesnt contains anything";
}

/****************** Boolean evaluation***************/
print "<br><center>************Boolena evaluation************</center>";
if ("FALSE") {
    print "</br>\"FALSE\" is evaluated as true";
} else {
    print "</br>\"FALSE\" is evaluated as false";
}

if (0) {
    print "</br>0 is evaluated as true";
} else {
    print "</br>0 is evaluated as false";
}

if ("0") {
    print "</br>\"0\" is evaluated as true";
} else {
    print "</br>\"0\" is evaluated as false";
}
if (FALSE) {
    print "</br>FALSE is evaluated as true";
} else {
    print "</br>FALSE is evaluated as false";
}
if (1) {
    print "</br>1 is evaluated as true";
} else {
    print "</br>1 is evaluated as false";
}
if (NULL) {
    print "</br>NULL is evaluated as true";
} else {
    print "</br>NULL is evaluated as false";
}
if ("NULL") {
    print "</br>\"NULL\" is evaluated as true";
} else {
    print "</br>\"NULL\" is evaluated as false";
}
?>