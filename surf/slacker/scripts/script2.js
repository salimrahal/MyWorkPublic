/**
 * Copyright (c) 2013, Michael Connell, Salim A. Rahal, safiratech.com.
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
 *	* Neither the names of  Michael Connell, Salim A. Rahal or safiratech.com, nor
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


var x;//compteur pour le chargement
checked = false;

function checkedAll(frm1) {
    var aa = document.getElementById('frm1');
    if (checked == false)
    {
        checked = true
    }
    else
    {
        checked = false
    }
    for (var i = 0; i < aa.elements.length; i++)
    {
        aa.elements[i].checked = checked;
    }
}

//fonction non utilise
function getXMLObject()  //XML OBJECT
{
    var xmlHttp;
    /*try {
     xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
     }
     catch (e) {
     try {
     xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
     }
     catch (e2) {
     xmlHttp = null;// No Browser accepts the XMLHTTP Object then false
     }
     }
     if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
     }
     /**/
    if (window.XMLHttpRequest)
    {// code for all new browsers
        xmlhttp = new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {// code for IE5 and IE6
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    /**/

    return xmlHttp;  // Mandatory Statement returning the ajax object created
}



var xmlhttp;
function getPage(url) {
    xmlhttp = null;
    //alert("begin getpageByajax");
    //xmlhttp= getXMLObject();	//xmlhttp holds the ajax object
    //initalialiser x
    if (window.XMLHttpRequest)
    {// code for all new browsers
        xmlhttp = new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {// code for IE5 and IE6
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    x = 1;
    if (xmlhttp == null) {
        alert('XMLHttpRequest failed to instantiate');
        return false;
    }
    xmlhttp.onreadystatechange = statusCheck;

    // alert("xmlhttp open(get..)....");
    //repairing the query string
    var urlparam = document.getElementById('urlId').value;
    //alert(urlparam);
    //accessing select options
    // var numprof=document.getElementById('numprof').value;
    // alert("numprof="+numprof);
    // if(numprof.toString().length>5 ||numprof.toString().length==0){
    //  alert("numero prof non valide..")
    //  }
    //  var annee=document.getElementById("annee").options[document.getElementById("annee").selectedIndex].value //accesses value attribute of 1st option
    //  alert("annee="+annee);
    //  var semestre=document.getElementById("semestre").options[document.getElementById("semestre").selectedIndex].value //accesses value attribute of 1st option
    //alert("semestre="+semestre);


    //recuperer la chaine des parametres
    var queryString = "?urlparam=" + urlparam;//+"&action=browse";//+"&numprof="+numprof+"&semestre="+semestre;
    var allurl = url + queryString;
    //alert (allurl);
    xmlhttp.open('POST', allurl, true);
    xmlhttp.send(null);


//var y=document.getElementById('y').value;
//  alert("y="+y);
//  alert ('getpage-finish');
}

/* TODO
 * Fonctionality:
 * 1- take a URL: /wiki/main.html or full URL: http://safiratech.com/mainpage
 * 2- if the passed URL doesn't contains Http://, so append the Root url to it
 * 3- send the page URL to Php script
 */
function getSubPage(urlparam) {
    xmlhttp = null;
    scriptUrl = "processURL.php";
    //alert("begin getSubPageByAjax param=" + urlparam);
    //xmlhttp= getXMLObject();	//xmlhttp holds the ajax object
    //initalialiser x
    if (window.XMLHttpRequest)
    {// code for all new browsers
        xmlhttp = new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {// code for IE5 and IE6
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    x = 1;
    if (xmlhttp == null) {
        alert('XMLHttpRequest failed to instantiate');
        return false;
    }
    xmlhttp.onreadystatechange = statusCheck;
    //TODO need to reviewed the root URL should be taken from the page not from the input
    //or it should be sent upon calling this getSubPageByAjax()!!??
//    var rootURL = document.getElementById('urlId').value;
//    alert("document.getElementById('urlId')="+rootURL);//EMPTY!
    /*TODO If the URL doesnt contain the absolute URL then add the URL Root to the urlParam
     * If URL_param doesn't contain the rootURL
     *    THEN Append it: URLParam = rootURL + URLParam
     *     e.g.: URLParam = "en.wikipedia" + "/wiki/main"
     * */

    //recuperer la chaine des parametres
    var queryString = "?urlparam=" + urlparam;//"&action=browse";//cibling URL
    var allurl = scriptUrl + queryString;
    xmlhttp.open('POST', allurl, true);
    xmlhttp.send(null);
}


function statusCheck() {
    //todo:fonction qui visualise le telechargement
    document.getElementById("user_contentId").innerHTML = "loading..... ";
   
    x = x + 1;
    // alert("status check:"+xmlhttp.readyState);
    if (xmlhttp.readyState == 4) {//0-->4
        //alert("status check dans if:" + xmlhttp.readyState);//object complete
        //alert("DEBUG MODE: xmlhttp.status=:" + xmlhttp.status);//error= 500(error in page requested), 404 if the page is incorrect name
        if (xmlhttp.status == 200) {//returned by the server indicating status
            //alert("DEBUG MODE: response["+xmlhttp.responseText+"]END");//the html of the page requested
            document.getElementById("user_contentId").innerHTML = (xmlhttp.responseText);         
        } else {
            alert('There was a problem with the request.');
        }
    }//else alert("readystate not complete"+xmlhttp.readyState);
}


