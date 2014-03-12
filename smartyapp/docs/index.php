<?php

/**
 * Project: Guestbook Sample Smarty Application
 * Author: Monte Ohrt <monte [AT] ohrt [DOT] com>
 * File: index.php
 * Version: 1.1
 * URL: http://www.smarty.net/sampleapp2
 */

// define our application directory
//define('GUESTBOOK_DIR', '/web/www.example.com/smarty/guestbook/');
define('GUESTBOOK_DIR', '../guestbook/');
// define smarty lib directory
//define('SMARTY_DIR', '/usr/local/lib/php/Smarty/');
//define('SMARTY_DIR', '../../Smarty3Dev/distribution/libs/');
///usr/lib/php5/smarty3.1.14/
define('SMARTY_DIR', '/usr/lib/php5/smarty3.1.14/');
// include the setup script
include(GUESTBOOK_DIR . 'libs/guestbook_setup.php');

// create guestbook object
$guestbook = new Guestbook;

// set the current action
$_action = isset($_REQUEST['action']) ? $_REQUEST['action'] : 'view';

switch($_action) {
    case 'add':
        // adding a guestbook entry
        $guestbook->displayForm();
        break;
    case 'submit':
        // submitting a guestbook entry
        $guestbook->mungeFormData($_POST);
        if($guestbook->isValidForm($_POST)) {
            $guestbook->addEntry($_POST);
            $guestbook->displayBook($guestbook->getEntries());
        } else {
            $guestbook->displayForm($_POST);
        }
        break;
    case 'view':
    default:
        // viewing the guestbook
        $guestbook->displayBook($guestbook->getEntries());        
        break;   
}

?>
