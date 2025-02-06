<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"  %>
<%@ include file="header.jsp" %>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Centre Personnel</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
  <div class="row">
    <div class="col-md-4">
      <div class="text-center">
        <form action="userCenter" method="post" enctype="multipart/form-data">
          <label for="avatarInput">
            <img src="${user.avatar}" alt="Avatar" width="100" height="100">
          </label>
          <input id="avatarInput" type="file" name="avatar" style="display: none;" onchange="this.form.submit();">
          <h3>${user.userName}</h3>
          <p>Type de compte: ${user.userType}</p>
          <input type="hidden" id="userId" name="userId" value="${sessionScope.user.userId}">
          <input type="hidden" id="userType" name="userType" value="${sessionScope.user.userType}">
        </form>
        <c:if test="${user.userType eq 'fournisseur'}">
          <button type="button" class="btn btn-info" data-toggle="modal" data-target="#editProfileModal">Modifier le profil</button>
          <button type="button" class="btn btn-success" data-toggle="modal" data-target="#createProfileModal">Créer un profil</button>
        </c:if>
      </div>
    </div>
    <div class="col-md-8">
      <!-- Display Profiles Here -->
      <div id="profilesDisplay">
        <c:if test="${user.userType eq 'fournisseur'}">
        <div class="container mt-4">
          <div class="row">
            <div class="col-md-12">
              <h4>Votre profil</h4>
              <table class="table">
                <thead>
                <tr>
                  <th>Localisation</th>
                  <th>Compétences</th>
                  <th>Années d'expérience</th>
                  <th>Biographie</th>
                  <th>Informations de contact</th>
                </tr>
                </thead>
                <tbody id="profileTableBody">

                </tbody>
              </table>
            </div>
          </div>
        </div>
        </c:if>


<c:if test="${user.userType eq 'fournisseur'}">
<!-- Profile Update Modal -->
<div class="modal fade" id="editProfileModal" tabindex="-1" role="dialog" aria-labelledby="editProfileModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editProfileModalLabel">Modifier le profil</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="editProfileForm" action="profile" method="post">
          <div class="form-group">
            <label for="newLocation">Localisation:</label>
            <input type="text" class="form-control" id="newLocation" name="location">
          </div>
          <div class="form-group">
            <label for="newSkills">Compétences:</label>
            <input type="text" class="form-control" id="newSkills" name="skills">
          </div>
          <div class="form-group">
            <label for="newExperience">Années d'expérience:</label>
            <input type="number" class="form-control" id="newExperience" name="experienceYears">
          </div>
          <div class="form-group">
            <label for="newBio">Biographie:</label>
            <textarea class="form-control" id="newBio" name="bio"></textarea>
          </div>
          <div class="form-group">
            <label for="newContactInfo">Informations de contact:</label>
            <input type="text" class="form-control" id="newContactInfo" name="contactInfo">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" onclick="updateProfile()">Enregistrer les modifications</button>
      </div>
    </div>
  </div>
</div>
</c:if>

<!-- Create Profile Modal -->
<div class="modal fade" id="createProfileModal" tabindex="-1" role="dialog" aria-labelledby="createProfileModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="createProfileModalLabel">Créer un profil</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="createProfileForm">
          <div class="form-group">
            <label for="createLocation">Localisation:</label>
            <input type="text" class="form-control" id="createLocation" name="location">
          </div>
          <div class="form-group">
            <label for="createSkills">Compétences:</label>
            <input type="text" class="form-control" id="createSkills" name="skills">
          </div>
          <div class="form-group">
            <label for="createExperience">Années d'expérience:</label>
            <input type="number" class="form-control" id="createExperience" name="experienceYears">
          </div>
          <div class="form-group">
            <label for="createBio">Biographie:</label>
            <textarea class="form-control" id="createBio" name="bio"></textarea>
          </div>
          <div class="form-group">
            <label for="createContactInfo">Informations de contact:</label>
            <input type="text" class="form-control" id="createContactInfo" name="contactInfo">
          </div>
          <button type="button" class="btn btn-primary" onclick="createProfile()">Créer</button>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
      </div>
    </div>
  </div>
</div>

<c:if test="${user.userType eq 'demandeur'}">
  <!-- Bouton de publication -->
  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#publishRequestModal">
    Publier une demande
  </button>

  <!-- Modal de publication de demande -->
  <div class="modal fade" id="publishRequestModal" tabindex="-1" role="dialog" aria-labelledby="publishRequestModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="publishRequestModalLabel">Publier une demande</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <!-- Formulaire de demande -->
          <form id="publishRequestForm">
            <div class="form-group">
              <label for="machineType">Type de machine</label>
              <input type="text" class="form-control" id="machineType" name="machineType">
            </div>
            <div class="form-group">
              <label for="requestDescription">Description de la demande</label>
              <textarea class="form-control" id="requestDescription" name="description"></textarea>
            </div>
            <div class="form-group">
              <label for="preferredTime">Heure préférée</label>
              <input type="datetime-local" class="form-control" id="preferredTime" name="preferredTime">
            </div>
            <button type="button" class="btn btn-primary" onclick="submitRequest()">Publier</button>
          </form>
        </div>
      </div>
    </div>
  </div>

  <!-- Affichage des demandes du demandeur -->
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-12">
        <h4>Vos demandes</h4>
        <table class="table">
          <thead>
          <tr>
            <th>Numéro</th>
            <th>Type de machine</th>
            <th>Description</th>
            <th>Heure préférée</th>
            <th>Statut</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody id="requestsTableBody">

          </tbody>
        </table>
      </div>
    </div>
  </div>
</c:if>



<div id="map" style="height: 200px; width: 100%;"></div>




        <%@ include file="footer.jsp" %>
</body>
</html>

