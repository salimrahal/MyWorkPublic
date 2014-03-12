<?php /* Smarty version 2.6.27, created on 2013-09-02 11:10:44
         compiled from default/header.tpl */ ?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=<?php echo $this->_tpl_vars['charset']; ?>
" />
    <title><?php if ($this->_tpl_vars['kbarticle']['title']): ?><?php echo $this->_tpl_vars['kbarticle']['title']; ?>
 - <?php endif; ?><?php echo $this->_tpl_vars['pagetitle']; ?>
 - <?php echo $this->_tpl_vars['companyname']; ?>
</title>

    <?php if ($this->_tpl_vars['systemurl']): ?><base href="<?php echo $this->_tpl_vars['systemurl']; ?>
" />
    <?php endif; ?><script type="text/javascript" src="includes/jscript/jquery.js"></script>
    <?php if ($this->_tpl_vars['livehelpjs']): ?><?php echo $this->_tpl_vars['livehelpjs']; ?>

    <?php endif; ?>
    <link href="templates/<?php echo $this->_tpl_vars['template']; ?>
/css/bootstrap.css" rel="stylesheet">
    <link href="templates/<?php echo $this->_tpl_vars['template']; ?>
/css/whmcs.css" rel="stylesheet">

    <script src="templates/<?php echo $this->_tpl_vars['template']; ?>
/js/whmcs.js"></script>

    <?php echo $this->_tpl_vars['headoutput']; ?>


  </head>

  <body>

<?php echo $this->_tpl_vars['headeroutput']; ?>


<div id="whmcsheader">
    <div class="whmcscontainer">
        <div id="whmcstxtlogo"><a href="index.php"><?php echo $this->_tpl_vars['companyname']; ?>
</a></div>
        <div id="whmcsimglogo"><a href="index.php"><img src="templates/<?php echo $this->_tpl_vars['template']; ?>
/img/whmcslogo.png" alt="<?php echo $this->_tpl_vars['companyname']; ?>
" /></a></div>
    </div>
</div>

  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </a>
        <div class="nav-collapse">
		<ul class="nav">
			<li><a href="<?php if ($this->_tpl_vars['loggedin']): ?>clientarea<?php else: ?>index<?php endif; ?>.php"><?php echo $this->_tpl_vars['LANG']['hometitle']; ?>
</a></li>
		</ul>
<?php if ($this->_tpl_vars['loggedin']): ?>
    <ul class="nav">
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $this->_tpl_vars['LANG']['navservices']; ?>
&nbsp;<b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="clientarea.php?action=products"><?php echo $this->_tpl_vars['LANG']['clientareanavservices']; ?>
</a></li>
            <?php if ($this->_tpl_vars['condlinks']['pmaddon']): ?><li><a href="index.php?m=project_management"><?php echo $this->_tpl_vars['LANG']['clientareaprojects']; ?>
</a></li><?php endif; ?>
            <li class="divider"></li>
            <li><a href="cart.php"><?php echo $this->_tpl_vars['LANG']['navservicesorder']; ?>
</a></li>
            <li><a href="cart.php?gid=addons"><?php echo $this->_tpl_vars['LANG']['clientareaviewaddons']; ?>
</a></li>
          </ul>
        </li>
      </ul>


		  <?php if ($this->_tpl_vars['condlinks']['domainreg'] || $this->_tpl_vars['condlinks']['domaintrans']): ?><ul class="nav">
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $this->_tpl_vars['LANG']['navdomains']; ?>
&nbsp;<b class="caret"></b></a>
			  <ul class="dropdown-menu">
				<li><a href="clientarea.php?action=domains"><?php echo $this->_tpl_vars['LANG']['clientareanavdomains']; ?>
</a></li>
				<li class="divider"></li>
				<li><a href="cart.php?gid=renewals"><?php echo $this->_tpl_vars['LANG']['navrenewdomains']; ?>
</a></li>
				<?php if ($this->_tpl_vars['condlinks']['domainreg']): ?><li><a href="cart.php?a=add&domain=register"><?php echo $this->_tpl_vars['LANG']['navregisterdomain']; ?>
</a></li><?php endif; ?>
				<?php if ($this->_tpl_vars['condlinks']['domaintrans']): ?><li><a href="cart.php?a=add&domain=transfer"><?php echo $this->_tpl_vars['LANG']['navtransferdomain']; ?>
</a></li><?php endif; ?>
				<li class="divider"></li>
				<li><a href="domainchecker.php"><?php echo $this->_tpl_vars['LANG']['navwhoislookup']; ?>
</a></li>
			  </ul>
			</li>
		  </ul><?php endif; ?>

		  <ul class="nav">
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $this->_tpl_vars['LANG']['navbilling']; ?>
&nbsp;<b class="caret"></b></a>
			  <ul class="dropdown-menu">
				<li><a href="clientarea.php?action=invoices"><?php echo $this->_tpl_vars['LANG']['invoices']; ?>
</a></li>
				<li><a href="clientarea.php?action=quotes"><?php echo $this->_tpl_vars['LANG']['quotestitle']; ?>
</a></li>
				<li class="divider"></li>
				<?php if ($this->_tpl_vars['condlinks']['addfunds']): ?><li><a href="clientarea.php?action=addfunds"><?php echo $this->_tpl_vars['LANG']['addfunds']; ?>
