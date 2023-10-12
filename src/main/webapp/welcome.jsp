<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: "Century Gothic", Arial, sans-serif;
            background-color: #f8e192;
            padding-top: 70px;
        }

        .left-half {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #f8e192;
            width: 50%;
            height: 100%;
            min-width:500px;
        }

        .right-half {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #f8e192;
            width: 50%;
            height: 100%;
            min-width:500px;
        }

        .logo-image {
            width: 20vw; /* Set the width to 20% of the viewport width */
            height: 20vw; /* Set the height to 20% of the viewport width, same as width to maintain circular shape */
            background-image: url("newLogo.png");
            background-size: cover;
            background-position: center;
            border-radius: 50%;
        }

        .welcome-message {
            font-size: 30px;
            font-weight: bold;
            font-family: "Century Gothic";
            text-align: center;
            line-height: 1.5;
            margin-top: 20px;
        }

        .sub-message {
            font-size: 18px;
            /*text-align: center;*/
            line-height: 1.5;
            margin-top: 20px;
            margin-left: 50px;
        }

        .button-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
            margin-top: 30px;
        }

        .button {
            padding: 20px 40px;
            font-size: 20px;
            border-radius: 30px;
            text-decoration: none;
            color: #f8e192;
            background-color: black;
            width: 250px;
            text-align: center;
            cursor: pointer;
        }

        .button:hover {
            background-color: black;
        }

        .navigation-panel {
            background-color: black;
            padding: 10px;
            display: flex;
            justify-content: space-around;
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
<%
    Integer userID = (Integer) session.getAttribute("current_user_id"); // Adjust this to get the actual userID
%>
<div class="navigation-panel">
    <a href="HomepageServlet" class="navigation-link">ReadVerse</a>
    <a href="CatalogueServlet" class="navigation-link">Catalogue</a>
    <a href="RecommendationsServlet" class="navigation-link">Recommendations</a>
    <% if(userID != null) { %>
    <a href="UserServlet?userID=<%= userID %>" class="navigation-link">My Profile</a>
    <% } %>
</div>
<body>
<div class="left-half">
    <div class="logo-image"></div>
    <div class="welcome-message">Welcome to ReadVerse!</div>
    <div class="sub-message">
        Get ready to explore an enchanting literary world. Discover captivating stories, timeless classics, and embark on exciting book adventures.
        <br>Happy reading!
    </div>
</div>
<div class="right-half">
    <div class="button-container">
        <a href="login.jsp" class="button">Log In</a>
        <a href="register.jsp" class="button">Create New Account</a>
    </div>
</div>
</body>
</html>