<?php /* Smarty version 2.6.27, created on 2013-08-27 12:20:53
         compiled from /home/salim/public_html/billing/templates/default/viewannouncement.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'date_format', '/home/salim/public_html/billing/templates/default/viewannouncement.tpl', 13, false),)), $this); ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['title'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<?php if ($this->_tpl_vars['twittertweet']): ?>
<div class="tweetbutton">
<a href="https://twitter.com/share" class="twitter-share-button" data-count="vertical" data-via="<?php echo $this->_tpl_vars['twitterusername']; ?>
">Tweet</a><script type="text/javascript" src="//platform.twitter.com/widgets.js"></script>
</div>
<?php endif; ?>

<?php echo $this->_tpl_vars['text']; ?>


<br /><br />

<p><strong><?php echo ((is_array($_tmp=$this->_tpl_vars['timestamp'])) ? $this->_run_mod_handler('date_format', true, $_tmp, "%A, %B %e, %Y") : smarty_modifier_date_format($_tmp, "%A, %B %e, %Y")); ?>
</strong></p>

<?php if ($this->_tpl_vars['googleplus1']): ?>
<br /><br />
<g:plusone annotation="inline"></g:plusone>
<?php echo '<script type="text/javascript">
  (function() {
    var po = document.createElement(\'script\'); po.type = \'text/javascript\'; po.async = true;
    po.src = \'https://apis.google.com/js/plusone.js\';
    var s = document.getElementsByTagName(\'script\')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>'; ?>

<?php endif; ?>

<?php if ($this->_tpl_vars['facebookrecommend']): ?>
<br /><br />
<?php echo '
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) {return;}
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, \'script\', \'facebook-jssdk\'));</script>
'; ?>

<div class="fb-like" data-href="<?php echo $this->_tpl_vars['systemurl']; ?>
<?php if ($this->_tpl_vars['seofriendlyurls']): ?>announcements/<?php echo $this->_tpl_vars['id']; ?>
/<?php echo $this->_tpl_vars['urlfriendlytitle']; ?>
.html<?php else: ?>announcements.php?id=<?php echo $this->_tpl_vars['id']; ?>
<?php endif; ?>" data-send="true" data-width="450" data-show-faces="true" data-action="recommend"></div>
<?php endif; ?>

<?php if ($this->_tpl_vars['facebookcomments']): ?>
<br /><br />
<?php echo '
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) {return;}
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, \'script\', \'facebook-jssdk\'));</script>
'; ?>

<fb:comments href="<?php echo $this->_tpl_vars['systemurl']; ?>
<?php if ($this->_tpl_vars['seofriendlyurls']): ?>announcements/<?php echo $this->_tpl_vars['id']; ?>
/<?php echo $this->_tpl_vars['urlfriendlytitle']; ?>
.html<?php else: ?>announcements.php?id=<?php echo $this->_tpl_vars['id']; ?>
<?php endif; ?>" num_posts="5" width="500"></fb:comments>
<?php endif; ?>

<p><a href="announcements.php"><?php echo $this->_tpl_vars['LANG']['clientareabacklink']; ?>
</a></p>