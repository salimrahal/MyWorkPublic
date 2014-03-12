{if $affiliatesystemenabled}

<p>{$LANG.affiliatesintrotext}</p>
<ul>
<li>{$LANG.affiliatesbullet1} {$bonusdeposit}</li>
<li>{$LANG.affiliatesearn} <strong>{$payoutpercentage}</strong> {$LANG.affiliatesbullet2}</li>
</ul>
<p>{$LANG.affiliatesfootertext}</p>
<br />
<p align="center"><input type="button" value="{$LANG.affiliatesactivate}" onclick="window.location='affiliates.php?activate=true'" class="buttongo" /></p>

{else}

<p>{$LANG.affiliatesdisabled}</p>

{/if}