<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Parser
 *
 * @author salim
 */
require_once('../libs/simplehtmldom_1_5/simple_html_dom.php');
require_once '../libs/AbsoluteUrl/url_to_absolute.php';
require_once './staticvar.php';

//------------------------------test--------------------------------------------
#test();
function test() {
    $urltest = "http://localhost/slacker/web/formtest/wikioriginalform.html";
    $html = getHtml($urltest); //ok
   // echo $html;
    /*
     * addInnertext($html, "form", "<input name=\"u1\" value=\"http://en.wikipedia.org/w/index.php1\" type=\"hidden\"/>");    //ok
      addInnertext($html, "form", "inner text = add input u2");//ok
      addOutertext($html, "form", "<h2>outer text: jquery script1</h2>");//OK
    */
    /*
     * 1- parse the form
     * 2- add the iframe
     */
    $htmlformeAdjusted = parseForms($html, $urltest);
    $iframe = "<iframe id=\"frm1\" name=\"frm1\" frameborder=\"0\" scrolling=\"auto\" width=\"1500\" height=\"2500px\">iframe</iframe>";
    //add th eiframe code
    $htmlIframe = appendtextToBody($htmlformeAdjusted, "$iframe") ;
    //echo $htmlIframe;   
    echo $htmlIframe; 
}

//Deprecated: problem in Jquery
/*Should use PhP DOM insread of the simple DOM parser Library
 * Desc: it should loop thur all the forms
 *               - retrieve their Id and Id of the submit button
 *               - build the jquery string (concating the id button, forms, div content,etc)
 *               - inject the jquery inside the html  
 *       
 */

function parseForms($html, $validParentUrl) {
// Find all Forms
    $idform = "fm";
    $i = 0;
    //echo "[$html]";
    foreach ($html->find('form') as $form) {
        //TODO: Retrieve and Update the FORM CONTENT HERE
        $idform = $form->id;
        $action = $form->action;
        //$method = $form->method;
        if (empty($idform)) {
            echo "parseForms: id is empty set it";
            $idformAdj = $idform . ++$i;
            $form->id = $idformAdj;
        }
        //TODO: 
        //get action url value and generate an input hidden -for the action URL- 
        //to passe it as POST      
        $adjustedHref = url_to_absolute($validParentUrl, $action);
        $form->action = "processURL.php?action=update";
        //TODO the value of the param should be a valid URL
       // addInnertext($html, "form", );
        //set method type POST
        $form->method = "post";
        $form->target = FRAME_TARGET;
         
        $inputHidden = "<input name=\"u\" value=\"$adjustedHref\" type=\"hidden\"/>";
        $forminner = $form->innertext;
        $form->innertext = "$inputHidden" . $forminner;
       // echo "form=$form";//ok
        //TODO generate Jquery script, then inject it in the outer text   
    }//end form loop
    return $html;
}

/*
 * UNUSED but working
 */

function modifyButtonusingPhpDom($html) {
    $doc = new DOMDocument();
    $doc->loadHTML($html);
// You can manipulate using the DOM object just follow the PHP manual for usage
    $forms = $doc->getElementsByTagName('form');

    foreach ($forms as $form) {
        //echo $form -> getAttribute('action').'<br>';
        $buttons = $doc->getElementsByTagName('button');
        foreach ($buttons as $button) {
            echo $button->getAttribute('type') . '<br>'; //type = submit
        }
    }
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
    //$simpleDom = new simple_html_dom();
    $html = file_get_html($url);
    #$html=mb_convert_encoding($html);
    #echo "All Html:[".$html."]";
    if (!isset($html)) {
        echo "<p >ERROR:Parser->getHtml($url): couldn't get HTML!!</p>";
    }
    return $html;
}

/*
 * Desc: it will return the HTML element by Tag, the first element it will find.
 * Param: 
 * e.g.: $html is a form, $tag is "button"
 *       Return is a button.   
 * <button type="submit" name="button" title="Search Wikipedia for this text" id="searchButton"> 
 */

function getHtmlTag($html, $tag) {

    $tagValue = $html->getElementByTagName($tag);
    return $tagValue;
}


function replaceInnertextBody($html, $text) {
    $html->find('body',0)->innertext = $text;
    return $html;
}
/*Ok: get a reference of the html*/
function appendtextToBody($html, $text) {
    $bodycontent = $html->find('body',0)->innertext;
    $html->find('body',0)->innertext = $text.$bodycontent;
    return $html;
}

