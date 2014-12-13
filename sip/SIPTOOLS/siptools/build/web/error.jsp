
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="REFRESH" content="15;url=/siptools">
        <title>Incorrect credentials</title>
       
        <link href="<%=request.getContextPath()%>/resources/css/cssLayout.css" rel="stylesheet" type="text/css" />
   

    </head>
    <body>
        <div class="center_content">
            <%
                //out.println("Merci " + request.getRemoteUser() + " à bientôt");
                session.invalidate();
            %>
              <b>Incorrect User [<%=request.getRemoteUser()%>] or incorrect password</b>

                <p>Back to
                   <strong><a href="<%=request.getContextPath()%>"> Login </a></strong>.
               </p>
        </div>
    </body>
</html>
