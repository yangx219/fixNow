<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/tags/core" prefix="c" %>


<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page d'accueil</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<header>
    <h1>FixNow</h1>
    <nav>
        <ul>
            <li><a href="#">Accueil</a></li>
            <li><a href="#">À propos</a></li>
            <li><a href="#">Contact</a></li>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li class="user-icon"><a href="userCenter.jsp">Bienvenue, ${sessionScope.user.userName}</a></li>
                    <li class="user-icon"><a href="logout">Déconnexion</a></li>
                </c:when>
                <c:otherwise>
                    <li class="user-icon"><a href="connection.jsp">Connection</a></li>
                    <li class="user-icon"><a href="inscription.jsp">Inscription</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>

<main>
    <div class="container">
        <aside class="sidebar">
            <h2>Filtres</h2>
        </aside>
        <section class="content">
            <div class="search-bar">
                <input type="text" id="search" placeholder="Rechercher...">
                <button>Rechercher</button>
            </div>
            <div class="announcement-list">
                <!-- 这里可以动态生成公告内容 -->
            </div>
        </section>
    </div>
</main>

<footer>
    <p>&copy; 2024 FixNow</p>
</footer>
</body>
</html>
