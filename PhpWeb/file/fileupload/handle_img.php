<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
       <form enctype="multipart/form-data" action="handle_img.php" method="post">
<input type="hidden" name="MAX_FILE_SIZE" value="16000" />
Send this file: <input name="book_image" type="file" /><br />
<input type="submit" value="Upload" />
</form>
        <?php
        print_r($_FILES['book_image']) ;
        ?>  
        
    </body>
</html>
