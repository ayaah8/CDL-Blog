<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="page-hdr">
        <h1><i class="fas fa-newspaper"></i> <fmt:message key="nav.myarticles"/></h1>
        <a href="${pageContext.request.contextPath}/member/articles?action=new" class="btn-solid">
            <i class="fas fa-plus"></i> <fmt:message key="article.new"/>
        </a>
    </div>

    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty articles}">
                <div class="empty-state" style="padding:3rem;">
                    <i class="fas fa-newspaper"></i>
                    <p><fmt:message key="home.noarticles"/></p>
                    <a href="${pageContext.request.contextPath}/member/articles?action=new" class="btn-solid">
                        <fmt:message key="article.new"/>
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th><fmt:message key="article.title"/></th>
                            <th>Statut</th>
                            <th><fmt:message key="article.title"/></th>
                            <th>Date</th>
                            <th><i class="fas fa-eye"></i></th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="a" items="${articles}">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/articles?action=view&id=${a.id}"
                                       style="font-weight:600;color:var(--ink)">${a.title}</a>
                                </td>
                                <td>
                                    <span class="badge ${a.status == 'PUBLISHED' ? 'pub' : 'draft'}">
                                        ${a.status == 'PUBLISHED' ? 'Publié' : 'Brouillon'}
                                    </span>
                                </td>
                                <td style="font-size:.82rem;color:var(--muted)">
                                    <c:if test="${not empty a.categoryName}">${a.categoryName}</c:if>
                                    <c:if test="${empty a.categoryName}">—</c:if>
                                </td>
                                <td style="font-size:.82rem;color:var(--muted)">
${a.createdAt.dayOfMonth}/${a.createdAt.monthValue}/${a.createdAt.year}                                </td>
                                <td style="font-weight:700">${a.views}</td>
                                <td>
                                    <div class="tactions">
                                        <a href="${pageContext.request.contextPath}/member/articles?action=edit&id=${a.id}"
                                           class="tbtn e" title="<fmt:message key='article.edit'/>">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/member/articles?action=delete&id=${a.id}"
                                           class="tbtn d" title="<fmt:message key='article.delete'/>"
                                           onclick="return confirm('<fmt:message key="article.delete.confirm"/>')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
