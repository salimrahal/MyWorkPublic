<?php /* Smarty version 2.6.27, created on 2013-09-29 12:57:39
         compiled from /home/salim/public_html/billing/templates/default/clientareaproductdetails.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'replace', '/home/salim/public_html/billing/templates/default/clientareaproductdetails.tpl', 101, false),)), $this); ?>
<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pageheader.tpl", 'smarty_include_vars' => array('title' => $this->_tpl_vars['product'])));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>

<?php if ($this->_tpl_vars['modulechangepwresult'] == 'success'): ?>
<div class="alert alert-success">
    <p><?php echo $this->_tpl_vars['LANG']['serverchangepasswordsuccessful']; ?>
</p>
</div>
<?php elseif ($this->_tpl_vars['modulechangepwresult'] == 'error'): ?>
<div class="alert alert-error">
    <p><?php echo $this->_tpl_vars['modulechangepasswordmessage']; ?>
</p>
</div>
<?php elseif ($this->_tpl_vars['modulecustombuttonresult'] == 'success'): ?>
<div class="alert alert-success">
    <p><?php echo $this->_tpl_vars['LANG']['moduleactionsuccess']; ?>
</p>
</div>
<?php elseif ($this->_tpl_vars['modulecustombuttonresult']): ?>
<div class="alert alert-error">
    <p><strong><?php echo $this->_tpl_vars['LANG']['moduleactionfailed']; ?>
:</strong> <?php echo $this->_tpl_vars['modulecustombuttonresult']; ?>
</p>
</div>
<?php endif; ?>

<div id="tabs">
    <ul class="nav nav-tabs" data-tabs="tabs">
        <li id="tab1nav" class="active"><a href="#tab1" data-toggle="tab"><?php echo $this->_tpl_vars['LANG']['information']; ?>
</a></li>
        <?php if ($this->_tpl_vars['modulechangepassword']): ?><li id="tab1nav"><a href="#tab2" data-toggle="tab"><?php echo $this->_tpl_vars['LANG']['serverchangepassword']; ?>
</a></li><?php endif; ?>
        <?php if ($this->_tpl_vars['downloads']): ?><li id="tab3nav"><a href="#tab3" data-toggle="tab"><?php echo $this->_tpl_vars['LANG']['downloadstitle']; ?>
</a></li><?php endif; ?>
        <li id="tab4nav"><a href="#tab4" data-toggle="tab"><?php echo $this->_tpl_vars['LANG']['clientareahostingaddons']; ?>
</a></li>
        <?php if ($this->_tpl_vars['packagesupgrade'] || $this->_tpl_vars['configoptionsupgrade'] || $this->_tpl_vars['showcancelbutton'] || $this->_tpl_vars['modulecustombuttons']): ?><li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $this->_tpl_vars['LANG']['productmanagementactions']; ?>
</a>
            <ul class="dropdown-menu">
                <?php $_from = $this->_tpl_vars['modulecustombuttons']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['label'] => $this->_tpl_vars['command']):
?>
                <li><a href="clientarea.php?action=productdetails&amp;id=<?php echo $this->_tpl_vars['id']; ?>
&amp;modop=custom&amp;a=<?php echo $this->_tpl_vars['command']; ?>
"><?php echo $this->_tpl_vars['label']; ?>
</a></li>
                <?php endforeach; endif; unset($_from); ?>
                <?php if ($this->_tpl_vars['packagesupgrade']): ?><li><a href="upgrade.php?type=package&amp;id=<?php echo $this->_tpl_vars['id']; ?>
"><?php echo $this->_tpl_vars['LANG']['upgradedowngradepackage']; ?>
</a></li><?php endif; ?>
                <?php if ($this->_tpl_vars['configoptionsupgrade']): ?><li><a href="upgrade.php?type=configoptions&amp;id=<?php echo $this->_tpl_vars['id']; ?>
"><?php echo $this->_tpl_vars['LANG']['upgradedowngradeconfigoptions']; ?>
</a></li><?php endif; ?>
                <?php if ($this->_tpl_vars['showcancelbutton']): ?><li><a href="clientarea.php?action=cancel&amp;id=<?php echo $this->_tpl_vars['id']; ?>
"><?php echo $this->_tpl_vars['LANG']['clientareacancelrequestbutton']; ?>
</a></li><?php endif; ?>
            </ul>
        </li><?php endif; ?>
    </ul>
</div>

<div data-toggle="tab" id="tab1" class="tab-content">

    <div class="row">

        <div class="col30">
            <div class="internalpadding">
                <div class="styled_title"><h2><?php echo $this->_tpl_vars['LANG']['information']; ?>
</h2></div>
                <p><?php echo $this->_tpl_vars['LANG']['clientareaproductdetailsintro']; ?>
</p>
                <br />
                <p><input type="button" value="<?php echo $this->_tpl_vars['LANG']['backtoserviceslist']; ?>
" class="btn" onclick="window.location='clientarea.php?action=products'" /></p>
            </div>
        </div>
        <div class="col70">
            <div class="internalpadding">
                <p><h4><?php echo $this->_tpl_vars['LANG']['clientareahostingregdate']; ?>
:</h4> <?php echo $this->_tpl_vars['regdate']; ?>
</p>
                <p><h4><?php echo $this->_tpl_vars['LANG']['orderproduct']; ?>
:</h4> <?php echo $this->_tpl_vars['groupname']; ?>
 - <?php echo $this->_tpl_vars['product']; ?>
 <span class="label <?php echo $this->_tpl_vars['rawstatus']; ?>
"><?php echo $this->_tpl_vars['status']; ?>
</span><?php if ($this->_tpl_vars['domain']): ?><div><a href="http://<?php echo $this->_tpl_vars['domain']; ?>
" target="_blank"><?php echo $this->_tpl_vars['domain']; ?>
</a></div><?php endif; ?></p>
                <?php if ($this->_tpl_vars['dedicatedip']): ?>
                <div class="col2half">
                	<p><h4><?php echo $this->_tpl_vars['LANG']['domainregisternsip']; ?>
:</h4> <?php echo $this->_tpl_vars['dedicatedip']; ?>
</p>
                </div>
                <div class="clear"></div>
                <?php endif; ?>
                <?php $_from = $this->_tpl_vars['configoptions']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['configoption']):
?>
                <p><h4><?php echo $this->_tpl_vars['configoption']['optionname']; ?>
:</h4> <?php if ($this->_tpl_vars['configoption']['optiontype'] == 3): ?><?php if ($this->_tpl_vars['configoption']['selectedqty']): ?><?php echo $this->_tpl_vars['LANG']['yes']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['no']; ?>
<?php endif; ?><?php elseif ($this->_tpl_vars['configoption']['optiontype'] == 4): ?><?php echo $this->_tpl_vars['configoption']['selectedqty']; ?>
 x <?php echo $this->_tpl_vars['configoption']['selectedoption']; ?>
<?php else: ?><?php echo $this->_tpl_vars['configoption']['selectedoption']; ?>
<?php endif; ?></p>
                <?php endforeach; endif; unset($_from); ?>
                <div class="col2half">
                    <h4><?php echo $this->_tpl_vars['LANG']['firstpaymentamount']; ?>
:</h4> <span><?php echo $this->_tpl_vars['firstpaymentamount']; ?>
</span>
                </div>
                <div class="col2half">
                    <h4><?php echo $this->_tpl_vars['LANG']['recurringamount']; ?>
:</h4> <span><?php echo $this->_tpl_vars['recurringamount']; ?>
</span>
                </div>
                <div class="col2half">
                    <h4><?php echo $this->_tpl_vars['LANG']['orderbillingcycle']; ?>
:</h4> <span><?php echo $this->_tpl_vars['billingcycle']; ?>
</span>
                </div>
                <div class="col2half">
                    <h4><?php echo $this->_tpl_vars['LANG']['clientareahostingnextduedate']; ?>
:</h4> <span><?php echo $this->_tpl_vars['nextduedate']; ?>
</span>
                </div>
                <p><h4><?php echo $this->_tpl_vars['LANG']['orderpaymentmethod']; ?>
:</h4> <?php echo $this->_tpl_vars['paymentmethod']; ?>
</p>
                <?php if ($this->_tpl_vars['suspendreason']): ?><h4><?php echo $this->_tpl_vars['LANG']['suspendreason']; ?>
:</h4> <span><?php echo $this->_tpl_vars['suspendreason']; ?>
</span><?php endif; ?>
                <?php if ($this->_tpl_vars['lastupdate']): ?>
                <div class="col2half">
                    <p><h4><?php echo $this->_tpl_vars['LANG']['clientareadiskusage']; ?>
:</h4> <?php echo $this->_tpl_vars['diskusage']; ?>
MB / <?php echo $this->_tpl_vars['disklimit']; ?>
MB (<?php echo $this->_tpl_vars['diskpercent']; ?>
)</p>
                    <div class="usagecontainer"><div class="used" style="width:<?php echo $this->_tpl_vars['diskpercent']; ?>
"></div></div>
                </div>
                <div class="col2half">
                    <p><h4><?php echo $this->_tpl_vars['LANG']['clientareabwusage']; ?>
:</h4> <?php echo $this->_tpl_vars['bwusage']; ?>
MB / <?php echo $this->_tpl_vars['bwlimit']; ?>
MB (<?php echo $this->_tpl_vars['bwpercent']; ?>
)</p>
                    <div class="usagecontainer"><div class="used" style="width:<?php echo $this->_tpl_vars['bwpercent']; ?>
"></div></div>
                </div>
                <div class="clear"></div>
                <?php endif; ?>
                <?php $_from = $this->_tpl_vars['configurableoptions']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['configoption']):
?>
                <div class="col2half">
                    <h4><?php echo $this->_tpl_vars['configoption']['optionname']; ?>
:</h4> <span><?php if ($this->_tpl_vars['configoption']['optiontype'] == 3): ?><?php if ($this->_tpl_vars['configoption']['selectedqty']): ?><?php echo $this->_tpl_vars['LANG']['yes']; ?>
<?php else: ?><?php echo $this->_tpl_vars['LANG']['no']; ?>
<?php endif; ?><?php elseif ($this->_tpl_vars['configoption']['optiontype'] == 4): ?><?php echo $this->_tpl_vars['configoption']['selectedqty']; ?>
 x <?php echo $this->_tpl_vars['configoption']['selectedoption']; ?>
<?php else: ?><?php echo $this->_tpl_vars['configoption']['selectedoption']; ?>
<?php endif; ?></span>
                </div>
                <?php endforeach; endif; unset($_from); ?>
                <?php $_from = $this->_tpl_vars['productcustomfields']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['customfield']):
?>
                <div class="col2half">
                    <p><h4><?php echo $this->_tpl_vars['customfield']['name']; ?>
:</h4> <span><?php echo $this->_tpl_vars['customfield']['value']; ?>
</span>
                </div>
                <?php endforeach; endif; unset($_from); ?>
                <div class="clear"></div>
                <?php if ($this->_tpl_vars['moduleclientarea']): ?><div class="moduleoutput"><?php echo ((is_array($_tmp=$this->_tpl_vars['moduleclientarea'])) ? $this->_run_mod_handler('replace', true, $_tmp, 'modulebutton', 'btn') : smarty_modifier_replace($_tmp, 'modulebutton', 'btn')); ?>
</div><?php endif; ?>
                <br />
                <br />
                <br />
            </div>
        </div>

    </div>

</div>
<div data-toggle="tab" id="tab2" class="tab-content">

    <div class="row">

        <div class="col30">
            <div class="internalpadding">
                <div class="styled_title"><h2><?php echo $this->_tpl_vars['LANG']['serverchangepassword']; ?>
</h2></div>
                <p><?php echo $this->_tpl_vars['LANG']['serverchangepasswordintro']; ?>
</p>
            </div>
        </div>
        <div class="col70">
            <div class="internalpadding">

                <form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>
?action=productdetails">
                <input type="hidden" name="id" value="<?php echo $this->_tpl_vars['id']; ?>
" />
                <input type="hidden" name="modulechangepassword" value="true" />

                  <fieldset class="onecol">

                    <?php if ($this->_tpl_vars['username']): ?><div class="control-group">
                	    <label class="control-label" for="password"><?php echo $this->_tpl_vars['LANG']['serverusername']; ?>
/<?php echo $this->_tpl_vars['LANG']['serverpassword']; ?>
</label>
                		<div class="controls">
                		    <?php echo $this->_tpl_vars['username']; ?>
<?php if ($this->_tpl_vars['password']): ?> / <?php echo $this->_tpl_vars['password']; ?>
<?php endif; ?>
                		</div>
                	</div><?php endif; ?>

                    <div class="control-group">
                	    <label class="control-label" for="password"><?php echo $this->_tpl_vars['LANG']['newpassword']; ?>
</label>
                		<div class="controls">
                		    <input type="password" name="newpw" id="password" />
                		</div>
                	</div>

                    <div class="control-group">
                	    <label class="control-label" for="confirmpw"><?php echo $this->_tpl_vars['LANG']['confirmnewpassword']; ?>
</label>
                		<div class="controls">
                		    <input type="password" name="confirmpw" id="confirmpw" />
                		</div>
                	</div>

                    <div class="control-group">
                	    <label class="control-label" for="passstrength"><?php echo $this->_tpl_vars['LANG']['pwstrength']; ?>
</label>
                		<div class="controls">
                            <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => ($this->_tpl_vars['template'])."/pwstrength.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
                		</div>
                	</div>

                  </fieldset>

                  <div class="form-actions">
                    <input class="btn btn-primary" type="submit" name="submit" value="<?php echo $this->_tpl_vars['LANG']['clientareasavechanges']; ?>
" />
                    <input class="btn" type="reset" value="<?php echo $this->_tpl_vars['LANG']['cancel']; ?>
" />
                  </div>

                </form>

            </div>
        </div>

    </div>

</div>
<div data-toggle="tab" id="tab3" class="tab-content">

    <div class="row">

        <div class="col30">
            <div class="internalpadding">
                <div class="styled_title"><h2><?php echo $this->_tpl_vars['LANG']['downloadstitle']; ?>
</h2></div>
                <p><?php echo $this->_tpl_vars['LANG']['clientareahostingaddonsintro']; ?>
</p>
            </div>
        </div>
        <div class="col70">
            <div class="internalpadding">
                <?php $_from = $this->_tpl_vars['downloads']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['download']):
?>
                <p><h4><?php echo $this->_tpl_vars['download']['title']; ?>
 - <a href="<?php echo $this->_tpl_vars['download']['link']; ?>
"><?php echo $this->_tpl_vars['LANG']['downloadname']; ?>
</a></h4> <?php echo $this->_tpl_vars['download']['description']; ?>
</p>
                <?php endforeach; endif; unset($_from); ?>
            </div>
        </div>

    </div>

</div>
<div data-toggle="tab" id="tab4" class="tab-content">

    <div class="row">

        <div class="col30">
            <div class="internalpadding">
                <div class="styled_title"><h2><?php echo $this->_tpl_vars['LANG']['clientareahostingaddons']; ?>
</h2></div>
                <p><?php echo $this->_tpl_vars['LANG']['yourclientareahostingaddons']; ?>
</p>
                <br />
                <?php if ($this->_tpl_vars['addonsavailable']): ?><p><a href="cart.php?gid=addons&pid=<?php echo $this->_tpl_vars['id']; ?>
"><?php echo $this->_tpl_vars['LANG']['orderavailableaddons']; ?>
</a></p><?php endif; ?>
            </div>
        </div>
        <div class="col70">
            <div class="internalpadding">
                <table class="table table-striped table-framed table-centered">
                    <thead>
                        <tr>
                            <th><?php echo $this->_tpl_vars['LANG']['clientareaaddon']; ?>
</th>
                            <th><?php echo $this->_tpl_vars['LANG']['clientareaaddonpricing']; ?>
</th>
                            <th><?php echo $this->_tpl_vars['LANG']['clientareahostingnextduedate']; ?>
</th>
                            <th><?php echo $this->_tpl_vars['LANG']['clientareastatus']; ?>
</th>
                        </tr>
                    </thead>
                    <tbody>
                    <?php $_from = $this->_tpl_vars['addons']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['num'] => $this->_tpl_vars['addon']):
?>
                        <tr>
                            <td><?php echo $this->_tpl_vars['addon']['name']; ?>
</td>
                            <td class="textcenter"><?php echo $this->_tpl_vars['addon']['pricing']; ?>
</td>
                            <td class="textcenter"><?php echo $this->_tpl_vars['addon']['nextduedate']; ?>
</td>
                            <td class="textcenter"><span class="label <?php echo $this->_tpl_vars['addon']['rawstatus']; ?>
"><?php echo $this->_tpl_vars['addon']['status']; ?>
</span></td>
                        </tr>
                    <?php endforeach; else: ?>
                        <tr>
                            <td class="textcenter" colspan="4"><?php echo $this->_tpl_vars['LANG']['clientareanoaddons']; ?>
</td>
                        </tr>
                    <?php endif; unset($_from); ?>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

</div>