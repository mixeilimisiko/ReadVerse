<%@ page import="model.Book" %>
<%@ page import="java.text.DecimalFormat" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 8/1/2023
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <style>
        body {
            margin: 0;
            padding-top: 70px;
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f8e192;
            padding-left: 15vw;
            padding-right: 15vw;
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

        .book_cover {
            width: 15vw;
            height: 20vw;
            max-width: 100%;
        }

        .book_cover>img {
            width: 100%;
            border-radius: 4px;
            border: 0.1vw black;
            width: 100%;
            height: 100%;
            object-fit: contain;
        }

        .book_title {
            font-size: 3vw;
        }

        .book_rating {
            font-size: 2vw;
            margin-left: 0.2vw;
            margin-top: 0.5vw;
        }

        .book_author {
            font-size: 1.6vw;
            font-family: Century Gothic, serif;
        }

        .book_year {
            font-size: 2.5vw;
            font-family: Century Gothic, serif;
        }

        .description_label {
            font-size: 2vw;
            font-family: Century Gothic, serif;
        }

        .book_description {
            border-radius: 5px;
            font-size: 18px;
            padding: 0.5vw;
            background-color: antiquewhite;
            word-break: break-word;
        }

        .see_reviews_link {
            font-size: 1.2vw;
            font-family: Century Gothic, serif;
        }

        .write_review_label {
            font-size: 1.6vw;
            font-family: Century Gothic, serif;
        }

        .review_textarea {
            width: 30vw; /* Set the width to match the book cover's width */
            height: 10vw; /* Set the height to a fixed value or adjust as needed */
            resize: none; /* Prevent the textarea from being resizable */
            font-size: 18px; /* Optionally, adjust the font size */
            background-color: antiquewhite;
        }


        .rating_input {
            width: 20vw;
            height: 3vh;
            font-size: 1.3vw;
        }

        .add_review_button {
            font-size: 1.6vw;
            padding: 10px 20px;
        }


        #book_info_container {
            display: flex;
            border: 10px black;
            flex-direction: column;
            width: 50vw;
        }

        #info_without_description {
            display: flex;
            flex-direction: column;
            margin-bottom: 36px;
        }

        #description_container {
            display: flex;
            flex-direction: column;
            align-items: flex-end;
        }

        #desc_container {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            gap: 0.5rem;
        }

        #picture_and_book_info_container {
            display: flex;
            /*align-items: center;*/
            justify-content: center;
            gap: 2rem;
            margin-bottom: 16px;
        }

        #write_review_container {
            display: flex;
            flex-direction: column;
            width: 45%;
        }

        #all_items {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            gap: 36px;
            margin-top: 5%;
        }

        #reviewText {
            margin-bottom: 8px;
        }

        #submit, #reviewRating, #reviewText {
            padding: 5px 10px;
            border-radius: 4px;
            border: none;

        }

        #submit:hover {
            cursor: pointer;
            background-color: #007bff;
            color: #fff;
        }
    </style>
    <%
        Integer userID = (Integer) session.getAttribute("current_user_id"); // Adjust this to get the actual userID
        Book book = (Book) request.getAttribute("book");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
    %>
    <title><%= book.getTitle() %> - ReadVerse</title>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
</head>
<body>


<div class="navigation-panel">
    <a href="${pageContext.request.contextPath}/HomepageServlet" class="navigation-link">ReadVerse</a>
    <a href="${pageContext.request.contextPath}/CatalogueServlet" class="navigation-link">Catalogue</a>
    <a href="${pageContext.request.contextPath}/RecommendationsServlet" class="navigation-link">Recommendations</a>
    <% if(userID != null) { %>
    <a href="${pageContext.request.contextPath}/UserServlet?userID=<%= userID %>" class="navigation-link">My Profile</a>
    <% } %>
</div>
<div id="all_items">
    <div id="picture_and_book_info_container">
        <div class="book_cover">
            <img src=<%=book.getFullPath() %> >
        </div>

        <div id="book_info_container">
            <div id="info_without_description">
                <div class="book_title"><%= book.getTitle() %></div>
                <div class="book_year"><%= book.getYear() %></div>
                <a href="AuthorServlet?authorName=<%= book.getAuthor() %>" class="book_author"><%= book.getAuthor() %></a>
                <div class ="book_rating"> Rating : <%=  decimalFormat.format(book.getRating()) %></div>
            </div>

            <div id="desc_container">
                <div class="description_label"><strong> Description: </strong></div>
                <div id="description_container">
                    <div class="book_description"><%= book.getDescription() %></div>
                    <a href="BookReviewServlet?bookID=<%= book.getId() %>" class="see_reviews_link">See Reviews</a>
                </div>
            </div>
        </div>
    </div>


    <div id="write_review_container">
        <div class="write_review_label"><strong>Write A Review:</strong></div>
        <form id="review_form" onsubmit="return validateReviewForm()" action="AddReviewServlet" method="post">
            <div>
                <label for="reviewText"> </label><br>
                <textarea id="reviewText" name="reviewText" rows="8" cols="70" class="review_textarea"></textarea>
            </div>
            <div>
                <label for="reviewRating">Rating:</label><br>
                <input type="number" id="reviewRating" name="reviewRating" min="0" max="5" step="0.1">
                <input type="hidden" name="bookID" value="<%= book.getId() %>">
                <input id="submit" type="submit" value="Add Review">
            </div>

        </form>
    </div>



    <script>
        function validateReviewForm() {
            var reviewText = document.getElementById("reviewText").value.trim();
            var reviewRating = document.getElementById("reviewRating").value.trim();

            if (reviewRating === "") {
                alert("Please fill the rating field.");
                return false;
            }
            if (reviewText === "") {
                document.getElementById("reviewText").value = "";
            }
            return true;
        }
    </script>
</div>
</body>
</html>
