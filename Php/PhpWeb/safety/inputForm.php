<!--
Desc 
       This exampe take two param, save the pssd 
       as SHA1 in the database and then verify it
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
       <!--Desc 
       This exampe take two param, save the pssd 
       as SHA1 in the database and then verify it
       1- create DB: my_own_bookshop
       2-mysql> CREATE TABLE users (
    -> email
    -> VARCHAR(128) NOT NULL PRIMARY KEY,
    -> passwd CHAR(40) NOT NULL
    -> );
       3- submit this form
       4- verify the record in the DB
       mysql> select * from users;
+--------------------------+------------------------------------------+
| email                    | passwd                                   |
+--------------------------+------------------------------------------+
| salim.rahal101@gmail.com | 7c4a8d09ca3762af61e59520943dc26494f8941b |
+--------------------------+------------------------------------------+

       -->       
        <form method="post" action="processAuth.php">
                <table>
                    <tr><td>email</td>
                        <td><input type='text' name='email'/></td></tr>
                     <tr><td>pssd</td>
                        <td><input type='text' name='passwd'/></td></tr>
                    <tr>
                        <td colspan='2'>
                            <input type='submit' name='Save' value='Save'/>
                        </td>
                    </tr>
                </table>
        </form>
    </body>
</html>
