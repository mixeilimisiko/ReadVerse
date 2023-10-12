<%@ page import="model.Genre" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalog - ReadVerse</title>
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

        .main-container {
            display: flex;
            justify-content: space-between; /* Distribute space between left and right sections */
            /*align-items: center;*/
            max-width: 1200px;
            height: 80%;
            background-color: #f8e192;
            margin: 0 auto; /* Center the container horizontally */
        }

        .left-half {
            display: flex;
            flex-direction: column;
            align-items: center;
            /*justify-content: center;*/
            width: calc(70% - 10px); /* Set width to 50% minus some spacing */
            /*padding: 10px; !* Add padding for spacing *!*/
        }


        .right-half {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            justify-content: center;
            width: calc(30% - 50px); /* Set width to 50% minus some spacing */
            /*padding: 10px; !* Add padding for spacing *!*/
        }



        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
            margin-top: 10px;
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
            padding: 10px 20px;
            font-size: 15px;
            border-radius: 30px;
            text-decoration: none;
            color: #f8e192;
            background-color: black;
            width: 200px;
            text-align: center;
            cursor: pointer;
        }

        .button:hover {
            background-color: black;
        }


        .categories-box {
            display: flex;
            justify-content: space-between; /* Distribute space between the two sections */
            margin-top: 20px;
            background-color: antiquewhite;
            width: 25vw;
            min-width:300px;
            max-width: 600px;
            border-radius: 10px; /* Rounded corners */
            text-align: center;


        }

        .categories-left,
        .categories-right {
            flex: 1; /* Grow and shrink to fill the available space */
            padding: 10px; /* Add some padding for spacing */
        }

        .categories-left a,
        .categories-right a {
            display: block; /* Make the links take up the entire width of the container */
            margin-bottom: 5px; /* Add spacing between links */
            text-decoration: none;
            color: black;
            font-size: 16px;
        }

        .book-row {
            display: flex;
            flex-wrap: wrap;
            gap: 20px; /* Adjust the gap between book covers */
            align-items: center;
            justify-content: center;
            margin-top: 10px; /* Add some margin between genre rows */
        }

        .genre-name{
            font-size: 24px;
            margin-bottom: 12px;
            margin-top: 12px;

        }

        .book {
            margin-bottom: 10px;
            text-align: center;
            width: 130px; /* Set a fixed width for the book container */
            overflow: hidden;
        }
        .book-cover {
            width: 120px;
            height: 150px;
            overflow: hidden;
            margin: 0 auto; /* Center-align the book cover */
        }

        .book-cover img {
            max-width: 100%;
            max-height: 100%;
            object-fit: cover;
        }

        .book-title {
            font-size: 14px;
            margin-top: 5px;
        }

        .book-list {
            display: flex; /* Use flexbox to arrange items horizontally */
            flex-wrap: wrap; /* Allow items to wrap to a new line when needed */
            gap: 20px; /* Adjust the gap between book covers */
            /*align-items: center;*/
            /*justify-content: center;*/
            margin-top: 10px; /* Add some margin between genre rows */
        }

        .book-title {
            display: block; /* Make the links take up the entire width of the container */
            margin-bottom: 5px; /* Add spacing between links */
            text-decoration: none;
            color: black;
            font-size: 16px;
        }

        .book-searchbar{
            margin-top: 100px;
            margin-bottom: 40px;
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
    String errorMessage = (String)request.getAttribute("errorMessage") ;
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

<%
    List<Genre> genres = (List<Genre>) request.getAttribute("genres");
    HashMap<String, List<Book>> allGenres = (HashMap<String, List<Book>>) request.getAttribute("allGenres");
%>

<div class="main-container">
    <div class="left-half">
        <h1>Genres</h1>
        <form action="SearchGenreServlet" method="post" onsubmit="return validateForm();">
            <div class="form-container">
                <input type="text" id="search" name="search" placeholder="Find a genre by name" class="form-input" required>
                <button type="submit" class="button">Find Genre</button>

            </div>
        </form>
        <%for(HashMap.Entry<String, List<Book>> entry : allGenres.entrySet()) { %>
        <div class="genre-name"> <%=entry.getKey()%> </div>
        <div class="book-list">
            <% for(int j = 0; j < entry.getValue().size(); j++) {
                Book currBook = entry.getValue().get(j);
            %>
            <div class="book">
                <a href="/BookServlet?bookID=<%= currBook.getId() %>">
                    <div class="book-cover">
                        <img src="<%= currBook.getFullPath() %>" />
                    </div>
                </a>
                <div class="book-title">
                    <%= currBook.getTitle() %>
                </div>
            </div>
            <% }%>
        </div>
        <% }%>
    </div>
    <div class="right-half">


        <div class="book-searchbar">
            <div class="panel-title">
                <p>Find Book </p>
            </div>
            <form class="searchbar" action="SearchBookServlet" method="get">

                <input type="text" id="bookTitle" name="bookTitle" placeholder="Enter book title">
                <button type="submit">Search</button>
            </form>
            <% if(errorMessage != null) { %>
            <%=errorMessage %>
            <% }%>
        </div>

        <h2>Categories</h2>
        <div class="categories-box">

            <div class="categories-left">
                <% for (int i = 0; i < genres.size()/2; i++ ) { %>
                <a href="/genre?genreName=<%= genres.get(i).getName() %>"><%= genres.get(i).getName() %></a>                <% } %>

            </div>
            <div class="categories-right">
                <% for (int i = genres.size()/2; i < genres.size(); i++ ) { %>
                <a href="/genre?genreName=<%= genres.get(i).getName() %>"><%= genres.get(i).getName() %></a>
                <% } %>
            </div>
        </div>

    </div>
</div>
</body>
</html>





