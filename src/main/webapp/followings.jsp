<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="dao.DbUserDAO" %>
<%@ page import="controller.servlet.UserPage.UserServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
  <title>Followings</title>
  <style>
    body {
      margin: 0;
      font-family: Arial, Helvetica, sans-serif;
      background-color: #f8e192;
      padding: 0 15px; /* Added 15px padding on left and right */
    }

    .center-content {
      max-width: 800px; /* Adjust as needed */
      margin: 0 auto;
      padding-top: 20px; /* Add some top padding */
    }

    .followers-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 10px;
    }

    .followings-label {
      font-size: 1.5rem;
      font-weight: bold;
      margin-top: 50px; /* Adjusted margin-top to move the label down */
      margin-bottom: 0;
    }

    .followers-button {
      background-color: #007bff;
      color: #fff;
      border: none;
      margin-top: 50px;
      border-radius: 5px;
      padding: 5px 20px;
      cursor: pointer;
      font-size: 1rem;
    }

    .followers-button:hover {
      background-color: #0056b3;
    }

    .following-box {
      background-color: #FAEBD7;
      margin-bottom: 20px;
      border-radius: 5px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      /* Adjust padding to move content slightly to the top left */
      padding: 5px 5px 5px 10px;
    }

    .following-name {
      font-size: 1.2rem;
      margin-bottom: 0;
    }

    .following-stats {
      font-size: 1rem;
      color: #555;
    }

    .following-link {
      text-decoration: none;
      color: #007bff;
    }

    .navigation-panel {
      background-color: black;
      padding: 10px;
      display: flex;
      justify-content: space-around;
      font-family: "Century Gothic", Arial, sans-serif;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      z-index: 1;
    }

    .navigation-link {
      text-decoration: none;
      color: #f8e192;
      font-size: 18px;
    }

  </style>
</head>
<div class="navigation-panel">
  <% List<User> followingsList = (List<User>) request.getAttribute("followingsList");
    DbUserDAO userDAO = (DbUserDAO) request.getAttribute("userDAO");
    Integer userID = (Integer) request.getAttribute("userID");
    HttpServletRequest req = (HttpServletRequest) request.getAttribute("req");
  %>
  <a href="HomepageServlet" class="navigation-link">ReadVerse</a>
  <a href="CatalogueServlet" class="navigation-link">Catalogue</a>
  <a href="RecommendationsServlet" class="navigation-link">Recommendations</a>
  <% if(userID != null) { %>
  <a href="UserServlet?userID=<%= userID %>" class="navigation-link">My Profile</a>
  <% } %>
</div>
<body>
<div class="center-content">
  <div class="followers-header">
    <h2 class="followings-label">Followings</h2>
    <a href="FollowersServlet?userID=<%= userID %>" class="followers-button">View Followers</a>
  </div>
  <%
    if (followingsList != null && !followingsList.isEmpty()) {
      for (User following : followingsList) {
        int numberOfReviews = UserServlet.getNumberOfReviewsForUser(following.getId(), req);
        double averageRating = UserServlet.getSumOfRatingsForUser(following.getId(), req) / (double) numberOfReviews;
  %>
  <div class="following-box">
    <p class="following-name">
      <a class="following-link" href="/UserServlet?userID=<%= following.getId() %>">
        <%= following.getUsername() %>
      </a>
    </p>
    <p class="following-stats">
      Number of Reviews: <%= numberOfReviews %><br>
      Average Rating: <%= String.format("%.2f", averageRating) %>
    </p>
  </div>
  <%
    }
  } else {
  %>
  <p>No followers to display.</p>
  <%
    }
  %>
</div>
</body>
</html>
