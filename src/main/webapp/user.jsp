<%@ page import="model.BookList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ page import="dao.Interfaces.UserDAO" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
  <link rel="icon" type="image/png" href="newLogo.png" sizes="16x16">
    <title>User Profile</title>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <link href='https://fonts.googleapis.com/css?family=Outfit&display=swap' rel='stylesheet'>
    <link rel="preconnect" href="https://rsms.me/">
    <link rel="stylesheet" href="https://rsms.me/inter/inter.css">
    <%
        String username = (String) request.getAttribute("username");
        Double ratingAverage = (Double) request.getAttribute("rating_average");
        Integer numberOfReviews = (Integer) request.getAttribute("number_of_reviews");
        Integer numberOfFollowers = (Integer) request.getAttribute("number_of_followers");
        Integer userID = (Integer) request.getAttribute("user_id");
        Integer numberOfFollowings = (Integer) request.getAttribute("number_of_followings");
        Boolean myProfileFlag = (Boolean) request.getAttribute("my_profile_flag");
        Boolean followingCurrUser = (Boolean) request.getAttribute("following_curr_user");
        Integer sessionID = (Integer) request.getAttribute("sessionID");
        List<BookList> bookLists = (List<BookList>) request.getAttribute("user_book_lists");
        String about = (String) request.getAttribute("about");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String imgPath = (String) request.getAttribute("imagePath");
    %>
</head>
<div class="navigation-panel">
    <a href="/HomepageServlet" class="navigation-link">ReadVerse</a>
    <a href="/CatalogueServlet" class="navigation-link">Catalogue</a>
    <a href="/RecommendationsServlet" class="navigation-link">Recommendations</a>
    <% if (userID != null) { %>
    <a href="/UserServlet?userID=<%= sessionID %>" class="navigation-link">My Profile</a>
    <% } %>
</div>
<body>

