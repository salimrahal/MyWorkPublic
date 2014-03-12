<?php /* Smarty version 2.6.27, created on 2013-08-27 08:56:43
         compiled from /home/salim/public_html/billing/templates/default/clientareadomains.tpl */ ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['clientareanavdomains'],'desc' => $this->_tpl_vars['LANG']['clientareadomainsintro'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="searchbox">
    <form method="post" action="clientarea.php?action=domains">
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

<div class="clear"></div>
<?php echo '
<script>
$(document).ready(function() {
	$(".setbulkaction").click(function(event) {
	  event.preventDefault();
	  $("#bulkaction").val($(this).attr(\'id\'));
	  $("#bulkactionform").submit();
	});
});
</script>
'; ?>

<form method="post" id="bulkactionform" action="clientarea.php?action=bulkdomain">
<input id="bulkaction" name="update" type="hidden" />

<table class="table table-striped table-framed">
    <thead>
        <tr>
            <th class="textcenter"><input type="checkbox" onclick="toggleCheckboxes('domids')" /></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'domain'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=domain"><?php echo $this->_tpl_vars['LANG']['clientareahostingdomain']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'regdate'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=regdate"><?php echo $this->_tpl_vars['LANG']['clientareahostingregdate']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'nextduedate'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=nextduedate"><?php echo $this->_tpl_vars['LANG']['clientareahostingnextduedate']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'status'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=status"><?php echo $this->_tpl_vars['LANG']['clientareastatus']; ?>
</a></th>
            <th<?php if ($this->_tpl_vars['orderby'] == 'autorenew'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&orderby=autorenew"><?php echo $this->_tpl_vars['LANG']['domainsautorenew']; ?>
</a></th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
<?php $_from = $this->_tpl_vars['domains']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['domain']):
?>
        <tr>
            <td class="textcenter"><input type="checkbox" name="domids[]" class="domids" value="<?php echo $this->_tpl_vars['domain']['id']; ?>
" /></td>
            <td><a href="http://<?php echo $this->_tpl_vars['domain']['domain']; ?>
/" target="_blank"><?php echo $this->_tpl_vars['domain']['domain']; ?>
</a></td>
            <td><?php echo $this->_tpl_vars['domain']['registrationdate']; ?>
</td>
            <td><?php echo $this->_tpl_vars['domain']['nextduedate']; ?>
</td>
            <td><span class="label <?php echo $this->_tpl_vars['domain']['rawstatus']; ?>
"><?php echo $this->_tpl_vars['domain']['statustext']; ?>
</span></td>
            <td><?php if ($this->_tpl_vars['domain']['autorenew']): ?><?php echo $this->_tpl_vars['LANG']['domainsautorenewenabled']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['domainsautorenewdisabled']; ?>
<?php endif; ?></td>
            <td>
                <div class="btn-group">
                <a class="btn" href="clientarea.php?action=domaindetails&id=<?php echo $this->_tpl_vars['domain']['id']; ?>
"> <i class="icon-wrench"></i> <?php echo $this->_tpl_vars['LANG']['managedomain']; ?>
</a>
                <?php if ($this->_tpl_vars['domain']['rawstatus'] == 'active'): ?>
                <a class="btn dropdown-toggle" href="#" data-toggle="dropdown"><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="clientarea.php?action=domaindetails&id=<?php echo $this->_tpl_vars['domain']['id']; ?>
#tab3"><i class="icon-globe"></i> <?php echo $this->_tpl_vars['LANG']['domainmanagens']; ?>
</a></li>
                    <li><a href="clientarea.php?action=domaincontacts&domainid=<?php echo $this->_tpl_vars['domain']['id']; ?>
"><i class="icon-user"></i> <?php echo $this->_tpl_vars['LANG']['domaincontactinfoedit']; ?>
</a></li>
                    <li><a href="clientarea.php?action=domaindetails&id=<?php echo $this->_tpl_vars['domain']['id']; ?>
#tab2"><i class="icon-globe"></i> <?php echo $this->_tpl_vars['LANG']['domainautorenewstatus']; ?>
</a></li>
                    <li class="divider"></li>
                    <li><a href="clientarea.php?action=domaindetails&id=<?php echo $this->_tpl_vars['domain']['id']; ?>
"><i class="icon-pencil"></i> <?php echo $this->_tpl_vars['LANG']['managedomain']; ?>
</a></li>
                </ul>
                <?php endif; ?>
                </div>
            </td>
        </tr>
<?php endforeach; else: ?>
        <tr>
            <td colspan="7" class="textcenter"><?php echo $this->_tpl_vars['LANG']['norecordsfound']; ?>
</td>
        </tr>
<?php endif; unset($_from); ?>
    </tbody>
</table>

<div class="btn-group">
<a class="btn btn-inverse" href="#" data-toggle="dropdown"><i class="icon-folder-open icon-white"></i> <?php echo $this->_tpl_vars['LANG']['withselected']; ?>
</a>
<a class="btn btn-inverse dropdown-toggle" href="#" data-toggle="dropdown"><span class="caret"></span></a>
<ul class="dropdown-menu">
    <li><a href="#" id="nameservers" class="setbulkaction"><i class="icon-globe"></i> <?php echo $this->_tpl_vars['LANG']['domainmanagens']; ?>
</a></li>
    <li><a href="#" id="autorenew" class="setbulkaction"><i class="icon-refresh"></i> <?php echo $this->_tpl_vars['LANG']['domainautorenewstatus']; ?>
</a></li>
    <li><a href="#" id="reglock" class="setbulkaction"><i class="icon-lock"></i> <?php echo $this->_tpl_vars['LANG']['domainreglockstatus']; ?>
</a></li>
    <li><a href="#" id="contactinfo" class="setbulkaction"><i class="icon-user"></i> <?php echo $this->_tpl_vars['LANG']['domaincontactinfoedit']; ?>
</a></li>
    <li><a href="#" id="renew" class="setbulkaction"><i class="icon-repeat"></i> <?php echo $this->_tpl_vars['LANG']['domainmassrenew']; ?>
</a></li>
</ul>
</div></form>

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
        <li class="prev<?php if (! $this->_tpl_vars['prevpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['prevpage']): ?>clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&amp;page=<?php echo $this->_tpl_vars['prevpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>">&larr; <?php echo $this->_tpl_vars['LANG']['previouspage']; ?>
</a></li>
        <li class="next<?php if (! $this->_tpl_vars['nextpage']): ?> disabled<?php endif; ?>"><a href="<?php if ($this->_tpl_vars['nextpage']): ?>clientarea.php?action=domains<?php if ($this->_tpl_vars['q']): ?>&q=<?php echo $this->_tpl_vars['q']; ?>
<?php endif; ?>&amp;page=<?php echo $this->_tpl_vars['nextpage']; ?>
<?php else: ?>javascript:return false;<?php endif; ?>"><?php echo $this->_tpl_vars['LANG']['nextpage']; ?>
 &rarr;</a></li>
    </ul>
</div>

</form>

<br />
<br />