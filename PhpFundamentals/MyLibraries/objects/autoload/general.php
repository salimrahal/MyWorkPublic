<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * $_SERVER["DOCUMENT_ROOT"] : /home/salim/public_html/
 */
function __autoload($class_name)
{
require_once($_SERVER["DOCUMENT_ROOT"] . "/PhpProject2/MyLibraries/objects/autoload/$class_name.php");
}

?>
