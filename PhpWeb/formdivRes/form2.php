<!--
To change this template, choose Tools | Templates
and open the template in the editor.

http://stackoverflow.com/questions/12744566/submitting-form-into-a-div
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script src="../jquery-1.10.1.min.js"></script>
        <script type="text/javascript">

            jQuery(document).ready(function($) {
                $("#Submit").click(function() {

                    var url = "add.php"; // the script where you handle the form input.

                    $.ajax({
                        type: "POST",
                        url: url,
                        data: $("#myForm").serialize(), // serializes the form's elements.
                        success: function(html) {
                            $("#right").html(html);
                        }
                    });

                    return false; // avoid to execute the actual submit of the form.
                });
            });
        </script>
    </head>

    <body>
        <div id="right">
        </div>
        <form action="add.php" method="post" enctype="multipart/form-data" id ="myForm">
            Name: <input type="text" name="name"><br> 
            E-mail: <input type="text" name = "email"><br>
            Phone: <input type="text" name = "phone"><br>
            Photo: <input type="file" name="photo"><br>
            <input type="submit" value="Upload" id="Submit">

        </form>
    </body>
</html>
