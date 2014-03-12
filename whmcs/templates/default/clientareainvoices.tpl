{include file="$template/pageheader.tpl" title=$LANG.invoices desc=$LANG.invoicesintro}

<div class="searchbox">
<span class="invoicetotal">{$LANG.invoicesoutstandingbalance}: <span class="text{if $nobalance}green{else}red{/if}">{$totalbalance}</span></span>{if $masspay}&nbsp; <a href="clientarea.php?action=masspay&all=true" class="btn btn-success"><i class="icon-ok-circle icon-white"></i> {$LANG.masspayall}</a>{/if}
</div>

<div class="resultsbox">
<p>{$numitems} {$LANG.recordsfound}, {$LANG.page} {$pagenumber} {$LANG.pageof} {$totalpages}</p>
</div>

<div class="clear"></div>

<form method="post" action="clientarea.php?action=masspay">
<table class="table table-striped table-framed table-centered">
    <thead>
        <tr>
            <th{if $orderby eq "id"} class="headerSort{$sort}"{/if}><a href="clientarea.php?action=invoices&orderby=id">{$LANG.invoicestitle}</a></th>
            <th{if $orderby eq "date"} class="headerSort{$sort}"{/if}><a href="clientarea.php?action=invoices&orderby=date">{$LANG.invoicesdatecreated}</a></th>
            <th{if $orderby eq "duedate"} class="headerSort{$sort}"{/if}><a href="clientarea.php?action=invoices&orderby=duedate">{$LANG.invoicesdatedue}</a></th>
            <th{if $orderby eq "total"} class="headerSort{$sort}"{/if}><a href="clientarea.php?action=invoices&orderby=total">{$LANG.invoicestotal}</a></th>
            <th{if $orderby eq "status"} class="headerSort{$sort}"{/if}><a href="clientarea.php?action=invoices&orderby=status">{$LANG.invoicesstatus}</a></th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
{foreach from=$invoices item=invoice}
        <tr>
            <td><a href="viewinvoice.php?id={$invoice.id}" target="_blank"><strong>{$invoice.invoicenum}</strong></a></td>
            <td>{$invoice.datecreated}</td>
            <td>{$invoice.datedue}</td>
            <td>{$invoice.total}</td>
            <td><span class="label {$invoice.rawstatus}">{$invoice.statustext}</span></td>
            <td class="textcenter"><a href="viewinvoice.php?id={$invoice.id}" target="_blank" class="btn">{$LANG.invoicesview}</a></td>
        </tr>
{foreachelse}
        <tr>
            <td colspan="6" class="textcenter">{$LANG.norecordsfound}</td>
        </tr>
{/foreach}
    </tbody>
</table>
</form>
<div class="recordslimit">
    <form method="post" action="{$smarty.server.PHP_SELF}?action={$clientareaaction}" />
    <select name="itemlimit" onchange="submit()">
        <option>{$LANG.resultsperpage}</option>
        <option value="10"{if $itemlimit==10} selected{/if}>10</option>
        <option value="25"{if $itemlimit==25} selected{/if}>25</option>
        <option value="50"{if $itemlimit==50} selected{/if}>50</option>
        <option value="100"{if $itemlimit==100} selected{/if}>100</option>
        <option value="all"{if $itemlimit==99999999} selected{/if}>{$LANG.clientareaunlimited}</option>
    </select>
    </form>
</div>

<div class="pagination">
    <ul>
        <li class="prev{if !$prevpage} disabled{/if}"><a href="{if $prevpage}clientarea.php?action=invoices{if $q}&q={$q}{/if}&amp;page={$prevpage}{else}javascript:return false;{/if}">&larr; {$LANG.previouspage}</a></li>
        <li class="next{if !$nextpage} disabled{/if}"><a href="{if $nextpage}clientarea.php?action=invoices{if $q}&q={$q}{/if}&amp;page={$nextpage}{else}javascript:return false;{/if}">{$LANG.nextpage} &rarr;</a></li>
    </ul>
</div>