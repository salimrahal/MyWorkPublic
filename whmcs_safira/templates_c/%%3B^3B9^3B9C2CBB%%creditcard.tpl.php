<?php /* Smarty version 2.6.27, created on 2013-08-30 13:02:32
         compiled from /home/www/whmcs/templates/default/creditcard.tpl */ ?>
<script type="text/javascript" src="includes/jscript/statesdropdown.js"></script>

<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['creditcard'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="alert alert-block alert-warn">
    <p class="textcenter"><strong>Paying Invoice #<?php echo $this->_tpl_vars['invoiceid']; ?>
</strong> - Balance Due: <strong><?php echo $this->_tpl_vars['balance']; ?>
</strong></p>
</div>

<?php if ($this->_tpl_vars['remotecode']): ?>

<div id="submitfrm" class="textcenter">

<?php echo $this->_tpl_vars['remotecode']; ?>


<iframe name="3dauth" width="90%" height="600" scrolling="auto" src="about:blank" style="border:1px solid #fff;"></iframe>

</div>

<br />

<?php echo '
<script language="javascript">
setTimeout ( "autoForward()" , 1000 );
function autoForward() {
	var submitForm = $("#submitfrm").find("form");
    submitForm.submit();
}
</script>
'; ?>


<?php else: ?>

<form method="post" action="creditcard.php" class="form-horizontal">
<input type="hidden" name="action" value="submit">
<input type="hidden" name="invoiceid" value="<?php echo $this->_tpl_vars['invoiceid']; ?>
">

<?php if ($this->_tpl_vars['errormessage']): ?>
    <div class="alert alert-error">
        <p class="bold"><?php echo $this->_tpl_vars['LANG']['clientareaerrors']; ?>
</p>
        <ul>
            <?php echo $this->_tpl_vars['errormessage']; ?>

        </ul>
    </div>
<?php endif; ?>

<fieldset class="control-group">

<div class="control-group">
    <div class="col2half">
        <div class="internalpadding">

        	<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/subheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['creditcardyourinfo'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

            <div class="control-group">
        	    <label class="control-label" for="firstname"><?php echo $this->_tpl_vars['LANG']['clientareafirstname']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="firstname" id="firstname" value="<?php echo $this->_tpl_vars['firstname']; ?>
"<?php if (in_array ( 'firstname' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="lastname"><?php echo $this->_tpl_vars['LANG']['clientarealastname']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="lastname" id="lastname" value="<?php echo $this->_tpl_vars['lastname']; ?>
"<?php if (in_array ( 'lastname' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="address1"><?php echo $this->_tpl_vars['LANG']['clientareaaddress1']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="address1" id="address1" value="<?php echo $this->_tpl_vars['address1']; ?>
"<?php if (in_array ( 'address1' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="address2"><?php echo $this->_tpl_vars['LANG']['clientareaaddress2']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="address2" id="address2" value="<?php echo $this->_tpl_vars['address2']; ?>
"<?php if (in_array ( 'address2' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="city"><?php echo $this->_tpl_vars['LANG']['clientareacity']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="city" id="city" value="<?php echo $this->_tpl_vars['city']; ?>
"<?php if (in_array ( 'city' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="state"><?php echo $this->_tpl_vars['LANG']['clientareastate']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="state" id="state" value="<?php echo $this->_tpl_vars['state']; ?>
"<?php if (in_array ( 'state' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="postcode"><?php echo $this->_tpl_vars['LANG']['clientareapostcode']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="postcode" id="postcode" value="<?php echo $this->_tpl_vars['postcode']; ?>
"<?php if (in_array ( 'postcode' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

            <div class="control-group">
        	    <label class="control-label" for="country"><?php echo $this->_tpl_vars['LANG']['clientareacountry']; ?>
</label>
        		<div class="controls">
        		    <?php echo $this->_tpl_vars['countriesdropdown']; ?>

        		</div>
        	</div>


            <div class="control-group">
        	    <label class="control-label" for="phonenumber"><?php echo $this->_tpl_vars['LANG']['clientareaphonenumber']; ?>
</label>
        		<div class="controls">
        		    <input type="text" name="phonenumber" id="phonenumber" value="<?php echo $this->_tpl_vars['phonenumber']; ?>
"<?php if (in_array ( 'phonenumber' , $this->_tpl_vars['uneditablefields'] )): ?> disabled="" class="disabled"<?php endif; ?> />
        		</div>
        	</div>

        </div>
    </div>
    <div class="col2half">
        <div class="internalpadding">

            <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/subheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['creditcarddetails'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

            <p><label class="full control-label"><input type="radio" class="radio inline" name="ccinfo" value="useexisting" onclick="disableFields('newccinfo',true)"<?php if ($this->_tpl_vars['cardnum']): ?> checked<?php else: ?> disabled<?php endif; ?> /> <?php echo $this->_tpl_vars['LANG']['creditcarduseexisting']; ?>
<?php if ($this->_tpl_vars['cardnum']): ?> (<?php echo $this->_tpl_vars['cardnum']; ?>
)<?php endif; ?></label></p>
<?php if ($this->_tpl_vars['cardnum']): ?>
            <br />

            <div class="control-group">
                <label class="control-label" for="cccvv2"><?php echo $this->_tpl_vars['LANG']['creditcardcvvnumber']; ?>
</label>
        		<div class="controls"><input type="text" name="cccvv2" id="cccvv2" size="5" value="<?php echo $this->_tpl_vars['cccvv']; ?>
" autocomplete="off" class="input-mini" />&nbsp;<a href="#" onclick="window.open('images/ccv.gif','','width=280,height=200,scrollbars=no,top=100,left=100');return false"><?php echo $this->_tpl_vars['LANG']['creditcardcvvwhere']; ?>
</a></div>
        	</div>
<?php endif; ?>
            <p><label class="full control-label"><input type="radio" class="radio inline" name="ccinfo" value="new" onclick="disableFields('newccinfo',false)"<?php if (! $this->_tpl_vars['cardnum'] || $this->_tpl_vars['ccinfo'] == 'new'): ?> checked<?php endif; ?> /> <?php echo $this->_tpl_vars['LANG']['creditcardenternewcard']; ?>
</label></p>

            <br />
            <br />

            <div class="control-group">
                <label class="control-label" for="cctype"><?php echo $this->_tpl_vars['LANG']['creditcardcardtype']; ?>
</label>
                <div class="controls">
                	<select name="cctype" id="cctype" class="newccinfo">
                    <?php $_from = $this->_tpl_vars['acceptedcctypes']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['cardtype']):
?>
                        <option<?php if ($this->_tpl_vars['cctype'] == $this->_tpl_vars['cardtype']): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['cardtype']; ?>
</option>
                    <?php endforeach; endif; unset($_from); ?>
                    </select>
        		</div>
            </div>

            <div class="control-group">
                <label class="control-label" for="ccnumber"><?php echo $this->_tpl_vars['LANG']['creditcardcardnumber']; ?>
</label>
        		<div class="controls"><input type="text" name="ccnumber" id="ccnumber" size="30" value="<?php echo $this->_tpl_vars['ccnumber']; ?>
" autocomplete="off" class="newccinfo" /></div>
        	</div>

           <?php if ($this->_tpl_vars['showccissuestart']): ?>
            <div class="control-group">
                <label class="control-label" for="ccstartmonth"><?php echo $this->_tpl_vars['LANG']['creditcardcardstart']; ?>
</label>
        		<div class="controls"><select name="ccstartmonth" id="ccstartmonth" class="newccinfo"><?php $_from = $this->_tpl_vars['months']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['month']):
?>
<option<?php if ($this->_tpl_vars['ccstartmonth'] == $this->_tpl_vars['month']): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['month']; ?>
</option>
<?php endforeach; endif; unset($_from); ?></select> / <select name="ccstartyear" class="newccinfo">
<?php $_from = $this->_tpl_vars['startyears']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['year']):
?>
<option<?php if ($this->_tpl_vars['ccstartyear'] == $this->_tpl_vars['year']): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['year']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
</select></div>
        	</div>
            <?php endif; ?>

            <div class="control-group">
                <label class="control-label" for="ccexpirymonth"><?php echo $this->_tpl_vars['LANG']['creditcardcardexpires']; ?>
