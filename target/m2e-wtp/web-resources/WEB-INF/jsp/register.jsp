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
            <h2><fmt:message key="auth.register.join"/><br><em>CDL Blog</em></h2>
            <p><fmt:message key="auth.register.desc"/></p>
            <div class="auth-feats">
                <div class="af"><span>✅</span><span><fmt:message key="auth.feat.publish"/></span></div>
                <div class="af"><span>💬</span><span><fmt:message key="auth.feat.comment"/></span></div>
                <div class="af"><span>🔐</span><span><fmt:message key="auth.feat.secure"/></span></div>
            </div>
        </div>
    </div>
    <div class="auth-right">
        <div class="auth-card">
            <h3><fmt:message key="auth.register.title"/></h3>
            <p class="auth-sub"><fmt:message key="auth.register.sub"/></p>

            <c:if test="${not empty error}">
                <div class="alert error"><i class="fas fa-exclamation-circle"></i> <fmt:message key="${error}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post" class="auth-form">
                <div class="field">
                    <label><fmt:message key="auth.register.username"/></label>
                    <input type="text" name="username" required minlength="3" maxlength="50" placeholder="johndoe">
                </div>
                <div class="field">
                    <label><fmt:message key="auth.register.email"/></label>
                    <input type="email" name="email" required placeholder="exemple@email.com">
                </div>
                <div class="field">
                    <label><fmt:message key="auth.register.password"/></label>
                    <input type="password" name="password" required minlength="6" placeholder="••••••••">
                </div>
                <div class="field">
                    <label><fmt:message key="auth.register.confirm"/></label>
                    <input type="password" name="confirm" required minlength="6" id="confirm" placeholder="••••••••">
                </div>
                <button type="submit" class="submit-btn"><fmt:message key="auth.register.submit"/></button>
            </form>

            <p class="switch-link">
                <fmt:message key="auth.register.hasaccount"/>
                <a href="${pageContext.request.contextPath}/login"><fmt:message key="nav.login"/></a>
            </p>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>