<%-- 
    Document   : index
    Created on : Apr 23, 2013, 2:10:14 PM
    Author     : salim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSTL</title>
    </head>
    <body>
        <c:out value="du out"></c:out>
        <p>Simple Example for C:out</p>

       Multiply 85 and 2::<c:out value="${85*2}" /> 
       </br>
       <c:forEach var="i" begin="1" end="10">
 ${i}
</c:forEach>

    </body>
</html>