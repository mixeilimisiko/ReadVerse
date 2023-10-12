package dao;

import dao.Interfaces.ReviewDAO;
import dao.Interfaces.UserDAO;
import model.Review;
import model.User;
import services.Hasher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUserDAO implements UserDAO {
    private boolean isTest;

    public DbUserDAO() {
        this.isTest = false;
    }

    public DbUserDAO(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest)) {
            String sql = "INSERT INTO users (username, password, email, about_user, avatar_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, Hasher.generateHash(user.getPassword()));
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getAbout());
                statement.setString(5, Integer.toString(user.getAvatarId()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(String username, String password, String email, String about, int avatarID) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (username, password, email, about_user, avatar_id) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, Hasher.generateHash(password));
            statement.setString(3, email);
            statement.setString(4, about);
            statement.setString(5, String.valueOf(avatarID));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
    }

    @Override
    // Retrieve a user by username
    public User getUser(String username) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
        return null;
    }

    @Override
    public User getUserByID(String userID) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE user_id = ?")) {
            statement.setString(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
        return null;
    }


    @Override
    public User getUserByName(String username) {
        User user = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = extractUserFromResultSet(resultSet);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = extractUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
        return users;
    }

    @Override
    public List<User> getUserFollowers(int userID) {
        List<User> followers = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT u.* FROM users u INNER JOIN followers f ON u.user_id = f.follower_id WHERE f.following_id = ?")) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                followers = new ArrayList<>();
                while (resultSet.next()) {
                    User follower = extractUserFromResultSet(resultSet);
                    followers.add(follower);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
        return followers;
    }

    @Override
    public void removeFollowing(int sessionID, int userID) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM followers WHERE follower_id = ? AND following_id = ?")) {
            statement.setInt(1, sessionID);
            statement.setInt(2, userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
    }

    @Override
    public void addToFollowing(int sessionID, int userID) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO followers (follower_id, following_id) VALUES (?, ?)")) {
            statement.setInt(1, sessionID);
            statement.setInt(2, userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
    }


    @Override
    public List<User> getUserFollowings(int userID) {
        List<User> userFollowings = new ArrayList<>();
        String sql = "SELECT u.* FROM users u INNER JOIN followers f ON u.user_id = f.following_id WHERE f.follower_id = ?";
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    userFollowings.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You may replace this with logging
        }
        return userFollowings;
    }

    @Override
    public User getUserByEmail(String email) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUsersAboutInfo(int userId) {
        String aboutInfo = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT about_user FROM users WHERE user_id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    aboutInfo = resultSet.getString("about_user");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aboutInfo;
    }

    @Override
    public void setUsersAboutInfo(int userId, String about) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET about_user = ? WHERE user_id = ?")) {
            statement.setString(1, about);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserAvatar(int userId, int avatarId) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET avatar_id = ? WHERE user_id = ?")) {
            statement.setInt(1, avatarId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getUserAvatar(int userId) {
        int avatarId = -1; // Default value in case no avatar is found

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT avatar_id FROM users WHERE user_id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    avatarId = resultSet.getInt("avatar_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avatarId;
    }

    @Override
    public List<String> getAllAvatars() {
        List<String> avatarPaths = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT img_path FROM avatars")) {

            while (resultSet.next()) {
                avatarPaths.add(resultSet.getString("img_path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avatarPaths;
    }

    public String getAvatarPath(int avatarId) {
        String imgPath = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT img_path FROM avatars WHERE avatar_id = ?")) {
            statement.setInt(1, avatarId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    imgPath = resultSet.getString("img_path");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imgPath;
    }



    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        if (user.getId() <= 0) return null;
        return user;
    }
}
