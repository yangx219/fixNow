<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"  %>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"  %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page d'accueil</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="index.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
<header class="bg-dark text-white p-3">
    <div class="container">
        <div class="d-flex justify-content-between align-items-center">
            <h1 class="h3">FixNow</h1>
            <nav>
                <ul class="nav">
                    <li class="nav-item"><a href="#" class="nav-link text-white">Accueil</a></li>
                    <li class="nav-item"><a href="#" class="nav-link text-white">À propos</a></li>
                    <li class="nav-item"><a href="#" class="nav-link text-white">Contact</a></li>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <li class="nav-item"><a href="userCenter.jsp" class="nav-link text-white">Bienvenue, ${sessionScope.user.userName}</a></li>
                            <li class="nav-item"><a href="userCenter?action=logout" class="nav-link text-white">Déconnexion</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item"><a href="connection.jsp" class="nav-link text-white">Connection</a></li>
                            <li class="nav-item"><a href="inscription.jsp" class="nav-link text-white">Inscription</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </div>
</header>

<main class="container mt-4 mb-4">
    <div class="row">
        <aside class="col-md-3">
            <h2>Filtres</h2>
            <form id="filterForm">
                <div class="form-group">
                    <label for="filterMachineType">Type de machine</label>
                    <input type="text" id="filterMachineType" class="form-control" placeholder="Type de machine">
                </div>
                <div class="form-group">
                    <label for="filterExperienceYears">Années d'expérience</label>
                    <input type="number" id="filterExperienceYears" class="form-control" placeholder="Années d'expérience">
                </div>
                <div class="form-group">
                    <label for="filterPreferredTime">Heure préférée</label>
                    <input type="datetime-local" id="filterPreferredTime" class="form-control">
                </div>
                <button type="button" class="btn btn-primary" onclick="applyFilters()">Appliquer les filtres</button>
            </form>
        </aside>
        <div class="col-md-9">
            <div class="row">
                <div class="col-md-6">
                    <!-- Liste des Fournisseurs -->
                    <h2>Liste des Fournisseurs</h2>
                    <ul id="fournisseurList" class="list-group">

                    </ul>
                </div>
                <div class="col-md-6">
                    <!-- Liste des demandes -->
                    <h2>Liste des Demandes</h2>
                    <table class="table table-bordered">
                        <thead class="thead-dark">
                        <tr>
                            <th>Numéro</th>
                            <th>Type de machine</th>
                            <th>Description</th>
                            <th>Heure préférée</th>
                            <th>Statut</th>
                        </tr>
                        </thead>
                        <tbody id="requestsTableBody">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="bg-dark text-white text-center p-3 fixed-bottom">
    <p>&copy; 2024 FixNow</p>
</footer>

<script>
    $(document).ready(function() {
        fetchFournisseurList();
        fetchRepairRequests();
    });

    function fetchFournisseurList() {
        $.ajax({
            url: 'profile',
            type: 'GET',
            success: function(response) {
                if (response.success) {
                    var fournisseurList = response.profiles;
                    var list = $('#fournisseurList');
                    list.empty();
                    fournisseurList.forEach(function(fournisseur) {
                        var listItem = '<li class="list-group-item">' +
                            '<h3>' + fournisseur.userName + '</h3>' + // 显示userName
                            '<p>Skills: ' + fournisseur.skills + '</p>' + // 显示skills
                            '</li>';
                        list.append(listItem);
                    });
                } else {
                    alert(response.message);
                }
            },
            error: function() {
                alert('Failed to fetch fournisseur list.');
            }
        });
    }

    function fetchRepairRequests() {
        $.ajax({
            url: 'repairRequest',
            type: 'GET',
            success: function(response) {
                if (response.success) {
                    var repairRequests = response.repairRequests;
                    var tableBody = $('#requestsTableBody');
                    tableBody.empty();
                    repairRequests.forEach(function(request) {
                        var row = '<tr>' +
                            '<td>' + request.requestId + '</td>' +
                            '<td>' + request.machineType + '</td>' +
                            '<td>' + request.description + '</td>' +
                            '<td>' + request.preferredTime + '</td>' +
                            '<td>' + request.status + '</td>' +
                            '</tr>';
                        tableBody.append(row);
                    });
                } else {
                    alert(response.message);
                }
            },
            error: function() {
                alert('Failed to fetch repair requests.');
            }
        });
    }



</script>
</body>
</html>
