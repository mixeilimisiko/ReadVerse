<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Select Avatar</title>
    <%
        List<String> pictures = (List<String>) request.getAttribute("pictures");
    %>
    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f8e192;
            padding: 0 15px;
        }

        .center-content {
            max-width: 800px;
            margin: 50px auto 0;
            padding-top: 20px;
            text-align: center; /* Center the content */
        }

        .avatar-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 10px;
        }

        .avatar-image {
            width: 100px;
            height: 100px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            transition: border-color 0.3s ease-in-out; /* Add transition for smooth effect */
        }

        .avatar-image.selected {
            border: 2px solid #007bff; /* Add blue border to selected image */
        }

        .save-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            padding: 5px 20px;
            cursor: pointer;
            font-size: 1rem;
            margin-top: 40px; /* Add margin to position the button below */
            margin-left: -85px;
        }

        .save-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="center-content">
    <form action="/UserServlet" method="post">
        <div class="avatar-grid">
            <% for (int i = 0; i < 16; i++) { %>
            <img class="avatar-image" src="/icons/<%= pictures.get(i) %>" alt="Avatar <%= i + 1 %>"
                 onclick="selectAvatar(<%= i + 1%>)" id="avatar_<%= i + 1 %>">
            <% } %>
        </div>
        <input type="hidden" name="selectedAvatar" id="selectedAvatar" value="">
        <button class="save-button" type="submit" id="saveButton" disabled>Save</button>
    </form>
</div>

<script>
    let selectedAvatar = null;

    function selectAvatar(avatarId) {
        if (selectedAvatar !== null) {
            document.getElementById('avatar_' + selectedAvatar).classList.remove('selected');
        }
        selectedAvatar = avatarId;
        document.getElementById('avatar_' + selectedAvatar).classList.add('selected');
        document.getElementById('selectedAvatar').value = selectedAvatar;
        document.getElementById('saveButton').disabled = false;
    }
</script>
</body>
</html>
