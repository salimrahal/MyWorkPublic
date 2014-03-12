
<?php

/**
 * Copyright (c) 2013, Salim A. Rahal,Michael Connell, safiratech.com.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 *
 * 	* Redistributions in binary form must reproduce the above
 * 	  copyright notice, this list of conditions and the following
 * 	  disclaimer in the documentation and/or other materials provided
 * 	  with the distribution.
 *
 * 	* Neither the names of Salim A. Rahal, Michael Connell or safiratech.com, nor
 * 	  the names of its contributors may be used to endorse or promote
 * 	  products derived from this software without specific prior
 * 	  written permission.
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

require_once('../libs/simplehtmldom_1_5/simple_html_dom.php');
require_once 'parserproxy.php';
require_once '../libs/AbsoluteUrl/url_to_absolute.php';
require_once './staticvar.php';
########################### MAIN Script######################################
//$debug = false;

mainProcess();
########################### END MAIN Script#########

/*
 * Main function: It takes a URL (http://www.foo.com | www.foo.com | foo.com ), then retrieves its body and 
 * print it. If the protocol is not determined, it takes by default HTTP protocol.
 * 
 * Parameter:: Absolute URL: HTTP | HTTPS
 * Return: Html Body tag content
 */

function mainProcess() {
    //This is the Valid protocol accepted by this script, Actually only THE HTTP is supported
    $default_protocol = "http:";
    /* 2 case to handle:
     * 1- browsing page 
     * 2- submtting forms: Deprecated
     */
    //cheking for param before processing
    $urlparam = isset($_GET['urlparam']) ? $_GET['urlparam'] : false;
    //Deprecated
    #$action = isset($_GET['action']) ? $_GET['action'] : false;
    //1- case of browsing
    if ($urlparam != false) {
        #$urlparam = 'google.com';
        //TODO: In the futur I should encode correctly the passed URL: http://www.php.net/manual/en/function.urlencode.php
        //checkURLValidity:returns true for the https | http | mailto |ssh
        if (checkURLValidity($urlparam)) {
            $html = getHtml($urlparam);
            processBodyContent($html, $urlparam);
        } else {
            //prepare URL depends on a protocol passed as param: http | https | emailto | ssh
            $urlparamAdjusted = prepareUrl($urlparam, $default_protocol);
            if (checkURLValidity($urlparamAdjusted)) {
                $html = getHtml($urlparamAdjusted);
                processBodyContent($html, $urlparamAdjusted);
            } else {
                echo "ERROR: URL [$urlparam] could not be parsed as a Valid URL!</br>";
            }
        }
    }//end of URL browsing
    /*2- case of submitting a form*/
    elseif ($action == "update") {
        /* TODO : 1- submit the form using POST
          2- parse the incoming html
          3- echo the result
        
        $url = $_POST['u'];
        print_r($_POST);
        echo "[Post params=".$url."]";
        
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($_POST));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HEADER, false);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        $response = curl_exec($ch);
        echo 'response='.$response;
        //processBodyContent($response, $url);
        */
         
    }  else {
        echo "ProcessURL.php: not a valid request!";
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

function processBodyContent($html, $urlValid) {
    #########get the HTML DOM Object
    #echo "URL === [$url]";
    //TODO: update here the Forms as follow:
    //1- retreive the ID of the form
    //2- get the submit button ID and set it if not exist
    //3- build the Jquery script
    //4- re-build the form with a proxied URL: ACTION should have a valid URL that called the POST request
    /*
     * deprecated: Form Parser
     *  $htmlformeAdjusted = parseForms($html, $urlValid);
    $iframe = "<iframe id=\"frm1\" name=\"frm1\" frameborder=\"0\" scrolling=\"auto\" width=\"1500\" height=\"2500px\">iframe</iframe>";
    //add th eiframe code
    $htmlIframe = appendtextToBody($htmlformeAdjusted, "$iframe") ;
    echo "$htmlIframe";
     * 
     */
   
   
    // Working properly for the LINKS
    //get the html and retrieve the body
    $body = getHtmlTag($html, 'body');
    
    //adjust the href to onclick=getsubpage("target url")
    $bodyAdjusted = parseHref($body, $urlValid);
    //Returning the BODY value  I.E. excluding the <body> tag from the response
    $bodyValue = $bodyAdjusted->innertext;
    //print out the result in the div    
    echo $bodyValue;
   
   
  
}

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