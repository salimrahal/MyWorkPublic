<?php /* Smarty version 2.6.27, created on 2013-08-30 13:02:31
         compiled from emailtpl:emailmessage */ ?>
<p><a href="<?php echo $this->_tpl_vars['company_domain']; ?>
" target="_blank"><img src="<?php echo $this->_tpl_vars['company_logo_url']; ?>
" alt="<?php echo $this->_tpl_vars['company_name']; ?>
" border="0" /></a></p>
<p>
Dear <?php echo $this->_tpl_vars['client_name']; ?>
, 
</p>
<p>
This is a notice that a recent credit card payment we attempted on the card we have registered for you failed. 
</p>
<p>
Invoice Date: <?php echo $this->_tpl_vars['invoice_date_created']; ?>
<br />
Invoice No: <?php echo $this->_tpl_vars['invoice_num']; ?>
<br />
Amount: <?php echo $this->_tpl_vars['invoice_total']; ?>
<br />
Status: <?php echo $this->_tpl_vars['invoice_status']; ?>
 
</p>
<p>
You now need to login to your client area to pay the invoice manually. During the payment process you will be given the opportunity to change the card on record with us.<br />
<?php echo $this->_tpl_vars['invoice_link']; ?>
 
</p>
<p>
Note: This email will serve as an official receipt for this payment. 
</p>
<p>
<?php echo $this->_tpl_vars['signature']; ?>

</p>