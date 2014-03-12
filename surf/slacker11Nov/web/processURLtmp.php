
<?php
/**
 * Copyright (c) 2013, Salim A. Rahal,Michael Connell, safiratech.com.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *	* Redistributions of source code must retain the above copyright
 *	  notice, this list of conditions and the following disclaimer.
 *
 *	* Redistributions in binary form must reproduce the above
 *	  copyright notice, this list of conditions and the following
 *	  disclaimer in the documentation and/or other materials provided
 *	  with the distribution.
 *
 *	* Neither the names of Salim A. Rahal, Michael Connell or safiratech.com, nor
 *	  the names of its contributors may be used to endorse or promote
 *	  products derived from this software without specific prior
 *	  written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

/*
 * This is a BSD License approved by the Open Source Initiative (OSI).
 * See:  http://www.opensource.org/licenses/bsd-license.php
 */

include('../libs/simplehtmldom_1_5/simple_html_dom.php');
include '../scripts/CurlProcessing.php';
 require '../libs/AbsoluteUrl/url_to_absolute.php';
########################### MAIN Script######################################
$debug = false;
mainProcess($debug);
########################### END MAIN Script#########

/*
 * Main function: It takes a URL (http://www.foo.com | www.foo.com | foo.com ), then retrieves its body and 
 * print it. If the protocol is not determined, it takes by default HTTP protocol.
 * 
 * Parameter:: Absolute URL: HTTP | HTTPS
 * Return: Html Body tag content
 */

function mainProcess($debug) {
    //This is the Valid protocol accepted by this script, Actually only THE HTTP is supported
    $default_protocol = "http:";
    //Excpecting a URL: e.g.: http://www.google.com
    $urlparam = $_GET['urlparam'];
    #$urlparam = 'google.com';
    //TODO: In the futur I should encode correctly the passed URL: http://www.php.net/manual/en/function.urlencode.php
    //checkURLValidity:returns true for the https | http | mailto |ssh
    if (checkURLValidity($urlparam)) {
        processBodyContent($urlparam, $debug);
    } else {
        //prepare URL depends on a protocol passed as param: http | https | emailto | ssh
        $urlparamAdjusted = prepareUrl($urlparam, $default_protocol);
        if (checkURLValidity($urlparamAdjusted)) {
            processBodyContent($urlparamAdjusted, $debug);
        } else {
            echo "ERROR: URL [$urlparam] could not be parsed as a Valid URL!</br>";
        }
    }
}

//end of main function
//
########################### Begin: FUNTIONS - Libraries ######################################

/*
 * Desc: It get a valid URL 
 *  -  retrieve the HTML ressource of the URL
 *  - Ajdust the HREF: from  <a href="myLink.html"> to <a onClick="getSubPageByAjax('myLink.html')">
 *  - then print out the BODY TAG CONTENT 
 */
function processBodyContent($urlValid,$debug) {
    #########get the HTML DOM Object
    #echo "URL === [$url]";
    $html = getHtml($urlValid);
    $body = getHtmlTag($html, 'body');
    #$body = $html;
    # turn the link from  <a href="myLink.html"> to <a onClick="getSubPageByAjax('myLink.html')">
    $bodyAdjusted = updateBodyHref($body, $urlValid, $debug);
    //Returning the BODY value  I.E. excluding the <body> tag from the response
    $bodyValue = $bodyAdjusted->innertext;
    echo $bodyValue;
}

/**
 * Desc: get the body Dom, Loop thru all the Hrefs:
 * 1- get href value : v1
 * 2- set onlclick attribut value to getSubPageByAjax(v1)
 * 3- return the body MODIFIED
 * * */
/*
 * Return DOM
 */
function getHtml($url) {
    // Create DOM from URL or file
    $html = file_get_html($url);
    #$html=mb_convert_encoding($html);
    #echo "All Html:[".$html."]";
    if (!isset($html)) {
        echo "<p >ERROR: getHtml($url): couldn't get HTML!!</p>";
    }
    return $html;
}

function getHtmlTag($html, $tag) {

    $tagValue = $html->getElementByTagName($tag);
    return $tagValue;
}

function getHtmlVtags($html) {
// Find all images
    foreach ($html->find('img') as $element)
        echo $element->src . '<br>';

// Find all links
    foreach ($html->find('a') as $element)
        echo $element->href . '<br>';
}

/* 
 * 1- It turn <a href="myLink.html">My Link</a>
 * into:
 * <a onClick="getSubPageByAjax('myLink.html')">My Link</a>
 * 2- It modify the HTML DOM
 * 3- It turns the relative URL to absolute URL
 */

