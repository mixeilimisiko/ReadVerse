<%@ page import="model.Review" %>
<%@ page import="model.Book" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 8/11/2023
  Time: 12:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

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
        padding-left: 10vw;
        padding-right: 10vw;
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




    .right-half {
        display: flex;
        flex-direction: column;
        /*align-items: center;*/
        /*justify-content: center;*/
        background-color: #f8e192;
        width: 50%;
        height: 100%;
        min-width:500px;
    }

    .panel-title {
        font-size: 24px;
    }



    .searchbar {
        display: flex;
        align-items: center;
        margin-top: 10px;
    }

    .searchbar input[type="text"] {
        padding: 6px;
        border: none;
        border-radius: 6px;
        margin-right: 10px;
    }

    .searchbar button {
        padding: 6px 12px;
        background-color: #555;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .scrollable-panel {
        /*width: 50%;*/
        height: 90%;
        overflow: auto; /* This enables scrolling when content overflows */
        /*border: 1px solid #ccc;*/
        /*background-color:antiquewhite;*/
        border-radius:2%;
        scrollbar-width: thin; /* For Firefox */
        scrollbar-color: #f8e192 #888888; /* For Firefox */
    }

    .scrollable-panel::-webkit-scrollbar {
        width: 12px; /* Width of the scrollbar */
    }

    .scrollable-panel::-webkit-scrollbar-thumb {
        background-color: #888888; /* Color of the thumb */
        border-radius: 3px; /* Rounded corners */
    }

    .scrollable-panel::-webkit-scrollbar-thumb:hover {
        background-color: #555555; /* Color of the thumb on hover */
    }

    .scrollable-panel::-webkit-scrollbar-track {
        background-color: #f8e192; /* Color of the track */
        border-radius: 3px; /* Rounded corners */
    }

    .review-box {
        display: flex;
        justify-content: space-between;
        /*align-items: center;*/
        background-color: antiquewhite;
        border-radius: 10px;
        padding: 10px;
        margin: 10px 0;
        margin-right: 15px;
        min-height: 120px; /* Set a minimum height for the review box */
        min-width: 250px;
    }

    .review-image {
        flex: 0 0 100px;
        height: 130px;
        max-width: 100%; /* Ensure the image doesn't exceed its container */
        background: rgba(255, 255, 255, 0.46);
    }

    .review-image>img {
        width: 100%;
        height: 100%;
        object-fit: contain; /* Fit the image within the container while maintaining aspect ratio */
        border-radius: 4px;
        border: 0.1vw black;
    }

    .review-details {
        flex: 1;
        overflow: hidden; /* Hide content that exceeds the dimensions */
        margin-left: 16px;
    }

    .rating-title {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: bold;
        font-size: 18px;
        width: 100%;
        font-family: Century Gothic, serif;
    }
    .review-author-year {
        font-style: italic;
        margin-top: 6px;
    }

    .line {
        width: 100%;
        height: 1px;
        background-color: black;
        margin-top:6px
    }

    .review-text {
        margin-top: 6px;
        margin-right:16px;
        word-wrap: break-word; /* Break long words to prevent horizontal overflow */
        font-size: 16px
    }

</style>

<head>
    <title>Homepage - ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
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


</head>

    <% List<Review> reviews = (List<Review>) request.getAttribute("reviews"); %>
    <% List<String> usernames = (List<String>) request.getAttribute("usernames"); %>
    <% List<Book> books = (List<Book>) request.getAttribute("books"); %>
    <% String errorMessage = (String)request.getAttribute("errorMessage") ;%>

    <div class="left-half">
        <div class="logo-image"></div>
        <div class="welcome-message">Welcome to ReadVerse!</div>

    </div>

    <div class="right-half">

        <div class="user-searchbar">
            <div class="panel-title">
                <p>Find Friends </p>
            </div>
            <form class="searchbar" action="SearchUserServlet" method="get">
<%--                <label for="username">Username:</label>--%>

                <input type="text" id="username" name="username" placeholder="Enter username">
                <button type="submit">Search</button>
            </form>
            <% if(errorMessage != null) { %>
                 <%=errorMessage %>
            <% }%>


        </div>

        <div class="panel-title">
            <p>Recent Friend Reviews : </p>
<%--            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. ...</p>--%>
        </div>
        <div class="scrollable-panel">
            <!-- Your content here -->
            <% for (int i = 0; i < reviews.size(); i++) { %>
            <div class="review-box">
                <div class="review-image">
                    <img src=<%= books.get(i).getFullPath()%>>
                </div>
                <div class="review-details">
                    <div class="title-and-rating">
                        <div class="rating-title">
                            <div class="review-title"><%= books.get(i).getTitle() %></div>
                            <div class="review-rating">Rating: <%= reviews.get(i).getRating() %></div>
                        </div>
                    </div>
                    <div class="review-author-year"><%= books.get(i).getAuthor() %> | <%= books.get(i).getYear() %>
                        <a href="AuthorServlet?authorName=<%= books.get(i).getAuthor() %>">    See Author</a>
                    </div>
                    <div class="line"></div>
                    <div class="review-text"><%= reviews.get(i).getReviewTxt() %></div>
                    <div class="review-username">User: <a href="UserServlet?userID=<%= reviews.get(i).getUserId() %>"><%= usernames.get(i) %></a></div>
                    <div class="review-date"><%=reviews.get(i).getDate()%></div>
                </div>
            </div>
            <% } %>

        </div>
    </div>
<body>

</body>
</html>
