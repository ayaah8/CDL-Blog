<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${lang == 'en' ? 'en' : 'fr'}"/>
<fmt:setBundle basename="messages"/>
</main>
<footer class="footer">
    <div class="footer-inner">
        <div class="foot-brand">✍ CDLBlog
            <small>CDL / EST Agadir</small>
        </div>
        <div class="foot-links">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/articles">Articles</a>
        </div>
        <p class="foot-copy">&copy; 2026 CDL Blog </p>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
