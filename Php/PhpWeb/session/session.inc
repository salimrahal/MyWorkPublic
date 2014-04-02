<?php

ini_set('session.use_cookies', 1); // means that cookies will be used for propagation of the session ID
ini_set('session.use_only_cookies', 1); //means that a session ID passed in the URL to the script will be discarded
session_start();

function check_login() {
    if (!isset($_SESSION['uid']) || !$_SESSION['uid']) {
        /* If no UID is in the cookie, we redirect to the login page */
        header('Location: http://localhost/PhpWeb/session/login.php');
    }
}

?>