/*
 * 
 */
function addInnertext($html, $tag, $text) {

    $tagValue = $html->getElementByTagName($tag);
    $innertext = $tagValue->innertext;
    $tagValue->innertext = "$text" . $innertext;
    echo "addInnertext=[ $html]";
    return $html;
}


function addOutertext($html, $tag, $text) {

    $tagValue = $html->getElementByTagName($tag);
    $innertext = $tagValue->outertext;
    $tagValue->outertext = "$text" . $innertext;
}

/*
 * 1- It turn <a href="myLink.html">My Link</a>
 * into:
 * <a onClick="getSubPageByAjax('myLink.html')">My Link</a>
 * 2- It modify the HTML DOM
 * 3- It turns the relative URL to absolute URL
 */

function parseHref($html, $validParentUrl) {
// Find all links
    foreach ($html->find('a') as $element) {
        //get the value of the Href: /wiki/main or http://www.safiratech.com/home
        $value = $element->href;
        if (DEBUG_MODE) {
            echo "<br>############DEBUG##############
            </br>updateHref function: before update href=[ $value ]";
        }
        if (!empty($value)) {
            //delete the A.href attribute
            $element->href = null;
            //initialize the adjusted valur with original Href value
            $adjustedHref = $value;
            //Update the incoming Href value only if its not valid
            if (!checkURLValidity($value)) {
                if (DEBUG_MODE) {
                    echo "</br>[$value] not valid! need to be fixed";
                }
                $adjustedHref = url_to_absolute($validParentUrl, $adjustedHref);
                if (DEBUG_MODE) {
                    echo "</br>relative to ABS = [$adjustedHref]";
                }
            }//end of check validity
            else {
                if (DEBUG_MODE) {
                    echo "</br> $value is valid";
                }
            }
            //adding new attribute Onclick that redirect the link to onClick event handler: getSubPageByAjax('http://www.safiratech.com/home')
            $element->onClick = "getSubPage('" . $adjustedHref . "')";
        }//end $value isset
        else {
            if (DEBUG_MODE) {
                echo " $value is empty, leave it.";
            }
        }
    }//end for loop
    //echo 'After change body['.$html.']';      
    return $html;
}

/* * ***********************TEST FUNCTIONS AND UNUSED************************************ */
/* * *********************************************************************** */


/* UNUSED
 * useful only for relative Href Value, where the relative URL is not Valid UR
 * http://en.wikipedia.org + /wiki/home; 
 * NB: should Avoid [//]: [http://en.wikipedia.org//wiki/home]
 */

function adjustHrefRelativeUrl($hrefvalue, $urlroot, $seperator) {
    $rootAppendedHrefUrl = "";
    //$str = rtrim("http://en.wikipedia.org/","/"); ==> http://en.wikipedia.org  
    //remove the end  "/" in case it exists
    $trimedUrlroot = rtrim($urlroot, "/");
    //remove the first "/":  /wiki/home ==> wiki/home
    $trimedHrefValue = ltrim($hrefvalue, "/");
    $rootAppendedHrefUrl = $trimedUrlroot . $seperator . $trimedHrefValue;
    if (DEBUG_MODE) {
        echo "adjustHrefRelativeUrl Appended:  [$trimedUrlroot] . [$seperator ]. [$trimedHrefValue]";
    }
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

function getHtmlVtags($html) {
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
/*
 * Not functional/ deprecated
 * injectiong Jquery using innerHtml won't work, jquery needs to be loaded first time
 */
function buildJqueryScript($idForm, $idButton, $idDivtarget, $http_method) {
    // echo "----$idForsm, $idButton, $idDivtarget, $http_method---";
    $jquery_script = "<script type=\"text/javascript\">
            jQuery(document).ready(function($) {
                $(\"#" . $idButton . "\").sclick(function() {
                    var url = \"submitForm.php?action=update\"; // the script where you handle the form input.

                    $.ajax({
                        type: \".$http_method\",
                        url: url,
                        data: $(\"#$idForm\").serialize(), // serializes the form's elements.
                        success: function(html) {
                            $(\"#$idDivtarget\").html(html);
                        }
                    });

                    return false; // avoid to execute the actual submit of the form.
                });
            });
        </script>
    ";
    //echo "inside[".$jquery_script."]";
    return $jquery_script;
}

?>
