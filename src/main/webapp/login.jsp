<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh; /* Use min-height instead of height to ensure the content is centered even if it's less than 100vh */
            font-family: "Century Gothic", Arial, sans-serif;
            background-color: #f8e192;
            margin: 0; /* Remove any default margins */
            padding: 20px; /* Add padding to body to separate from the edge of the viewport */
        }

        .main-container {
            display: flex;
            flex-direction: column; /* Change to column to stack elements vertically */
            align-items: center;
            justify-content: center;
            max-width: 1200px;
            height: 80%;
            background-color: #f8e192;
        }

        .logo-image {
            width: 150px;
            height: 150px;
            background-image: url("newLogo.png");
            background-size: cover;
            background-position: center;
            border-radius: 50%;
        }

        .left-half {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 100%; /* Set width to 100% to fill the entire left-half container */
        }

        .welcome-message {
            font-size: 30px;
            font-weight: bold;
            font-family: "Century Gothic";
            text-align: center;
            line-height: 1;
            margin-top: 20px;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
            margin-top: 30px;
            width: 300px; /* Set a fixed width to control the form width */
        }

        .form-input {
            padding: 10px;
            font-size: 18px;
            border-radius: 20px; /* Set border-radius to 20px for more oval shape */
            border: 1px solid #ccc;
            width: 100%; /* Use width: 100% to fill the container */
        }

        .form-input:focus {
            outline: none;
            border-color: #f8e192;
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

        .register-text {
            text-align: center;
            font-size: 16px;
            margin-top: 10px;
            color: black;
        }

        /* Style the button */
        .register-button {
            padding: 10px 20px; /* Adjust the padding to make the button smaller */
            font-size: 16px; /* Adjust the font size to make the button smaller */
            border-radius: 30px;
            text-decoration: none;
            color: #f8e192;
            background-color: black;
            width: 150px; /* Adjust the width to make the button smaller */
            text-align: center;
            cursor: pointer;
            display: block;
            margin-top: 0px;
        }

        .register-button:hover {
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
    <script>
        function validateForm() {
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            if (username === "" || password === "") {
                alert("Please fill in all the fields.");
                return false;
            }

        }
    </script>
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

<div class="main-container">
    <div class="left-half">
        <div class="logo-image"></div>
        <div class="welcome-message">Log In</div>
        <form action="LoginServlet" method="post" onsubmit="return validateForm();">
            <div class="form-container">
                <input type="text" id="username" name="username" placeholder="Username" class="form-input" required>
                <input type="password" id="password" name="password" placeholder="Password" class="form-input" required>
                <button type="submit" class="button">Log In</button>
                <div class="error-text">
                    <%
                        if (request.getAttribute("errorMess") != null) { %>
                    <%= request.getAttribute("errorMess") %>
                    <% } %>

                </div>
                <div class="register-text">Don't have an account? Register here.</div>
                <!-- Styled button -->
                <a href="register.jsp" class="register-button">Register</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>






