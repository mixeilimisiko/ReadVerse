<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="dao.DbUserDAO" %>
<%@ page import="controller.servlet.UserPage.UserServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <title>Followers</title>
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

        .followers-label {
            font-size: 1.5rem;
            font-weight: bold;
            margin-top: 50px; /* Adjusted margin-top to move the label down */
            margin-bottom: 0;
        }

        .followings-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            margin-top: 50px;
            border-radius: 5px;
            padding: 5px 20px;
            cursor: pointer;
            font-size: 1rem;
        }

        .followings-button:hover {
            background-color: #0056b3;
        }

        .follower-box {
            background-color: #FAEBD7;
            margin-bottom: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            /* Adjust padding to move content slightly to the top left */
            padding: 5px 5px 5px 10px;
        }

        .follower-name {
            font-size: 1.2rem;
            margin-bottom: 0;
        }

        .follower-stats {
            font-size: 1rem;
            color: #555;
        }

        .follower-link {
            text-decoration: none;
            color: #007bff;
        }

        .navigation-panel {
            background-color: black;
            padding: 10px;
            display: flex;
            justify-content: space-around;
            position: fixed;
            font-family: "Century Gothic", Arial, sans-serif;
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
    <% List<User> followersList = (List<User>) request.getAttribute("followersList");
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
        <h2 class="followers-label">Followers</h2>
        <a href="FollowingsServlet?userID=<%= userID %>" class="followings-button">View Followings</a>
    </div>
    <%
        if (followersList != null && !followersList.isEmpty()) {
            for (User follower : followersList) {
                int numberOfReviews = UserServlet.getNumberOfReviewsForUser(follower.getId(), req);
                double averageRating = UserServlet.getSumOfRatingsForUser(follower.getId(), req) / (double) numberOfReviews;
    %>
    <div class="follower-box">
        <p class="follower-name">
            <a class="follower-link" href="/UserServlet?userID=<%= follower.getId() %>">
                <%= follower.getUsername() %>
            </a>
        </p>
        <p class="follower-stats">
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
