package tests.modelTests;

import junit.framework.TestCase;
import model.User;

public class UserTest extends TestCase {

    public void testConstructor(){
        User user = new User(1, "Filianor3", "tmp123", "sgven20@freeuni.edu.ge", "", 0);
        assertEquals(1, user.getId());
        assertEquals("Filianor3", user.getUsername());
        assertEquals("tmp123", user.getPassword());
        assertEquals("sgven20@freeuni.edu.ge", user.getEmail());
        assertEquals("", user.getAbout());
    }

    public void testSetters(){
        User user = new User(1, "Filianor3", "tmp123", "sgven20@freeuni.edu.ge", "", 0);
        user.setUserId(2);
        user.setEmail("lol@riot.com");
        user.setPassword("123");
        user.setUsername("perm");
        user.setAbout("");
        assertEquals(2, user.getId());
        assertEquals("perm", user.getUsername());
        assertEquals("123", user.getPassword());
        assertEquals("lol@riot.com", user.getEmail());
        assertEquals("", user.getAbout());
    }

    public void testToString(){
        User user = new User(1, "Filianor3", "tmp123", "sgven20@freeuni.edu.ge", "", 0);
        assertEquals("User{" +
                "userId=" + 1 +
                ", username='" + "Filianor3" + '\'' +
                ", password='" + "tmp123" + '\'' +
                ", email='" + "sgven20@freeuni.edu.ge" + '\'' +
                '}', user.toString());
    }

}
