<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * The following example is not a valid PHP script because it is trying to
override a final method:

 */

class MyBaseClass {

    final function idGenerator() {
        return $this->id++;
    }

    protected $id = 0;

}

class MyConcreteClass extends MyBaseClass {
//Runtime exception line 28:  
//Fatal error: Cannot override final method MyBaseClass::idGenerator()
//    function idGenerator() {
//        return $this->id += 2;
//    }

}

$concrete = new MyConcreteClass();
print $concrete->idGenerator();

//final class
final class MyBaseClassF {

}
class MyConcreteClassIn extends MyBaseClassF {
/*
 * MyBaseClassF has been declared as final; MyConcreteClass may not extend
it and, therefore, execution of the script fails.
 */
}
?>
