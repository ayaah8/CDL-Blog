<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="auth-wrap">
    <div class="auth-left">
        <div class="auth-left-content">
            <span class="hero-badge">CDL Blog</span>
            <h2><fmt:message key="auth.reset.title"/><br><em><fmt:message key="auth.reset.title2"/></em></h2>
            <p style="margin-top: 1rem; color: #ede9fe; font-size: 1.1rem;"><fmt:message key="auth.reset.desc"/></p>
        </div>
    </div>
    <div class="auth-right">
        <div class="auth-card">
            <h3><fmt:message key="auth.reset.cardtitle"/></h3>
            <p class="auth-sub"><fmt:message key="auth.reset.cardsub"/></p>

            <c:if test="${not empty error}">
                <div class="alert error"><i class="fas fa-exclamation-circle"></i> <fmt:message key="${error}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/reset-password" method="post" class="auth-form">
                <input type="hidden" name="token" value="${token}">

                <div class="field">
                    <label><fmt:message key="auth.reset.newpwd"/></label>
                    <input type="password" name="password" required placeholder="••••••••">
                </div>
                
                <div class="field">
                    <label><fmt:message key="auth.reset.confirm"/></label>
                    <input type="password" name="confirm" required placeholder="••••••••">
                </div>
                
                <button type="submit" class="submit-btn"><fmt:message key="auth.reset.submit"/></button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>