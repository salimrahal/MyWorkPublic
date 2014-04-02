<?php

/* * ***************** Data Type****************** */
/* * ****** String***** */
$str = "PHP: Hypertext Pre-processor"
        . "GET / HTTP/1.0\n"
        . "1234567890";

echo "new line test:" . $str;
echo"<br>-------------------------------</br>";
echo "\$str : $str";
echo"<br>-------------------------------</br>";
print "text</br>";
/* * *********Embeding large text in php Script******** */
<<<the_end
PHP stands for "PHP: Hypertext Preprocessor".
The acronym "PHP" is therefore, usually referred to as a recursive acronym
➥because the long form contains the acronym itself.
As this text is being written in a here-doc there is no need to escape the
➥double quotes.
the_end;

/* * **********Accessing String Offsets**** */
$str = "A";
$str{2} = "d";
$str{1} = "n";
$str = $str . "i";
print $str; //out: andi
$str1 = "salim";
echo"<br>-------------------------------</br>";
//using $str1[index] syntaxe
print "\$str1[0] $str1[0] \$str1[1] $str1[1] \$str1[2] $str1[2] \$str1[3] $str1[3] \$str1[4] $str1[4]\n";
echo"<br>-------------------------------</br>";
//using "str{index syntaxe}
//Inside double the {} are treated as char
print "$str1{0}"; //out: salim{0}

print $str1{0}; //out: a

//---------------------example-----------
echo"<br>-------------indirect reference------------------</br>";
//Which of the following is not a correct way of printing 'ABS Ltd, Sydney'? 
$company = 'ABS Ltd';
$$company = ', Sydney';

echo '$company $$company=' . "$company $$company";
echo"<br>------";
echo '$company {$$company}=' . "$company {$$company}";
echo"<br>------";
echo '$company ${\'ABS Ltd\'}=' . "$company ${'ABS Ltd'}";
echo"<br>------";
echo '$company ${$company}=' . "$company ${$company}";

//---------------example-----------
$par = "The boy is a good student";
$first = $par[0];
$last = $par[strlen($par)-1];
echo $first."/".$last;

//----------------example-------------
// Get the first character of a string
$str = 'This is a test.';
$first = $str[0];

// Get the third character of a string
$third = $str[2];

// Get the last character of a string.
$str = 'This is still a test.';
$last = $str[strlen($str)-1]; 

// Modify the last character of a string
$str = 'Look at the sea';
$str[strlen($str)-1] = 'e';
echo $str;
?>
