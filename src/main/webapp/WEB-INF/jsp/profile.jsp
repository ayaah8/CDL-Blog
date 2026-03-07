<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <h1 style="margin-bottom:1.5rem;font-family:'Playfair Display',serif;">
        <i class="fas fa-user-cog"></i> <fmt:message key="profile.title"/>
    </h1>

    <!-- Profile header card -->
    <div class="form-card" style="margin-bottom:1.5rem;display:flex;align-items:center;gap:1.5rem;padding:1.5rem 2rem;">
        <div class="big-avatar">${sessionScope.user.username.substring(0,1).toUpperCase()}</div>
        <div>
            <h3 style="font-size:1.2rem;font-weight:800;">${sessionScope.user.username}</h3>
            <p style="color:var(--muted);font-size:.85rem;">${sessionScope.user.email}</p>
            <span class="badge ${sessionScope.user.admin ? 'pub' : 'draft'}" style="margin-top:6px;display:inline-block;">
                ${sessionScope.user.role}
            </span>
        </div>
    </div>

    <div class="profile-grid">
        <!-- Update profile -->
        <div class="form-card">
            <h4><i class="fas fa-id-card"></i> <fmt:message key="profile.title"/></h4>

            <c:if test="${not empty success}">
                <div class="alert success"><i class="fas fa-check-circle"></i> <fmt:message key="${success}"/></div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error"><i class="fas fa-exclamation-circle"></i> <fmt:message key="${error}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/member/profile" method="post" class="auth-form">
                <input type="hidden" name="action" value="updateProfile">
                <div class="field">
                    <label><fmt:message key="profile.username"/></label>
                    <input type="text" name="username" required value="${sessionScope.user.username}">
                </div>
                <div class="field">
                    <label><fmt:message key="profile.email"/></label>
                    <input type="email" value="${sessionScope.user.email}" disabled>
                </div>
                <div class="field">
                    <label><fmt:message key="profile.bio"/></label>
                    <textarea name="bio" rows="3"
                              placeholder="Parlez de vous...">${sessionScope.user.bio}</textarea>
                </div>
                <button type="submit" class="submit-btn">
                    <i class="fas fa-save"></i> <fmt:message key="profile.save"/>
                </button>
            </form>
        </div>

        <!-- Change password -->
        <div class="form-card">
            <h4><i class="fas fa-key"></i> <fmt:message key="profile.changepassword"/></h4>

            <c:if test="${not empty pwdSuccess}">
                <div class="alert success"><i class="fas fa-check-circle"></i> ${pwdSuccess}</div>
            </c:if>
            <c:if test="${not empty pwdError}">
                <div class="alert error"><i class="fas fa-exclamation-circle"></i> ${pwdError}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/member/profile" method="post" class="auth-form">
                <input type="hidden" name="action" value="changePassword">
                <div class="field">
                    <label><fmt:message key="profile.oldpassword"/></label>
                    <input type="password" name="oldPassword" required placeholder="••••••••">
                </div>
                <div class="field">
                    <label><fmt:message key="profile.newpassword"/></label>
                    <input type="password" name="newPassword" required minlength="6" placeholder="••••••••">
                   
                </div>
                <div class="field">
                    <label><fmt:message key="profile.confirmpassword"/></label>
                    <input type="password" name="confirmPassword" required minlength="6" placeholder="••••••••">
                    
                </div>
                <button type="submit" class="submit-btn" style="background:var(--indigo);">
                    <i class="fas fa-key"></i> <fmt:message key="profile.changepassword"/>
                </button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
