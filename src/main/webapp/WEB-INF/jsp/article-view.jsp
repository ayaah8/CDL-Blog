<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="article-wrap">
    <div class="article-main">
        <div class="article-main-inner">
            <div class="art-banner c1">
                <c:if test="${not empty article.categoryName}">
                    <span class="art-tag">${article.categoryName}</span>
                </c:if>
                <span style="font-size:4rem;">📝</span>
            </div>
            <div class="art-content-wrap">
                <h1>${article.title}</h1>
                <div class="art-meta">
                    <span><i class="fas fa-user-circle"></i> <strong>${article.authorUsername}</strong></span>
                    <span><i class="fas fa-calendar"></i>
${a.createdAt.dayOfMonth}/${a.createdAt.monthValue}/${a.createdAt.year}                    </span>
                    <span><i class="fas fa-eye"></i> ${article.views}</span>
                    <span><i class="fas fa-comments"></i> ${article.comments.size()}</span>
                </div>

                <c:if test="${sessionScope.user != null &&
                    (sessionScope.user.id == article.authorId || sessionScope.user.admin)}">
                    <div class="art-actions">
                        <a href="${pageContext.request.contextPath}/member/articles?action=edit&id=${article.id}"
                           class="btn-edit"><i class="fas fa-edit"></i> <fmt:message key="article.edit"/></a>
                        <a href="${pageContext.request.contextPath}/member/articles?action=delete&id=${article.id}"
                           class="btn-del"
                           onclick="return confirm('<fmt:message key="article.delete.confirm"/>')">
                            <i class="fas fa-trash"></i> <fmt:message key="article.delete"/>
                        </a>
                    </div>
                </c:if>

                <div class="art-body">${article.content}</div>
            </div>
        </div>

        <!-- Comments -->
        <div class="comments-box">
            <h3><i class="fas fa-comments"></i> <fmt:message key="comment.title"/> (${article.comments.size()})</h3>

            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <form action="${pageContext.request.contextPath}/member/comments" method="post" class="cmt-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="articleId" value="${article.id}">
                        <textarea name="content" rows="3" required
                                  placeholder="<fmt:message key='comment.content'/>"></textarea>
                        <button type="submit" class="btn-solid">
                            <i class="fas fa-paper-plane"></i> <fmt:message key="comment.submit"/>
                        </button>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="login-prompt">
                        <i class="fas fa-lock"></i>
                        <fmt:message key="comment.loginrequired"/>
                        <a href="${pageContext.request.contextPath}/login"><fmt:message key="nav.login"/></a>
                    </div>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${empty article.comments}">
                    <div class="empty-state" style="padding:2rem;">
                        <i class="fas fa-comment-slash"></i>
                        <p><fmt:message key="comment.nocomments"/></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="cm" items="${article.comments}">
                        <div class="cmt-item">
                            <div class="cmt-hdr">
                                <span class="cmt-author"><i class="fas fa-user-circle"></i> ${cm.authorUsername}</span>
                                <span class="cmt-date">
${cm.createdAt.dayOfMonth}/${cm.createdAt.monthValue}/${cm.createdAt.year} ${cm.createdAt.hour}:${cm.createdAt.minute < 10 ? '0' : ''}${cm.createdAt.minute}                                </span>
                                <c:if test="${sessionScope.user != null &&
                                    (sessionScope.user.id == cm.authorId || sessionScope.user.admin)}">
                                    <form action="${pageContext.request.contextPath}/member/comments"
                                          method="post" style="display:inline">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${cm.id}">
                                        <button type="submit" class="btn-icon-del"
                                                onclick="return confirm('Supprimer ce commentaire ?')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                            <div class="cmt-text">${cm.content}</div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <a href="${pageContext.request.contextPath}/articles" class="btn-back">
            <i class="fas fa-arrow-left"></i> <fmt:message key="general.back"/>
        </a>
    </div>

  
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
