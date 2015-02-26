<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of numbersTest
 *
 * @author salim
 */
class NumbersTest {

    //put your code here
    function compute() {
        $x = 1 + "11.5 salim";
        return 'result='.$x;
    }
    
    function unsettest(){
        $x= '100';
        return (unset)$x;
    }

}
