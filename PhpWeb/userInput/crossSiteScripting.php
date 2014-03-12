<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * It’s a straightforward script. Suppose the attacker types the following into
your form field:
'><script language='JavaScript'>alert('boo!');</script><a b='
 */
?>
<html>
    <head><title>XSS example</title></head>
    <body>
        <form>
            <input name='foo' value=''>
        </form>
    </body>
</html>