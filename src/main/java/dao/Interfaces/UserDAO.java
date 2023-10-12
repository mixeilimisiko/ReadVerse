package dao.Interfaces;

import model.User;
import services.Hasher;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface UserDAO {

    public void addUser(User user) ;

    public void addUser(String username, String password, String email, String about, int avatarID);

    public User getUser(String username);

    public User getUserByID(String userID) ;

    public User getUserByName(String username);

    public List<User> getAllUsers() ;

    public List<User> getUserFollowers(int userID);

    public void removeFollowing(int sessionID, int userID);

    public void addToFollowing(int sessionID, int userID);

    public List<User> getUserFollowings(int userID);

    public User getUserByEmail(String email);

    public String getUsersAboutInfo(int userId);

    public void setUsersAboutInfo(int userId, String about);

    public void setUserAvatar(int userId, int avatarId);

    public int getUserAvatar(int userId);

    public List<String> getAllAvatars();

    public String getAvatarPath(int avatarId);
}
