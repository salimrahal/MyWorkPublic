<?php /* Smarty version 2.6.27, created on 2013-09-02 11:47:06
         compiled from /home/salim/public_html/whmcs/templates/default/viewinvoice.tpl */ ?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=<?php echo $this->_tpl_vars['charset']; ?>
" />
    <title><?php echo $this->_tpl_vars['companyname']; ?>
 - <?php echo $this->_tpl_vars['LANG']['invoicenumber']; ?>
<?php echo $this->_tpl_vars['invoicenum']; ?>
</title>

    <link href="templates/<?php echo $this->_tpl_vars['template']; ?>
/css/invoice.css" rel="stylesheet">

  </head>

  <body>

<div class="wrapper">

<?php if ($this->_tpl_vars['error']): ?>

<div class="creditbox"><?php echo $this->_tpl_vars['LANG']['invoiceserror']; ?>
</div>

<?php else: ?>

<table class="header"><tr><td width="50%" nowrap>

<?php if ($this->_tpl_vars['logo']): ?><p><img src="<?php echo $this->_tpl_vars['logo']; ?>
" title="<?php echo $this->_tpl_vars['companyname']; ?>
" /></p><?php else: ?><h1><?php echo $this->_tpl_vars['companyname']; ?>
</h1><?php endif; ?>

</td><td width="50%" align="center">

<?php if ($this->_tpl_vars['status'] == 'Unpaid'): ?>
<font class="unpaid"><?php echo $this->_tpl_vars['LANG']['invoicesunpaid']; ?>
</font><br />
<?php if ($this->_tpl_vars['allowchangegateway']): ?>
<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?id=<?php echo $this->_tpl_vars['invoiceid']; ?>
"><?php echo $this->_tpl_vars['gatewaydropdown']; ?>
</form>
<?php else: ?>
<?php echo $this->_tpl_vars['paymentmethod']; ?>
<br />
<?php endif; ?>
<?php echo $this->_tpl_vars['paymentbutton']; ?>

<?php elseif ($this->_tpl_vars['status'] == 'Paid'): ?>
<font class="paid"><?php echo $this->_tpl_vars['LANG']['invoicespaid']; ?>
</font><br />
<?php echo $this->_tpl_vars['paymentmethod']; ?>
<br />
(<?php echo $this->_tpl_vars['datepaid']; ?>
)
<?php elseif ($this->_tpl_vars['status'] == 'Refunded'): ?>
<font class="refunded"><?php echo $this->_tpl_vars['LANG']['invoicesrefunded']; ?>
</font>
<?php elseif ($this->_tpl_vars['status'] == 'Cancelled'): ?>
<font class="cancelled"><?php echo $this->_tpl_vars['LANG']['invoicescancelled']; ?>
</font>
<?php elseif ($this->_tpl_vars['status'] == 'Collections'): ?>
<font class="collections"><?php echo $this->_tpl_vars['LANG']['invoicescollections']; ?>
</font>
<?php endif; ?>

</td></tr></table>

<?php if ($_GET['paymentsuccess']): ?>
<p align="center" class="paid"><?php echo $this->_tpl_vars['LANG']['invoicepaymentsuccessconfirmation']; ?>
</p>
<?php elseif ($_GET['pendingreview']): ?>
<p align="center" class="paid"><?php echo $this->_tpl_vars['LANG']['invoicepaymentpendingreview']; ?>
</p>
<?php elseif ($_GET['paymentfailed']): ?>
<p align="center" class="unpaid"><?php echo $this->_tpl_vars['LANG']['invoicepaymentfailedconfirmation']; ?>
</p>
<?php elseif ($this->_tpl_vars['offlinepaid']): ?>
<p align="center" class="refunded"><?php echo $this->_tpl_vars['LANG']['invoiceofflinepaid']; ?>
</p>
<?php endif; ?>

<?php if ($this->_tpl_vars['manualapplycredit']): ?>
<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?id=<?php echo $this->_tpl_vars['invoiceid']; ?>
">
<input type="hidden" name="applycredit" value="true" />
<div class="creditbox">
<?php echo $this->_tpl_vars['LANG']['invoiceaddcreditdesc1']; ?>
 <?php echo $this->_tpl_vars['totalcredit']; ?>
. <?php echo $this->_tpl_vars['LANG']['invoiceaddcreditdesc2']; ?>
<br />
<?php echo $this->_tpl_vars['LANG']['invoiceaddcreditamount']; ?>
: <input type="text" name="creditamount" size="10" value="<?php echo $this->_tpl_vars['creditamount']; ?>
" /> <input type="submit" value="<?php echo $this->_tpl_vars['LANG']['invoiceaddcreditapply']; ?>
" />
</div>
</form>
<?php endif; ?>

<table class="items"><tr><td width="50%">

<div class="addressbox">

<strong><?php echo $this->_tpl_vars['LANG']['invoicesinvoicedto']; ?>
</strong><br />
<?php if ($this->_tpl_vars['clientsdetails']['companyname']): ?><?php echo $this->_tpl_vars['clientsdetails']['companyname']; ?>
<br /><?php endif; ?>
<?php echo $this->_tpl_vars['clientsdetails']['firstname']; ?>
 <?php echo $this->_tpl_vars['clientsdetails']['lastname']; ?>
<br />
<?php echo $this->_tpl_vars['clientsdetails']['address1']; ?>
, <?php echo $this->_tpl_vars['clientsdetails']['address2']; ?>
<br />
<?php echo $this->_tpl_vars['clientsdetails']['city']; ?>
, <?php echo $this->_tpl_vars['clientsdetails']['state']; ?>
, <?php echo $this->_tpl_vars['clientsdetails']['postcode']; ?>
<br />
<?php echo $this->_tpl_vars['clientsdetails']['country']; ?>

