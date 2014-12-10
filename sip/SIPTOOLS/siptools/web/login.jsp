<%-- 
    Document   : login
    Created on : 9 déc. 2012, 12:07:18
    Author     : pascalfares
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SIP</title>
           <link href="<%=request.getContextPath()%>/resources/css/default.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/css/cssLayout.css" rel="stylesheet" type="text/css" />
   
    </head>
    <body onload="document.forms[0].j_username.focus()">
        <div id="index-content">
            <H2 ALIGN="center">Welcome Admin access</H2>
           
           <form action="j_security_check" method=post>
                <table align="center">
                    <tr>
                        <td>User Name:</td>
                        <td><input type="text" name="j_username"></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="j_password"></td>
                    </tr>
                </table>
                <br><p align="center"> <input type="submit" value="Valider"></p>
            </form>
        </div>
    </body>
</html>