</a></li><?php endif; ?>
				<?php if ($this->_tpl_vars['condlinks']['masspay']): ?><li><a href="clientarea.php?action=masspay&all=true"><?php echo $this->_tpl_vars['LANG']['masspaytitle']; ?>
</a></li><?php endif; ?>
				<?php if ($this->_tpl_vars['condlinks']['updatecc']): ?><li><a href="clientarea.php?action=creditcard"><?php echo $this->_tpl_vars['LANG']['navmanagecc']; ?>
</a></li><?php endif; ?>
			  </ul>
			</li>
		  </ul>

		  <ul class="nav">
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $this->_tpl_vars['LANG']['navsupport']; ?>
&nbsp;<b class="caret"></b></a>
			  <ul class="dropdown-menu">
				<li><a href="supporttickets.php"><?php echo $this->_tpl_vars['LANG']['navtickets']; ?>
</a></li>
				<li><a href="knowledgebase.php"><?php echo $this->_tpl_vars['LANG']['knowledgebasetitle']; ?>
</a></li>
				<li><a href="downloads.php"><?php echo $this->_tpl_vars['LANG']['downloadstitle']; ?>
</a></li>
				<li><a href="serverstatus.php"><?php echo $this->_tpl_vars['LANG']['networkstatustitle']; ?>
</a></li>
			  </ul>
			</li>
		  </ul>

		  <ul class="nav">
			<li><a href="submitticket.php"><?php echo $this->_tpl_vars['LANG']['navopenticket']; ?>
</a></li>
		  </ul>

		  <?php if ($this->_tpl_vars['condlinks']['affiliates']): ?><ul class="nav">
            <li><a href="affiliates.php"><?php echo $this->_tpl_vars['LANG']['affiliatestitle']; ?>
</a></li>
		  </ul><?php endif; ?>

		  <ul class="nav pull-right">
			<li class="dropdown">
			  <a href="#" class="dropdown-toggle" data-toggle="dropdown"><?php echo $this->_tpl_vars['LANG']['hello']; ?>
, <?php echo $this->_tpl_vars['loggedinuser']['firstname']; ?>
!&nbsp;<b class="caret"></b></a>
			  <ul class="dropdown-menu">
				<li><a href="clientarea.php?action=details"><?php echo $this->_tpl_vars['LANG']['editaccountdetails']; ?>
</a></li>
				<?php if ($this->_tpl_vars['condlinks']['updatecc']): ?><li><a href="clientarea.php?action=creditcard"><?php echo $this->_tpl_vars['LANG']['navmanagecc']; ?>
</a></li><?php endif; ?>
				<li><a href="clientarea.php?action=contacts"><?php echo $this->_tpl_vars['LANG']['clientareanavcontacts']; ?>
</a></li>
				<?php if ($this->_tpl_vars['condlinks']['addfunds']): ?><li><a href="clientarea.php?action=addfunds"><?php echo $this->_tpl_vars['LANG']['addfunds']; ?>
</a></li><?php endif; ?>
				<li><a href="clientarea.php?action=emails"><?php echo $this->_tpl_vars['LANG']['navemailssent']; ?>
</a></li>
				<li><a href="clientarea.php?action=changepw"><?php echo $this->_tpl_vars['LANG']['clientareanavchangepw']; ?>
</a></li>
				<li class="divider"></li>
				<li><a href="logout.php"><?php echo $this->_tpl_vars['LANG']['logouttitle']; ?>
</a></li>
			  </ul>
			</li>
		  </ul>
<?php else: ?>
		  <ul class="nav">
			<li><a href="announcements.php"><?php echo $this->_tpl_vars['LANG']['announcementstitle']; ?>
</a></li>
		  </ul>
          
		  <ul class="nav">
			<li><a href="knowledgebase.php"><?php echo $this->_tpl_vars['LANG']['knowledgebasetitle']; ?>
</a></li>
		  </ul>
          
		  <ul class="nav">
			<li><a href="serverstatus.php"><?php echo $this->_tpl_vars['LANG']['networkstatustitle']; ?>
</a></li>
		  </ul>
          
		  <ul class="nav">
			<li><a href="affiliates.php"><?php echo $this->_tpl_vars['LANG']['affiliatestitle']; ?>
</a></li>
		  </ul>
          
		  <ul class="nav">
			<li><a href="contact.php"><?php echo $this->_tpl_vars['LANG']['contactus']; ?>
</a></li>
		  </ul>

		  <ul class="nav pull-right">
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $this->_tpl_vars['LANG']['account']; ?>
&nbsp;<b class="caret"></b></a>
			  <ul class="dropdown-menu">
				<li><a href="clientarea.php"><?php echo $this->_tpl_vars['LANG']['login']; ?>
</a></li>
				<li><a href="register.php"><?php echo $this->_tpl_vars['LANG']['register']; ?>
</a></li>
				<li class="divider"></li>
				<li><a href="pwreset.php"><?php echo $this->_tpl_vars['LANG']['forgotpw']; ?>
</a></li>
			  </ul>
			</li>
		  </ul>
<?php endif; ?>

        </div><!-- /.nav-collapse -->
      </div>
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->


<div class="whmcscontainer">
    <div class="contentpadded">

<?php if ($this->_tpl_vars['pagetitle'] == $this->_tpl_vars['LANG']['carttitle']): ?><div id="whmcsorderfrm"><?php endif; ?>
