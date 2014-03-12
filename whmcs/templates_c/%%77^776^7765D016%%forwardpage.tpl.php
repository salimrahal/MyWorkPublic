<?php /* Smarty version 2.6.27, created on 2013-08-27 08:56:54
         compiled from /home/salim/public_html/billing/templates/default/forwardpage.tpl */ ?>
<br /><br />

<div class="alert alert-block alert-warn">
    <p><?php echo $this->_tpl_vars['message']; ?>
</p>
</div>

<p class="textcenter"><img src="images/loading.gif" alt="Loading" border="0" /></p>

<br />

<div id="submitfrm" class="textcenter"><?php echo $this->_tpl_vars['code']; ?>
</div>

<form method="post" action="<?php if ($this->_tpl_vars['invoiceid']): ?>viewinvoice.php?id=<?php echo $this->_tpl_vars['invoiceid']; ?>
<?php else: ?>clientarea.php<?php endif; ?>"></form>

<br /><br /><br />

<?php echo '
<script language="javascript">
setTimeout ( "autoForward()" , 5000 );
function autoForward() {
	var submitForm = $("#submitfrm").find("form");
    submitForm.submit();
}
</script>
'; ?>