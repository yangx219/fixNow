$(document).ready(function() {
  var userType = $('#userType').val();

  console.log("User type:", userType);
  if (userType === "fournisseur") {
    fetchProfileDetails();

    $('#editProfileForm').submit(function(event) {
      event.preventDefault();
      updateProfile();
    });
  }

  if (userType === "demandeur") {
    console.log("Fetching repair requests...");
    fetchRepairRequests();
  }
});



function createProfile() {
  var profileData = {
    userId: $('#userId').val(),
    location: $('#createLocation').val(),
    skills: $('#createSkills').val(),
    experienceYears: parseInt($('#createExperience').val(), 10),
    bio: $('#createBio').val(),
    contactInfo: $('#createContactInfo').val()
  };

  $.ajax({
    url: 'profile',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(profileData),
    success: function(response) {
      if(response.success) {
        alert('Profile created successfully!');
        $('#createProfileModal').modal('hide');
        location.reload();
      } else {
        alert(response.message);
      }
    },
    error: function() {
      alert('Error creating profile.');
    }
  });
}

function fetchProfileDetails() {
  $.ajax({
    url: 'profile?userId=' + $('#userId').val(),
    type: 'GET',
    contentType: 'application/json',
    success: function(response) {
      if (response.success && response.profile) {
        var profileTableBody = $('#profileTableBody');
        profileTableBody.empty();
        var row = '<tr>' +
            '<td>' + (response.profile.location || 'Not set') + '</td>' +
            '<td>' + (response.profile.skills || 'Not set') + '</td>' +
            '<td>' + (response.profile.experienceYears || 'Not set') + '</td>' +
            '<td>' + (response.profile.bio || 'Not set') + '</td>' +
            '<td>' + (response.profile.contactInfo || 'Not set') + '</td>' +
            '</tr>';
        profileTableBody.append(row);

        console.error('Failed to load profile:', response.message);
        alert(response.message);
        geocodeAddress(response.profile.location);

      } else {
        console.error('Failed to load profile:', response.message);
        alert(response.message);
      }
    },
    error: function(xhr, status, error) {
      console.error('AJAX Error:', status, error);
      alert('Failed to retrieve profile details due to server error.');
    }
  });
}

function updateProfile() {
  var updatedData = {
    userId: $('#userId').val(),
    location: $('#newLocation').val(),
    skills: $('#newSkills').val(),
    experienceYears: parseInt($('#newExperience').val(), 10),
    bio: $('#newBio').val(),
    contactInfo: $('#newContactInfo').val()
  };

  $.ajax({
    url: 'profile',
    type: 'PUT',
    contentType: 'application/json',
    data: JSON.stringify(updatedData),
    success: function(response) {
      if(response.success) {
        alert('Profile updated successfully!');
        $('#editProfileModal').modal('hide');
        location.reload();
      } else {
        alert(response.message);
      }
    },
    error: function() {
      alert('Error updating profile.');
    }
  });
}


function geocodeAddress(address) {
  var geocoder = new google.maps.Geocoder();
  geocoder.geocode({ 'address': address }, function(results, status) {
    if (status === 'OK') {
      var mapOptions = {
        zoom: 14,
        center: results[0].geometry.location
      };
      var map = new google.maps.Map(document.getElementById('map'), mapOptions);
      var marker = new google.maps.Marker({
        map: map,
        position: results[0].geometry.location
      });
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}



function fetchRepairRequests() {
  var userId = $('#userId').val();
  console.log("Fetching repair requests for userId:", userId); // 调试代码

  $.ajax({
    url: 'repairRequest',
    type: 'GET',
    data: { userId: userId },
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
              '<td><button type="button" class="btn btn-danger" onclick="deleteRequest(' + request.requestId + ')">Supprimer</button></td>' +
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


function deleteRequest(requestId) {
  $.ajax({
    url: 'repairRequest',
    type: 'DELETE',
    contentType: 'application/json',
    data: JSON.stringify({ requestId: requestId }),
    success: function(response) {
      if (response.success) {
        alert('Demande supprimée avec succès !');
        location.reload();
      } else {
        alert(response.message);
      }
    },
    error: function() {
      alert('Erreur lors de la suppression de la demande.');
    }
  });
}



function submitRequest() {
  var userId = $('#userId').val();
  var requestData = {
    userId: userId,
    machineType: $('#machineType').val(),
    description: $('#requestDescription').val(),
    preferredTime: $('#preferredTime').val()
  };

  $.ajax({
    url: 'repairRequest',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(requestData),
    success: function(response) {
      if (response.success) {
        alert('Demande publiée avec succès !');
        $('#publishRequestModal').modal('hide');
        location.reload();
      } else {
        alert(response.message);
      }
    },
    error: function() {
      alert('Erreur lors de la publication de la demande.');
    }
  });
}
