<%@ page import="model.Author" %>
<%@ page import="java.util.Set" %>
<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Author Page - ReadVerse </title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <style>

        body {
            /*display: flex;*/
            /*flex-direction: column;*/
            /*align-items: center;*/
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f8e192;
            margin: 0;
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
            font-family: Century Gothic, serif;
        }

        .navigation-link {
            text-decoration: none;
            color: #f8e192;
            font-size: 18px;
        }

        .author {
            display: flex;
        }

        .author-image {
            flex: none; /* Prevent the image container from growing */
            width: 180px; /* Set the fixed width for the image container */
            height: 240px; /* Set the fixed height for the image container */
            padding: 10px; /* Optional padding around the image */
        }

        .author-image img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* Maintain aspect ratio and cover the container */
            border-radius: 10px; /* Optional rounded corners */
            display: inline-block;
            vertical-align: top;
        }

        .picture {
            width: 100%;
            height: 100%;
            object-fit: cover; /* Maintain aspect ratio and cover the container */
            border-radius: 10px; /* Optional rounded corners */
            display: inline-block;
            vertical-align: top;
        }

        .author-details {
            flex: 2;
            padding: 10px; /* Optional padding around author details */
        }

        .author-name {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-weight: bold;
            font-size: 32px;
            width: 100%;
            font-family: Century Gothic, serif;
        }

        .genres{
            margin-top: 18px;
            font-size:18px;
        }

        .line {
            width: 100%;
            height: 1px;
            background-color: black;
            margin-top:6px;
            margin-bottom: 30px;
        }

        .author-info {
            background-color: antiquewhite;
            min-height: 120px;
            min-width: 324px;
            border-radius: 10px;
            padding: 5px;
            margin-top: 20px;
            line-height: 1.5;
            max-height: 540px;
            overflow: auto; /* Add this property to make the content scrollable */
        }

        .author-info p {
            margin-bottom: 10px;
            font-size: 16px;
        }

        .author-info a {
            color: #333; /* Change link color */
            text-decoration: none;
        }
        .author-info::-webkit-scrollbar {
            width: 12px; /* Width of the scrollbar */
        }

        .author-info::-webkit-scrollbar-thumb {
            background-color: #888888; /* Color of the thumb */
            border-radius: 3px; /* Rounded corners */
        }

        .author-info::-webkit-scrollbar-thumb:hover {
            background-color: #555555; /* Color of the thumb on hover */
        }

        .author-info::-webkit-scrollbar-track {
            background-color: #f8e192; /* Color of the track */
            border-radius: 3px; /* Rounded corners */
        }

        .author-bibliography {
            margin-top: 20px; /* Adjust this value as needed */
            margin-left: 208px;
            min-width: 324px;
        }

        .author-bibliography h3 {
            font-size: 24px;
            margin-bottom: 10px;
            font-family: Century Gothic, serif;
        }

        .book-box {
            background-color: antiquewhite;
            border-radius: 10px;
            padding: 10px;
            margin-bottom: 10px; /* Add some spacing between book boxes */
            display: flex;
            flex-direction: column;
        }

        .book-title {
            font-weight: bold;
            font-size: 18px;
            font-family: Century Gothic, serif;
        }

        .book-year {
            font-style: italic;
            margin-top: 6px;
        }

        .book-rating {
            margin-top: 6px;
        }

        .see-book-button {
            margin-top: 10px;
            align-self: flex-end;
            text-decoration: none;
            background-color: #007bff;
            bottom: 5px;
            right: 5px;
            color: #fff;
            border-radius: 2px;
            cursor: pointer;
            padding: 5px 5px;
        }


    </style>
</head>
<body>
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

<%
    Author author = (Author)request.getAttribute("author");
    String imgPath = author.getImgPath();
%>

<div class="author">
    <div class="author-image">
        <img src="/authors/<%= imgPath %>" alt="picture" class="picture">
    </div>

    <div class="author-details">
        <div class="author-name">
            <%=author.getName()%>
        </div>
        <div class="genres"> Genres:
            <% Set<String> genres = (Set<String>) request.getAttribute("genres");
                for (String genre : genres) { %>
            <span><%= genre %></span>
            <% } %>
        </div>
        <div class="line"></div>
        <div class="author-info"><%= author.getInfo() %></div>
    </div>
</div>

<div class="author-bibliography">
    <h3>Bibliography:</h3>
    <% List<Book> bibliography = (List<Book>) request.getAttribute("bibliography");
        for (Book book : bibliography) { %>
    <div class="book-box">
        <div class="book-title"><%= book.getTitle() %></div>
        <div class="book-year"> <%=book.getYear() %></div>
        <div class="book-rating">Rating: <%= book.getRating() %></div>
        <a href="BookServlet?bookID=<%= book.getId() %>" class="see-book-button">See Book</a>
    </div>
    <% } %>
</div>

</body>
</html>