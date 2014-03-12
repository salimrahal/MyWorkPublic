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
        // put your code here that does any local processing with form submit data
echo"<br>--POST:---</br>";
        print_r($_POST);
echo"<br>--GET:---</br>";
print_r($_GET);
//http://en.wikipedia.org/w/index.php?search=salim&button=&title=Special%3ASearch
// this is the URL of the OTHER website that will receive a copy of the form submission data 
#$url = "http://en.wikipedia.org/w/index.php"; // change this!
$url = $_POST['u'];
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($_POST));
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HEADER, false);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
$response = curl_exec($ch);
// $response will contain the output of the OTHER website processing the form submission
// you can echo it to the screen or do your own processing if you want.
echo $response;
        ?>
    </body>
</html>
