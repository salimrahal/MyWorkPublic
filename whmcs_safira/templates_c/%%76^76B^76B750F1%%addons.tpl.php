<?php /* Smarty version 2.6.27, created on 2013-08-30 12:41:58
         compiled from /home/www/whmcs/templates/orderforms/modern/addons.tpl */ ?>
<script type="text/javascript" src="includes/jscript/jqueryui.js"></script>
<script type="text/javascript" src="templates/orderforms/<?php echo $this->_tpl_vars['carttpl']; ?>
/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="templates/orderforms/<?php echo $this->_tpl_vars['carttpl']; ?>
/style.css" />

<div id="order-modern">

<h1><?php echo $this->_tpl_vars['LANG']['cartproductaddons']; ?>
</h1>
<div align="center"><a href="#" onclick="showcats();return false;">(<?php echo $this->_tpl_vars['LANG']['cartchooseanothercategory']; ?>
)</a></div>

<div id="categories">
<?php $_from = $this->_tpl_vars['productgroups']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['productgroup']):
?>
<?php if ($this->_tpl_vars['productgroup']['gid'] != $this->_tpl_vars['gid']): ?><a href="cart.php?gid=<?php echo $this->_tpl_vars['productgroup']['gid']; ?>
"><?php echo $this->_tpl_vars['productgroup']['name']; ?>
</a><?php endif; ?>
<?php endforeach; endif; unset($_from); ?>
<?php if ($this->_tpl_vars['loggedin']): ?>
<?php if ($this->_tpl_vars['gid'] != 'addons'): ?><a href="cart.php?gid=addons"><?php echo $this->_tpl_vars['LANG']['cartproductaddons']; ?>
</a><?php endif; ?>
<?php if ($this->_tpl_vars['renewalsenabled'] && $this->_tpl_vars['gid'] != 'renewals'): ?><a href="cart.php?gid=renewals"><?php echo $this->_tpl_vars['LANG']['domainrenewals']; ?>
</a><?php endif; ?>
<?php endif; ?>
<?php if ($this->_tpl_vars['registerdomainenabled'] && $this->_tpl_vars['domain'] != 'register'): ?><a href="cart.php?a=add&domain=register"><?php echo $this->_tpl_vars['LANG']['registerdomain']; ?>
</a><?php endif; ?>
<?php if ($this->_tpl_vars['transferdomainenabled'] && $this->_tpl_vars['domain'] != 'transfer'): ?><a href="cart.php?a=add&domain=transfer"><?php echo $this->_tpl_vars['LANG']['transferdomain']; ?>
</a><?php endif; ?>
<a href="cart.php?a=view"><?php echo $this->_tpl_vars['LANG']['viewcart']; ?>
</a>
</div>
<div class="clear"></div>

<br />
<br />

<?php $_from = $this->_tpl_vars['addons']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['addon']):
?>
<div class="product">
<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?a=add">
<input type="hidden" name="aid" value="<?php echo $this->_tpl_vars['addon']['id']; ?>
" />

<div class="pricing">
<?php if ($this->_tpl_vars['addon']['free']): ?>
<?php echo $this->_tpl_vars['LANG']['orderfree']; ?>

<?php else: ?>
<span class="pricing"><?php echo $this->_tpl_vars['addon']['recurringamount']; ?>
 <?php echo $this->_tpl_vars['addon']['billingcycle']; ?>
</span>
<?php if ($this->_tpl_vars['addon']['setupfee']): ?><br />+ <?php echo $this->_tpl_vars['addon']['setupfee']; ?>
 <?php echo $this->_tpl_vars['LANG']['ordersetupfee']; ?>
<?php endif; ?>
<?php endif; ?>
</div>

<div class="name"><?php echo $this->_tpl_vars['addon']['name']; ?>
</div>

<div class="description"><?php echo $this->_tpl_vars['addon']['description']; ?>
</div>

<div class="ordernowbox"><select name="productid"><?php $_from = $this->_tpl_vars['addon']['productids']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['product']):
?>
<option value="<?php echo $this->_tpl_vars['product']['id']; ?>
"><?php echo $this->_tpl_vars['product']['product']; ?>
<?php if ($this->_tpl_vars['product']['domain']): ?> - <?php echo $this->_tpl_vars['product']['domain']; ?>
<?php endif; ?></option>
<?php endforeach; endif; unset($_from); ?></select> <input type="submit" value="<?php echo $this->_tpl_vars['LANG']['ordernowbutton']; ?>
" class="ordernow" />
</div>

</form>
</div>
<?php endforeach; endif; unset($_from); ?>

<?php if ($this->_tpl_vars['noaddons']): ?>
<div class="errorbox" style="display:block;"><?php echo $this->_tpl_vars['LANG']['cartproductaddonsnone']; ?>
</div>
<?php endif; ?>

<br />

<p align="center"><input type="button" value="<?php echo $this->_tpl_vars['LANG']['viewcart']; ?>
" onclick="window.location='cart.php?a=view'" /></p>

</div>