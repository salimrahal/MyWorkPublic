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
#Which of the following sequence will run successfully?
        Function ExpensesV() {

            Function Salary() {
                
            }

            Function Loan() {

                Function Balance() {
                    
                }

            }

        }
        #will run
        ExpensesV();Salary();Loan();Balance();
        
        #Fatal error: Cannot redeclare Salary() (previously declared in /home/salim/public_html/PhpProject2/MyLibraries/functions/functiontricks.php:16
       // Salary();ExpensesV();Loan();Balance();
        print "</br>------------------------------------------------------</br>";

        function test() {
            $t = "returnVal";
            return $t;
        }

        echo "test()=" . test();
        print "</br>";
        //print "isset=".isset(test());//indicates error
        print "</br>";
        print test();

        //global and local var
        // What will be the output of the following code?
        $var = 10;

        function fn() {
            $var = 20;
            return $var;
        }

        fn();
        echo "</br>" . $var; //10s
        print "------------------------------</br>";
        /*
         * What will be the output of the following code?
         */
        $Rent = 250;

        function Expenses($Other) {
            $Rent = 250 + $Other;
            return $Rent;
        }

        Expenses(50);
        echo $Rent; //250       
        ?>
    </body>
</html>
