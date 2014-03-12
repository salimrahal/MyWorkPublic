<?php /* Smarty version 2.6.27, created on 2013-09-02 11:46:48
         compiled from /home/salim/public_html/whmcs/templates/default/clientareahome.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'strip_tags', '/home/salim/public_html/whmcs/templates/default/clientareahome.tpl', 40, false),array('modifier', 'truncate', '/home/salim/public_html/whmcs/templates/default/clientareahome.tpl', 40, false),array('modifier', 'sprintf2', '/home/salim/public_html/whmcs/templates/default/clientareahome.tpl', 45, false),)), $this); ?>
<div class="row">

    <div class="col60">
        <div class="internalpadding">

            <div class="page-header">
                <div class="styled_title">
                    <h2><?php echo $this->_tpl_vars['LANG']['accountinfo']; ?>
 &nbsp;&nbsp;&nbsp;<small><a href="clientarea.php?action=details"><?php echo $this->_tpl_vars['LANG']['clientareaupdateyourdetails']; ?>
</a></small></h2>
                </div>
            </div>
            <p><strong><?php echo $this->_tpl_vars['clientsdetails']['firstname']; ?>
 <?php echo $this->_tpl_vars['clientsdetails']['lastname']; ?>
 <?php if ($this->_tpl_vars['clientsdetails']['companyname']): ?>(<?php echo $this->_tpl_vars['clientsdetails']['companyname']; ?>
)<?php endif; ?></strong></p>
            <p><?php echo $this->_tpl_vars['clientsdetails']['address1']; ?>
<?php if ($this->_tpl_vars['clientsdetails']['address2']): ?>, <?php echo $this->_tpl_vars['clientsdetails']['address2']; ?>
<?php endif; ?></p>
            <p><?php if ($this->_tpl_vars['clientsdetails']['city']): ?><?php echo $this->_tpl_vars['clientsdetails']['city']; ?>
, <?php endif; ?><?php if ($this->_tpl_vars['clientsdetails']['state']): ?><?php echo $this->_tpl_vars['clientsdetails']['state']; ?>
, <?php endif; ?><?php echo $this->_tpl_vars['clientsdetails']['postcode']; ?>
</p>
            <p><?php echo $this->_tpl_vars['clientsdetails']['countryname']; ?>
</p>
            <p><a href="mailto:<?php echo $this->_tpl_vars['clientsdetails']['email']; ?>
"><?php echo $this->_tpl_vars['clientsdetails']['email']; ?>
</a></p>

        </div>
    </div>
    <div class="col40">
        <div class="internalpadding">

            <div class="page-header">
                <div class="styled_title">
                    <h2><?php echo $this->_tpl_vars['LANG']['accountoverview']; ?>
</h2>
                </div>
            </div>

            <p><?php echo $this->_tpl_vars['LANG']['statsnumproducts']; ?>
: <a href="clientarea.php?action=products"><strong><?php echo $this->_tpl_vars['clientsstats']['productsnumactive']; ?>
</strong> (<?php echo $this->_tpl_vars['clientsstats']['productsnumtotal']; ?>
) - <?php echo $this->_tpl_vars['LANG']['view']; ?>
 &raquo;</a></p>
            <p><?php echo $this->_tpl_vars['LANG']['statsnumdomains']; ?>
: <a href="clientarea.php?action=domains"><strong><?php echo $this->_tpl_vars['clientsstats']['numactivedomains']; ?>
</strong> (<?php echo $this->_tpl_vars['clientsstats']['numdomains']; ?>
) - <?php echo $this->_tpl_vars['LANG']['view']; ?>
 &raquo;</a></p>
            <p><?php echo $this->_tpl_vars['LANG']['statsnumtickets']; ?>
: <a href="supporttickets.php"><strong><?php echo $this->_tpl_vars['clientsstats']['numtickets']; ?>
</strong> - <?php echo $this->_tpl_vars['LANG']['view']; ?>
 &raquo;</a></p>
            <p><?php echo $this->_tpl_vars['LANG']['statsnumreferredsignups']; ?>
: <a href="affiliates.php"><strong><?php echo $this->_tpl_vars['clientsstats']['numaffiliatesignups']; ?>
</strong> - <?php echo $this->_tpl_vars['LANG']['view']; ?>
 &raquo;</a></p>
            <p><?php echo $this->_tpl_vars['LANG']['creditcardyourinfo']; ?>
: <strong><?php if ($this->_tpl_vars['defaultpaymentmethod']): ?><?php echo $this->_tpl_vars['defaultpaymentmethod']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['paymentmethoddefault']; ?>
<?php endif; ?></strong> <?php if ($this->_tpl_vars['clientsdetails']['cctype']): ?>(<?php echo $this->_tpl_vars['clientsdetails']['cctype']; ?>
-<?php echo $this->_tpl_vars['clientsdetails']['cclastfour']; ?>
) - <a href="clientarea.php?action=creditcard">Update &raquo;</a></p><?php endif; ?>

        </div>
    </div>
</div>

<?php if ($this->_tpl_vars['announcements']): ?>
<div class="alert alert-warning">
    <p><strong><?php echo $this->_tpl_vars['LANG']['ourlatestnews']; ?>
:</strong> <?php echo ((is_array($_tmp=((is_array($_tmp=$this->_tpl_vars['announcements']['0']['text'])) ? $this->_run_mod_handler('strip_tags', true, $_tmp) : smarty_modifier_strip_tags($_tmp)))) ? $this->_run_mod_handler('truncate', true, $_tmp, 100, '...') : smarty_modifier_truncate($_tmp, 100, '...')); ?>
 - <a href="announcements.php?id=<?php echo $this->_tpl_vars['announcements']['0']['id']; ?>
" class="btn btn-mini"><?php echo $this->_tpl_vars['LANG']['more']; ?>
...</a></p>
</div>
<?php endif; ?>
<?php if ($this->_tpl_vars['ccexpiringsoon']): ?>
<div class="alert alert-error">
    <p><strong><?php echo $this->_tpl_vars['LANG']['ccexpiringsoon']; ?>
:</strong> <?php echo ((is_array($_tmp=$this->_tpl_vars['LANG']['ccexpiringsoondesc'])) ? $this->_run_mod_handler('sprintf2', true, $_tmp, '<a href="clientarea.php?action=creditcard" class="btn btn-mini">', '</a>') : smarty_modifier_sprintf2($_tmp, '<a href="clientarea.php?action=creditcard" class="btn btn-mini">', '</a>')); ?>
</p>
</div>
<?php endif; ?>
<?php if ($this->_tpl_vars['clientsstats']['incredit']): ?>
<div class="alert alert-success">
    <p><strong><?php echo $this->_tpl_vars['LANG']['availcreditbal']; ?>
:</strong> <?php echo ((is_array($_tmp=$this->_tpl_vars['LANG']['availcreditbaldesc'])) ? $this->_run_mod_handler('sprintf2', true, $_tmp, $this->_tpl_vars['clientsstats']['creditbalance']) : smarty_modifier_sprintf2($_tmp, $this->_tpl_vars['clientsstats']['creditbalance'])); ?>
</p>
</div>
<?php endif; ?>
<?php if ($this->_tpl_vars['clientsstats']['numoverdueinvoices'] > 0): ?>
<div class="alert alert-block alert-error">
    <p><strong><?php echo ((is_array($_tmp=$this->_tpl_vars['LANG']['youhaveoverdueinvoices'])) ? $this->_run_mod_handler('sprintf2', true, $_tmp, $this->_tpl_vars['clientsstats']['numoverdueinvoices']) : smarty_modifier_sprintf2($_tmp, $this->_tpl_vars['clientsstats']['numoverdueinvoices'])); ?>
:</strong> <?php echo ((is_array($_tmp=$this->_tpl_vars['LANG']['overdueinvoicesdesc'])) ? $this->_run_mod_handler('sprintf2', true, $_tmp, '<a href="clientarea.php?action=masspay&all=true" class="btn btn-mini btn-danger">', '</a>') : smarty_modifier_sprintf2($_tmp, '<a href="clientarea.php?action=masspay&all=true" class="btn btn-mini btn-danger">', '</a>')); ?>
</p>
</div>
<?php endif; ?>

<?php if ($this->_tpl_vars['condlinks']['domainreg'] || $this->_tpl_vars['condlinks']['domaintrans'] || $this->_tpl_vars['condlinks']['domainown']): ?>
<form method="post" action="domainchecker.php">
<div class="well textcenter">
    <div class="styled_title">
        <h3><?php echo $this->_tpl_vars['LANG']['domaincheckerchecknewdomain']; ?>
</h3>
    </div>
    <input class="bigfield" name="domain" type="text" value="<?php echo $this->_tpl_vars['LANG']['domaincheckerdomainexample']; ?>
" onfocus="if(this.value=='<?php echo $this->_tpl_vars['LANG']['domaincheckerdomainexample']; ?>
')this.value=''" onblur="if(this.value=='')this.value='<?php echo $this->_tpl_vars['LANG']['domaincheckerdomainexample']; ?>
'" />
    <div class="internalpadding">
        <?php if ($this->_tpl_vars['condlinks']['domainreg']): ?><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['checkavailability']; ?>
" class="btn btn-primary btn-large" /><?php endif; ?>
        <?php if ($this->_tpl_vars['condlinks']['domaintrans']): ?><input type="submit" name="transfer" value="<?php echo $this->_tpl_vars['LANG']['domainstransfer']; ?>
" class="btn btn-success btn-large" /><?php endif; ?>
        <?php if ($this->_tpl_vars['condlinks']['domainown']): ?><input type="submit" name="hosting" value="<?php echo $this->_tpl_vars['LANG']['domaincheckerhostingonly']; ?>
" class="btn btn-large" /><?php endif; ?>
    </div>
</div>
</form>
<?php endif; ?>

<?php $_from = $this->_tpl_vars['addons_html']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['addon_html']):
?>
<div style="margin:15px 0;"><?php echo $this->_tpl_vars['addon_html']; ?>
</div>
<?php endforeach; endif; unset($_from); ?>

<?php if (in_array ( 'tickets' , $this->_tpl_vars['contactpermissions'] )): ?>

<a class="btn pull-right" href="submitticket.php"><i class="icon-comment"></i> <?php echo $this->_tpl_vars['LANG']['opennewticket']; ?>
</a>
<div class="styled_title">
    <h3><?php echo $this->_tpl_vars['LANG']['supportticketsopentickets']; ?>
 <span class="badge badge-info"><?php echo $this->_tpl_vars['clientsstats']['numactivetickets']; ?>
</span></h3>
</div>

<br />

<table class="table table-striped table-framed table-centered">
    <thead>
        <tr>
            <th><a href="supporttickets.php?orderby=date"><?php echo $this->_tpl_vars['LANG']['supportticketsdate']; ?>
</a></th>
            <th><a href="supporttickets.php?orderby=dept"><?php echo $this->_tpl_vars['LANG']['supportticketsdepartment']; ?>
</a></th>
            <th><a href="supporttickets.php?orderby=subject"><?php echo $this->_tpl_vars['LANG']['supportticketssubject']; ?>
</a></th>
            <th><a href="supporttickets.php?orderby=status"><?php echo $this->_tpl_vars['LANG']['supportticketsstatus']; ?>
</a></th>
            <th class="headerSortdesc"><a href="supporttickets.php?orderby=lastreply"><?php echo $this->_tpl_vars['LANG']['supportticketsticketlastupdated']; ?>
</a></th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
<?php $_from = $this->_tpl_vars['tickets']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['ticket']):
?>
    <tr>
        <td><?php echo $this->_tpl_vars['ticket']['date']; ?>
</td>
        <td><?php echo $this->_tpl_vars['ticket']['department']; ?>
</td>
        <td><div align="left"><img src="images/article.gif" alt="Ticket" border="0" />&nbsp;<a href="viewticket.php?tid=<?php echo $this->_tpl_vars['ticket']['tid']; ?>
&amp;c=<?php echo $this->_tpl_vars['ticket']['c']; ?>
"><?php if ($this->_tpl_vars['ticket']['unread']): ?><strong><?php endif; ?>#<?php echo $this->_tpl_vars['ticket']['tid']; ?>
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
        <td colspan="6" class="textcenter"><?php echo $this->_tpl_vars['LANG']['supportticketsnoneopen']; ?>
</td>
    </tr>
<?php endif; unset($_from); ?>
    </tbody>
</table>

<?php endif; ?>

<?php if (in_array ( 'invoices' , $this->_tpl_vars['contactpermissions'] )): ?>

<div class="styled_title">
    <h3><?php echo $this->_tpl_vars['LANG']['invoicesdue']; ?>
 <span class="badge badge-important"><?php echo $this->_tpl_vars['clientsstats']['numdueinvoices']; ?>
</span></h3>
</div>

<br />

<form method="post" action="clientarea.php?action=masspay">

<table class="table table-striped table-framed table-centered">
    <thead>
        <tr>
            <?php if ($this->_tpl_vars['masspay']): ?><th class="textcenter"><input type="checkbox" onclick="toggleCheckboxes('invids')" /></th><?php endif; ?>
            <th class="headerSortdesc"><a href="clientarea.php?action=invoices&orderby=id"><?php echo $this->_tpl_vars['LANG']['invoicestitle']; ?>
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
            <th<?php if ($this->_tpl_vars['orderby'] == 'balance'): ?> class="headerSort<?php echo $this->_tpl_vars['sort']; ?>
"<?php endif; ?>><a href="clientarea.php?action=invoices&orderby=balance"><?php echo $this->_tpl_vars['LANG']['invoicesbalance']; ?>
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
            <?php if ($this->_tpl_vars['masspay']): ?><td class="textcenter"><input type="checkbox" name="invoiceids[]" value="<?php echo $this->_tpl_vars['invoice']['id']; ?>
" class="invids" /></td><?php endif; ?>
            <td><a href="viewinvoice.php?id=<?php echo $this->_tpl_vars['invoice']['id']; ?>
" target="_blank"><?php echo $this->_tpl_vars['invoice']['invoicenum']; ?>
</a></td>
            <td><?php echo $this->_tpl_vars['invoice']['datecreated']; ?>
</td>
            <td><?php echo $this->_tpl_vars['invoice']['datedue']; ?>
</td>
            <td><?php echo $this->_tpl_vars['invoice']['total']; ?>
</td>
            <td><?php echo $this->_tpl_vars['invoice']['balance']; ?>
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
            <td colspan="<?php if ($this->_tpl_vars['masspay']): ?>8<?php else: ?>7<?php endif; ?>" class="textcenter"><?php echo $this->_tpl_vars['LANG']['invoicesnoneunpaid']; ?>
</td>
        </tr>
<?php endif; unset($_from); ?>
<?php if ($this->_tpl_vars['invoices']): ?>
        <tr>
            <td colspan="<?php if ($this->_tpl_vars['masspay']): ?>4<?php else: ?>3<?php endif; ?>"><?php if ($this->_tpl_vars['masspay']): ?><div align="left"><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['masspayselected']; ?>
" class="btn" /> <a href="clientarea.php?action=masspay&all=true" class="btn btn-success"><i class="icon-ok-circle icon-white"></i> <?php echo $this->_tpl_vars['LANG']['masspayall']; ?>
</a></div><?php endif; ?></td>
            <td class="textright"><strong><?php echo $this->_tpl_vars['LANG']['invoicestotaldue']; ?>
</strong></td>
            <td><strong><?php echo $this->_tpl_vars['totalbalance']; ?>
</strong></td>
            <td colspan="2">&nbsp;</td>
        </tr>
<?php endif; ?>
    </tbody>
</table>

</form>

<?php endif; ?>

<?php if ($this->_tpl_vars['files']): ?>

<div class="styled_title">
    <h3><?php echo $this->_tpl_vars['LANG']['clientareafiles']; ?>
</h3>
</div>

<div class="row">
<div class="control-group">
<?php $_from = $this->_tpl_vars['files']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['file']):
?>
    <div class="col4">
        <div class="internalpadding">
            <img src="images/file.png" /> <a href="dl.php?type=f&id=<?php echo $this->_tpl_vars['file']['id']; ?>
" class="fontsize2"><strong><?php echo $this->_tpl_vars['file']['title']; ?>
</strong></a><br />
            <?php echo $this->_tpl_vars['LANG']['clientareafilesdate']; ?>
: <?php echo $this->_tpl_vars['file']['date']; ?>

        </div>
    </div>
<?php endforeach; endif; unset($_from); ?>
</div>
</div>

<?php endif; ?>