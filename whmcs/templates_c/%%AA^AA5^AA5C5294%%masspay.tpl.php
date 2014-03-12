<?php /* Smarty version 2.6.27, created on 2013-08-27 08:56:51
         compiled from /home/salim/public_html/billing/templates/default/masspay.tpl */ ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['masspaytitle'],'desc' => $this->_tpl_vars['LANG']['masspayintro'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<form method="post" action="clientarea.php?action=masspay" class="form-horizontal">
<input type="hidden" name="geninvoice" value="true" />

<br />

<div class="center80">
<table class="table table-striped table-framed">
    <thead>
        <tr>
            <th><?php echo $this->_tpl_vars['LANG']['invoicesdescription']; ?>
</th>
            <th><?php echo $this->_tpl_vars['LANG']['invoicesamount']; ?>
</th>
        </tr>
    </thead>
    <tbody>
<?php $_from = $this->_tpl_vars['invoiceitems']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['invid'] => $this->_tpl_vars['invoiceitem']):
?>
        <tr>
            <td colspan="2">
                <strong><?php echo $this->_tpl_vars['LANG']['invoicenumber']; ?>
 <?php echo $this->_tpl_vars['invid']; ?>
</strong>
                <input type="hidden" name="invoiceids[]" value="<?php echo $this->_tpl_vars['invid']; ?>
" />
            </td>
        </tr>
<?php $_from = $this->_tpl_vars['invoiceitem']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['item']):
?>
        <tr>
            <td><?php echo $this->_tpl_vars['item']['description']; ?>
</td>
            <td><?php echo $this->_tpl_vars['item']['amount']; ?>
</td>
        </tr>
<?php endforeach; endif; unset($_from); ?>
<?php endforeach; else: ?>
        <tr>
            <td colspan="6" align="center"><?php echo $this->_tpl_vars['LANG']['norecordsfound']; ?>
</td>
        </tr>
<?php endif; unset($_from); ?>
        <tr class="subtotal">
            <td style="text-align:right;"><?php echo $this->_tpl_vars['LANG']['invoicessubtotal']; ?>
:</td>
            <td><?php echo $this->_tpl_vars['subtotal']; ?>
</td>
        </tr>
        <?php if ($this->_tpl_vars['tax']): ?><tr class="tax">
            <td style="text-align:right;"><?php echo $this->_tpl_vars['LANG']['invoicestax']; ?>
:</td>
            <td><?php echo $this->_tpl_vars['tax']; ?>
</td>
        </tr><?php endif; ?>
        <?php if ($this->_tpl_vars['tax2']): ?><tr class="tax">
            <td style="text-align:right;"><?php echo $this->_tpl_vars['LANG']['invoicestax']; ?>
 2:</td>
            <td><?php echo $this->_tpl_vars['tax2']; ?>
</td>
        </tr><?php endif; ?>
        <?php if ($this->_tpl_vars['credit']): ?><tr class="credit">
            <td style="text-align:right;"><?php echo $this->_tpl_vars['LANG']['invoicescredit']; ?>
:</td>
            <td><?php echo $this->_tpl_vars['credit']; ?>
</td>
        </tr><?php endif; ?>
        <?php if ($this->_tpl_vars['partialpayments']): ?><tr class="credit">
            <td style="text-align:right;"><?php echo $this->_tpl_vars['LANG']['invoicespartialpayments']; ?>
:</td>
            <td><?php echo $this->_tpl_vars['partialpayments']; ?>
</td>
        </tr><?php endif; ?>
        <tr class="total">
            <td style="text-align:right;"><?php echo $this->_tpl_vars['LANG']['invoicestotaldue']; ?>
:</td>
            <td><?php echo $this->_tpl_vars['total']; ?>
</td>
        </tr>
    </tbody>
</table>
</div>

<div class="center80">

<h3><?php echo $this->_tpl_vars['LANG']['orderpaymentmethod']; ?>
</h3>

<?php $_from = $this->_tpl_vars['gateways']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['gateway']):
?>
<label class="radio inline">
<input type="radio" class="radio inline" name="paymentmethod" value="<?php echo $this->_tpl_vars['gateway']['sysname']; ?>
"<?php if ($this->_tpl_vars['gateway']['sysname'] == $this->_tpl_vars['defaultgateway']): ?> checked<?php endif; ?> /> <?php echo $this->_tpl_vars['gateway']['name']; ?>

</label>
<?php endforeach; endif; unset($_from); ?>

<br />
<br />

<p align="center"><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['masspaymakepayment']; ?>
" class="btn btn-success btn-large" /></p>

</div>

<br />
<br />
<br />

</form>