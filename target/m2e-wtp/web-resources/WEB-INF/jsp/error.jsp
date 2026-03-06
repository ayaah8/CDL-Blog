<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="container" style="text-align:center;padding:5rem 2rem;">
    <div style="font-size:6rem;font-weight:900;color:var(--border);line-height:1;">
        ${pageContext.errorData.statusCode}
    </div>
    <h2 style="margin:1rem 0;">
        ${pageContext.errorData.statusCode == 404 ? 'Page introuvable' : 'Erreur serveur'}
    </h2>
    <p style="color:var(--muted);margin-bottom:2rem;">
        ${pageContext.errorData.statusCode == 404
            ? 'La page que vous cherchez n\'existe pas.'
            : 'Une erreur interne est survenue.'}
    </p>
    <a href="${pageContext.request.contextPath}/" class="btn-solid">
        <i class="fas fa-home"></i> Accueil
    </a>
</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
