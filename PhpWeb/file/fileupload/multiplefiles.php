<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<body>

        <form action="multiplefiles.php" method="Post">
      Select images: <input type="file" name="img[]" multiple>
      <input type="submit">
    </form>

    <p>Try selecting more than one file when browsing for files.</p>

    <p><strong>Note:</strong> The multiple attribute of the input tag is not supported in Internet Explorer 9 and earlier versions.</p>
    <?php
    print_r($_POST);
   
   // print_r($_FILES);//empty array!
    goto b;
    print("a");
    b:print "point b";
    
    ?>

<form action="file-upload.php" method="post" enctype="multipart/form-data">
  Send these files:<br />
  <input name="userfile[]" type="file" /><br />
  <input name="userfile[]" type="file" /><br />
  <input type="submit" value="Send files" />
</form>
</body>
</html>
