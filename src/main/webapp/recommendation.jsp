<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recommendations - ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f8e192;
            margin: 0;
            padding-top: 70px;
        }

        .recommendByBooks {
            margin: 20px auto;
            width: 500px;
            padding: 10px;
            /*border: 2px solid black;*/

        }

        .recommendByBooks label {
            display: block;
            margin-bottom: 10px;
            font-family: Century Gothic, serif;


        }

        .recommendByBooks input[type="text"] {
            background-color: antiquewhite;
            font-family: Century Gothic, serif;

            width: 400px;
            padding: 5px;
        }


        .book {
            display: flex;
            background-color: antiquewhite;
            margin: 10px auto;
            width: 350px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 10px;
            position: relative;
        }
        .book_cover {
            width: 100px;
            height: 130px;
            margin-right: 10px;
            margin-top: 8px;
            flex: 0 0 90px;
        }

        .book_cover>img {
            width: 100%;
            border-radius: 4px;
            border: 0.1vw black;
        }

        .book_info{
            margin-left: 16px;
        }




        .book h2 {
            margin: 0;
        }

        .submit-button {
            margin-top: 20px;
            text-align: center;
        }

        .error-message {
            color: black;
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

        .delete-button-form {
            position: absolute;
            bottom: 0;
            right: 0;
            margin: 10px; /* Add margin for spacing */
        }

        .button {
            padding: 5px 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 5px;
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

<div class="recommendByBooks">
    <label>Enter your favourite book titles, and we will recommend you our best 10 books:</label>
    <form action="AddBookForRecommendationServlet" method="post">
        <input type="text" name="bookTitle" placeholder="Enter Book Title" required>
        <button class="button" type="submit">Add Book</button>
    </form>
    <%-- Display error message, if any --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
    <p class="error-message"><%= errorMessage %></p>
    <% } %>
</div>

<div class="recommendByBooks">
    <h3>Books to Process: </h3>
    <%-- Retrieve the List of books from the session attribute "booksToProcess" --%>
    <% Set<Book> booksToProcess = (Set<Book>) session.getAttribute("booksToProcess"); %>
    <% if (booksToProcess != null && !booksToProcess.isEmpty()) { %>
    <% for (Book book : booksToProcess) { %>
    <div class="book">
        <div class="book_cover">
            <img src=<%=book.getFullPath()%>>
        </div>
        <div class="book_info">
        <h2><%= book.getTitle() %></h2>
        <p>Author: <%= book.getAuthor() %></p>
<%--        <p>Description: <%= book.getDescription() %></p>--%>
        <p>Genres: <% List<String> genres = book.getGenres(); for(String genre : genres) { %>
            <%= genre+ "  "%>
            <% } %>
        </p>
        <p>Year: <%= book.getYear() %></p>
        <p>Rating: <%= book.getRating() %></p>
        </div>
        <form action="DeleteBookForRecommendationServlet" method="POST" class="delete-button-form">
            <input type="hidden" name="bookID" value="<%= book.getId() %>">
            <button type="submit" class="button">Delete Book</button>
        </form>
    </div>
    <% } %>
    <% } %>
</div>

<div class="submit-button">
    <form action="ProcessBooksServlet" method="get" onsubmit="return validateBooksToProcess()">
        <button class="button" type="submit">Submit</button>
        <div id="error-message" style="color: black; display: none; margin-top: 10px;"></div>
    </form>
</div>


<script>
    function validateBooksToProcess() {
        var booksToProcess = <%= session.getAttribute("booksToProcess") %>;
        if (!booksToProcess || booksToProcess.length === 0) {
            var errorMessage = document.getElementById("error-message");
            errorMessage.textContent = "Please enter at least one book before submitting.";
            errorMessage.style.display = "block";
            setTimeout(function() {
                errorMessage.style.display = "none";
            }, 10000); // 10 seconds timeout
            return false; // Prevent form submission
        }
        return true; // Allow form submission
    }
</script>

</body>
</html>