<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of CurlProcessing
 *
 * @author salim
 */
class CurlProcessing {
    //put your code here

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//$text = getHTMLUsingCurl($urlparam);
//echo 'return html/text['.$text.']';
//
////Ok it returns only the body
//$body = getBodyTagUsingCurl($text);
//echo'returned body=['.$body.']';


/*
 * It return the body of a web ressource remote/local
 * It Accpets: website.com | www.website.com | http://www.website.com
 * 

function getBodyTagUsingCurl($url) {
    $ch = curl_init($url);
    //$ch = curl_init('http://localhost/slacker/sourceSample.html');
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $text = curl_exec($ch);
    //echo 'getBodyTagUsingCurl result $text=[' . $text . ']<br>';
    if (preg_match('~<body[^>]*>(.*?)</body>~si', $text, $body)) {
        
        //echo $body[1];
    } else {
        echo 'Error: no body bound or the URL is not Accessible!';
    }
    curl_close($ch);
    return $body[1];
}
 *
 */
function getHTMLUsingCurl($url) {
    $ch = curl_init($url);
    //$ch = curl_init('http://localhost/slacker/sourceSample.html');
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $text = curl_exec($ch); 
    curl_close($ch);
    return $text;
}

function getBodyTagUsingCurl($text) {
    //echo 'getBodyTagUsingCurl result $text=[' . $text . ']<br>';
    if (preg_match('~<body[^>]*>(.*?)</body>~si', $text, $body)) {      
        //echo $body[1];
    } else {
        echo 'Error: no body bound or the URL is not Accessible!';
    }
    return $body[1];
}


}

?>
