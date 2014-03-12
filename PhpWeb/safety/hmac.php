<!--
To change this template, choose Tools | Templates
and open the template in the editor.

5.5.2 HMAC Verification
If you need to prevent bad guys from tampering with variables passed in the
URL (such as for a redirect as shown previously, or for links that pass special
parameters to the linked script), you can use a hash, as shown in the following
script:

-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php

        function create_parameters($array) {
            $data = '';
            $ret = array();
            /* For each variable in the array we a string containing
             * "$key=$value" to an array and concatenate
             * $key and $value to the $data string. */
            foreach ($array as $key => $value) {
                $data .= $key . $value;
                $ret[] = "$key=$value";//cause=vars
            }
            /* We also add the md5sum of the $data as element
             * to the $ret array. */
            $hash = md5($data);
            $ret[] = "hash=$hash";
            //echo $data."</br>";//causevars
            return join('&amp;', $ret);
        }
        echo '<a href="script.php?' . create_parameters(array('cause' => 'vars')) . '">err!</a>';
        //output: <a href="script.php?cause=vars&amp;hash=8eee14fe10d3f612589cdef079c025f6">err!</a>
        ?>
    </body>
</html>
