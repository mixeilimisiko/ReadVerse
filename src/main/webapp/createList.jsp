<%@ page import="model.Book" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create List - ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding-top: 70px;
            background-color: #f8e192;

        }

        .form {
            margin: 20px auto;
            width: 550px;
            padding: 10px;

        }

        .form label {
            display: block;
            margin-bottom: 10px;
            font-family: Century Gothic, serif;
        }

        .form input[type="text"] {
            width: 400px;
            padding: 5px;
        }

        .form button {
            padding: 5px 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }


        .description-text {
            margin-top: 10px;

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
            width: 120px;
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

        .book_info{
            margin-left: 16px;
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

        .submit_form {
            display: flex;
            justify-content: center; /* Center the content horizontally */
            align-items: center; /* Center the content vertically */
            font-size: 15px;
            margin-top: 20px; /* Add some top margin for better spacing */
        }
        .delete-button-form {
            position: absolute;
            bottom: 0;
            right: 0;
            margin: 10px; /* Add margin for spacing */
        }

        .button {
            /*padding: 5px 10px;*/
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 2px;
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
<div class="form">
    <form action="UpdateNewListPropertiesServlet" method="post">
        <label>List Title:</label>
        <input type="text" name="listTitle" placeholder="Enter List Title" required>
        <button type="submit">Save Title</button>
    </form>
    <%-- Display current list name --%>
    <% String newListName = (String) session.getAttribute("newListTitle"); %>
    <% if (newListName != null) { %>
    <p><strong>Current List Name:</strong> <%= newListName %></p>
    <% } %>

    <%-- Display error message, if any --%>
    <% String titleErrorMessage = (String) request.getAttribute("titleErrorMessage"); %>
    <% if (titleErrorMessage != null && !titleErrorMessage.isEmpty()) { %>
    <p class="title-error-message"> <%= titleErrorMessage %></p>
    <% } %>
</div>

<div class="form">
    <form action="UpdateNewListPropertiesServlet" method="post">
        <label>List Description:</label>
        <input type="text" name="listDescription" placeholder="Enter List Description" required>
        <button type="submit">Save Description</button>
    </form>
    <%-- Display current list description --%>
    <% String newListDescription = (String) session.getAttribute("newListDescription"); %>
    <% if (newListDescription != null) { %>
    <p class="description-text"><strong>Current List Description:</strong> <%= newListDescription %></p>
    <% } %>
</div>

<div class="form">
    <form action="UpdateNewListContentServlet" method="post">
        <label>Add Books to List:</label>
        <input type="text" name="bookTitle" placeholder="Enter Book Title" required>
        <button type="submit">Add Book</button>
    </form>
    <%-- Display error message, if any --%>
    <% String bookErrorMessage = (String) request.getAttribute("bookErrorMessage"); %>
    <% if (bookErrorMessage != null && !bookErrorMessage.isEmpty()) { %>
    <p class="book-error-message"><%= bookErrorMessage %></p>
    <% } %>

    <%-- Display selected books --%>
    <% List<Book> selectedBooks = (List<Book>) session.getAttribute("selectedBooks"); %>
    <% if (selectedBooks != null && !selectedBooks.isEmpty()) { %>
    <h3>Selected Books:</h3>
    <% for (Book book : selectedBooks) { %>


    <div class="book">
        <div class="book_cover">
            <img src=<%=book.getFullPath()%>>
        </div>
        <div class="book_info">
            <h2><%= book.getTitle() %></h2>
            <p>Author: <%= book.getAuthor() %></p>
            <p>Genres: <% List<String> genres = book.getGenres(); for(String genre : genres) { %>
                <%= genre+ "  "%>
                <% } %>
            </p>
            <p>Year: <%= book.getYear() %></p>
            <p>Rating: <%= book.getRating() %></p>
        </div>
                <form class="delete-button-form" action="DeleteFromNewListServlet" method="POST" style="position: relative;">
                    <input type="hidden" name="bookID" value="<%= book.getId() %>">
<%--                    <button class="delete-button" type="submit" style="position: absolute; bottom: 0; right: 0;">Delete Book</button>--%>
                    <button class="button" type="submit" style="position: absolute; bottom: 0; right: 0;">Delete Book</button>

                </form>
    </div>
    <% } %>
    <% } %>
</div>

<div class = submit_form>
<%-- Submit button to create the list --%>
<% if (newListName == null || newListName.isEmpty() || newListDescription == null || newListDescription.isEmpty()) { %>
<p>Please enter List Title and List Description before submitting.</p>
<% } else { %>
<form  action="CreateListServlet" method="post">
    <input type="hidden" name="listTitle" value="<%= newListName %>">
    <input type="hidden" name="listDescription" value="<%= newListDescription %>">
    <button class="button" type="submit">SUBMIT</button>
</form>
<% } %>
</div>

</body>
</html>