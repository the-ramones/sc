<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sc.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="container">
    <h2><spring:message code="welcome" />, <span class="user"><c:out value="${user.getFirstname()}" /> <c:out value="${user.getLastname()}" /></span></h2>
    <div class="info">
        <spring:message code="your.lang" />:<br />
        <spring:message code="lang" />
    </div><img class="clearfix" src="<spring:url value="/resources/img/${flag}" />" /><br />
    <p><a id="logout" href=""><spring:message code="logout" /></a>
    <label class="error invisible"></label>
</p>
</div>

<script type="text/javascript">
    
    $("#logout").click(function(e) {
        e.preventDefault();
        $.ajax("<c:url value="/logout.do" />", {
            method: "POST",
            async: true,
            cache: false,
            dataType: "text",
            data: {},
            success: successHandler,
            error: errorHandler
        });
        return false;
    }); 

    
    function errorHandler(jqXHR, status) {
        $("label.error").text("Cannot logout. Try later");
    }
    
    function successHandler(res) {
        if (res === "success") {
            $("body").empty();
            $("body").load("<c:url value="/login" />");
        } 
    }    
</script>