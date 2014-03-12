<?php /* Smarty version 2.6.27, created on 2013-08-27 09:09:52
         compiled from emailtpl:emailmessage */ ?>
<p><a href="<?php echo $this->_tpl_vars['company_domain']; ?>
" target="_blank"><img src="<?php echo $this->_tpl_vars['company_logo_url']; ?>
" alt="<?php echo $this->_tpl_vars['company_name']; ?>
" border="0" /></a></p>
<p>
<?php echo $this->_tpl_vars['client_name']; ?>
,
</p>
<p>
Thank you for contacting our support team. A support ticket has now been opened for your request. You will be notified when a response is made by email. The details of your ticket are shown below.
</p>
<p>
Subject: <?php echo $this->_tpl_vars['ticket_subject']; ?>
<br />
Priority: <?php echo $this->_tpl_vars['ticket_priority']; ?>
<br />
Status: <?php echo $this->_tpl_vars['ticket_status']; ?>

</p>
<p>
You can view the ticket at any time at <?php echo $this->_tpl_vars['ticket_link']; ?>

</p>
<p>
<?php echo $this->_tpl_vars['signature']; ?>

</p>