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
        //no syntax error:
        $i=0;
        #infinite loop
       // while ($i<=10)print 'salim';
       #not infinite loop
        while ($i<=10){$i++;print $i;}//out: 234567891011 
         $i=0;
         echo '</br>';
        while ($i<=10)$i++;print $i;//out: 11
         $i=0;
         echo '</br>';
        while($i<=10):print $i;$i++;       endwhile;
        ?>
    </body>
</html>
