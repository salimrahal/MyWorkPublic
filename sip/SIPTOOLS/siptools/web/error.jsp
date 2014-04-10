
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="REFRESH" content="15;url=/trakingEtud">
        <title>Informations Erreur</title>
       
           <link href="/trackingChef/resources/css/default.css" rel="stylesheet" type="text/css" />
        <link href="/trackingChef/resources/css/cssLayout.css" rel="stylesheet" type="text/css" />
   

    </head>
    <body>
        <div id="index-content">
            <%
                //out.println("Merci " + request.getRemoteUser() + " à bientôt");
                session.invalidate();
            %>
              <b>Utilisateur [<%=request.getRemoteUser()%>] ou mot de passe erroné / Ou permission non accordée</b>

                <p>Revenir à la page 
                   <strong><a href="<%=request.getContextPath()%>"> d’authentification </a></strong>.
               </p>
        </div>
    </body>
</html>
