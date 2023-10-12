<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 7/8/2023
  Time: 8:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Book Reviews - ReadVerse</title>
  <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
  <style>
    body {
      display: flex;
      flex-direction: column;
      align-items: center;
      font-family: Arial, Helvetica, sans-serif;
      background-color: #f8e192;
      padding-top: 70px;
    }

    h1 {
      font-family: Century Gothic, serif;
    }

    .review-box {
      display: flex;
      justify-content: space-between;
      width: 80vw;
      /*align-items: center;*/
      background-color: azure;
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
<h1>Book Reviews</h1>
<div>
  <%@ page import="model.Book" %>
  <%@ page import="model.Review" %>
  <%@ page import="java.util.Map" %>


  <%-- Retrieve the data map from the request attribute --%>
  <% Map<Review, Book> data = (Map<Review, Book>) request.getAttribute("data"); %>
  <% Map<Integer, String> usernames = (Map<Integer, String>) request.getAttribute("usernames"); %>

  <%-- Check if the data map is not null and contains the required values --%>
  <% if (data != null ) { %>
  <%-- Retrieve the book and reviews from the data map --%>
  <%--  <% List<Review> reviews = (List<Review>) data.get("reviews"); %>--%>

  <%-- Iterate over the reviews and generate the review boxes --%>
  <% for (Review currReview : data.keySet()) { %>
  <div class="review-box">
    <div class="review-image">
      <img src=<%= data.get(currReview).getFullPath()%>>
    </div>
    <div class="review-details">
      <div class="title-and-rating">
        <div class="rating-title">
          <div class="review-title"><%= data.get(currReview).getTitle() %></div>
          <div class="review-rating">Rating: <%= currReview.getRating() %></div>
        </div>
      </div>
      <div class="review-author-year"><%= data.get(currReview).getAuthor() %> | <%= data.get(currReview).getYear() %>
        <a href="AuthorServlet?authorName=<%= data.get(currReview).getAuthor() %>">    See Author</a>
      </div>
      <div class="line"></div>
      <div class="review-text"><%= currReview.getReviewTxt() %></div>
      <div class="review-username">User: <a href="UserServlet?userID=<%= currReview.getUserId() %>"><%= usernames.get(currReview.getUserId()) %></a></div>
      <div class="review-date"><%=currReview.getDate()%></div>
    </div>
  </div>
  <% } %>
  <% } else { %>
  <p>No reviews found.</p>
  <% } %>
</div>
</body>
</html>