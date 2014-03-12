<%-- 
    Document   : index
    Created on : Feb 7, 2014, 2:39:36 PM
    Author     : salim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Server host name in jsp</title>
    </head>
    <body>
        <%
 String hostName=request.getServerName();
 String remoteAddr = request.getRemoteHost();
%>
        <h1> Host Name of server: <%=hostName%></h1><!--Host Name of server: localhost -->
        <h1> remoteAddr: <%=remoteAddr%></h1><!--127.0.0.1 -->
    </body>
</html>
