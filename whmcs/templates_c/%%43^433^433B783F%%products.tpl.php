<?php /* Smarty version 2.6.27, created on 2013-08-27 09:54:07
         compiled from /home/salim/public_html/billing/templates/orderforms/boxes/products.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('function', 'cycle', '/home/salim/public_html/billing/templates/orderforms/boxes/products.tpl', 38, false),)), $this); ?>
<link rel="stylesheet" type="text/css" href="templates/orderforms/<?php echo $this->_tpl_vars['carttpl']; ?>
/style.css" />

<div id="order-boxes">

<div class="left">

<form method="get" action="<?php echo $_SERVER['PHP_SELF']; ?>
">
<p><?php echo $this->_tpl_vars['LANG']['ordercategories']; ?>
: <select name="gid" onchange="submit()">
<?php $_from = $this->_tpl_vars['productgroups']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['productgroup']):
?>
<option value="<?php echo $this->_tpl_vars['productgroup']['gid']; ?>
"<?php if ($this->_tpl_vars['gid'] == $this->_tpl_vars['productgroup']['gid']): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['productgroup']['name']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
<?php if ($this->_tpl_vars['loggedin']): ?>
<option value="addons"><?php echo $this->_tpl_vars['LANG']['cartproductaddons']; ?>
</option>
<?php if ($this->_tpl_vars['renewalsenabled']): ?><option value="renewals"><?php echo $this->_tpl_vars['LANG']['domainrenewals']; ?>
</option><?php endif; ?>
<?php endif; ?>
</select></p>
</form>

</div>
<div class="right">

<?php if (! $this->_tpl_vars['loggedin'] && $this->_tpl_vars['currencies']): ?>
<form method="post" action="cart.php?gid=<?php echo $_GET['gid']; ?>
">
<p align="right"><?php echo $this->_tpl_vars['LANG']['choosecurrency']; ?>
: <select name="currency" onchange="submit()"><?php $_from = $this->_tpl_vars['currencies']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['curr']):
?>
<option value="<?php echo $this->_tpl_vars['curr']['id']; ?>
"<?php if ($this->_tpl_vars['curr']['id'] == $this->_tpl_vars['currency']['id']): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['curr']['code']; ?>
</option>
<?php endforeach; endif; unset($_from); ?></select> <input type="submit" value="<?php echo $this->_tpl_vars['LANG']['go']; ?>
" /></p>
</form>
<?php endif; ?>

</div>
<div class="clear"></div>

<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?a=add">

<table width="90%" align="center" cellspacing="1" cellpadding="5">
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<?php $_from = $this->_tpl_vars['products']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['product']):
?>
<tr class="<?php echo smarty_function_cycle(array('values' => "orderrow1,orderrow2"), $this);?>
"><td width="10"><input type="radio" name="pid" value="<?php if ($this->_tpl_vars['product']['bid']): ?>b<?php echo $this->_tpl_vars['product']['bid']; ?>
<?php else: ?><?php echo $this->_tpl_vars['product']['pid']; ?>
<?php endif; ?>"<?php if ($this->_tpl_vars['product']['qty'] == '0'): ?> disabled<?php endif; ?> /></td><td><strong><?php echo $this->_tpl_vars['product']['name']; ?>
</strong> <?php if ($this->_tpl_vars['product']['qty'] != ""): ?><em>(<?php echo $this->_tpl_vars['product']['qty']; ?>
 <?php echo $this->_tpl_vars['LANG']['orderavailable']; ?>
)</em><?php endif; ?><?php if ($this->_tpl_vars['product']['description']): ?> - <?php echo $this->_tpl_vars['product']['description']; ?>
<?php endif; ?></td></tr>
<?php endforeach; endif; unset($_from); ?>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
</table>

<p align="center"><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['ordercontinuebutton']; ?>
" /></p>

</form>

<?php if ($this->_tpl_vars['registerdomainenabled']): ?><p align="center"><a href="<?php echo $_SERVER['PHP_SELF']; ?>
?a=add&domain=register"><?php echo $this->_tpl_vars['LANG']['orderdomainregonly']; ?>
</a></p><?php endif; ?>

<p><img align="left" src="images/padlock.gif" border="0" alt="Secure Transaction" style="padding-right: 10px;" /> <?php echo $this->_tpl_vars['LANG']['ordersecure']; ?>
 (<strong><?php echo $this->_tpl_vars['ipaddress']; ?>
</strong>) <?php echo $this->_tpl_vars['LANG']['ordersecure2']; ?>
</p>

<?php 
if (isset($_SESSION["cart"])) {
    unset($_SESSION["cart"]);
}
 ?>

</div>