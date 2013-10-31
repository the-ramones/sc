<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="container">
    <h2><spring:message code="welcome" />, <span class="user"><c:out value="${user.username}" /></span></h2>
    <div class="info">
        <spring:message code="logout" /><br />
        <spring:message code="lang" />
    </div><img src="" />
    <p><a href="<spring:url value="/logout" />"><spring:message code="logout" /></a></p>
</div>