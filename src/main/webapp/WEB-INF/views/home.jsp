<%-- 
    Document   : container
    Created on : Oct 31, 2013, 12:42:15 AM
    Author     : the-ramones
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="home.title" /></title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sc.css" />">
        <script src="<c:url value="/resources/js/libs/jquery-1.10.2.js" />" type="text/javascript"></script>
    </head>
    <body>
        <c:choose>            
            <c:when test="${authenticated}">
                <jsp:include page="service.jsp" />
            </c:when>
            <c:otherwise> 
                <jsp:include page="login.jsp" />
            </c:otherwise>
        </c:choose>

        <script src="<c:url value="/resources/js/sc.js" />" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#confirm").click(function(e) {
                    e.preventDefault();
                    var credentials = $("#login-form").serialize();
                    login(credentials);
                    return false;
                });
            });

            function login(credentials) {
                $.ajax("<c:url value="/login" />", {
                    method: "POST",
                    async: true,
                    cache: false,
                    data: credentials,
                    error: onErrorHandler,
                    success: onSuccessHandler,
                    dataType: "text"
                });

            }

            function onErrorHandler() {
                $("#error").text("Cannot login due to network error");
            }

            function onSuccessHandler(auth) {
                if (auth === "authenticated") {
                    $("body").empty();
                    $("body").load("<c:url value="/service" />");
                } else {
                    $("#error").text("CHeck your credentials");
                }
            }
        </script>
    </body>
</html>