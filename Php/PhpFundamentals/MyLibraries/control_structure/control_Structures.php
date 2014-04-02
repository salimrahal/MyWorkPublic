<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Conditionnals
//if statement
$num = 0;
if ($num < 0) {
    print '$num is negative';
} elseif ($num == 0) {
    print '$num is zero';
} elseif ($num > 0) {
    print '$num is positive';
}
?>
<!--if statement list, HTML Readable style -->
<?php if ($num < 0): ?>
    <h3>$num is negative</h3>
<?php elseif ($num == 0): ?>
    <h3>$num is zero</h3>
<?php else: ?>
    <h3>$num is positive</h3>
<?php endif; ?>

<?php
$answer = 'Y';
switch ($answer) {
    case 'y':
    case 'Y':
        print "The answer was yes\n";
        break;
    case 'n':
    case 'N':
        print "The answer was no\n";
        break;
    default:
        print "Error: $answer is not a valid answer\n";
        break;
}
print "</br>";
//using different param type
$answer = 12;
switch ($answer) {
    case 1:
    case '12':
        print "The answer was yes\n";
        break;
    case '13':
        print "The answer was no\n";
        break;
    default:
        print "Error: $answer is not a valid answer\n";
        break;
}//out :"The answer was yes
print "</br>";

//while loop
//My factorielle
$x = 10;
$fac = $x;
while ($x > 1) {
    $fac = $fac * ($x - 1);
    $x--;
    //print $fac."</br>";  
}
print "fac(10)=" . $fac; //3628800
print "</br>";
//Their factorielle alternative
$result = 1;
$n = 10;
while ($n > 0) {
    $result *= $n--;
//print $result."</br>";
}
print "The result is $result"; //3628800
//print "</br>";
//do while
do{print "do";
}while(false);
print "</br>";
//for loop
for ($i = 0; $i < 10; $i++) {
print "The square of $i is " . $i*$i . "\n";
}
/*
 * for (;;) {
print "I'm infinite\n";
}
 */
print "</br>";
//Optimization
$arr = array(23,45,66,778,988,859);
/*the count is executed every time*/
for($i=0; $i < count($arr); $i++){
    print $arr[$i]." ";
}

print "</br>";
//instead use this
$count = count($arr);
print "count=".$count."</br>";
for($j=0; $j < $count; $j++){
    print $arr[$j]." ";
}

$a = 10;
if ($a > 5 OR $a < 15)
echo 'true';
else
echo 'false</br>'; 

$a = 10;
if ($a > 5 OR $a < 15){
echo '</br>true';
echo 'second statement';
}
else{
echo 'false';
}
echo '</br>---------test----------</br>';
for ($i = 0; $i <> 10;$i++)
{if($i == 2)
continue;
print "$i\n";
}

Print 4<<5;
?>
