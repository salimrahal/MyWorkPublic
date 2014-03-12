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
        /* Include our authentication class and sanitizing function */
        require_once 'Auth.php';
        //require_once 'sanitize.php';
        /* Define our parameters */
        $sigs = array(
            'email' => array('required' => TRUE, 'type' => 'string',
                'function' => 'addslashes'),
            'passwd' => array('required' => TRUE, 'type' => 'string',
                'function' => 'addslashes')
        );
        /* Clean up our input */
        //sanitize_vars(&$_POST, $sigs);++++++++++++++++++SR commented, Not working
        /* Instantiate the Auth class and add the user */
        $a = new Auth();
        $a->addUser($_POST['email'], $_POST['passwd']);
        /* or... we instantiate the Auth class and validate the user */
        $a = new Auth();
        echo $a->authUser($_POST['email'], $_POST['passwd']) ? 'OK' : 'ERROR';
        ?>

        ?>
    </body>
</html>
