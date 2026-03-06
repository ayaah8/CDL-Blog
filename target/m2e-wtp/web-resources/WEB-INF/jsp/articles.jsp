<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="page-hdr">
        <h1><fmt:message key="home.latestarticles"/></h1>
        <c:if test="${sessionScope.user != null}">
            <a href="${pageContext.request.contextPath}/member/articles?action=new" class="btn-solid">
                <i class="fas fa-plus"></i> <fmt:message key="article.new"/>
            </a>
        </c:if>
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

    <c:if test="${totalPages > 1}">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="?page=${currentPage - 1}" class="pg">
                    <i class="fas fa-chevron-left"></i> <fmt:message key="general.previous"/>
                </a>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="?page=${i}" class="pg ${i == currentPage ? 'on' : ''}">${i}</a>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="?page=${currentPage + 1}" class="pg">
                    <fmt:message key="general.next"/> <i class="fas fa-chevron-right"></i>
                </a>
            </c:if>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
