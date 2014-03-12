<!--
To change this template, choose Tools | Templates
and open the template in the editor.

$arr1+$arr2
---------------all elements are with duplicate key-------------
{0->a,1->b}
{0->c,1->d}
arr = arr1+ arr2 = {0->a,1->b}
----------
{0->a,1->b}
{0->c,1->d,2->e}
arr = arr1+ arr2 = {0->a,1->b,2->e}

deduction:
If they have element(0=>x,1=>b) with the same key, elements of the first array are choose
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        // having a Same Key in two array the append wont work.
        $array1 = array("name2", "name3", "nom" => "carlos");
        $array2 = array("name1", "nom" => "carlos", "prenom" => "garcia", "age" => "32");
        echo "</br>---------------arr1----------------</br>";
        print_r($array1);
        echo "</br>---------------arr2--------------</br>";
        print_r($array2);
        echo "</br>---------------arr1+arr2--------------</br>";
        $arrayT = $array1 + $array2;
        print_r($arrayT); //Array ( [0] => name2 [1] => name3 [nom] => carlos [prenom] => garcia [age] => 32 ) 

        echo "</br>---------------merge----------------</br>";
        $a = array('a', 'b');
        $b = array('c', 'd');
        $merge = array_merge($a, $b);
// $merge is now equals to array('a','b','c','d');
        print_r($merge); //Array ( [0] => a [1] => b [2] => c [3] => d ) 
        //the + operator wont merge, if they have the same key it will do anything
        echo "</br>---------------a----------------</br>";
        print_r($a);
        echo "</br>---------------b--------------</br>";
        print_r($b);
        echo "</br>---------------a+b: no merge performed since they have the same key--------------</br>";
        //$arrayT = $array1 + $array2;
        $merge = $a + $b; // Array ( [0] => a [1] => b )
        print_r($merge);
 echo "</br>---------------Odesk excercise------------</br>";
        function vec_add(&$a, $b) {
            $a['x'] += $b['x'];
            $a['y'] += $b['y'];
            $a['z'] += $b['z'];
        }

        $a = array(x => 3, y => 2, z => 5);
        $b = array(x => 9, y => 3, z => -7);

        vec_add($a, $b);
        //vec_add(&$a, $b);//error: pass by ref remove from the function call.
        print_r($a);
        ?>
    </body>
</html>
