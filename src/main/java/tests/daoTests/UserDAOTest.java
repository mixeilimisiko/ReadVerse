package tests.daoTests;

import dao.ConnectionManager;
import dao.DbBookDAO;
import dao.DbUserDAO;
import dao.Interfaces.BookDAO;
import dao.Interfaces.UserDAO;
import junit.framework.TestCase;
import model.Book;
import model.User;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest extends TestCase {

    static {
        try {

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws SQLException, IOException {
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);
        setupScriptPath = "/sql/UserDAOTestContent.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }

    public void testDummy() {
        UserDAO ud = new DbUserDAO(true);
        List<User> allUsers = ud.getAllUsers();
        assertEquals(5, allUsers.size());
    }

    public void testGetAllUsers() {
        UserDAO ud = new DbUserDAO(true);
        List<User> allUsers = ud.getAllUsers();
        assertEquals(5, allUsers.size());
        for(int i = 0; i < allUsers.size(); i++) {
            assertEquals("User" + (i + 1), allUsers.get(i).getUsername());
            assertEquals((i + 1), allUsers.get(i).getId());
        }
    }

    public void testUserAdd(){
        UserDAO ud = new DbUserDAO(true);
        User tmpUser = new User(6, "User6", "Password6", "user6@example.com", "", 1);
        ud.addUser(tmpUser);
        ud.addUser("User7", "Password7", "user7@example.com", "", 1);
        List<User> allUsers = ud.getAllUsers();
        assertEquals(7, allUsers.size());
    }

    public void testGetUser(){
        UserDAO ud = new DbUserDAO(true);
        User tmpUser1 = ud.getUser("User1");
        User tmpUser2 = new User(1, "User1", "Password1", "user1@example.com", "", 1);
        assertEquals(tmpUser1.toString(), tmpUser2.toString());
    }

    public void testGetUserByID(){
        UserDAO ud = new DbUserDAO(true);
        User tmpUser1 = ud.getUserByID("1");
        User tmpUser2 = new User(1, "User1", "Password1", "user1@example.com", "", 1);
        assertEquals(tmpUser1.toString(), tmpUser2.toString());
    }

    public void testGetUserByEmail(){
        UserDAO ud = new DbUserDAO(true);
        User tmpUser1 = ud.getUserByEmail("user1@example.com");
        User tmpUser2 = new User(1, "User1", "Password1", "user1@example.com", "", 1);
        assertEquals(tmpUser1.toString(), tmpUser2.toString());
    }

    public void testGetUsersFollowings(){
        UserDAO ud = new DbUserDAO(true);
        List<User> tmpList = ud.getUserFollowings(1);
        assertEquals(1, tmpList.size());
    }

    public void testGetUsersFollowers(){
        UserDAO ud = new DbUserDAO(true);
        List<User> tmpList = ud.getUserFollowers(1);
        assertEquals(1, tmpList.size());
    }

    public void testAddToFollowing(){
        UserDAO ud = new DbUserDAO(true);
        ud.addToFollowing(1, 3);
        List<User> tmpList = ud.getUserFollowings(1);
        assertEquals(2, tmpList.size());
    }

    public void testRemoveFollowing(){
        UserDAO ud = new DbUserDAO(true);
        ud.removeFollowing(1, 2);
        List<User> tmpList = ud.getUserFollowings(1);
        assertEquals(0, tmpList.size());
    }

    public void testUserAbout(){
        UserDAO ud = new DbUserDAO(true);
        ud.setUsersAboutInfo(1, "I am 20 years old");
        String tempInfo = ud.getUsersAboutInfo(1);
        assertEquals("I am 20 years old", tempInfo);
        ud.setUsersAboutInfo(1, "");
        tempInfo = ud.getUsersAboutInfo(1);
        assertEquals("", tempInfo);
    }

}
