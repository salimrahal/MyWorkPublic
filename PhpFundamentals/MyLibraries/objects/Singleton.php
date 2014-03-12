<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Singleton {

    static private $instance = null;

    public function getInstance() {
        if (self::instance == NULL) {// or use self::$instance
             self::$instance = new Singleton();
        }
        return self::instance; //self::$instance
    }

}
//MAIN
$sing1 = new Singleton();
$sing2 = new Singleton();
print "Singleton: hash Object1=".spl_object_hash ($sing1)."</br>/hash Object2=".spl_object_hash ($sing2);
?>
