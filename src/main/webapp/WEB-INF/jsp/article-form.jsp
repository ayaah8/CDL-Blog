<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="form-card">
        <h2>
            <c:choose>
                <c:when test="${not empty article}">
                    <i class="fas fa-edit"></i> <fmt:message key="article.edit"/>
                </c:when>
                <c:otherwise>
                    <i class="fas fa-plus-circle"></i> <fmt:message key="article.new"/>
                </c:otherwise>
            </c:choose>
        </h2>

        <form action="${pageContext.request.contextPath}/member/articles" method="post" class="art-form">
            <input type="hidden" name="action" value="save">
            <c:if test="${not empty article}">
                <input type="hidden" name="id" value="${article.id}">
            </c:if>

            <div class="field">
                <label><i class="fas fa-heading"></i> <fmt:message key="article.title"/> *</label>
                <input type="text" name="title" required maxlength="255"
                       value="${not empty article ? article.title : ''}"
                       placeholder="Titre de l'article...">
            </div>

            <div class="field-row">
                <div class="field">
                    <label><i class="fas fa-toggle-on"></i> Statut</label>
                    <select name="status">
                        <option value="PUBLISHED" ${article.status == 'PUBLISHED' || article == null ? 'selected' : ''}>
                            <fmt:message key="article.published"/>
                        </option>
                        <option value="DRAFT" ${article.status == 'DRAFT' ? 'selected' : ''}>
                            <fmt:message key="article.draft"/>
                        </option>
                    </select>
                </div>
            </div>

            <div class="field">
                <label><i class="fas fa-align-left"></i> <fmt:message key="article.summary"/></label>
                <textarea name="summary" rows="2"
                          placeholder="Résumé court...">${not empty article ? article.summary : ''}</textarea>
            </div>

            <div class="field">
                <label><i class="fas fa-file-alt"></i> <fmt:message key="article.content"/> *</label>
                <textarea name="content" rows="14" required id="content"
                          placeholder="Contenu de l'article...">${not empty article ? article.content : ''}</textarea>
                <small id="char-counter" style="color:var(--muted);display:block;text-align:right;"></small>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-solid">
                    <i class="fas fa-save"></i> <fmt:message key="article.save"/>
                </button>
                <a href="${pageContext.request.contextPath}/member/articles" class="btn-cancel">
                    <i class="fas fa-times"></i> <fmt:message key="article.cancel"/>
                </a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
