<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
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

#not exact equal
if($judi === $kookie){print 'exact equal: Yes';}
else{print 'exact equal: No';}

#Exact Equal: YES
if($judi === $judi){print 'exact equal: Yes';}
else{print 'exact equal: No';}
?>

    </body>
</html>
