

{if $pagetitle eq $LANG.carttitle}</div>{/if}

    </div>
</div>

<div class="footerdivider">
    <div class="fill"></div>
</div>
 <p style="color: red">footer.tpl</p>
<div class="whmcscontainer">
    <div class="footer">
        <div id="copyright">{$LANG.copyright} &copy; {$date_year} {$companyname}. {$LANG.allrightsreserved}.</div>
        {if $langchange}<div id="languagechooser">{$setlanguage}</div>{/if}
        <div class="clear"></div>
    </div>
</div>

{$footeroutput}

</body>
</html>