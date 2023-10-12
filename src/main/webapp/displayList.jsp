<%@ page import="model.BookList" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Book List - ReadVerse</title>
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
    }

    h1 {
      margin-top: 40px;
      font-family: Century Gothic, serif;
    }

    .button {
      margin-left: 10px;
      padding: 5px 10px;
      background-color: #007bff;
      color: #fff;
      border: none;
      cursor: pointer;
      border-radius: 2px;
    }
    .add-form {
      margin-top: 20px;
    }

    .add-input {
      width: 300px;
      padding: 5px;
      margin-right: 10px;
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
<% boolean myPage = (Boolean)request.getAttribute("myPage"); %>
<%
  BookList bookList = (BookList) request.getAttribute("bookList");
%>
<% User user = (User)request.getAttribute("user"); %>
<%
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


<h1>List: <%= bookList.getTitle() %> by <%= user.getUsername() %></h1>



<% if(myPage) { %>
<form action="DeleteListServlet" method="POST" style="display: inline;">
  <input type="hidden" name="listID" value="<%= bookList.getListId() %>">
  <input type="hidden" name="sourceJSP" value="displayList">
  <button type="submit" class="button">Delete List</button>
</form>

<div class="add-form">
  <form action="AddBookToListServlet" method="post">
    <input type="text" name="bookTitle" class="add-input" placeholder="Enter Book Name" required>
    <input type="hidden" name="listID" value="<%= bookList.getListId() %>">
    <input type="hidden" name="userID" value="<%= bookList.getUserId() %>">
    <input type="hidden" name="sourceJSP" value="displayList">
    <button type="submit" class="button">Add to List</button>
  </form>
  <%-- display error if something went wrong while adding  --%>
  <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
  <% if (errorMessage != null) { %>
  <p class="error-message"><%= errorMessage %></p>
  <% } %>
</div>
<% } %>
<div>
  <% for (model.Book book : bookList.getBooks()) { %>
  <div class="book">
    <div class="book_cover">
      <img src=<%=book.getFullPath()%>>
      <p></p>
      <a href="BookServlet?bookID=<%= book.getId() %>">  See Book</a>
    </div>
    <div class="book_info">
        <h2><%= book.getTitle() %> </h2>
        <p>Author: <%= book.getAuthor() %> <a href="AuthorServlet?authorName=<%= book.getAuthor() %>">  See Author</a></p>
      <p>Description: <%= book.getDescription() %></p>
      <p>Rating: <%= book.getRating() %></p>
      <p>Genres:
        <% List<String> genres = book.getGenres();
          for (String genre : genres) { %>
        <%= genre + " " %>
        <% } %>
      </p>
      <p>Year: <%= book.getYear() %></p>
      <% if(myPage) { %>
      <form action="DeleteBookFromListServlet" method="POST" style="position: relative;">
        <input type="hidden" name="bookID" value="<%= book.getId() %>">
        <input type="hidden" name="listID" value="<%= bookList.getListId() %>">
        <%--      <input type="hidden" name="userID" value="<%= bookList.getUserId() %>">--%>
        <input type="hidden" name="sourceJSP" value="displayList">
        <button class="button" type="submit" style="position: absolute; bottom: 0; right: 0;">Delete Book</button>
      </form>
      <% } %>
    </div>

  </div>

  <% } %>
</div>
</body>
</html>