
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="REFRESH" content="15;url=/trackingChef">
        <title>Logout</title>
          <link href="<%=request.getContextPath()%>/resources/css/default.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/css/cssLayout.css" rel="stylesheet" type="text/css" />
   
    </head>
    <body>
        <div id="index-content">
            <%
                out.println("Merci " + request.getRemoteUser() + " à bientôt");
                session.invalidate();
            %>
            <center>
                Vous êtes déconnecté avec succès.
                <!--<a href="login.jsp">login</a>-->
                <a href="<%=request.getContextPath()%>">login</a>
            </center>
        </div>
    </body>
</html>
