<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"  %>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"  %>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Connection</title>
  <link rel="stylesheet" href="connection.css">
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
  <h2>Connection</h2>
  <form method="post" action="connection" id = "connForm">
    <div class="form-group">
      <label for="email">Adresse email :</label>
      <input type="email" id="email" name="email" value="${sessionScope.messageModel.object.email}" required>
    </div>
    <div class="form-group">
      <label for="password">Mot de passe :</label>
      <input type="password" id="password" name="password" value="${sessionScope.messageModel.object.userPwd}" required>
      <br>

    </div>
    <button type="button" id="connection">Connection</button>
    <br>
    <span id = "msg" style="color: brown; font-size: 20px ">${sessionScope.messageModel.msg}</span>
  </form>
</div>
</body>
<script type="text/javascript" src = "js/jquery-3.7.1.js"></script>
<script type="text/javascript">
  $("#connection").click(function (){
    var email =$("#email").val();
    var password = $("#password").val();

    if(isEmpty(email)){
      $("#msg").html("Email ne peut pas être vide!");
      return;
    }

    if(isEmpty(password)){
      $("#msg").html("Mot de passe ne peut pas être vide!");
      return;
    }
    $("#connForm").submit();


  });

  function isEmpty(str){
    if(str==null||str.trim() === ""){
      return true;
    }
    return false;

  }
</script>


</html>