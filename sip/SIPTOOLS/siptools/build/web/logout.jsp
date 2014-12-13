
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="REFRESH" content="15;url=/siptools">
        <title>Logout</title>
        <link href="<%=request.getContextPath()%>/resources/css/cssLayout.css" rel="stylesheet" type="text/css" />
   
    </head>
    <body>
        <div class="center_content">
            <%
                out.println("Thank you ");//" + request.getRemoteUser() + "."
                session.invalidate();
            %>
            <center>
                Successfully disconnected.
                <!--<a href="login.jsp">login</a>-->
                <a href="<%=request.getContextPath()%>">Home page</a>
            </center>
        </div>
    </body>
</html>
