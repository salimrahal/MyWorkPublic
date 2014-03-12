<?php /* Smarty version 2.6.27, created on 2013-08-30 10:11:57
         compiled from blend/footer.tpl */ ?>

</div>
<div class="clear"></div>

</div>

<div class="clear"></div>

<div class="footerbar">
<div class="left"><a href="#">Top</a></div>
<div class="right">Copyright &copy; <a href="http://www.whmcs.com/" target="_blank">WHMCompleteSolution</a>.  All Rights Reserved.</div>
</div>

<div class="intellisearch">
<form method="get" onsubmit="intellisearch();return false">
<input type="text" id="intellisearchval" />
<input type="submit" style="display:none;">
</form>
</div>

<div id="searchresults">
<div id="searchresultsscroller"></div>
<div class="close"><a href="#" onclick="searchclose();return false"><?php echo $this->_tpl_vars['_ADMINLANG']['clientsummary']['close']; ?>
 <img src="images/delete.gif" width="16" height="16" border="0" align="top" /></a></div>
</div>

<div id="greyout"></div>

<div id="popupcontainer">
<div id="mynotes">
<div align="right"><a href="#" onclick="notesclose('');return false"><img src="images/delete.gif" width="16" height="16" align="absmiddle" border="0" /> <?php echo $this->_tpl_vars['_ADMINLANG']['clientsummary']['close']; ?>
</a></div>
<textarea id="mynotesbox" rows="15"><?php echo $this->_tpl_vars['admin_notes']; ?>
</textarea><br /><input type="button" value="<?php echo $this->_tpl_vars['_ADMINLANG']['global']['savechanges']; ?>
" onclick="notesclose('1');return false" /></div>
</div>

<?php echo $this->_tpl_vars['footeroutput']; ?>


</body>
</html>