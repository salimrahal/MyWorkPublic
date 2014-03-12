<?php /* Smarty version 2.6.27, created on 2013-09-02 11:10:44
         compiled from /home/salim/public_html/whmcs/templates/default/homepage.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'strip_tags', '/home/salim/public_html/whmcs/templates/default/homepage.tpl', 74, false),array('modifier', 'truncate', '/home/salim/public_html/whmcs/templates/default/homepage.tpl', 74, false),)), $this); ?>
<?php if ($this->_tpl_vars['condlinks']['domainreg'] || $this->_tpl_vars['condlinks']['domaintrans'] || $this->_tpl_vars['condlinks']['domainown']): ?>
<div class="well">
    <div class="styled_title">
        <h1><?php echo $this->_tpl_vars['LANG']['domaincheckerchoosedomain']; ?>
</h1>
    </div>
    <p><?php echo $this->_tpl_vars['LANG']['domaincheckerenterdomain']; ?>
</p>
    <br />
    <div class="textcenter">
        <form method="post" action="<?php echo $this->_tpl_vars['systemsslurl']; ?>
domainchecker.php">
        <input class="bigfield" name="domain" type="text" value="<?php echo $this->_tpl_vars['LANG']['domaincheckerdomainexample']; ?>
" onfocus="if(this.value=='<?php echo $this->_tpl_vars['LANG']['domaincheckerdomainexample']; ?>
')this.value=''" onblur="if(this.value=='')this.value='<?php echo $this->_tpl_vars['LANG']['domaincheckerdomainexample']; ?>
'" />
        <?php if ($this->_tpl_vars['capatacha']): ?>
        <div class="captchainput" align="center">
            <p><?php echo $this->_tpl_vars['LANG']['captchaverify']; ?>
</p>
            <?php if ($this->_tpl_vars['capatacha'] == 'recaptcha'): ?>
            <p><?php echo $this->_tpl_vars['recapatchahtml']; ?>
</p>
            <?php else: ?>
            <p><img src="includes/verifyimage.php" align="middle" /> <input type="text" name="code" class="input-small" maxlength="5" /></p>
            <?php endif; ?>
        </div>
        <?php endif; ?>
        <div class="internalpadding"><?php if ($this->_tpl_vars['condlinks']['domainreg']): ?><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['checkavailability']; ?>
" class="btn btn-primary btn-large" /> <?php endif; ?><?php if ($this->_tpl_vars['condlinks']['domaintrans']): ?><input type="submit" name="transfer" value="<?php echo $this->_tpl_vars['LANG']['domainstransfer']; ?>
" class="btn btn-success btn-large" /><?php endif; ?><?php if ($this->_tpl_vars['condlinks']['domainown']): ?> <input type="submit" name="hosting" value="<?php echo $this->_tpl_vars['LANG']['domaincheckerhostingonly']; ?>
" class="btn btn-large" /><?php endif; ?></div>
        </form>
    </div>
</div>
<?php endif; ?>

<div class="row">

<div class="col2half">
    <div class="internalpadding">
        <div class="styled_title">
            <h2><?php echo $this->_tpl_vars['LANG']['navservicesorder']; ?>
</h2>
        </div>
        <p><?php echo $this->_tpl_vars['LANG']['clientareahomeorder']; ?>
<br /><br /></p>
        <form method="post" action="cart.php">
        <p align="center"><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['clientareahomeorderbtn']; ?>
 &raquo;" class="btn" /></p>
        </form>
    </div>
</div>
<div class="col2half">
    <div class="internalpadding">
        <div class="styled_title"><h2><?php echo $this->_tpl_vars['LANG']['manageyouraccount']; ?>
</h2></div>
        <p><?php echo $this->_tpl_vars['LANG']['clientareahomelogin']; ?>
<br /><br /></p>
        <form method="post" action="clientarea.php">
        <p align="center"><input type="submit" value="<?php echo $this->_tpl_vars['LANG']['clientareahomeloginbtn']; ?>
 &raquo;" class="btn" /></p>
        </form>
    </div>
</div>

</div>

<div class="row">

<?php if ($this->_tpl_vars['twitterusername']): ?>
<div class="styled_title">
    <h2><?php echo $this->_tpl_vars['LANG']['twitterlatesttweets']; ?>
</h2>
</div>
<div id="twitterfeed">
    <p><img src="images/loading.gif"></p>
</div>
<?php echo '<script language="javascript">
jQuery(document).ready(function(){
  jQuery.post("announcements.php", { action: "twitterfeed", numtweets: 3 },
    function(data){
      jQuery("#twitterfeed").html(data);
    });
});
</script>'; ?>

<?php elseif ($this->_tpl_vars['announcements']): ?>
<div class="styled_title">
    <h2><?php echo $this->_tpl_vars['LANG']['latestannouncements']; ?>
</h2>
</div>
<?php $_from = $this->_tpl_vars['announcements']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['announcement']):
?>
<p><?php echo $this->_tpl_vars['announcement']['date']; ?>
 - <a href="<?php if ($this->_tpl_vars['seofriendlyurls']): ?>announcements/<?php echo $this->_tpl_vars['announcement']['id']; ?>
/<?php echo $this->_tpl_vars['announcement']['urlfriendlytitle']; ?>
.html<?php else: ?>announcements.php?id=<?php echo $this->_tpl_vars['announcement']['id']; ?>
<?php endif; ?>"><b><?php echo $this->_tpl_vars['announcement']['title']; ?>
</b></a><br /><?php echo ((is_array($_tmp=((is_array($_tmp=$this->_tpl_vars['announcement']['text'])) ? $this->_run_mod_handler('strip_tags', true, $_tmp) : smarty_modifier_strip_tags($_tmp)))) ? $this->_run_mod_handler('truncate', true, $_tmp, 100, "...") : smarty_modifier_truncate($_tmp, 100, "...")); ?>
</p>
<?php endforeach; endif; unset($_from); ?>
<?php endif; ?>

</div>