<?php /* Smarty version 2.6.27, created on 2013-08-27 09:09:27
         compiled from /home/salim/public_html/billing/templates/default/supportticketsubmit-steptwo.tpl */ ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['navopenticket'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<script language="javascript">
var currentcheckcontent,lastcheckcontent;
<?php if ($this->_tpl_vars['kbsuggestions']): ?>
<?php echo '
function getticketsuggestions() {
    currentcheckcontent = jQuery("#message").val();
    if (currentcheckcontent!=lastcheckcontent && currentcheckcontent!="") {
        jQuery.post("submitticket.php", { action: "getkbarticles", text: currentcheckcontent },
        function(data){
            if (data) {
                jQuery("#searchresults").html(data);
                jQuery("#searchresults").slideDown();
            }
        });
        lastcheckcontent = currentcheckcontent;
	}
    setTimeout(\'getticketsuggestions();\', 3000);
}
getticketsuggestions();
'; ?>

<?php endif; ?>
</script>

<?php if ($this->_tpl_vars['errormessage']): ?>
<div class="alert alert-error">
    <p class="bold"><?php echo $this->_tpl_vars['LANG']['clientareaerrors']; ?>
</p>
    <ul>
        <?php echo $this->_tpl_vars['errormessage']; ?>

    </ul>
</div>
<?php endif; ?>

<br />

<form name="submitticket" method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?step=3" enctype="multipart/form-data" class="center95 form-stacked">

    <fieldset class="control-group">

	    <div class="row">
            <div class="multicol">
                <div class="control-group">
        		    <label class="control-label bold" for="name"><?php echo $this->_tpl_vars['LANG']['supportticketsclientname']; ?>
</label>
        			<div class="controls">
        			    <?php if ($this->_tpl_vars['loggedin']): ?><input class="input-xlarge disabled" type="text" id="name" value="<?php echo $this->_tpl_vars['clientname']; ?>
" disabled="disabled" /><?php else: ?><input class="input-xlarge" type="text" name="name" id="name" value="<?php echo $this->_tpl_vars['name']; ?>
" /><?php endif; ?>
        			</div>
        		</div>
        	</div>
            <div class="multicol">
                <div class="control-group">
        		    <label class="control-label bold" for="email"><?php echo $this->_tpl_vars['LANG']['supportticketsclientemail']; ?>
</label>
        			<div class="controls">
        			    <?php if ($this->_tpl_vars['loggedin']): ?><input class="input-xlarge disabled" type="text" id="email" value="<?php echo $this->_tpl_vars['email']; ?>
" disabled="disabled" /><?php else: ?><input class="input-xlarge" type="text" name="email" id="email" value="<?php echo $this->_tpl_vars['email']; ?>
" /><?php endif; ?>
        			</div>
        		</div>
        	</div>
        </div>
        <div class="row">
    	    <div class="control-group">
    		    <label class="control-label bold" for="subject"><?php echo $this->_tpl_vars['LANG']['supportticketsticketsubject']; ?>
</label>
    			<div class="controls">
    			    <input class="input-xlarge" type="text" name="subject" id="subject" value="<?php echo $this->_tpl_vars['subject']; ?>
" style="width:80%;" />
    			</div>
    		</div>
		</div>
        <div class="row">
            <div class="multicol">
                <div class="control-group">
        		    <label class="control-label bold" for="name"><?php echo $this->_tpl_vars['LANG']['supportticketsdepartment']; ?>
</label>
        			<div class="controls">
        			    <select name="deptid">
                        <?php $_from = $this->_tpl_vars['departments']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['department']):
?>
                            <option value="<?php echo $this->_tpl_vars['department']['id']; ?>
"<?php if ($this->_tpl_vars['department']['id'] == $this->_tpl_vars['deptid']): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['department']['name']; ?>
</option>
                        <?php endforeach; endif; unset($_from); ?>
                        </select>
        			</div>
        		</div>
    		</div>
<?php if ($this->_tpl_vars['relatedservices']): ?>
    	     <div class="multicol">
                 <div class="control-group">
        		    <label class="control-label bold" for="relatedservice"><?php echo $this->_tpl_vars['LANG']['relatedservice']; ?>
</label>
        			<div class="controls">
        			    <select name="relatedservice" id="relatedservice">
                            <option value=""><?php echo $this->_tpl_vars['LANG']['none']; ?>
</option>
                            <?php $_from = $this->_tpl_vars['relatedservices']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['relatedservice']):
