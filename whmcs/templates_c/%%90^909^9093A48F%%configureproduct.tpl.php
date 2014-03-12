<?php /* Smarty version 2.6.27, created on 2013-08-27 09:56:06
         compiled from /home/salim/public_html/billing/templates/orderforms/boxes/configureproduct.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'replace', '/home/salim/public_html/billing/templates/orderforms/boxes/configureproduct.tpl', 10, false),)), $this); ?>
<link rel="stylesheet" type="text/css" href="templates/orderforms/<?php echo $this->_tpl_vars['carttpl']; ?>
/style.css" />

<div id="order-boxes">

<p><?php echo $this->_tpl_vars['LANG']['cartproductdesc']; ?>
</p>

<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?a=confproduct&i=<?php echo $this->_tpl_vars['i']; ?>
">
<input type="hidden" name="configure" value="true">

<?php if ($this->_tpl_vars['errormessage']): ?><div class="errorbox"><?php echo ((is_array($_tmp=$this->_tpl_vars['errormessage'])) ? $this->_run_mod_handler('replace', true, $_tmp, '<li>', ' &nbsp;#&nbsp; ') : smarty_modifier_replace($_tmp, '<li>', ' &nbsp;#&nbsp; ')); ?>
 &nbsp;#&nbsp; </div><br /><?php endif; ?>

<table width="90%" align="center" cellspacing="1" cellpadding="5">

<?php if ($this->_tpl_vars['productinfo']): ?>
<tr><td colspan="2"><strong><?php echo $this->_tpl_vars['LANG']['orderproduct']; ?>
</strong></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr class="orderrow1"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['orderproduct']; ?>
</td><td><strong><?php echo $this->_tpl_vars['productinfo']['groupname']; ?>
 - <?php echo $this->_tpl_vars['productinfo']['name']; ?>
</strong></td></tr>
<tr class="orderrow2"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['orderdesc']; ?>
</td><td><?php echo $this->_tpl_vars['productinfo']['description']; ?>
</td></tr>
<tr class="orderrow1"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['orderbillingcycle']; ?>
</td><td><input type="hidden" name="previousbillingcycle" value="<?php echo $this->_tpl_vars['billingcycle']; ?>
" /><?php if ($this->_tpl_vars['pricing']['type'] == 'free'): ?>
<input type="hidden" name="billingcycle" value="free" />
<?php echo $this->_tpl_vars['LANG']['orderfree']; ?>

<?php elseif ($this->_tpl_vars['pricing']['type'] == 'onetime'): ?>
<input type="hidden" name="billingcycle" value="onetime" />
<?php echo $this->_tpl_vars['pricing']['onetime']; ?>
 <?php echo $this->_tpl_vars['LANG']['orderpaymenttermonetime']; ?>

<?php else: ?>
<select name="billingcycle" onchange="submit()">
<?php if ($this->_tpl_vars['pricing']['monthly']): ?><option value="monthly"<?php if ($this->_tpl_vars['billingcycle'] == 'monthly'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['pricing']['monthly']; ?>
</option><?php endif; ?>
<?php if ($this->_tpl_vars['pricing']['quarterly']): ?><option value="quarterly"<?php if ($this->_tpl_vars['billingcycle'] == 'quarterly'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['pricing']['quarterly']; ?>
</option><?php endif; ?>
<?php if ($this->_tpl_vars['pricing']['semiannually']): ?><option value="semiannually"<?php if ($this->_tpl_vars['billingcycle'] == 'semiannually'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['pricing']['semiannually']; ?>
</option><?php endif; ?>
<?php if ($this->_tpl_vars['pricing']['annually']): ?><option value="annually"<?php if ($this->_tpl_vars['billingcycle'] == 'annually'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['pricing']['annually']; ?>
</option><?php endif; ?>
<?php if ($this->_tpl_vars['pricing']['biennially']): ?><option value="biennially"<?php if ($this->_tpl_vars['billingcycle'] == 'biennially'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['pricing']['biennially']; ?>
</option><?php endif; ?>
<?php if ($this->_tpl_vars['pricing']['triennially']): ?><option value="triennially"<?php if ($this->_tpl_vars['billingcycle'] == 'triennially'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['pricing']['triennially']; ?>
</option><?php endif; ?>
</select>
<?php endif; ?></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr><td height="10"></td></tr>

<?php endif; ?>

<?php if ($this->_tpl_vars['productinfo']['type'] == 'server'): ?>
<tr><td colspan="2"><strong><?php echo $this->_tpl_vars['LANG']['cartconfigserver']; ?>
</strong></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr class="orderrow1"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['serverhostname']; ?>
</td><td><input type="text" name="hostname" size="15" value="<?php echo $this->_tpl_vars['server']['hostname']; ?>
" /> eg. server1(.yourdomain.com)</td></tr>
<tr class="orderrow2"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['serverns1prefix']; ?>
</td><td><input type="text" name="ns1prefix" size="10" value="<?php echo $this->_tpl_vars['server']['ns1prefix']; ?>
" /> eg. ns1(.yourdomain.com)</td></tr>
<tr class="orderrow1"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['serverns2prefix']; ?>
</td><td><input type="text" name="ns2prefix" size="10" value="<?php echo $this->_tpl_vars['server']['ns2prefix']; ?>
" /> eg. ns2(.yourdomain.com)</td></tr>
<tr class="orderrow2"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['serverrootpw']; ?>
</td><td><input type="password" name="rootpw" size="20" value="<?php echo $this->_tpl_vars['server']['rootpw']; ?>
" /></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr><td height="10"></td></tr>
<?php endif; ?>

<?php if ($this->_tpl_vars['configurableoptions']): ?>
<tr><td colspan="2"><strong><?php echo $this->_tpl_vars['LANG']['orderconfigpackage']; ?>
</strong></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<?php $_from = $this->_tpl_vars['configurableoptions']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['configoption']):
?>
<tr class="orderrow<?php if ($this->_tpl_vars['num'] % 2): ?>2<?php else: ?>1<?php endif; ?>"><td class="leftcol"><?php echo $this->_tpl_vars['configoption']['optionname']; ?>
:</td><td>
<?php if ($this->_tpl_vars['configoption']['optiontype'] == 1): ?>
<select name="configoption[<?php echo $this->_tpl_vars['configoption']['id']; ?>
]">
<?php $_from = $this->_tpl_vars['configoption']['options']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num2'] => $this->_tpl_vars['options']):
?>
<option value="<?php echo $this->_tpl_vars['options']['id']; ?>
"<?php if ($this->_tpl_vars['configoption']['selectedvalue'] == $this->_tpl_vars['options']['id']): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['options']['name']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
</select>
<?php elseif ($this->_tpl_vars['configoption']['optiontype'] == 2): ?>
<?php $_from = $this->_tpl_vars['configoption']['options']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num2'] => $this->_tpl_vars['options']):
?>
<label><input type="radio" name="configoption[<?php echo $this->_tpl_vars['configoption']['id']; ?>
]" value="<?php echo $this->_tpl_vars['options']['id']; ?>
"<?php if ($this->_tpl_vars['configoption']['selectedvalue'] == $this->_tpl_vars['options']['id']): ?> checked="checked"<?php endif; ?>> <?php echo $this->_tpl_vars['options']['name']; ?>
</label><br />
<?php endforeach; endif; unset($_from); ?>
<?php elseif ($this->_tpl_vars['configoption']['optiontype'] == 3): ?>
<label><input type="checkbox" name="configoption[<?php echo $this->_tpl_vars['configoption']['id']; ?>
]" value="1"<?php if ($this->_tpl_vars['configoption']['selectedqty']): ?> checked<?php endif; ?>> <?php echo $this->_tpl_vars['configoption']['options']['0']['name']; ?>
</label>
<?php elseif ($this->_tpl_vars['configoption']['optiontype'] == 4): ?>
<input type="text" name="configoption[<?php echo $this->_tpl_vars['configoption']['id']; ?>
]" value="<?php echo $this->_tpl_vars['configoption']['selectedqty']; ?>
" size="5"> x <?php echo $this->_tpl_vars['configoption']['options']['0']['name']; ?>

<?php endif; ?>
</td></tr>
<?php endforeach; endif; unset($_from); ?>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr><td height="10"></td></tr>
<?php endif; ?>

<?php if ($this->_tpl_vars['addons']): ?>
<tr><td colspan="2"><strong><?php echo $this->_tpl_vars['LANG']['cartaddons']; ?>
</strong></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<?php $_from = $this->_tpl_vars['addons']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['addon']):
?>
<tr class="orderrow<?php if ($this->_tpl_vars['num'] % 2): ?>2<?php else: ?>1<?php endif; ?>"><td class="leftcol"><?php echo $this->_tpl_vars['addon']['checkbox']; ?>
</td><td><label for="a<?php echo $this->_tpl_vars['addon']['id']; ?>
"><strong><?php echo $this->_tpl_vars['addon']['name']; ?>
</strong> - <?php echo $this->_tpl_vars['addon']['description']; ?>
 (<?php echo $this->_tpl_vars['addon']['pricing']; ?>
)</label></td></tr>
<?php endforeach; endif; unset($_from); ?>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr><td height="10"></td></tr>
<?php endif; ?>

<?php if ($this->_tpl_vars['customfields']): ?>
<tr><td colspan="2"><strong><?php echo $this->_tpl_vars['LANG']['orderadditionalrequiredinfo']; ?>
</strong></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<?php $_from = $this->_tpl_vars['customfields']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['customfield']):
?>
<tr class="orderrow<?php if ($this->_tpl_vars['num'] % 2): ?>2<?php else: ?>1<?php endif; ?>"><td class="leftcol"><?php echo $this->_tpl_vars['customfield']['name']; ?>
</td><td><?php echo $this->_tpl_vars['customfield']['input']; ?>
 <?php echo $this->_tpl_vars['customfield']['description']; ?>
</td></tr>
<?php endforeach; endif; unset($_from); ?>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr><td height="10"></td></tr>
<?php endif; ?>

<?php if ($this->_tpl_vars['domainoption']): ?>
<tr><td colspan="2"><strong><?php echo $this->_tpl_vars['LANG']['cartproductdomain']; ?>
</strong></td></tr>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<?php if ($this->_tpl_vars['domains']): ?>
<input type="hidden" name="domainoption" value="<?php echo $this->_tpl_vars['domainoption']; ?>
" />
<?php $_from = $this->_tpl_vars['domains']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['domain']):
?>
<input type="hidden" name="domains[]" value="<?php echo $this->_tpl_vars['domain']['domain']; ?>
" />
<input type="hidden" name="domainsregperiod[<?php echo $this->_tpl_vars['domain']['domain']; ?>
]" value="<?php echo $this->_tpl_vars['domain']['regperiod']; ?>
" />
<tr class="orderrow<?php if ($this->_tpl_vars['num'] % 2): ?>2<?php else: ?>1<?php endif; ?>"><td class="leftcol"><?php echo $this->_tpl_vars['LANG']['orderdomain']; ?>
 <?php echo $this->_tpl_vars['num']+1; ?>
</td><td><?php echo $this->_tpl_vars['domain']['domain']; ?>
<?php if ($this->_tpl_vars['domain']['regperiod']): ?> (<?php echo $this->_tpl_vars['domain']['regperiod']; ?>
 <?php echo $this->_tpl_vars['LANG']['orderyears']; ?>
)<?php endif; ?></td></tr>
<?php endforeach; endif; unset($_from); ?>
<?php endif; ?>
<?php if ($this->_tpl_vars['additionaldomainfields']): ?>
<?php $_from = $this->_tpl_vars['additionaldomainfields']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['domainfieldname'] => $this->_tpl_vars['domainfield']):
?>
<tr class="orderrow<?php if ($this->_tpl_vars['num'] % 2): ?>2<?php else: ?>1<?php endif; ?>"><td class="leftcol"><?php echo $this->_tpl_vars['domainfieldname']; ?>
</td><td><?php echo $this->_tpl_vars['domainfield']; ?>
</td></tr>
<?php endforeach; endif; unset($_from); ?>
<?php endif; ?>
<tr class="orderheadingrow"><td colspan="2"></td></tr>
<tr><td height="10"></td></tr>
<?php endif; ?>

</table>

<p align="center"><?php if ($this->_tpl_vars['firstconfig']): ?><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['addtocart']; ?>
" /><?php else: ?><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['updatecart']; ?>
" /><?php endif; ?></p>

</form>

</div>