<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <form id="login-form" name="login-form">
        <h2><spring:message code="login.welcome" /></h2>
        <label for="login"><spring:message code="login.login" /></label>
        <input type="text" id="login" name="login" /> <br />
        <label for="password"><spring:message code="login.password" /></label>
        <input type="password" id="password" name="password" /> <br />
        <label for="language"><spring:message code="login.language" /></label>
        <select id="language" name="language">
            <option selected></option>
            <option value="en"><spring:message code="en" /></option>
            <option value="ru"><spring:message code="ru" /></option>
            <option value="be"><spring:message code="be" /></option>
        </select> <br />
        <label for="rememberme"><spring:message code="login.rememberme" />?</label>
        <input type="checkbox" id="rememberme" name="rememberMe" /> <br />
        <label id="error" class="error invisible"></label> 
        <input type="button" id="confirm" name="confirm" value="<spring:message code="login.enter" />" />
    </form>
</div>

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
            $("#error").text("Check your credentials");
        }
    }
</script>