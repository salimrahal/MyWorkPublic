<?php /* Smarty version 2.6.27, created on 2013-08-30 13:42:55
         compiled from /home/www/whmcs/templates/default/clientareaproducts.tpl */ ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['clientareaproducts'],'desc' => $this->_tpl_vars['LANG']['clientareaproductsintro'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="searchbox">
    <form method="post" action="clientarea.php?action=products">
        <div class="input-append">
            <input type="text" name="q" value="<?php if ($this->_tpl_vars['q']): ?><?php echo $this->_tpl_vars['q']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['searchenterdomain']; ?>
<?php endif; ?>" class="input-medium appendedInputButton" onfocus="if(this.value=='<?php echo $this->_tpl_vars['LANG']['searchenterdomain']; ?>
')this.value=''" /><button type="submit" class="btn btn-info"><?php echo $this->_tpl_vars['LANG']['searchfilter']; ?>
</button>
        </div>
    </form>
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

<table class="table table-striped table-framed">
    <thead>
        <tr>
            <th<?php if ($this->_tpl_vars['orderby'] == 'product'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=product"><?php echo $this->_tpl_vars['LANG']['orderproduct']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'price'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=price"><?php echo $this->_tpl_vars['LANG']['orderprice']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'billingcycle'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=billingcycle"><?php echo $this->_tpl_vars['LANG']['orderbillingcycle']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'nextduedate'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=nextduedate"><?php echo $this->_tpl_vars['LANG']['clientareahostingnextduedate']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'status'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=status"><?php echo $this->_tpl_vars['LANG']['clientareastatus']; ?>
</a></th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
<?php $_from = $this->_tpl_vars['services']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['service']):
?>
        <tr>
            <td><strong><?php echo $this->_tpl_vars['service']['group']; ?>
 - <?php echo $this->_tpl_vars['service']['product']; ?>
</strong><?php if ($this->_tpl_vars['service']['domain']): ?><br /><a href="http://<?php echo $this->_tpl_vars['service']['domain']; ?>
" target="_blank"><?php echo $this->_tpl_vars['service']['domain']; ?>
</a><?php endif; ?></td>
            <td><?php echo $this->_tpl_vars['service']['amount']; ?>
</td>
            <td><?php echo $this->_tpl_vars['service']['billingcycle']; ?>
</td>
            <td><?php echo $this->_tpl_vars['service']['nextduedate']; ?>
</td>
            <td><span class="label <?php echo $this->_tpl_vars['service']['rawstatus']; ?>
"><?php echo $this->_tpl_vars['service']['statustext']; ?>
</span></td>
            <td>
                <div class="btn-group">
                <a class="btn" href="clientarea.php?action=productdetails&id=<?php echo $this->_tpl_vars['service']['id']; ?>
"> <i class="icon icon-list-alt"></i> <?php echo $this->_tpl_vars['LANG']['clientareaviewdetails']; ?>
</a>
                <?php if ($this->_tpl_vars['service']['rawstatus'] == 'active'): ?>
                <a class="btn dropdown-toggle" href="#" data-toggle="dropdown"><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <?php if ($this->_tpl_vars['service']['downloads']): ?> <li><a href="clientarea.php?action=productdetails&id=<?php echo $this->_tpl_vars['service']['id']; ?>
#tab3"><i class="icon-download"></i> <?php echo $this->_tpl_vars['LANG']['downloadstitle']; ?>
</a></li><?php endif; ?>
                    <?php if ($this->_tpl_vars['service']['addons']): ?> <li><a href="clientarea.php?action=productdetails&id=<?php echo $this->_tpl_vars['service']['id']; ?>
#tab4"><i class="icon-th-large"></i> <?php echo $this->_tpl_vars['LANG']['clientareahostingaddons']; ?>
</a></li><?php endif; ?>
                    <?php if ($this->_tpl_vars['service']['packagesupgrade']): ?> <li><a href="upgrade.php?type=package&id=<?php echo $this->_tpl_vars['service']['id']; ?>
#tab3"><i class="icon-resize-vertical"></i> <?php echo $this->_tpl_vars['LANG']['upgradedowngradepackage']; ?>
</a></li><?php endif; ?>
                    <?php if (( $this->_tpl_vars['service']['addons'] || $this->_tpl_vars['service']['downloads'] || $this->_tpl_vars['service']['packagesupgrade'] ) && $this->_tpl_vars['service']['showcancelbutton']): ?> <li class="divider"></li><?php endif; ?>
                    <?php if ($this->_tpl_vars['service']['showcancelbutton']): ?> <li><a href="clientarea.php?action=cancel&id=<?php echo $this->_tpl_vars['service']['id']; ?>
"><i class="icon-off"></i> <?php echo $this->_tpl_vars['LANG']['clientareacancelrequestbutton']; ?>
</a></li><?php endif; ?>
                </ul>
                <?php endif; ?>
                </div>
            </td>
        </tr>
<?php endforeach; else: ?>
        <tr>
            <td colspan="6" class="textcenter"><?php echo $this->_tpl_vars['LANG']['norecordsfound']; ?>
</td>
        </tr>
<?php endif; unset($_from); ?>
    </tbody>
</table>

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
        <li class="prev<?php if (! $this->_tpl_vars['prevpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['prevpage']): ?>clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&amp;page=<?php echo $this->_tpl_vars['prevpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>">&larr; <?php echo $this->_tpl_vars['LANG']['previouspage']; ?>
</a></li>
        <li class="next<?php if (! $this->_tpl_vars['nextpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['nextpage']): ?>clientarea.php?action=products<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&amp;page=<?php echo $this->_tpl_vars['nextpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>"><?php echo $this->_tpl_vars['LANG']['nextpage']; ?>
 &rarr;</a></li>
    </ul>
</div>