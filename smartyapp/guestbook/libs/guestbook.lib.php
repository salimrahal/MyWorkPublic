<?php

/**
 * Project: Guestbook Sample Smarty Application
 * Author: Monte Ohrt <monte [AT] ohrt [DOT] com>
 * File: guestbook.lib.php
 * Version: 1.1
 */

/**
 * guestbook application library
 *
 */
class Guestbook {

  // database object
  var $pdo = null;
  // smarty template object
  var $tpl = null;
  // error messages
  var $error = null;

  /* set database settings here! */
  // PDO database type
  var $dbtype = 'mysql';
  // PDO database name
  var $dbname = 'GUESTBOOK';
  // PDO database host
  var $dbhost = 'localhost';
  // PDO database username
  var $dbuser = 'guestbook';
  // PDO database password
  var $dbpass = 'foobar';


  /**
  * class constructor
  */
  function __construct() {

    // instantiate the pdo object
    try {
      $dsn = "{$this->dbtype}:host={$this->dbhost};dbname={$this->dbname}";
      $this->pdo =  new PDO($dsn,$this->dbuser,$this->dbpass);
    } catch (PDOException $e) {
      print "Error!: " . $e->getMessage();
      die();
    }	
    // instantiate the template object
    $this->tpl = new Guestbook_Smarty;

  }

  /**
  * display the guestbook entry form
  *
  * @param array $formvars the form variables
  */
  function displayForm($formvars = array()) {

    // assign the form vars
    $this->tpl->assign('post',$formvars);
    // assign error message
    $this->tpl->assign('error', $this->error);
    //$this->tpl->display('guestbook_form.tpl');
    $this->tpl->display('guestbook_form_salim.tpl');

  }
  
  /**
  * fix up form data if necessary
  *
  * @param array $formvars the form variables
  */
  function mungeFormData(&$formvars) {

    // trim off excess whitespace
    $formvars['Name'] = trim($formvars['Name']);
    $formvars['Comment'] = trim($formvars['Comment']);
    $formvars['Email'] = trim($formvars['Email']);

  }

  /**
  * test if form information is valid
  *
  * @param array $formvars the form variables
  */
  function isValidForm($formvars) {

    // reset error message
    $this->error = null;

    // test if "Name" is empty
    if(strlen($formvars['Name']) == 0) {
      $this->error = 'name_empty';
      return false; 
    }

    // test if "Comment" is empty
    if(strlen($formvars['Comment']) == 0) {
      $this->error = 'comment_empty';
      return false; 
    }
     // test if "Email" is empty
    if(strlen($formvars['Email']) == 0) {
      $this->error = 'email_empty';
      return false; 
    }

    // form passed validation
    return true;
  }

  /**
  * add a new guestbook entry
  *
  * @param array $formvars the form variables
  */
   function addEntry($formvars) {        
    try {
      $rh = $this->pdo->prepare("insert into GUESTBOOK values(0,?,NOW(),?,?)");
      $rh->execute(array($formvars['Name'],$formvars['Comment'],$formvars['Email']));
    } catch (PDOException $e) {
      print "Error!: " . $e->getMessage();
      return false;
    }	
    return true;
  }

  /**
  * get the guestbook entries
  */
  function getEntries() {
    try {
      foreach($this->pdo->query(
        "select * from GUESTBOOK order by EntryDate DESC") as $row)
      $rows[] = $row;
    } catch (PDOException $e) {
      print "Error!: " . $e->getMessage();
      return false;
    } 	
    return $rows;   
  }

  /**
  * display the guestbook
  *
  * @param array $data the guestbook data
  */
  function displayBook($data = array()) {

    $this->tpl->assign('data', $data);
    $this->tpl->display('guestbook.tpl');     
 }
}

?>
