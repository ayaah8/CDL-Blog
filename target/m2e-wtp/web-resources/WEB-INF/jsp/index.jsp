<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<section class="hero">
    <div class="hero-inner">
        <div class="hero-text">
            <span class="hero-badge">✨ CDL / EST Agadir</span>
            <h1><fmt:message key="home.title"/></h1>
            <p><fmt:message key="home.subtitle"/></p>
            <div class="hero-btns">
                <a href="${pageContext.request.contextPath}/articles" class="hbtn solid">
                    <fmt:message key="home.latestarticles"/> →
                </a>
                <c:if test="${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/register" class="hbtn ghost">
                        <fmt:message key="nav.register"/>
                    </a>
                </c:if>
            </div>
        </div>
        <div class="hero-deco">✍</div>
    </div>
</section>

<section class="features-bar">
    <div class="feat"><i class="fas fa-users"></i>
        <h3><c:if test="${lang=='fr'}">Espace Membres</c:if><c:if test="${lang=='en'}">Member Space</c:if></h3>
        <p><c:if test="${lang=='fr'}">Inscription sécurisée avec vérification email.</c:if><c:if test="${lang=='en'}">Secure registration with email verification.</c:if></p>
    </div>
    <div class="feat"><i class="fas fa-newspaper"></i>
        <h3><c:if test="${lang=='fr'}">Articles</c:if><c:if test="${lang=='en'}">Articles</c:if></h3>
        <p><c:if test="${lang=='fr'}">Créez, éditez et publiez vos articles.</c:if><c:if test="${lang=='en'}">Create, edit and publish your articles.</c:if></p>
    </div>
    <div class="feat"><i class="fas fa-comments"></i>
        <h3><c:if test="${lang=='fr'}">Commentaires</c:if><c:if test="${lang=='en'}">Comments</c:if></h3>
        <p><c:if test="${lang=='fr'}">Échangez avec la communauté.</c:if><c:if test="${lang=='en'}">Engage with the community.</c:if></p>
    </div>
    <div class="feat"><i class="fas fa-globe"></i>
        <h3>FR / EN</h3>
        <p><c:if test="${lang=='fr'}">Interface bilingue Français & English.</c:if><c:if test="${lang=='en'}">Bilingual French & English interface.</c:if></p>
    </div>
</section>

<div class="container">
    <div class="sec-hdr">
        <h2><fmt:message key="home.latestarticles"/></h2>
        <a href="${pageContext.request.contextPath}/articles" class="view-all">Voir tout →</a>
    </div>
    <div class="articles-grid">
        <c:choose>
            <c:when test="${empty articles}">
                <div class="empty-state">
                    <i class="fas fa-newspaper"></i>
                    <p><fmt:message key="home.noarticles"/></p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="a" items="${articles}" varStatus="st">
                    <article class="card">
                        <div class="card-img c${(st.index % 6) + 1}">
                            <span class="card-tag">
                                <c:if test="${not empty a.categoryName}">${a.categoryName}</c:if>
                                <c:if test="${empty a.categoryName}">Article</c:if>
                            </span>
                            <span class="card-emoji">📝</span>
                        </div>
                        <div class="card-body">
                            <div class="card-meta">
                                <span><i class="fas fa-user-circle"></i> ${a.authorUsername}</span>
                                <span><i class="fas fa-calendar"></i>
${a.createdAt.dayOfMonth}/${a.createdAt.monthValue}/${a.createdAt.year}                                </span>
                            </div>
                            <h3 class="card-title">
                                <a href="${pageContext.request.contextPath}/articles?action=view&id=${a.id}">
                                    ${a.title}
                                </a>
                            </h3>
                            <p class="card-excerpt">${a.shortContent}</p>
                            <div class="card-footer">
                                <a href="${pageContext.request.contextPath}/articles?action=view&id=${a.id}" class="read-btn">
                                    <fmt:message key="home.readmore"/> →
                                </a>
                                <span class="card-views"><i class="fas fa-eye"></i> ${a.views}</span>
                            </div>
                        </div>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
