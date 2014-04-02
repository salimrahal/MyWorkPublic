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
        function sanitize_vars(&$vars, $signatures, $redir_url = null)
{
$tmp = array();
/* Walk through the signatures and add them to the temporary
* array $tmp */
foreach ($signatures as $name => $sig) {
if (!isset($vars[$name]]) && isset($sig['required']) && $sig['required'])
{
/* redirect if the variable doesn't exist in the array */
if ($redir_url) {
header("Location: $redir_url");
} else {
echo 'Parameter $name not present and no redirect
➥URL';
}
exit();
}
/* apply type to variable */
$tmp[$name] = $vars[$name];
if (isset($sig['type'])) {
settype($tmp[$name], $sig['type']);
}
/* apply functions to the variables, you can use the standard PHP
* functions, but also use your own for added flexibility. */
if (isset($sig['function'])) {
$tmp[$name] = {$sig['function']}($tmp[$name]);
}
}
$vars = $tmp;
}
$sigs = array(
'prod_id' => array('required' => true, 'type' => 'int'),
'desc' =>
array('required' => true, 'type' => 'string',
'function' => 'addslashes')
);
sanitize_vars(&$_GET, $sigs,
"http:// {$_SERVER['SERVER_NAME']}/error.php?cause=vars");
?>

        ?>
    </body>
</html>
