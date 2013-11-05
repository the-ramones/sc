<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <form id="login-form" name="login-form">
        <h2><spring:message code="login.welcome" /></h2>
        <div class="form-line clearix">
            <label for="login"><spring:message code="login.login" /></label>        
            <input type="text" id="login" name="login" />
        </div>
        <div class="form-line clearix">
            <label for="password"><spring:message code="login.password" /></label>
            <input type="password" id="password" name="password" />
        </div>
        <div class="form-line clearix">
            <label for="language"><spring:message code="login.language" /></label>
            <select id="language" name="language">
                <option selected disabled><spring:message code="login.select" /></option>
                <option value="en"><spring:message code="en" /></option>
                <option value="ru"><spring:message code="ru" /></option>
                <option value="be"><spring:message code="be" /></option>
            </select>
        </div>
        <div class="form-line clearfix">
            <label for="rememberme"><spring:message code="login.rememberme" />?</label>
            <input type="checkbox" id="rememberme" name="rememberMe" />
        </div>
        <div class="form-line clearfix">
            <label id="error" class="error invisible"></label> 
        </div>
        <div class="form-line clearfix">
            <input type="button" id="confirm" name="confirm" value="<spring:message code="login.enter" />" />
        </div>
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
        $.ajax("<c:url value="/login.do" />", {
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
        var msg = "Cannot perform login due to network error";
        $.post("<c:url value="/i18n" />", {"code": "error.net"}, function(data) {
            $("#error").text(data);
        }, 'text');
        $('input[type="password"]').val('');
        $('input[type="checkbox"]').prop("checked", false);
    }

    function onSuccessHandler(auth) {
        if (auth === "authenticated") {
            $("body").empty();
            $("body").load("<c:url value="/service" />");
        } else {
            var msg = "Check your credentials";
            $.post("<c:url value="/i18n" />", {"code": "error.invalid"}, function(data) {
                $("#error").text(data);
            }, 'text');
            $('input[type="password"]').val('');
            $('input[type="checkbox"]').prop("checked", false);
        }
    }
</script>