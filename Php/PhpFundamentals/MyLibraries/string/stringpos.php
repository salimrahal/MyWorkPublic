<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
containtest3();
function containtest3() {
    $url = "http://localhost/slacker/web/sourceSample/out2.html";
    if (strpos($url, "http://", 0) === FALSE) {
        echo "-----------: Yes, it doesnt contaisn http";
    } else {
        echo "--it contains http://</br>";
    }
}

function containtest2() {
    if (strpos("#", "#", 0) === FALSE) {
        //delete the href attribute
        echo"adjust the href";
    } else {
        echo"Dont add event / Leave the element";
    }
    if (strpos("#nav-div", "#", 0) === FALSE) {
        //delete the href attribute
        echo"adjust the href";
    } else {
        echo"Dont add event / Leave the element";
    }
    if (strpos("/w/dd/", "#", 0) === FALSE) {
        //delete the href attribute
        echo"adjust the href";
    } else {
        echo"Dont add event / Leave the element";
    }

    echo"strpos(\"http://salim.rahal.com\", 'http:')'=" . strpos("http://salim.rahal.com", 'http:') . "</br>"; //retrurns 0
    echo"strpos(\"http://salim.rahal.com\", 'http://')'=" . strpos("http://salim.rahal.com", 'http://') . "</br>"; // res = 0

    if (strpos("www.salim.rahal.com", '//') === 0) {
        echo 'Found'; // it goes here
    } else {
        echo "Not found";
    }
}

/*
 * 
  //www.safiratech.com/ URL is not valid
  google.com URL is not valid
  www.google.com URL is not valid
  http://google.com URL is valid
  http://www.google.com URL is valid
 */
$url = "//www.safiratech.com/";
checkValidity($url);
$url = prepareUrl($url);
checkValidity($url);

$url = "safiratech.com";
checkValidity($url);
$url = prepareUrl($url);
checkValidity($url);

$url = "google.com";
checkValidity($url);
$url = prepareUrl($url);
checkValidity($url);

$url = "www.google.com";
checkValidity($url);
$url = prepareUrl($url);
checkValidity($url);

$url = "http://google.com";
checkValidity($url);
$url = prepareUrl($url);
checkValidity($url);

$url = "http://www.google.com";
checkValidity($url);
$url = prepareUrl($url);
checkValidity($url);

function checkValidity($url) {
    if (!filter_var($url, FILTER_VALIDATE_URL)) {
        echo "<br> $url URL is not valid</br>";
    } else {
        echo " $url URL is valid</br>";
    }
}

/*
 * Desc: this function Act on the URL pattern: xx.xxxx.xxx
 * Other URL or Href that contains: [#] or other special character shouldnt be passed to this function
 */

function prepareUrl($urlparam) {
    $validUrl = $urlparam;
    //covering 2 cases: http://google.com and http://www.google.com
    if (strpos($urlparam, 'http://') === 0) {
        # echo "append nothing, Valid URl";
    }
    //If doesnt contain http://  the append it. it Covers [www.google.com] [#] [anything Else]
    elseif (strpos($urlparam, 'http://') === FALSE) {
        if (strpos($urlparam, '//') === 0) {
            #echo "appending missing char http:";
            $validUrl = "http:" . $urlparam;
        } else {//Anything else will be appended a http://
            # echo "appending missing char http://";
            $validUrl = "http://" . $urlparam;
        }
    }
    return $validUrl;
}

function checkURLValidity($url) {
    $res = false;
    if (isset($url)) {
        if (!filter_var($url, FILTER_VALIDATE_URL)) {
            echo "URL is not valid:" . $url;
            $res = false;
        } else {
            #echo "URL is valid";
            $res = true;
        }
    } else {
        echo 'the Passed URL is NOT SET! URL[' . $url . ']';
    }
    return $res;
}

?>
