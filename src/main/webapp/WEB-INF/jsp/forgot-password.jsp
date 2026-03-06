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
            <h2><fmt:message key="auth.forgot.title"/><br><em><fmt:message key="auth.forgot.title2"/></em></h2>
            <p style="margin-top: 1rem; color: #ede9fe; font-size: 1.1rem;"><fmt:message key="auth.forgot.desc"/></p>
        </div>
    </div>
    <div class="auth-right">
        <div class="auth-card">
            <h3><fmt:message key="auth.forgot.cardtitle"/></h3>
            <p class="auth-sub"><fmt:message key="auth.forgot.cardsub"/></p>

            <c:if test="${not empty error}">
                <div class="alert error"><i class="fas fa-exclamation-circle"></i> <fmt:message key="${error}"/></div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert success"><i class="fas fa-check-circle"></i> <fmt:message key="${success}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/forgot-password" method="post" class="auth-form">
                <div class="field">
                    <label><fmt:message key="auth.forgot.email"/></label>
                    <input type="email" name="email" required placeholder="exemple@email.com">
                </div>
                <button type="submit" class="submit-btn"><fmt:message key="auth.forgot.submit"/></button>
            </form>

            <p class="switch-link" style="margin-top: 2rem;">
                <a href="${pageContext.request.contextPath}/login"><fmt:message key="auth.forgot.back"/></a>
            </p>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>