?>
                            <option value="<?php echo $this->_tpl_vars['relatedservice']['id']; ?>
"><?php echo $this->_tpl_vars['relatedservice']['name']; ?>
 (<?php echo $this->_tpl_vars['relatedservice']['status']; ?>
)</option>
                            <?php endforeach; endif; unset($_from); ?>
                        </select>
        			</div>
        		</div>
    		</div>
<?php endif; ?>
            <div class="multicol">
        	    <div class="control-group">
        		    <label class="control-label bold" for="priority"><?php echo $this->_tpl_vars['LANG']['supportticketspriority']; ?>
</label>
        			<div class="controls">
        			    <select name="urgency" id="priority">
                            <option value="High"<?php if ($this->_tpl_vars['urgency'] == 'High'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['LANG']['supportticketsticketurgencyhigh']; ?>
</option>
                            <option value="Medium"<?php if ($this->_tpl_vars['urgency'] == 'Medium' || ! $this->_tpl_vars['urgency']): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['LANG']['supportticketsticketurgencymedium']; ?>
</option>
                            <option value="Low"<?php if ($this->_tpl_vars['urgency'] == 'Low'): ?> selected="selected"<?php endif; ?>><?php echo $this->_tpl_vars['LANG']['supportticketsticketurgencylow']; ?>
</option>
                        </select>
        			</div>
        		</div>
    		</div>
        </div>

	    <div class="control-group">
		    <label class="control-label bold" for="message"><?php echo $this->_tpl_vars['LANG']['contactmessage']; ?>
</label>
			<div class="controls">
			    <textarea name="message" id="message" rows="12" class="fullwidth"><?php echo $this->_tpl_vars['message']; ?>
</textarea>
			</div>
		</div>
<?php $_from = $this->_tpl_vars['customfields']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['customfield']):
?>
	    <div class="control-group">
		    <label class="control-label bold" for="customfield<?php echo $this->_tpl_vars['customfield']['id']; ?>
"><?php echo $this->_tpl_vars['customfield']['name']; ?>
</label>
			<div class="controls">
			    <?php echo $this->_tpl_vars['customfield']['input']; ?>
 <?php echo $this->_tpl_vars['customfield']['description']; ?>

			</div>
		</div>
<?php endforeach; endif; unset($_from); ?>
	    <div class="control-group">
		    <label class="control-label bold" for="attachments"><?php echo $this->_tpl_vars['LANG']['supportticketsticketattachments']; ?>
:</label>
			<div class="controls">
			    <input type="file" name="attachments[]" style="width:70%;" /><br />
                <div id="fileuploads"></div>
                <a href="#" onclick="extraTicketAttachment();return false"><img src="images/add.gif" align="absmiddle" border="0" /> <?php echo $this->_tpl_vars['LANG']['addmore']; ?>
</a><br />
                (<?php echo $this->_tpl_vars['LANG']['supportticketsallowedextensions']; ?>
: <?php echo $this->_tpl_vars['allowedfiletypes']; ?>
)
			</div>
		</div>

    </fieldset>

<div id="searchresults" class="contentbox" style="display:none;"></div>

<?php if ($this->_tpl_vars['capatacha']): ?>
<h3><?php echo $this->_tpl_vars['LANG']['captchatitle']; ?>
</h3>
<p><?php echo $this->_tpl_vars['LANG']['captchaverify']; ?>
</p>
<?php if ($this->_tpl_vars['capatacha'] == 'recaptcha'): ?>
<div align="center"><?php echo $this->_tpl_vars['recapatchahtml']; ?>
</div>
<?php else: ?>
<p align="center"><img src="includes/verifyimage.php" align="middle" /> <input type="text" name="code" class="input-small" maxlength="5" /></p>
<?php endif; ?>
<?php endif; ?>

<div class="form-actions" style="padding-left:160px;">
    <input class="btn btn-primary" type="submit" name="save" value="<?php echo $this->_tpl_vars['LANG']['supportticketsticketsubmit']; ?>
" />
    <input class="btn" type="reset" value="<?php echo $this->_tpl_vars['LANG']['cancel']; ?>
" />
</div>

</form>