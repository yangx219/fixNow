<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.jsp" style="font-size: 50px;">FixNow</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">Accueil <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="about.jsp">À propos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="contact.jsp">Contact</a>
            </li>
        </ul>
        <c:if test="${not empty sessionScope.user}">
            <span class="navbar-text">Bienvenue, ${sessionScope.user.userName}!</span>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="userCenter?action=logout">Déconnexion</a>
                </li>
            </ul>
        </c:if>
    </div>
</nav>
