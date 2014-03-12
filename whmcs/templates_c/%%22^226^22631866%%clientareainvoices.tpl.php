<?php /* Smarty version 2.6.27, created on 2013-09-02 11:47:04
         compiled from /home/salim/public_html/whmcs/templates/default/clientareainvoices.tpl */ ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['invoices'],'desc' => $this->_tpl_vars['LANG']['invoicesintro'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="searchbox">
<span class="invoicetotal"><?php echo $this->_tpl_vars['LANG']['invoicesoutstandingbalance']; ?>
: <span class="text<?php if ($this->_tpl_vars['nobalance']): ?>green<?php else: ?>red<?php endif; ?>"><?php echo $this->_tpl_vars['totalbalance']; ?>
</span></span><?php if ($this->_tpl_vars['masspay']): ?>&nbsp; <a href="clientarea.php?action=masspay&all=true" class="btn btn-success"><i class="icon-ok-circle icon-white"></i> <?php echo $this->_tpl_vars['LANG']['masspayall']; ?>
</a><?php endif; ?>
</div>

<div class="resultsbox">
<p><?php echo $this->_tpl_vars['numitems']; ?>
 <?php echo $this->_tpl_vars['LANG']['recordsfound']; ?>
, <?php echo $this->_tpl_vars['LANG']['page']; ?>
 <?php echo $this->_tpl_vars['pagenumber']; ?>
 <?php echo $this->_tpl_vars['LANG']['pageof']; ?>
 <?php echo $this->_tpl_vars['totalpages']; ?>
</p>
</div>

<div class="clear"></div>

<form method="post" action="clientarea.php?action=masspay">
<table class="table table-striped table-framed table-centered">
    <thead>
        <tr>
            <th<?php if ($this->_tpl_vars['orderby'] == 'id'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=invoices&orderby=id"><?php echo $this->_tpl_vars['LANG']['invoicestitle']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'date'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=invoices&orderby=date"><?php echo $this->_tpl_vars['LANG']['invoicesdatecreated']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'duedate'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=invoices&orderby=duedate"><?php echo $this->_tpl_vars['LANG']['invoicesdatedue']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'total'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=invoices&orderby=total"><?php echo $this->_tpl_vars['LANG']['invoicestotal']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'status'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=invoices&orderby=status"><?php echo $this->_tpl_vars['LANG']['invoicesstatus']; ?>
</a></th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
<?php $_from = $this->_tpl_vars['invoices']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['invoice']):
?>
        <tr>
            <td><a href="viewinvoice.php?id=<?php echo $this->_tpl_vars['invoice']['id']; ?>
" target="_blank"><strong><?php echo $this->_tpl_vars['invoice']['invoicenum']; ?>
</strong></a></td>
            <td><?php echo $this->_tpl_vars['invoice']['datecreated']; ?>
</td>
            <td><?php echo $this->_tpl_vars['invoice']['datedue']; ?>
</td>
            <td><?php echo $this->_tpl_vars['invoice']['total']; ?>
</td>
            <td><span class="label <?php echo $this->_tpl_vars['invoice']['rawstatus']; ?>
"><?php echo $this->_tpl_vars['invoice']['statustext']; ?>
</span></td>
            <td class="textcenter"><a href="viewinvoice.php?id=<?php echo $this->_tpl_vars['invoice']['id']; ?>
" target="_blank" class="btn"><?php echo $this->_tpl_vars['LANG']['invoicesview']; ?>
</a></td>
        </tr>
<?php endforeach; else: ?>
        <tr>
            <td colspan="6" class="textcenter"><?php echo $this->_tpl_vars['LANG']['norecordsfound']; ?>
</td>
        </tr>
<?php endif; unset($_from); ?>
    </tbody>
</table>
</form>
<div class="recordslimit">
    <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?action=<?php echo $this->_tpl_vars['clientareaaction']; ?>
" />
    <select name="itemlimit" onchange="submit()">
        <option><?php echo $this->_tpl_vars['LANG']['resultsperpage']; ?>
</option>
        <option value="10"<?php if ($this->_tpl_vars['itemlimit'] == 10): ?> selected<?php endif; ?>>10</option>
        <option value="25"<?php if ($this->_tpl_vars['itemlimit'] == 25): ?> selected<?php endif; ?>>25</option>
        <option value="50"<?php if ($this->_tpl_vars['itemlimit'] == 50): ?> selected<?php endif; ?>>50</option>
        <option value="100"<?php if ($this->_tpl_vars['itemlimit'] == 100): ?> selected<?php endif; ?>>100</option>
        <option value="all"<?php if ($this->_tpl_vars['itemlimit'] == 99999999): ?> selected<?php endif; ?>><?php echo $this->_tpl_vars['LANG']['clientareaunlimited']; ?>
</option>
    </select>
    </form>
</div>

<div class="pagination">
    <ul>
        <li class="prev<?php if (! $this->_tpl_vars['prevpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['prevpage']): ?>clientarea.php?action=invoices<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&amp;page=<?php echo $this->_tpl_vars['prevpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>">&larr; <?php echo $this->_tpl_vars['LANG']['previouspage']; ?>
</a></li>
        <li class="next<?php if (! $this->_tpl_vars['nextpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['nextpage']): ?>clientarea.php?action=invoices<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&amp;page=<?php echo $this->_tpl_vars['nextpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>"><?php echo $this->_tpl_vars['LANG']['nextpage']; ?>
 &rarr;</a></li>
    </ul>
</div>