<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recommended Books - ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <style>

        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f8e192;
            margin: 0;
            padding-top: 70px;
            padding-left: 15vw;
            padding-right: 15vw;
        }

        h1{
            font-family: Century Gothic, serif;
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

        .book {
            display: flex;
            background-color: antiquewhite;
            border-radius: 10px;
            margin-bottom: 20px;
            margin-top: 32px;
            width: 65vw;
            /*display: flex;*/
            gap: 10px;
            border: solid 1px black;
            padding: 16px;
        }

        .book_cover {
            width: 100px;
            height: 130px;
            margin-right: 10px;
            margin-top: 16px;
            flex: 0 0 90px;
        }

        .book_cover>img {
            width: 100%;
            border-radius: 4px;
            border: 0.1vw black;
        }

        .book_info {
            flex-grow: 1;
        }
    </style>
</head>
<body>
<%
    List<Book> recommendedBooks = (List<Book>) request.getAttribute("recommendedBooks");
    Integer userID = (Integer) session.getAttribute("current_user_id");
%>
<div class="navigation-panel">
    <a href="HomepageServlet" class="navigation-link">ReadVerse</a>
    <a href="CatalogueServlet" class="navigation-link">Catalogue</a>
    <a href="RecommendationsServlet" class="navigation-link">Recommendations</a>
    <% if(userID != null) { %>
    <a href="UserServlet?userID=<%= userID %>" class="navigation-link">My Profile</a>
    <% } %>
</div>

<h1>Recommended Books</h1>

<div>
    <% for (Book book : recommendedBooks) { %>
    <div class="book">
        <div class="book_cover">
            <img src=<%=book.getFullPath()%>>
            <p></p>
            <a href="BookServlet?bookID=<%= book.getId() %>">  See Book</a>
        </div>
        <div class="book_info">
            <h2><%= book.getTitle() %></h2>
            <p>Author: <%= book.getAuthor() %></p>
            <p>Description: <%= book.getDescription() %></p>
            <p>Rating: <%= book.getRating() %></p>
            <p>Genres:
                <% List<String> genres = book.getGenres();
                    for (String genre : genres) { %>
                <%= genre + " " %>
                <% } %>
            </p>
            <p>Year: <%= book.getYear() %></p>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>