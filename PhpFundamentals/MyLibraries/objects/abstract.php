<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

abstract class Shape {

     function setCenter($x, $y) {
        $this->x = $x;
        $this->y = $y;
    }

    abstract function draw();

    protected $x, $y;

}

class Square extends Shape {

    function draw() {
// Here goes the code which draws the Square
        echo "square draw..";
    }

}

class Circle extends Shape {

    function draw() {
// Here goes the code which draws the Circle
         echo "circle draw..";
    }

}

$square = new Square();
$square->setCenter(2, 6);
$square->draw();


$circle = new Circle();
$circle->setCenter(2, 6);
$circle->draw();
?>
