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
            <h2><fmt:message key="auth.login.welcome"/><br><em>CDL Blog</em></h2>
            <div class="auth-feats">
                <div class="af"><span>📰</span><span><fmt:message key="auth.feat.manage"/></span></div>
                <div class="af"><span>💬</span><span><fmt:message key="auth.feat.comment"/></span></div>
                <div class="af"><span>🔐</span><span><fmt:message key="auth.feat.secure"/></span></div>
            </div>
        </div>
    </div>
    <div class="auth-right">
        <div class="auth-card">
            <h3><fmt:message key="auth.login.title"/></h3>
            <p class="auth-sub"><fmt:message key="auth.login.sub"/></p>

            <c:if test="${not empty error}">
                <div class="alert error"><i class="fas fa-exclamation-circle"></i> <fmt:message key="${error}"/></div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert success"><i class="fas fa-check-circle"></i> <fmt:message key="${success}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
                <div class="field">
                    <label><fmt:message key="auth.login.email"/></label>
                    <input type="email" name="email" required placeholder="exemple@email.com">
                </div>
                <div class="field">
                    <div style="display: flex; justify-content: space-between;">
                        <label><fmt:message key="auth.login.password"/></label>
                        <a href="${pageContext.request.contextPath}/forgot-password" style="font-size: 0.8rem; font-weight: 600;"><fmt:message key="auth.login.forgot"/></a>
                    </div>
                    <div class="pwd-wrap">
                        <input type="password" name="password" id="pwd" required placeholder="••••••••">
                        <button type="button" class="toggle-pwd" onclick="togglePwd()">
                            <i class="fas fa-eye" id="eyeIcon"></i>
                        </button>
                    </div>
                </div>
                <button type="submit" class="submit-btn"><fmt:message key="auth.login.submit"/></button>
            </form>

            <p class="switch-link">
                <fmt:message key="auth.login.noaccount"/>
                <a href="${pageContext.request.contextPath}/register"><fmt:message key="nav.register"/></a>
            </p>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>