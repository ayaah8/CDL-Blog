<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>

<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CDL Blog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700;800&family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<nav class="navbar">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">
            ✍ CDL<span>Blog</span>
        </a>

        <div class="nav-links" id="navLinks">
            <a href="${pageContext.request.contextPath}/articles">
                <fmt:message key="nav.articles"/>
            </a>

            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/member/articles">
                        <fmt:message key="nav.myarticles"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/member/profile" class="nav-user">
                        <i class="fas fa-user-circle"></i> ${sessionScope.user.username}
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="btn-nav-outline">
                        <fmt:message key="nav.logout"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">
                        <fmt:message key="nav.login"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/register" class="btn-nav-solid">
                        <fmt:message key="nav.register"/>
                    </a>
                </c:otherwise>
            </c:choose>

            <div class="lang-pill">
                <a href="${pageContext.request.contextPath}/lang?lang=fr"
                   class="lp ${lang == 'fr' ? 'on' : ''}">FR</a>
                <a href="${pageContext.request.contextPath}/lang?lang=en"
                   class="lp ${lang == 'en' ? 'on' : ''}">EN</a>
            </div>
        </div>

        <button class="nav-toggle" onclick="document.getElementById('navLinks').classList.toggle('open')">
            <i class="fas fa-bars"></i>
        </button>
    </div>
</nav>

<main class="main-wrap">
