<?php /* Smarty version 2.6.27, created on 2013-08-27 09:08:01
         compiled from /home/salim/public_html/billing/templates/default/supportticketslist.tpl */ ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['clientareanavsupporttickets'],'desc' => $this->_tpl_vars['LANG']['supportticketsintro'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="searchbox">
    <form method="post" action="supporttickets.php">
        <div class="input-append">
            <input type="text" name="searchterm" value="<?php if ($this->_tpl_vars['q']): ?><?php echo $this->_tpl_vars['q']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['searchtickets']; ?>
<?php endif; ?>" class="input-medium appendedInputButton" onfocus="if(this.value=='<?php echo $this->_tpl_vars['LANG']['searchtickets']; ?>
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
<table class="table table-striped table-framed table-centered">
    <thead>
        <tr>
            <th<?php if ($this->_tpl_vars['orderby'] == 'date'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="supporttickets.php?orderby=date"><?php echo $this->_tpl_vars['LANG']['supportticketsdate']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'dept'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="supporttickets.php?orderby=dept"><?php echo $this->_tpl_vars['LANG']['supportticketsdepartment']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'subject'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="supporttickets.php?orderby=subject"><?php echo $this->_tpl_vars['LANG']['supportticketssubject']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'status'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="supporttickets.php?orderby=status"><?php echo $this->_tpl_vars['LANG']['supportticketsstatus']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'lastreply'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="supporttickets.php?orderby=lastreply"><?php echo $this->_tpl_vars['LANG']['supportticketsticketlastupdated']; ?>
</a></th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
<?php $_from = $this->_tpl_vars['tickets']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['ticket']):
?>
        <tr>
            <td><?php echo $this->_tpl_vars['ticket']['date']; ?>
</td>
            <td><?php echo $this->_tpl_vars['ticket']['department']; ?>
</td>
            <td><div align="left"><a href="viewticket.php?tid=<?php echo $this->_tpl_vars['ticket']['tid']; ?>
&amp;c=<?php echo $this->_tpl_vars['ticket']['c']; ?>
"><img src="images/article.gif" alt="" border="0" /> <?php if ($this->_tpl_vars['ticket']['unread']): ?><strong><?php endif; ?>#<?php echo $this->_tpl_vars['ticket']['tid']; ?>
 - <?php echo $this->_tpl_vars['ticket']['subject']; ?>
<?php if ($this->_tpl_vars['ticket']['unread']): ?></strong><?php endif; ?></a></div></td>
            <td><?php echo $this->_tpl_vars['ticket']['status']; ?>
</td>
            <td><?php echo $this->_tpl_vars['ticket']['lastreply']; ?>
</td>
            <td class="textcenter"><a href="viewticket.php?tid=<?php echo $this->_tpl_vars['ticket']['tid']; ?>
&c=<?php echo $this->_tpl_vars['ticket']['c']; ?>
" class="btn btn-inverse"><?php echo $this->_tpl_vars['LANG']['supportticketsviewticket']; ?>
</a></td>
        </tr>
<?php endforeach; else: ?>
        <tr>
            <td colspan="7" class="textcenter"><?php echo $this->_tpl_vars['LANG']['norecordsfound']; ?>
</td>
        </tr>
<?php endif; unset($_from); ?>
    </tbody>
</table>

<div class="recordslimit">
    <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
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
        <li class="prev<?php if (! $this->_tpl_vars['prevpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['prevpage']): ?>supporttickets.php?page=<?php echo $this->_tpl_vars['prevpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>">&larr; <?php echo $this->_tpl_vars['LANG']['previouspage']; ?>
</a></li>
        <li class="next<?php if (! $this->_tpl_vars['nextpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['nextpage']): ?>supporttickets.php?page=<?php echo $this->_tpl_vars['nextpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>"><?php echo $this->_tpl_vars['LANG']['nextpage']; ?>
 &rarr;</a></li>
    </ul>
</div>