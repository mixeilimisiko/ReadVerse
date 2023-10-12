<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>


<html>
<head>
    <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <title><c:out value="${genre.getName()}"/></title>
    <style>
        body {
            margin: 10px;
            font-family: "Century Gothic", Arial, sans-serif;
            background-color: #f8e192;
            padding-top: 70px;
            padding-left: 40px;
        }

        .genre_description {
            border-radius: 5px;
            font-size: 1.3vw;
            border: 0.2vw solid black;
            padding: 0.5vw;
            background-color: antiquewhite;
            word-break: break-word;
            width: 70vw;
        }

        .book-container {
            display: flex;
            flex-wrap: wrap;
            margin-top: 1rem;
            justify-content: flex-start;
            align-items: flex-start;
            gap: 10px; /* Adjust the gap value as desired */
            width: 50vw; /* Set the width to 50% of the viewport width */
        }

        .book-cover {
            flex: 0 0 15%; /* Each book cover will take 20% of the container width */

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
    <a href="${pageContext.request.contextPath}/HomepageServlet" class="navigation-link">ReadVerse</a>
    <a href="${pageContext.request.contextPath}/CatalogueServlet" class="navigation-link">Catalogue</a>
    <a href="${pageContext.request.contextPath}/RecommendationsServlet" class="navigation-link">Recommendations</a>
    <% if(userID != null) { %>
    <a href="${pageContext.request.contextPath}/UserServlet?userID=<%= userID %>" class="navigation-link">My Profile</a>
    <% } %>
</div>
<body>
<h1>Genre: <c:out value="${genre.getName()}"/></h1>

<div class="genre_description"> <c:out value="${genre.getDescription()}" />
</div>

<div class="book-container">
    <c:forEach items="${genreBooks}" var="book" varStatus="loop">
        <div class="book-cover">
            <a href="/BookServlet?bookID=${book.id}">
                <img src="${book.getFullPath() }" alt="${book.title}" width="115" height="180" />
            </a>
        </div>
        <c:if test="${loop.index % 5 == 4}">
            <br/>
        </c:if>
    </c:forEach>
</div>
</body>
</html>
