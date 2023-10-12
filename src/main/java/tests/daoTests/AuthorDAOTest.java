package tests.daoTests;

import dao.ConnectionManager;
import dao.DbAuthorDAO;
import dao.Interfaces.AuthorDAO;
import dao.Interfaces.UserDAO;
import junit.framework.TestCase;
import model.Author;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AuthorDAOTest extends TestCase {

    static {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws SQLException, IOException {
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);
        setupScriptPath = "/sql/AuthorDAOTestContent.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }

    public void testDummy() {
        AuthorDAO ad = new DbAuthorDAO(true);
        List<Author> allAuthors = ad.getAllAuthors();
        assertEquals(5, allAuthors.size());
    }

    public void testGetAuthor() {
        AuthorDAO ad = new DbAuthorDAO(true);
        Author firstAuthor = ad.getAuthor(1);
        assertEquals(1, firstAuthor.getId());
        assertEquals("Author 1", firstAuthor.getName());
        assertEquals("Author 1 info", firstAuthor.getInfo());
    }

    public void testGetAuthor2() {
        AuthorDAO ad = new DbAuthorDAO(true);
        Author secAuthor = ad.getAuthor("Author 2");
        assertEquals(2, secAuthor.getId());
        assertEquals("Author 2", secAuthor.getName());
        assertEquals("Author 2 info", secAuthor.getInfo());
    }

    public void testUserAdd() {
        AuthorDAO ad = new DbAuthorDAO(true);
        int authorsSize = ad.getAllAuthors().size();
        Author author = new Author(authorsSize + 1, "Author " + Integer.toString(authorsSize + 1), "Author " + Integer.toString(authorsSize + 1) + " info", "noPicture.png");
        ad.addAuthor(author);
        List<Author> allAuthors = ad.getAllAuthors();
        assertEquals(authorsSize + 1, allAuthors.size());
    }

    public void testDeleteAuthor() {
        AuthorDAO ad = new DbAuthorDAO(true);
        int authorsSize = ad.getAllAuthors().size();
        for (int i = authorsSize; i < 5; i++) {
            ad.deleteAuthor("Author " + Integer.toString(i));
        }
        assertEquals(5, ad.getAllAuthors().size());
    }

    public void testGetImgPath(){
        AuthorDAO ad = new DbAuthorDAO(true);
        assertEquals("Dan_Brown.jpg", ad.getImage("Author 1"));
    }


}