</label>
        		<div class="controls"><select name="ccexpirymonth" id="ccexpirymonth" class="newccinfo"><?php $_from = $this->_tpl_vars['months']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['month']):
?>
<option<?php if ($this->_tpl_vars['ccexpirymonth'] == $this->_tpl_vars['month']): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['month']; ?>
</option>
<?php endforeach; endif; unset($_from); ?></select> / <select name="ccexpiryyear" class="newccinfo">
<?php $_from = $this->_tpl_vars['expiryyears']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['year']):
?>
<option<?php if ($this->_tpl_vars['ccexpiryyear'] == $this->_tpl_vars['year']): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['year']; ?>
</option>
<?php endforeach; endif; unset($_from); ?>
</select></div>
        	</div>

           <?php if ($this->_tpl_vars['showccissuestart']): ?>
            
            <div class="control-group">
                <label class="control-label" for="ccissuenum"><?php echo $this->_tpl_vars['LANG']['creditcardcardissuenum']; ?>
</label>
        		<div class="controls"><input type="text" name="ccissuenum" id="ccissuenum" size="5" maxlength="3" value="<?php echo $this->_tpl_vars['ccissuenum']; ?>
" class="input-small newccinfo" /></div>
        	</div>
            <?php endif; ?>

            <div class="control-group">
                <label class="control-label" for="cccvv"><?php echo $this->_tpl_vars['LANG']['creditcardcvvnumber']; ?>
</label>
        		<div class="controls"><input type="text" name="cccvv" id="cccvv" size="5" value="<?php echo $this->_tpl_vars['cccvv']; ?>
" autocomplete="off" class="input-mini newccinfo" />&nbsp;<a href="#" onclick="window.open('images/ccv.gif','','width=280,height=200,scrollbars=no,top=100,left=100');return false"><?php echo $this->_tpl_vars['LANG']['creditcardcvvwhere']; ?>
</a></div>
        	</div>

        	<?php if ($this->_tpl_vars['shownostore']): ?>
            <p><label class="full control-label"><input type="checkbox" name="nostore" id="nostore" class="newccinfo" /> <?php echo $this->_tpl_vars['LANG']['creditcardnostore']; ?>
</label></p>
        	<?php endif; ?>

            <br />
            <br />

            <p class="textcenter"><input class="btn btn-large btn-primary" type="submit" value="<?php echo $this->_tpl_vars['LANG']['ordercontinuebutton']; ?>
" onclick="this.value='<?php echo $this->_tpl_vars['LANG']['pleasewait']; ?>
'" /></p>

        </div>
    </div>
</div>

<p align="center"><img src="images/padlock.gif" alt="Secure" /> <?php echo $this->_tpl_vars['LANG']['creditcardsecuritynotice']; ?>
</p>

</fieldset>

<?php if (! $this->_tpl_vars['cardnum'] || $this->_tpl_vars['ccinfo'] == 'new'): ?><?php else: ?>
<script> disableFields('newccinfo',true); </script>
<?php endif; ?>

</form>

<?php endif; ?>