<style>
    body {
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
        background-color: #f8e192;
    }

    .main_content {
        max-width: 1200px;
        height: 100vh;
        margin: 6rem auto 0;
    }

    .profile_picture {
        background-color: black;
        width: 200px;
        height: 200px;
        margin-right: 20px;
        display: inline-block;
        vertical-align: top;
    }

    .followers,
    .followings,
    .rating_average,
    .number_of_reviews {
        font-size: 1.2rem;
        margin-top: -6px;
    }

    .username {
        font-size: 1.5rem;
        font-weight: bold;
        margin-top: 30px;
    }

    .attribute_group {
        margin-top: -25px;
    }

    .whole-profile {
        display: flex;
        flex-direction: column;
    }

    .upper-profile {
        display: flex;
    }

    .follow_button {
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: bold;
        margin-top: 2px;
    }

    .follow_button:hover {
        background-color: #0056b3;
    }

    .lists_section {
        margin-top: 30px;
        text-align: left; /* Align the content to the left */
    }

    .lists_heading {
        font-size: 1.2rem;
        /* Add some margin below the "Lists" heading to create space for the button */
        margin: 0 0 10px;
    }

    /* Increase height of the white box */
    .white_box {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 10px;
        /* Increase height to make the box larger */
        height: 300px;
        /* Add some space between the list boxes */
        margin-bottom: 30px;
        position: relative;
        overflow-x: auto;
    }

    .book_container {
        display: flex;
        flex-wrap: wrap; /* Add this property to allow content to wrap to the next line */
        justify-content: flex-start; /* Align the boxes to the left */
    }

    .book_image {
        width: 100px; /* Adjust width as needed */
        height: 150px; /* Increase the height to make the images taller */
        margin: 20px; /* Add some space between the book images */
        border-radius: 5px;
        object-fit: cover; /* Make sure the image covers the entire container */
    }

    /* Added new CSS for positioning list_title */
    .list_box {
        display: flex;
        flex-direction: column;
    }

    .list_title {
        align-self: flex-start;
    }

    .navigation-panel {
        background-color: black;
        padding: 10px;
        font-family: "Century Gothic", Arial, sans-serif;
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

    .user_info {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: calc(100% - 220px);
        margin-top: 0px;
    }

    .username {
        font-size: 1.5rem;
        font-weight: bold;
        margin: 0;
    }

    .log-out-button {
        padding: 10px 20px;
        background-color: #dc3545;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: bold;
        margin-top: -100px;
    }

    .log-out-button:hover {
        background-color: #c82333
    }


    .create_list_button {
        padding: 10px 20px;
        background-color: #28a745; /* Use a green color, you can adjust this to your preference */
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: bold;
        margin-bottom: 10px; /* Add some margin below the button */
    }

    .create_list_button:hover {
        background-color: #218838; /* Darker green color on hover */
    }

    .about-label {
        font-size: 1rem;
        color: #555; /* You can adjust the color to your preference */
        margin-top: 5px;
    }

    /* Add styles for the about section */
    .about-textarea,
    .about-text-box {
        background-color: antiquewhite;
        border-radius: 5px;
        padding: 10px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 1rem;
        width: 450px;
        height: 140px;
    }

    .about-textarea {
        resize: none; /* Prevent textarea from being resizable */
        margin-top: -10px;
    }

    .update-about-button {
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: bold;
        margin-top: 5px;
    }

    .update-about-button:hover {
        background-color: #0056b3;
    }

    .change-picture-button {
        padding: 10px 20px;
        background-color: mediumpurple; /* Use a yellow color, you can adjust this to your preference */
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: bold;
        margin-top: 5px;
    }

    .book_box {
        display: flex;
        flex-direction: column;
        align-items: center; /* Center align the content horizontally */
        text-align: center; /* Center align the text horizontally */
    }

    .change-picture-button:hover {
        background-color: rebeccapurple; /* Darker yellow color on hover */
    }

    .book_title {
        max-width : 100px;
    }

</style>

<script>
    function validateReviewForm() {
        const about_text = document.getElementById("about_text").value.trim();
        if (about_text === "") {
            alert("Please fill the rating field.");
            return false;
        }
        alert("About info has been updated");
        return true;
    }
</script>


<div class="main_content">
    <div class="whole-profile">
        <div class="upper-profile">
            <img src="/icons/<%= imgPath %>" alt="Profile Picture" class="profile_picture">
            <div class="user_info">
                <div class="username-about">
                    <h2 class="username"><%= username %>
                    </h2>
                    <!-- Add "About:" label -->
                    <p class="about-label">About:</p>
                    <!-- Display textarea or text-box based on myProfileFlag -->
                    <% if (myProfileFlag) { %>
                    <form id="about_form" onsubmit="return validateReviewForm()" action="/UserServlet" method="get">
                        <div>
                            <label for="about_text"></label>
                            <textarea id="about_text" name="about_text"
                                      class="about-textarea"><%= (about != null) ? about : "No About info for this user." %></textarea>
                        </div>
                        <div>
                            <button type="submit" class="update-about-button">Update About Info</button>
                            <input type="hidden" name="userID" value="<%=sessionID%>">
                        </div>
                    </form>
                    <% } else { %>
                    <div class="about-text-box"><%= (about != null) ? about : "No About info for this user." %>
                    </div>
                    <% } %>
                </div>
                <!-- Log-out button -->
                <% if (myProfileFlag) { %>
                <form action="/LogoutServlet" method="post">
                    <button type="submit" class="log-out-button">Log Out</button>
                </form>
                <% } %>
            </div>
        </div>
        <div class="attribute_group">
            <% if (myProfileFlag) { %>
            <!-- Add the "Change Picture" button -->
            <form action="/ProfilePictureServlet" method="post">
                <button type="submit" class="change-picture-button">Change Picture</button>
                <input type="hidden" name="userID" value="<%= sessionID %>">
            </form>
            <% } %>
            <% if (!myProfileFlag) { %>
            <form action="/FollowerServlet" method="post">
                <input type="hidden" name="sessionID" value="<%= sessionID %>">
                <input type="hidden" name="userID" value="<%= userID %>">
                <input type="hidden" name="followOrUnfollow" value="<%= followingCurrUser %>">
                <% if (followingCurrUser) { %>
                <button type="submit" class="follow_button">Following</button>
                <% } else { %>
                <button type="submit" class="follow_button">Follow</button>
                <% } %>
            </form>
            <% } %>
            <p class="followers">Followers: <a class="followers_link"
                                               href="/FollowersServlet?userID=<%= userID %>"><%= numberOfFollowers %>
            </a></p>
            <p class="followings">Following: <a class="followings_link"
                                                href="/FollowingsServlet?userID=<%= userID %>"><%= numberOfFollowings %>
            </a></p>
            <p class="number_of_reviews">Number of Reviews: <a class="reviews_link"
                                                               href="/UserReviewServlet?userID=<%= userID %>"><%= numberOfReviews %>
            </a></p>
            <p class="rating_average">Rating Average: <%=  decimalFormat.format(ratingAverage) %>
            </p>
        </div>

        <!-- ... (previous code) ... -->
        <!-- New code for displaying book lists and their associated books -->
        <div class="lists_section">
            <% if (myProfileFlag) { %>
            <form action="/CreateListServlet" method="post">
                <button type="submit" class="create_list_button">Create List</button>
            </form>
            <% } %>
            <h3 class="lists_heading">Lists:</h3>
            <% if (bookLists != null && !bookLists.isEmpty()) {
                for (BookList bookList : bookLists) {
            %>
            <div class="white_box">
                <div class="list_box">
                    <!-- Use anchor tag for hyperlink to DisplayServlet -->
                    <h4 class="list_title"><a
                            href="/DisplayListServlet?listID=<%= bookList.getListId() %>"><%= bookList.getTitle() %>
                    </a></h4>
                    <div class="book_container">
                        <% List<Book> booksInList = bookList.getBooks();
                            if (booksInList != null && !booksInList.isEmpty()) {
                                int maxBooksToShow = 7; // Maximum number of books to display
                                for (int i = 0; i < Math.min(maxBooksToShow, booksInList.size()); i++) {
                                    Book book = booksInList.get(i);
                        %>
                        <div class="book_box">
                            <img src="<%= book.getFullPath() %>" class="book_image">
                            <h5 class="book_title"><a
                                    href="/BookServlet?bookID=<%= book.getId() %>"><%= book.getTitle() %>
                            </a></h5>
                        </div>
                        <% }
                        } else { %>
                        <p>No books in this list.</p>
                        <% } %>
                    </div>
                </div>
            </div>
            <% }
            } else { %>
            <p>No lists to display.</p>
            <% } %>
        </div>
        <!-- End of Lists section -->
    </div>
</div>
</body>
</html>
