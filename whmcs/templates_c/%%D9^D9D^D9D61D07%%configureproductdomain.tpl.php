<?php /* Smarty version 2.6.27, created on 2013-08-27 09:54:13
         compiled from /home/salim/public_html/billing/templates/orderforms/boxes/configureproductdomain.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'replace', '/home/salim/public_html/billing/templates/orderforms/boxes/configureproductdomain.tpl', 12, false),)), $this); ?>
<link rel="stylesheet" type="text/css" href="templates/orderforms/<?php echo $this->_tpl_vars['carttpl']; ?>
/style.css" />

<div id="order-boxes">

<p><?php echo $this->_tpl_vars['LANG']['cartproductdomaindesc']; ?>
</p>

<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?a=add&pid=<?php echo $this->_tpl_vars['pid']; ?>
">
<?php $_from = $this->_tpl_vars['passedvariables']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['name'] => $this->_tpl_vars['value']):
?>
<input type="hidden" name="<?php echo $this->_tpl_vars['name']; ?>
" value="<?php echo $this->_tpl_vars['value']; ?>
" />
<?php endforeach; endif; unset($_from); ?>

<?php if ($this->_tpl_vars['errormessage']): ?><div class="errorbox"><?php echo ((is_array($_tmp=$this->_tpl_vars['errormessage'])) ? $this->_run_mod_handler('replace', true, $_tmp, '<li>', ' &nbsp;#&nbsp; ') : smarty_modifier_replace($_tmp, '<li>', ' &nbsp;#&nbsp; ')); ?>
 &nbsp;#&nbsp; </div><br /><?php endif; ?>

<table width="90%" align="center" cellspacing="1" cellpadding="5">
<tr class="orderheadingrow"><td colspan="2"></td></tr>

<?php if ($this->_tpl_vars['incartdomains']): ?>
<tr class="orderrow1"><td width="10"><input type="radio" name="domainoption" value="incart" id="selincart" onclick="document.getElementById('register').style.display='none';document.getElementById('transfer').style.display='none';document.getElementById('owndomain').style.display='none';document.getElementById('subdomain').style.display='none';document.getElementById('incart').style.display=''" /></td><td><label for="selincart"><?php echo $this->_tpl_vars['LANG']['cartproductdomainuseincart']; ?>
</label></td></tr>
<?php endif; ?>

<tr id="incart" class="orderrow2"><td colspan="2" class="textcenter">
<select name="incartdomain">
<?php $_from = $this->_tpl_vars['incartdomains']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['incartdomain']):
?>
<option value="<?php echo $this->_tpl_vars['incartdomain']; ?>
"><?php echo $this->_tpl_vars['incartdomain']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
</select>
</td></tr>

<?php if ($this->_tpl_vars['registerdomainenabled']): ?>
<tr class="orderrow1"><td width="10"><input type="radio" name="domainoption" value="register" id="selregister" onclick="document.getElementById('register').style.display='';document.getElementById('transfer').style.display='none';document.getElementById('owndomain').style.display='none';document.getElementById('subdomain').style.display='none';document.getElementById('incart').style.display='none'" /></td><td><label for="selregister"><?php echo $this->_tpl_vars['LANG']['orderdomainoption1part1']; ?>
 <?php echo $this->_tpl_vars['companyname']; ?>
 <?php echo $this->_tpl_vars['LANG']['orderdomainoption1part2']; ?>
</label></td></tr>
<?php endif; ?>

<tr id="register" class="orderrow2"><td colspan="2" class="textcenter">
www. <input type="text" name="sld[0]" size="40" value="<?php echo $this->_tpl_vars['sld']; ?>
" /> <select name="tld[0]">
<?php $_from = $this->_tpl_vars['registertlds']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['listtld']):
?>
<option value="<?php echo $this->_tpl_vars['listtld']; ?>
"<?php if ($this->_tpl_vars['listtld'] == $this->_tpl_vars['tld']): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['listtld']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
</select>
</td></tr>

<?php if ($this->_tpl_vars['transferdomainenabled']): ?>
<tr class="orderrow1"><td width="10"><input type="radio" name="domainoption" value="transfer" id="seltransfer" onclick="document.getElementById('register').style.display='none';document.getElementById('transfer').style.display='';document.getElementById('owndomain').style.display='none';document.getElementById('subdomain').style.display='none';document.getElementById('incart').style.display='none'" /></td><td><label for="seltransfer"><?php echo $this->_tpl_vars['LANG']['orderdomainoption3']; ?>
 <?php echo $this->_tpl_vars['companyname']; ?>
</label></td></tr>
<?php endif; ?>

<tr id="transfer" class="orderrow2"><td colspan="2" class="textcenter">
www. <input type="text" name="sld[1]" size="40" value="<?php echo $this->_tpl_vars['sld']; ?>
" /> <select name="tld[1]">
<?php $_from = $this->_tpl_vars['transfertlds']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['listtld']):
?>
<option value="<?php echo $this->_tpl_vars['listtld']; ?>
"<?php if ($this->_tpl_vars['listtld'] == $this->_tpl_vars['tld']): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['listtld']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
</select>
</td></tr>

<?php if ($this->_tpl_vars['owndomainenabled']): ?>
<tr class="orderrow1"><td width="10"><input type="radio" name="domainoption" value="owndomain" id="selowndomain" onclick="document.getElementById('register').style.display='none';document.getElementById('transfer').style.display='none';document.getElementById('owndomain').style.display='';document.getElementById('subdomain').style.display='none';document.getElementById('incart').style.display='none'" /></td><td><label for="selowndomain"><?php echo $this->_tpl_vars['LANG']['orderdomainoption2']; ?>
</label></td></tr>
<?php endif; ?>

<tr id="owndomain" class="orderrow2"><td colspan="2" class="textcenter">
www. <input type="text" name="sld[2]" size="40" value="<?php echo $this->_tpl_vars['sld']; ?>
" /> . <input type="text" name="tld[2]" size="7" value="<?php 
global $tld;
echo substr($tld,1);
 ?>" />
</td></tr>

<?php if ($this->_tpl_vars['subdomains']): ?>
<tr class="orderrow1"><td width="10"><input type="radio" name="domainoption" value="subdomain" id="selsubdomain" onclick="document.getElementById('register').style.display='none';document.getElementById('transfer').style.display='none';document.getElementById('owndomain').style.display='none';document.getElementById('subdomain').style.display='';document.getElementById('incart').style.display='none'" /></td><td><label for="selsubdomain"><?php echo $this->_tpl_vars['LANG']['orderdomainoption4']; ?>
</label></td></tr>
<?php endif; ?>

<tr id="subdomain" class="orderrow2"><td colspan="2" class="textcenter">
http:// <input type="text" name="sld[3]" size="40" value="<?php echo $this->_tpl_vars['sld']; ?>
" /> <select name="tld[3]"><?php $_from = $this->_tpl_vars['subdomains']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['subid'] => $this->_tpl_vars['subdomain']):
?><option value="<?php echo $this->_tpl_vars['subid']; ?>
"><?php echo $this->_tpl_vars['subdomain']; ?>
</option><?php endforeach; endif; unset($_from); ?></select>
</td></tr>

<tr class="orderheadingrow"><td colspan="2"></td></tr>
</table>

<script language="javascript" type="text/javascript">
document.getElementById('incart').style.display='none';
document.getElementById('register').style.display='none';
document.getElementById('transfer').style.display='none';
document.getElementById('owndomain').style.display='none';
document.getElementById('subdomain').style.display='none';
document.getElementById('sel<?php echo $this->_tpl_vars['domainoption']; ?>
').checked='true';
document.getElementById('<?php echo $this->_tpl_vars['domainoption']; ?>
').style.display='';
</script>

<?php if ($this->_tpl_vars['availabilityresults']): ?>

<h1><?php echo $this->_tpl_vars['LANG']['choosedomains']; ?>
</h1>

<div class="center90">
<table class="styled">
<tr><th><?php echo $this->_tpl_vars['LANG']['domainname']; ?>
</th><th><?php echo $this->_tpl_vars['LANG']['domainstatus']; ?>
</th><th><?php echo $this->_tpl_vars['LANG']['domainmoreinfo']; ?>
</th></tr>
<?php $_from = $this->_tpl_vars['availabilityresults']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['result']):
?>
<tr><td><?php echo $this->_tpl_vars['result']['domain']; ?>
</td><td class="<?php if ($this->_tpl_vars['result']['status'] == $this->_tpl_vars['searchvar']): ?>textgreen<?php else: ?>textred<?php endif; ?>"><?php if ($this->_tpl_vars['result']['status'] == $this->_tpl_vars['searchvar']): ?><input type="checkbox" name="domains[]" value="<?php echo $this->_tpl_vars['result']['domain']; ?>
"<?php if ($this->_tpl_vars['num'] == 0): ?> checked<?php endif; ?> /> <?php echo $this->_tpl_vars['LANG']['domainavailable']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['domainunavailable']; ?>
<?php endif; ?></td><td><?php if ($this->_tpl_vars['result']['regoptions']): ?><select name="domainsregperiod[<?php echo $this->_tpl_vars['result']['domain']; ?>
]"><?php $_from = $this->_tpl_vars['result']['regoptions']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['period'] => $this->_tpl_vars['regoption']):
?><?php if ($this->_tpl_vars['regoption'][$this->_tpl_vars['domainoption']]): ?><option value="<?php echo $this->_tpl_vars['period']; ?>
"><?php echo $this->_tpl_vars['period']; ?>
 <?php echo $this->_tpl_vars['LANG']['orderyears']; ?>
 @ <?php echo $this->_tpl_vars['regoption'][$this->_tpl_vars['domainoption']]; ?>
</option><?php endif; ?><?php endforeach; endif; unset($_from); ?></select><?php endif; ?></td></tr>
<?php endforeach; endif; unset($_from); ?>
</table>
</div>

<?php endif; ?>

<?php if ($this->_tpl_vars['freedomaintlds']): ?>* <em><?php echo $this->_tpl_vars['LANG']['orderfreedomainregistration']; ?>
 <?php echo $this->_tpl_vars['LANG']['orderfreedomainappliesto']; ?>
: <?php echo $this->_tpl_vars['freedomaintlds']; ?>
</em><?php endif; ?>

<p align="center"><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['ordercontinuebutton']; ?>
" /></p>

</form>

</div>