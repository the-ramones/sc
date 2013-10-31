<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <form id="login-form" name="login-form">
        <h2><spring:message code="login.welcome" /></h2>
        <label for="login"><spring:message code="login.login" /></label>
        <input type="text" id="login" name="login" /> <br />
        <label for="password"><spring:message code="login.password" /></label>
        <input type="password" id="password" name="password" /> <br />
        <label for="language"><spring:message code="login.language" /></label>
        <select id="language" name="language">
            <%--<c:forEach items="languages" var="lang">
                    <option><c:out value="${lang}" /></option> 
                </c:forEach>--%>
            <option><spring:message code="en" /></option>
            <option><spring:message code="ru" /></option>
            <option><spring:message code="be" /></option>
        </select> <br />
        <label for="rememberme"><spring:message code="login.rememberme" />?</label>
        <input type="checkbox" id="rememberme" name="rememberMe" /> <br />
        <label id="error" class="invisible"></label> 
        <input type="button" id="confirm" name="confirm" value="<spring:message code="login.enter" />" />
    </form>
</div>
