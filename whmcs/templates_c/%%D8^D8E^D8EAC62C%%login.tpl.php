<?php /* Smarty version 2.6.27, created on 2013-08-22 12:11:01
         compiled from /home/salim/public_html/billing/templates/default/login.tpl */ ?>
<div class="halfwidthcontainer">

<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['login'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<?php if ($this->_tpl_vars['incorrect']): ?>
<div class="alert alert-error textcenter">
    <p><?php echo $this->_tpl_vars['LANG']['loginincorrect']; ?>
</p>
</div>
<?php endif; ?>

<form method="post" action="<?php echo $this->_tpl_vars['systemsslurl']; ?>
dologin.php" class="form-stacked" name="frmlogin">

<div class="logincontainer">

    <fieldset class="control-group">

	    <div class="control-group">
		    <label class="control-label" for="username"><?php echo $this->_tpl_vars['LANG']['loginemail']; ?>
:</label>
			<div class="controls">
			    <input class="input-xlarge" name="username" id="username" type="text" />
			</div>
		</div>

		<div class="control-group">
		    <label class="control-label" for="password"><?php echo $this->_tpl_vars['LANG']['loginpassword']; ?>
:</label>
			<div class="controls">
			    <input class="input-xlarge" name="password" id="password" type="password"/>
			</div>
		</div>

        <div align="center">
		  <div class="loginbtn"><input type="submit" class="btn btn-primary btn-large" value="<?php echo $this->_tpl_vars['LANG']['loginbutton']; ?>
" /></div>
          <div class="rememberme"><input type="checkbox" name="rememberme"<?php if ($this->_tpl_vars['rememberme']): ?> checked="checked"<?php endif; ?> /> <strong><?php echo $this->_tpl_vars['LANG']['loginrememberme']; ?>
</strong></div>
          <br />
          <br />
          <p><a href="pwreset.php"><?php echo $this->_tpl_vars['LANG']['loginforgotteninstructions']; ?>
</a></p>
        </div>

	</fieldset>

</div>

</form>

<script language="javascript">
document.frmlogin.username.focus();
</script>

</div>