function updateBodyHref($html, $validParentUrl, $debug) {
// Find all links
    foreach ($html->find('a') as $element) {
        //get the value of the Href: /wiki/main or http://www.safiratech.com/home
        $value = $element->href;
        if($debug){echo "<br>############DEBUG##############
            </br>updateHref function: before update href=[ $value ]";}
        if (!empty($value)) {
                //delete the A.href attribute
                $element->href = null;
                //initialize the adjusted valur with original Href value
                $adjustedHref = $value;
                //Update the incoming Href value only if its not valid
                if (!checkURLValidity($value)) {
                    if($debug){echo "</br>[$value] not valid! need to be fixed";}   
                    $adjustedHref = url_to_absolute($validParentUrl, $adjustedHref );
                     if($debug){echo "</br>relative to ABS = [$adjustedHref]";}  
                }//end of check validity
                else {
                    if($debug){echo "</br> $value is valid";}
                }
                //adding new attribute Onclick that redirect the link to onClick event handler: getSubPageByAjax('http://www.safiratech.com/home')
                $element->onClick = "getSubPageByAjax('" . $adjustedHref . "')";
        }//end $value isset
        else {
            if($debug){echo " $value is empty, leave it.";}
        }
    }//end for loop
    
    //echo 'After change body['.$html.']';      
    return $html;
}

/* useful only for relative Href Value, where the relative URL is not Valid UR
 * http://en.wikipedia.org + /wiki/home; 
 * NB: should Avoid [//]: [http://en.wikipedia.org//wiki/home]
 */

function adjustHrefRelativeUrl($hrefvalue, $urlroot, $seperator, $debug) {
    $rootAppendedHrefUrl = "";
    //$str = rtrim("http://en.wikipedia.org/","/"); ==> http://en.wikipedia.org  
    //remove the end  "/" in case it exists
    $trimedUrlroot = rtrim($urlroot, "/");
    //remove the first "/":  /wiki/home ==> wiki/home
    $trimedHrefValue = ltrim($hrefvalue, "/");
    $rootAppendedHrefUrl = $trimedUrlroot . $seperator . $trimedHrefValue;
    if($debug){echo "adjustHrefRelativeUrl Appended:  [$trimedUrlroot] . [$seperator ]. [$trimedHrefValue]";}
    return $rootAppendedHrefUrl;
}

function testGoogle() {
// Create DOM from URL or file
#$html = file_get_html('http://www.google.com/');
    $html = file_get_html('http://localhost/slacker/web/sourceSample.html');
// Find all images
    foreach ($html->find('img') as $element)
        echo $element->src . '<br>';

// Find all links
    foreach ($html->find('a') as $element)
        echo $element->href . '<br>';
}

function printHtmlElts($html, $elt) {
    // Find all images
    //foreach ($html->find('img') as $element)
    //  echo $element->src . '<br>';
    // Find all links
//    foreach ($html->find($elt) as $element)
//        echo $element->href . '<br>';
    // Find all links
    foreach ($html->find('a') as $element)
        echo $element->href . '<br>';
}

/*
 * 
 */

function getURLasString() {
    $urlparam = $_GET['urlparam'];
//echo 'from php script '.$urlparam;
    if (strpos($urlparam, 'http') !== FALSE)
        echo 'Found http, dont append it';
    else
        $urlparam = "http://" . $urlparam;

    $res = file_get_contents("sourceSample.html");
    $txt = file_get_contents($urlparam);
    echo "$txt";
}

/*
 * get the body tag
 *  Create DOM from URL or file
 */

function findHtmlBodyTag() {
//$html = new simple_html_dom(); 
    $html = file_get_html('http://www.safiratech.com/');
    echo "$html"; //ok
    $body = $html->find('body'); //returns Nothing
    echo "body = " . $body;
}

function getBodyTag() {
    $html = file_get_html('http://www.safiratech.com/');
    echo "$html";
    $str = preg_replace("/\r/", $html, "\s");
//retrieve html between body tags
    preg_match("/<\s*body.*>.*/", $str, $body);
    $result = preg_split("/<(.|\n)*?>/", $body);
    echo "$result";
}

/*
 * should render a valid URL to the SimpleDom php lib
 * URL incoming type
 * 
 */

/*
 * Desc: this function Act on the URL pattern: xx.xxxx.xxx
 * Other URL or Href that contains: [#] [#mw-navigation] or other special character shouldn't be passed to this function
 * Param: $url: web ressource passed from the user input
 *        $protocol: e.g.: http:
 */

function prepareUrl($urlparam, $protocol) {
    $validUrl = $urlparam;
    //covering 2 cases: http://google.com and http://www.google.com
    if (strpos($urlparam, $protocol . '//') === 0) {
        # echo "append nothing, Valid URl";
    }
    //If doesnt contain http://  the append it. it Covers [www.google.com] [#] [anything Else]
    elseif (strpos($urlparam, $protocol . '//') === FALSE) {
        if (strpos($urlparam, '//') === 0) {
            #e.g.: [//en.wikipedia.org/] 
            #echo "appending missing char http:";
            $validUrl = $protocol . $urlparam;
        } else {//Anything else will be appended a http://
            # echo "appending missing char http://";
            $validUrl = $protocol . "//" . $urlparam;
        }
    }
    return $validUrl;
}

/*
 *  Test URL Validity
 *  
  Invalid URL: google.com; www.google.com; //www.safiratech.com/
  Valid URL: http://google.com | http://wwww.google.com,
 *           https://google.com
 *           mailto://salim.rahal@gmail.com
  ssh://ulserver.isae.edu.lb
 * 
 */

function checkURLValidity($url) {
    $res = false;
    if (isset($url)) {
        if (!filter_var($url, FILTER_VALIDATE_URL)) {
            #echo "checkURLValidity URL is not valid:[" . $url . "]";
            $res = false;
        } else {
            #echo "URL is valid";
            $res = true;
        }
    } else {
        echo 'the Passed URL is NOT SET! URL[' . $url . ']</br>';
    }
    return $res;
}

/**
 * http://php.net/manual/en/function.file-get-contents.php
 * 
  make an http POST request and return the response content and headers
  @param string $url    url of the requested script
  @param array $data    hash array of request variables
  @return returns a hash array with response content and headers in the following form:
  array ('content'=>'<html></html>'
  , 'headers'=>array ('HTTP/1.1 200 OK', 'Connection: close', ...)
  )
 */
function http_post($url, $data) {
    $data_url = http_build_query($data);
    $data_len = strlen($data_url);

    return array('content' => file_get_contents($url, false, stream_context_create(array('http' => array('method' => 'POST'
                , 'header' => "Connection: close\r\nContent-Length: $data_len\r\n"
                , 'content' => $data_url
))))
        , 'headers' => $http_response_header
    );
}

?>