<?php

ini_set('session.use_cookies', 1); // means that cookies will be used for propagation of the session ID
ini_set('session.use_only_cookies', 1); //means that a session ID passed in the URL to the script will be discarded
session_start();

?>