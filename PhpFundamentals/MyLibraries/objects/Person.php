<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Person {

    public $publicMember = "Public member";
    protected $protectedMember = "Protected member";
    private $privateMember = "Private member";
    private $name;
   

    function setName($name) {
        $this->name = $name;
    }

    function getName() {
        return $this->name;
    }

    //old fashion php3
    /*
      function __construct($name)
      {
      $this->name = $name;
      }
     */
    function __destruct() {
        print "An object of type MyClass is being destroyed</br>";
    }

}

$judi = new Person();
$judi->setName("judi");
$kookie = new Person();
$kookie->setName("kookie");
echo $judi->getName() . "/" . $kookie->getName();
//destroying the object
$kookie = NULL;
?>
