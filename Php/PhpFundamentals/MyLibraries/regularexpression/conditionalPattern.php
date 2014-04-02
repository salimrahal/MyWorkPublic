<!--
To change this template, choose Tools | Templates
and open the template in the editor.
http://www.phpliveregex.com/
[abc] 	A single character of: a, b or c
[^abc] 	Any single character except: a, b, or c
[a-z] 	Any single character in the range a-z
[a-zA-Z] 	Any single character in the range a-z or A-Z
^ 	Start of line
$ 	End of line
\A 	Start of string
\z 	End of string
. 	Any single character
\s 	Any whitespace character
\S 	Any non-whitespace character
\d 	Any digit
\D 	Any non-digit
\w 	Any word character (letter, number, underscore)
\W 	Any non-word character
\b 	Any word boundary
(...) 	Capture everything enclosed
(a|b) 	a or b
a? 	Zero or one of a
a* 	Zero or more of a
a+ 	One or more of a
a{3} 	Exactly 3 of a
a{3,} 	3 or more of a
a{3,6} 	Between 3 and 6 of a


-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        /*
         * Repetition of a subpattern will repeat conditionals that are contained inside it, 
          updating subpattern matches with iteration.
          Consider the following code, which scans thru HTML, keeping track of angle brackets "<" ">".
          If open bracket "<" matches, then closing bracket ">" must follow before repetition can possibly end.
          That way regex will effectively match only outside of tags.
         */
        $pattern = '%(*ANY)(.*?(<)(?(2).*?>)(.*?))*?\'\'%s';
        $replace = '\1Fred';
        $subject = "<html><body class=\'\'>\'\' went to '\'\meyer and ran into <b>\'\'</b>. </body></html>";
        echo preg_replace("%(*ANY)(.*?((<)(?(3).*?>).*?)*?)\'\'%s", '\1Fred', $subject);

        /*
         * second pattern : email
         */
        echo "</br>----------second example email matching-----</br>";
        $pattern = "/^[^@ ]+@[^@ ]+.[^@ ]+$/";
        $subject = "salim.rahal@hotmail.com";
        preg_match($pattern, $subject, $matches, PREG_OFFSET_CAPTURE);
        print_r($matches); //ErrorWarning: preg_match(): Unknown modifier '@' in /home/salim/public_html/PhpProject2/MyLibraries/regularexpression/conditionalPattern.php on line 3

        echo "</br>----------3rd example / using offeset and substring -----</br>";
        /*
         *  Using offset is not equivalent to passing substr($subject, $offset) to preg_match() in place of the subject string, 
         * because pattern can contain assertions such as ^, $ or (?<=x). Compare: 
         */
        $subject = "abcdef";
        $pattern = '/^def/';
        preg_match($pattern, $subject, $matches, PREG_OFFSET_CAPTURE, 3);
        print_r($matches); //empty match: Array ( ) 

        echo "</br>--------</br>";
        $subject = "abcdef";
        $pattern = '/^def/';
        preg_match($pattern, substr($subject, 3), $matches, PREG_OFFSET_CAPTURE);
        print_r($matches);
        $str = substr($subject, 3); //Array ( [0] => Array ( [0] => def [1] => 0 ) )
        print "substr=" . $str;

        echo "</br>-----4 voiyels Test---</br>";
        $pattern = "/^[aieuo]{4}$/";
        $subject = "aaaa";
        if (preg_match($pattern,$subject, $matches1, PREG_OFFSET_CAPTURE)) {
            echo "A match was found.";
             print_r($matches1);//it matches
        } else {
            echo "A match was not found.";
             print_r($matches1);
        }
        ?> 

    </body>
</html>
