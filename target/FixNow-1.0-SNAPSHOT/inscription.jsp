<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"  %>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"  %>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription</title>
    <link rel="stylesheet" href="inscription.css">
</head>
<body>


<header>
    <h1>FixNow</h1>
    <nav>
        <ul>
            <li><a href="index.jsp">Accueil</a></li>
            <li><a href="#">À propos</a></li>
            <li><a href="#">Contact</a></li>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li class="user-icon"><a href="userCenter.jsp">Bienvenue, ${sessionScope.user.userName}</a></li>
                    <li class="user-icon"><a href="userCenter?action=logout">Déconnexion</a></li>
                </c:when>
                <c:otherwise>
                    <li class="user-icon"><a href="connection.jsp">Connection</a></li>
                    <li class="user-icon"><a href="inscription.jsp">Inscription</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>



<div class="container">
    <h2>Inscription</h2>
    <form method="post" action="inscription" id="inscForm">
        <div class="form-group">
            <label for="username">Nom d'utilisateur :</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="usertype">Type d'utilisateur :</label>
            <select id="usertype" name="usertype" required>
                <option value="" selected disabled>Choisissez une option</option>
                <option value="fournisseur">fournisseur</option>
                <option value="demandeur">demandeur</option>
            </select>
        </div>
        <div class="form-group">
            <label for="email">Adresse email :</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="button" id="inscriptionBtn">S'inscrire</button>
    </form>
    <span id="msg" style="color: red;"></span>
</div>
<script type="text/javascript" src="js/jquery-3.7.1.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#inscriptionBtn').click(function(event) {
            var username = $('#username').val();
            var usertype = $('#usertype').val();
            var email = $('#email').val();
            var password = $('#password').val();

            if(isEmpty(username) || isEmpty(usertype) || isEmpty(email) || isEmpty(password)) {
                $('#msg').html('Tous les champs doivent être remplis!');
            } else {
                $('#inscForm').submit();
            }
        });

        function isEmpty(str) {
            return (!str || 0 === str.trim().length);
        }
    });
</script>
</body>
</html>
