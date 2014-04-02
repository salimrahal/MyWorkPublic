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
         * echo print_r("\$_POST=" . $_POST['name'] . "/" . $_POST['age'] . "</br>");
        echo print_r("\$_REQUEST=" . $_REQUEST['name'] . "/" . $_REQUEST['age'] . "</br>");
         */
        
        ?>
        <p>Using htmlspecialchars for name, and int casting for age:</p>
        Hi <?php 
        echo "htmlspecialchars=".htmlspecialchars($_POST['name'])."</br>"; //convert the special char: [<] -> [&lt;] 
       echo"<br>-------------------------------</br>";
        echo $_POST['name']; //no conversion for special char: <b>bold</bold> is displayed bold in the browser.
        ?>.
        You are <?php echo (int) $_POST['age']."</br>"; ?> years old.
        echo"<br>-------------------------------</br>";
        <?php
        $str = "'quote' 10 < 100 , <b>bold</b>";
        //output: 'quote' 10 &lt; 100 , &lt;b&gt;bold&lt;/b&gt;
        echo htmlentities($str)."</br>";
echo"<br>-------------------------------</br>";        
//ENT_QUOTES: 	Will convert both double and single quotes.
        //output: &#039;quote&#039; 10 &lt; 100 , &lt;b&gt;bold&lt;/b&gt;</br>
        echo htmlentities($str, ENT_QUOTES)."</br>";
        ?>

    </body>
</html>
