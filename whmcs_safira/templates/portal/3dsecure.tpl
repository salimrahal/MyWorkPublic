<br />

<p align="center">{$LANG.creditcard3dsecure}</p>

<p align="center" id="submitfrm">{$code}</p>

<p align="center"><iframe name="3dauth" width="400" height="500" scrolling="auto" src="about:blank" style="border:1px solid #fff;"></iframe></p>

<br /><br /><br />

{literal}
<script language="javascript">
setTimeout ( "autoForward()" , 1000 );
function autoForward() {
	var submitForm = $("#submitfrm").find("form");
    submitForm.submit();
}
</script>
{/literal}