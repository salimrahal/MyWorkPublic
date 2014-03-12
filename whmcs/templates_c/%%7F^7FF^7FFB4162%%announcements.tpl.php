<?php /* Smarty version 2.6.27, created on 2013-08-27 09:57:42
         compiled from /home/salim/public_html/billing/templates/default/announcements.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'date_format', '/home/salim/public_html/billing/templates/default/announcements.tpl', 6, false),array('modifier', 'strip_tags', '/home/salim/public_html/billing/templates/default/announcements.tpl', 10, false),array('modifier', 'truncate', '/home/salim/public_html/billing/templates/default/announcements.tpl', 10, false),)), $this); ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['announcementstitle'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<?php $_from = $this->_tpl_vars['announcements']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['announcement']):
?>

<h2><a href="<?php if ($this->_tpl_vars['seofriendlyurls']): ?>announcements/<?php echo $this->_tpl_vars['announcement']['id']; ?>
/<?php echo $this->_tpl_vars['announcement']['urlfriendlytitle']; ?>
.html<?php else: ?><?php echo $_SERVER['PHP_SELF']; ?>
?id=<?php echo $this->_tpl_vars['announcement']['id']; ?>
<?php endif; ?>"><?php echo $this->_tpl_vars['announcement']['title']; ?>
</a></h2>
<p><strong><?php echo ((is_array($_tmp=$this->_tpl_vars['announcement']['timestamp'])) ? $this->_run_mod_handler('date_format', true, $_tmp, "%A, %B %e, %Y") : smarty_modifier_date_format($_tmp, "%A, %B %e, %Y")); ?>
</strong></p>

<br />

<p><?php echo ((is_array($_tmp=((is_array($_tmp=$this->_tpl_vars['announcement']['text'])) ? $this->_run_mod_handler('strip_tags', true, $_tmp) : smarty_modifier_strip_tags($_tmp)))) ? $this->_run_mod_handler('truncate', true, $_tmp, 400, "...") : smarty_modifier_truncate($_tmp, 400, "...")); ?>
</p>

<?php if (strlen ( $this->_tpl_vars['announcement']['text'] ) > 400): ?><p><a href="<?php if ($this->_tpl_vars['seofriendlyurls']): ?>announcements/<?php echo $this->_tpl_vars['announcement']['id']; ?>
/<?php echo $this->_tpl_vars['announcement']['urlfriendlytitle']; ?>
.html<?php else: ?><?php echo $_SERVER['PHP_SELF']; ?>
?id=<?php echo $this->_tpl_vars['announcement']['id']; ?>
<?php endif; ?>"><?php echo $this->_tpl_vars['LANG']['more']; ?>
 &raquo;</a></p><?php endif; ?>

<br />

<?php if ($this->_tpl_vars['facebookrecommend']): ?>
<?php echo '
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) {return;}
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, \'script\', \'facebook-jssdk\'));</script>
'; ?>

<div class="fb-like" data-href="<?php echo $this->_tpl_vars['systemurl']; ?>
<?php if ($this->_tpl_vars['seofriendlyurls']): ?>announcements/<?php echo $this->_tpl_vars['announcement']['id']; ?>
/<?php echo $this->_tpl_vars['announcement']['urlfriendlytitle']; ?>
.html<?php else: ?>announcements.php?id=<?php echo $this->_tpl_vars['announcement']['id']; ?>
<?php endif; ?>" data-send="true" data-width="450" data-show-faces="true" data-action="recommend"></div>
<?php endif; ?>
<br /><br />
<?php endforeach; else: ?>
<p align="center"><strong><?php echo $this->_tpl_vars['LANG']['announcementsnone']; ?>
</strong></p>
<?php endif; unset($_from); ?>

<br />

<?php if ($this->_tpl_vars['prevpage'] || $this->_tpl_vars['nextpage']): ?>

<div style="float: left; width: 200px;">
<?php if ($this->_tpl_vars['prevpage']): ?><a href="announcements.php?page=<?php echo $this->_tpl_vars['prevpage']; ?>
"><?php endif; ?>&laquo; <?php echo $this->_tpl_vars['LANG']['previouspage']; ?>
<?php if ($this->_tpl_vars['prevpage']): ?></a><?php endif; ?>
</div>

<div style="float: right; width: 200px; text-align: right;">
<?php if ($this->_tpl_vars['nextpage']): ?><a href="announcements.php?page=<?php echo $this->_tpl_vars['nextpage']; ?>
"><?php endif; ?><?php echo $this->_tpl_vars['LANG']['nextpage']; ?>
 &raquo;<?php if ($this->_tpl_vars['nextpage']): ?></a><?php endif; ?>
</div>

<?php endif; ?>

<br />

<p align="right"><img src="images/rssfeed.gif" alt="RSS" align="absmiddle" /> <a href="announcementsrss.php"><?php echo $this->_tpl_vars['LANG']['announcementsrss']; ?>
</a></p>

<br />