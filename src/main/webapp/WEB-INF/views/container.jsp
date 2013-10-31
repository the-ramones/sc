<%-- 
    Document   : container
    Created on : Oct 31, 2013, 12:42:15 AM
    Author     : the-ramones
--%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="home.title" /></title>
    </head>
    <body>
        <jsp:include page="home.jsp" flush="true" />
    </body>
</html>