<?php if ($this->_tpl_vars['customfields']): ?>
<br /><br />
<?php $_from = $this->_tpl_vars['customfields']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['customfield']):
?>
<?php echo $this->_tpl_vars['customfield']['fieldname']; ?>
: <?php echo $this->_tpl_vars['customfield']['value']; ?>
<br />
<?php endforeach; endif; unset($_from); ?>
<?php endif; ?>

</div>

</td><td width="50%">

<div class="addressbox">

<strong><?php echo $this->_tpl_vars['LANG']['invoicespayto']; ?>
</strong><br />
<?php echo $this->_tpl_vars['payto']; ?>


</div>

</td></tr></table>

<div class="row">
<span class="title"><?php echo $this->_tpl_vars['LANG']['invoicenumber']; ?>
<?php echo $this->_tpl_vars['invoicenum']; ?>
</span><br />
<?php echo $this->_tpl_vars['LANG']['invoicesdatecreated']; ?>
: <?php echo $this->_tpl_vars['datecreated']; ?>
<br />
<?php echo $this->_tpl_vars['LANG']['invoicesdatedue']; ?>
: <?php echo $this->_tpl_vars['datedue']; ?>

</div>

<table class="items">
    <tr class="title textcenter">
        <td width="70%"><?php echo $this->_tpl_vars['LANG']['invoicesdescription']; ?>
</td>
        <td width="30%"><?php echo $this->_tpl_vars['LANG']['invoicesamount']; ?>
</td>
    </tr>
<?php $_from = $this->_tpl_vars['invoiceitems']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['item']):
?>
    <tr>
        <td><?php echo $this->_tpl_vars['item']['description']; ?>
<?php if ($this->_tpl_vars['item']['taxed'] == 'true'): ?> *<?php endif; ?></td>
        <td class="textcenter"><?php echo $this->_tpl_vars['item']['amount']; ?>
</td>
    </tr>
<?php endforeach; endif; unset($_from); ?>
    <tr class="title">
        <td class="textright"><?php echo $this->_tpl_vars['LANG']['invoicessubtotal']; ?>
:</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['subtotal']; ?>
</td>
    </tr>
    <?php if ($this->_tpl_vars['taxrate']): ?>
    <tr class="title">
        <td class="textright"><?php echo $this->_tpl_vars['taxrate']; ?>
% <?php echo $this->_tpl_vars['taxname']; ?>
:</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['tax']; ?>
</td>
    </tr>
    <?php endif; ?>
    <?php if ($this->_tpl_vars['taxrate2']): ?>
    <tr class="title">
        <td class="textright"><?php echo $this->_tpl_vars['taxrate2']; ?>
% <?php echo $this->_tpl_vars['taxname2']; ?>
:</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['tax2']; ?>
</td>
    </tr>
    <?php endif; ?>
    <tr class="title">
        <td class="textright"><?php echo $this->_tpl_vars['LANG']['invoicescredit']; ?>
:</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['credit']; ?>
</td>
    </tr>
    <tr class="title">
        <td class="textright"><?php echo $this->_tpl_vars['LANG']['invoicestotal']; ?>
:</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['total']; ?>
</td>
    </tr>
</table>

<?php if ($this->_tpl_vars['taxrate']): ?><p>* <?php echo $this->_tpl_vars['LANG']['invoicestaxindicator']; ?>
</p><?php endif; ?>

<div class="row">
<span class="subtitle"><?php echo $this->_tpl_vars['LANG']['invoicestransactions']; ?>
</span>
</div>

<table class="items">
    <tr class="title textcenter">
        <td width="30%"><?php echo $this->_tpl_vars['LANG']['invoicestransdate']; ?>
</td>
        <td width="25%"><?php echo $this->_tpl_vars['LANG']['invoicestransgateway']; ?>
</td>
        <td width="25%"><?php echo $this->_tpl_vars['LANG']['invoicestransid']; ?>
</td>
        <td width="20%"><?php echo $this->_tpl_vars['LANG']['invoicestransamount']; ?>
</td>
    </tr>
<?php $_from = $this->_tpl_vars['transactions']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['transaction']):
?>
    <tr>
        <td class="textcenter"><?php echo $this->_tpl_vars['transaction']['date']; ?>
</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['transaction']['gateway']; ?>
</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['transaction']['transid']; ?>
</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['transaction']['amount']; ?>
</td>
    </tr>
<?php endforeach; else: ?>
    <tr>
        <td class="textcenter" colspan="4"><?php echo $this->_tpl_vars['LANG']['invoicestransnonefound']; ?>
</td>
    </tr>
<?php endif; unset($_from); ?>
    <tr class="title">
        <td class="textright" colspan="3"><?php echo $this->_tpl_vars['LANG']['invoicesbalance']; ?>
:</td>
        <td class="textcenter"><?php echo $this->_tpl_vars['balance']; ?>
</td>
    </tr>
</table>

<?php if ($this->_tpl_vars['notes']): ?>
<p><?php echo $this->_tpl_vars['LANG']['invoicesnotes']; ?>
: <?php echo $this->_tpl_vars['notes']; ?>
</p>
<?php endif; ?>

<?php endif; ?>

</div>

<p align="center"><a href="clientarea.php"><?php echo $this->_tpl_vars['LANG']['invoicesbacktoclientarea']; ?>
</a> | <a href="dl.php?type=i&amp;id=<?php echo $this->_tpl_vars['invoiceid']; ?>
"><?php echo $this->_tpl_vars['LANG']['invoicesdownload']; ?>
</a> | <a href="javascript:window.close()"><?php echo $this->_tpl_vars['LANG']['closewindow']; ?>
</a></p>

</body>
</html>