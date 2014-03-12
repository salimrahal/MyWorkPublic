<?php /* Smarty version 2.6.27, created on 2013-08-27 09:10:01
         compiled from /home/salim/public_html/billing/templates/default/knowledgebase.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'truncate', '/home/salim/public_html/billing/templates/default/knowledgebase.tpl', 36, false),)), $this); ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['knowledgebasetitle'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="well">
    <div class="textcenter">
        <form method="post" action="knowledgebase.php?action=search" class="form-inline">
            <fieldset class="control-group">
        	    <input class="bigfield" name="search" type="text" value="<?php echo $this->_tpl_vars['LANG']['kbquestionsearchere']; ?>
" onfocus="this.value=(this.value=='<?php echo $this->_tpl_vars['LANG']['kbquestionsearchere']; ?>
') ? '' : this.value;" onblur="this.value=(this.value=='') ? '<?php echo $this->_tpl_vars['LANG']['kbquestionsearchere']; ?>
' : this.value;"/>
                <input type="submit" class="btn btn-large btn-primary" value="<?php echo $this->_tpl_vars['LANG']['knowledgebasesearch']; ?>
" />
        	</fieldset>
        </form>
    </div>
</div>

<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/subheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['knowledgebasecategories'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<div class="row">
<div class="control-group">
<?php $_from = $this->_tpl_vars['kbcats']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['kbasecats'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['kbasecats']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['kbcat']):
        $this->_foreach['kbasecats']['iteration']++;
?>
    <div class="col4">
        <div class="internalpadding">
            <img src="images/folder.gif" /> <a href="<?php if ($this->_tpl_vars['seofriendlyurls']): ?>knowledgebase/<?php echo $this->_tpl_vars['kbcat']['id']; ?>
/<?php echo $this->_tpl_vars['kbcat']['urlfriendlyname']; ?>
<?php else: ?>knowledgebase.php?action=displaycat&amp;catid=<?php echo $this->_tpl_vars['kbcat']['id']; ?>
<?php endif; ?>" class="fontsize2"><strong><?php echo $this->_tpl_vars['kbcat']['name']; ?>
</strong></a> (<?php echo $this->_tpl_vars['kbcat']['numarticles']; ?>
)<br />
            <?php echo $this->_tpl_vars['kbcat']['description']; ?>

        </div>
    </div>
	<?php if (!(( ($this->_foreach['kbasecats']['iteration']-1)+1 ) % 4)): ?><div class="clear"></div>
    <?php endif; ?>
<?php endforeach; endif; unset($_from); ?>
</div>
</div>

<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/subheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['LANG']['knowledgebasepopular'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<?php $_from = $this->_tpl_vars['kbmostviews']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['kbarticle']):
?>
<div class="row">
    <img src="images/article.gif"> <a href="<?php if ($this->_tpl_vars['seofriendlyurls']): ?>knowledgebase/<?php echo $this->_tpl_vars['kbarticle']['id']; ?>
/<?php echo $this->_tpl_vars['kbarticle']['urlfriendlytitle']; ?>
.html<?php else: ?>knowledgebase.php?action=displayarticle&amp;id=<?php echo $this->_tpl_vars['kbarticle']['id']; ?>
<?php endif; ?>" class="fontsize2"><strong><?php echo $this->_tpl_vars['kbarticle']['title']; ?>
</strong></a><br />
    <?php echo ((is_array($_tmp=$this->_tpl_vars['kbarticle']['article'])) ? $this->_run_mod_handler('truncate', true, $_tmp, 100, "...") : smarty_modifier_truncate($_tmp, 100, "...")); ?>

</div>
<?php endforeach; endif; unset($_from); ?>

<br />