<?php /* Smarty version 2.6.27, created on 2013-09-02 11:10:48
         compiled from blend/menu.tpl */ ?>
<div class="navigation">
<ul id="menu">
<li><a <?php if (in_array ( 'List Clients' , $this->_tpl_vars['admin_perms'] )): ?>href="clients.php"<?php endif; ?> title="Clients"><?php echo $this->_tpl_vars['_ADMINLANG']['clients']['title']; ?>
</a>
  <ul>
    <?php if (in_array ( 'List Clients' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="clients.php"><?php echo $this->_tpl_vars['_ADMINLANG']['clients']['viewsearch']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Add New Client' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="clientsadd.php"><?php echo $this->_tpl_vars['_ADMINLANG']['clients']['addnew']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'List Services' , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="clientshostinglist.php"><?php echo $this->_tpl_vars['_ADMINLANG']['services']['title']; ?>
</a>
        <ul>
        <li><a href="clientshostinglist.php?listtype=hostingaccount">- <?php echo $this->_tpl_vars['_ADMINLANG']['services']['listhosting']; ?>
</a></li>
        <li><a href="clientshostinglist.php?listtype=reselleraccount">- <?php echo $this->_tpl_vars['_ADMINLANG']['services']['listreseller']; ?>
</a></li>
        <li><a href="clientshostinglist.php?listtype=server">- <?php echo $this->_tpl_vars['_ADMINLANG']['services']['listservers']; ?>
</a></li>
        <li><a href="clientshostinglist.php?listtype=other">- <?php echo $this->_tpl_vars['_ADMINLANG']['services']['listother']; ?>
</a></li>
        </ul>
    </li>
    <?php endif; ?>
    <?php if (in_array ( 'List Addons' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="clientsaddonslist.php"><?php echo $this->_tpl_vars['_ADMINLANG']['services']['listaddons']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'List Domains' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="clientsdomainlist.php"><?php echo $this->_tpl_vars['_ADMINLANG']['services']['listdomains']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'View Cancellation Requests' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="cancelrequests.php"><?php echo $this->_tpl_vars['_ADMINLANG']['clients']['cancelrequests']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Manage Affiliates' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="affiliates.php"><?php echo $this->_tpl_vars['_ADMINLANG']['affiliates']['manage']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Mass Mail' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="massmail.php"><?php echo $this->_tpl_vars['_ADMINLANG']['clients']['massmail']; ?>
</a></li><?php endif; ?>
  </ul>
</li>
<li><a <?php if (in_array ( 'View Orders' , $this->_tpl_vars['admin_perms'] )): ?>href="orders.php"<?php endif; ?> title="Orders"><?php echo $this->_tpl_vars['_ADMINLANG']['orders']['title']; ?>
</a>
  <ul>
    <?php if (in_array ( 'View Orders' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="orders.php"><?php echo $this->_tpl_vars['_ADMINLANG']['orders']['listall']; ?>
</a></li>
    <li><a href="orders.php?status=Pending">- <?php echo $this->_tpl_vars['_ADMINLANG']['orders']['listpending']; ?>
</a></li>
    <li><a href="orders.php?status=Active">- <?php echo $this->_tpl_vars['_ADMINLANG']['orders']['listactive']; ?>
</a></li>
    <li><a href="orders.php?status=Fraud">- <?php echo $this->_tpl_vars['_ADMINLANG']['orders']['listfraud']; ?>
</a></li>
    <li><a href="orders.php?status=Cancelled">- <?php echo $this->_tpl_vars['_ADMINLANG']['orders']['listcancelled']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Add New Order' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="ordersadd.php"><?php echo $this->_tpl_vars['_ADMINLANG']['orders']['addnew']; ?>
</a></li><?php endif; ?>
  </ul>
</li>
<li><a <?php if (in_array ( 'List Transactions' , $this->_tpl_vars['admin_perms'] )): ?>href="transactions.php"<?php endif; ?> title="Billing"><?php echo $this->_tpl_vars['_ADMINLANG']['billing']['title']; ?>
</a>
  <ul>
    <?php if (in_array ( 'List Transactions' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="transactions.php"><?php echo $this->_tpl_vars['_ADMINLANG']['billing']['transactionslist']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'List Invoices' , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="invoices.php"><?php echo $this->_tpl_vars['_ADMINLANG']['invoices']['title']; ?>
</a>
        <ul>
        <li><a href="invoices.php?status=Paid">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['paid']; ?>
</a></li>
        <li><a href="invoices.php?status=Unpaid">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['unpaid']; ?>
</a></li>
        <li><a href="invoices.php?status=Overdue">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['overdue']; ?>
</a></li>
        <li><a href="invoices.php?status=Cancelled">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['cancelled']; ?>
</a></li>
        <li><a href="invoices.php?status=Refunded">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['refunded']; ?>
</a></li>
        <li><a href="invoices.php?status=Collections">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['collections']; ?>
</a></li>
        </ul>
    </li><?php endif; ?>
    <?php if (in_array ( 'View Billable Items' , $this->_tpl_vars['admin_perms'] )): ?><li class="expand"><a href="billableitems.php"><?php echo $this->_tpl_vars['_ADMINLANG']['billableitems']['title']; ?>
</a>
        <ul>
        <li><a href="billableitems.php?status=Uninvoiced">- <?php echo $this->_tpl_vars['_ADMINLANG']['billableitems']['uninvoiced']; ?>
</a></li>
        <li><a href="billableitems.php?status=Recurring">- <?php echo $this->_tpl_vars['_ADMINLANG']['billableitems']['recurring']; ?>
</a></li>
        <?php if (in_array ( 'Manage Billable Items' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="billableitems.php?action=manage">- <?php echo $this->_tpl_vars['_ADMINLANG']['billableitems']['addnew']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
    <?php if (in_array ( 'Manage Quotes' , $this->_tpl_vars['admin_perms'] )): ?><li class="expand"><a href="quotes.php"><?php echo $this->_tpl_vars['_ADMINLANG']['quotes']['title']; ?>
</a>
        <ul>
        <li><a href="quotes.php?validity=Valid">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['valid']; ?>
</a></li>
        <li><a href="quotes.php?validity=Expired">- <?php echo $this->_tpl_vars['_ADMINLANG']['status']['expired']; ?>
</a></li>
        <li><a href="quotes.php?action=manage">- <?php echo $this->_tpl_vars['_ADMINLANG']['quotes']['createnew']; ?>
</a></li>
        </ul>
    </li><?php endif; ?>
    <?php if (in_array ( 'Offline Credit Card Processing' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="offlineccprocessing.php"><?php echo $this->_tpl_vars['_ADMINLANG']['billing']['offlinecc']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'View Gateway Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="gatewaylog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['billing']['gatewaylog']; ?>
</a></li><?php endif; ?>
  </ul>
</li>
<li><a <?php if (in_array ( 'Support Center Overview' , $this->_tpl_vars['admin_perms'] )): ?>href="supportcenter.php"<?php endif; ?> title="Support"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['title']; ?>
</a>
  <ul>
    <?php if (in_array ( 'Support Center Overview' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="supportcenter.php"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['supportoverview']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Manage Announcements' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="supportannouncements.php"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['announcements']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Manage Downloads' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="supportdownloads.php"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['downloads']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Manage Knowledgebase' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="supportkb.php"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['knowledgebase']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'List Support Tickets' , $this->_tpl_vars['admin_perms'] )): ?><li class="expand"><a href="supporttickets.php"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['supporttickets']; ?>
</a>
        <ul>
        <li><a href="supporttickets.php?view=flagged">- <?php echo $this->_tpl_vars['_ADMINLANG']['support']['flagged']; ?>
</a></li>
        <li><a href="supporttickets.php?view=active">- <?php echo $this->_tpl_vars['_ADMINLANG']['support']['allactive']; ?>
</a></li>
        <li><a href="supporttickets.php?view=Open">- Open</a></li>
        <li><a href="supporttickets.php?view=Answered">- Answered</a></li>
        <li><a href="supporttickets.php?view=Customer-Reply">- Customer-Reply</a></li>
        <li><a href="supporttickets.php?view=On Hold">- On Hold</a></li>
        <li><a href="supporttickets.php?view=In Progress">- In Progress</a></li>
        <li><a href="supporttickets.php?view=Closed">- Closed</a></li>
        </ul>
    </li><?php endif; ?>
    <?php if (in_array ( 'Open New Ticket' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="supporttickets.php?action=open"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['opennewticket']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Manage Predefined Replies' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="supportticketpredefinedreplies.php"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['predefreplies']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Manage Network Issues' , $this->_tpl_vars['admin_perms'] )): ?><li class="expand"><a href="networkissues.php"><?php echo $this->_tpl_vars['_ADMINLANG']['networkissues']['title']; ?>
</a>
        <ul>
        <li><a href="networkissues.php">- <?php echo $this->_tpl_vars['_ADMINLANG']['networkissues']['open']; ?>
</a></li>
        <li><a href="networkissues.php?view=scheduled">- <?php echo $this->_tpl_vars['_ADMINLANG']['networkissues']['scheduled']; ?>
</a></li>
        <li><a href="networkissues.php?view=resolved">- <?php echo $this->_tpl_vars['_ADMINLANG']['networkissues']['resolved']; ?>
</a></li>
        <li><a href="networkissues.php?action=manage">- <?php echo $this->_tpl_vars['_ADMINLANG']['networkissues']['addnew']; ?>
</a></li>
        </ul>
    </li><?php endif; ?>
  </ul>
</li>
<?php if (in_array ( 'View Reports' , $this->_tpl_vars['admin_perms'] )): ?><li><a title="Reports" href="reports.php"><?php echo $this->_tpl_vars['_ADMINLANG']['reports']['title']; ?>
</a>
  <ul>
    <li><a href="reports.php?report=daily_performance">Daily Performance</a></li>
    <li><a href="reports.php?report=income_forecast">Income Forecast</a></li>
    <li><a href="reports.php?report=annual_income_report">Annual Income Report</a></li>
    <li><a href="reports.php?report=new_customers">New Customers</a></li>
    <li><a href="reports.php?report=ticket_feedback_scores">Ticket Feedback Scores</a></li>
    <li><a href="reports.php?report=pdf_batch">Batch Invoice PDF Export</a></li>
    <li><a href="reports.php">More...</a></li>
  </ul>
</li><?php endif; ?>
<li><a title="Utilities" href=""><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['title']; ?>
</a>
  <ul>
    <?php if (in_array ( 'Email Marketer' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="utilitiesemailmarketer.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['emailmarketer']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Link Tracking' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="utilitieslinktracking.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['linktracking']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Browser' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="browser.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['browser']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Calendar' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="calendar.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['calendar']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( "To-Do List" , $this->_tpl_vars['admin_perms'] )): ?><li><a href="todolist.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['todolist']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'WHOIS Lookups' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="whois.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['whois']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Domain Resolver Checker' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="utilitiesresolvercheck.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['domainresolver']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'View Integration Code' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemintegrationcode.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['integrationcode']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'WHM Import Script' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="whmimport.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['cpanelimport']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Database Status' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'System Cleanup Operations' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View PHP Info' , $this->_tpl_vars['admin_perms'] )): ?><li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['system']; ?>
</a>
        <ul>
        <?php if (in_array ( 'Database Status' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemdatabase.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['dbstatus']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'System Cleanup Operations' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemcleanup.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['syscleanup']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View PHP Info' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemphpinfo.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['phpinfo']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
    <?php if (in_array ( 'View Activity Log' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View Admin Log' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View Module Debug Log' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View Email Message Log' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View Ticket Mail Import Log' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View WHOIS Lookup Log' , $this->_tpl_vars['admin_perms'] )): ?><li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['logs']; ?>
</a>
        <ul>
        <?php if (in_array ( 'View Activity Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemactivitylog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['activitylog']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View Admin Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemadminlog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['adminlog']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View Module Debug Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemmodulelog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['modulelog']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View Email Message Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systememaillog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['emaillog']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View Ticket Mail Import Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemmailimportlog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['ticketmaillog']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View WHOIS Lookup Log' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemwhoislog.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['whoislog']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
  </ul>
</li>
<li><a title="Addons" href="addonmodules.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['addonmodules']; ?>
</a>
  <ul>
    <?php $_from = $this->_tpl_vars['addon_modules']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['module'] => $this->_tpl_vars['displayname']):
?>
    <li><a href="addonmodules.php?module=<?php echo $this->_tpl_vars['module']; ?>
"><?php echo $this->_tpl_vars['displayname']; ?>
</a></li>
    <?php endforeach; else: ?>
    <li><a href="addonmodules.php"><?php echo $this->_tpl_vars['_ADMINLANG']['utilities']['addonsdirectory']; ?>
</a></li>
    <?php endif; unset($_from); ?>
  </ul>
</li>
<li><a title="Setup" href=""><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['title']; ?>
</a>
  <ul>
    <?php if (in_array ( 'Configure General Settings' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configgeneral.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['general']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure Automation Settings' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configauto.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['automation']; ?>
</a></li><?php endif; ?>
<?php if (in_array ( 'Configure Administrators' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Admin Roles' , $this->_tpl_vars['admin_perms'] ) || in_array ( "Configure Two-Factor Authentication" , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['staff']; ?>
</a>
        <ul>
        <?php if (in_array ( 'Configure Administrators' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configadmins.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['admins']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Admin Roles' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configadminroles.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['adminroles']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( "Configure Two-Factor Authentication" , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configtwofa.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['twofa']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php else: ?>
    <li><a href="myaccount.php"><?php echo $this->_tpl_vars['_ADMINLANG']['global']['myaccount']; ?>
</a></li><?php endif; ?>
<?php if (in_array ( 'Configure Currencies' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Payment Gateways' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Tax Setup' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View Promotions' , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['payments']; ?>
</a>
        <ul>
        <?php if (in_array ( 'Configure Currencies' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configcurrencies.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['currencies']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Payment Gateways' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configgateways.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['gateways']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Tax Setup' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configtax.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['tax']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View Promotions' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configpromotions.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['promos']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
<?php if (in_array ( "View Products/Services" , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Product Addons' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Product Bundles' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Domain Pricing' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Domain Registrars' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Servers' , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['products']; ?>
</a>
        <ul>
        <?php if (in_array ( "View Products/Services" , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configproducts.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['products']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( "View Products/Services" , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configproductoptions.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['configoptions']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Product Addons' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configaddons.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['addons']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Product Bundles' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configbundles.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['bundles']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Domain Pricing' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configdomains.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['domainpricing']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Domain Registrars' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configregistrars.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['registrars']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Servers' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configservers.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['servers']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
<?php if (in_array ( 'Configure Support Departments' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Ticket Statuses' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Support Departments' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Spam Control' , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['support']['title']; ?>
</a>
        <ul>
        <?php if (in_array ( 'Configure Support Departments' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configticketdepartments.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['supportdepartments']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Ticket Statuses' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configticketstatuses.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['ticketstatuses']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Support Departments' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configticketescalations.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['escalationrules']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Spam Control' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configticketspamcontrol.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['spam']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
    <?php if (in_array ( 'View Email Templates' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configemailtemplates.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['emailtpls']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure Addon Modules' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configaddonmods.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['addonmodules']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure Client Groups' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configclientgroups.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['clientgroups']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure Custom Client Fields' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configcustomfields.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['customclientfields']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure Fraud Protection' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configfraud.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['fraud']; ?>
</a></li><?php endif; ?>
<?php if (in_array ( 'Configure Order Statuses' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Security Questions' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'View Banned IPs' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Banned Emails' , $this->_tpl_vars['admin_perms'] ) || in_array ( 'Configure Database Backups' , $this->_tpl_vars['admin_perms'] )): ?>
    <li class="expand"><a href="#"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['other']; ?>
</a>
        <ul>
        <?php if (in_array ( 'Configure Order Statuses' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configorderstatuses.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['orderstatuses']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Security Questions' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configsecurityqs.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['securityqs']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'View Banned IPs' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configbannedips.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['bannedips']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Banned Emails' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configbannedemails.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['bannedemails']; ?>
</a></li><?php endif; ?>
        <?php if (in_array ( 'Configure Database Backups' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="configbackups.php"><?php echo $this->_tpl_vars['_ADMINLANG']['setup']['backups']; ?>
</a></li><?php endif; ?>
        </ul>
    </li><?php endif; ?>
  </ul>
</li>
<li><a title="Help" href=""><?php echo $this->_tpl_vars['_ADMINLANG']['help']['title']; ?>
</a>
  <ul>
    <li><a href="http://docs.whmcs.com/" target="_blank"><?php echo $this->_tpl_vars['_ADMINLANG']['help']['docs']; ?>
</a></li>
    <?php if (in_array ( 'Main Homepage' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemlicense.php"><?php echo $this->_tpl_vars['_ADMINLANG']['help']['licenseinfo']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure Administrators' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="licenseerror.php?licenseerror=change"><?php echo $this->_tpl_vars['_ADMINLANG']['help']['changelicense']; ?>
</a></li><?php endif; ?>
    <?php if (in_array ( 'Configure General Settings' , $this->_tpl_vars['admin_perms'] )): ?><li><a href="systemupdates.php"><?php echo $this->_tpl_vars['_ADMINLANG']['help']['updates']; ?>
</a></li>
    <li><a href="systemsupportrequest.php"><?php echo $this->_tpl_vars['_ADMINLANG']['help']['support']; ?>
</a></li><?php endif; ?>
    <li><a href="http://forum.whmcs.com/" target="_blank"><?php echo $this->_tpl_vars['_ADMINLANG']['help']['forums']; ?>
</a></li>
  </ul>
</li>
</ul>
</div>