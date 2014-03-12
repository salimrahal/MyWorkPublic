{* Smarty *}

<form action="{$SCRIPT_NAME}?action=submit" method="post">
    <p>$SCRIPT_NAME is {$SCRIPT_NAME} </p>
  <table border="1">
    {if $error ne ""}
      <tr>
      <td bgcolor="red" colspan="2">
      {if $error eq "name_empty"}You must supply a name.
      {elseif $error eq "comment_empty"} You must supply a comment.
      {elseif $error eq "email_empty"} You must supply an email.
      {/if}
      </td>
      </tr>
    {/if}
    <tr>
      <td>Name:</td>
      <td>
        <input type="text" name="Name"
          value="{$post.Name|escape}" size="40">
      </td>
    </tr>
    <tr>
      <td valign="top">Comment:</td>
      <td><textarea name="Comment" cols="40" rows="10">{$post.Comment|escape}</textarea></td>
    </tr>
     <tr>
      <td valign="top">Email:</td>
      <td><textarea name="Email" cols="40" rows="10">{$post.Email|escape}</textarea></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="submit" value="Submit">
      </td>
    </tr>
  </table>
</form>
