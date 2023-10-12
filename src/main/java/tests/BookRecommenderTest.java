package tests;

import dao.ConnectionManager;
import dao.DbBookDAO;
import dao.Interfaces.BookDAO;
import junit.framework.TestCase;
import model.Book;
import dao.BookRecommender;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookRecommenderTest extends TestCase {

    public void setUp() throws SQLException, IOException {
        System.out.println("BEFORECLASS");
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);
        setupScriptPath = "/sql/contentForBookRecommender.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }

    public void testOne() {
        BookDAO bd = new DbBookDAO(true);
        List<Book> allBooks = bd.getAllBooks();
        Set<Book> testBooks = new HashSet<>();
        testBooks.add(allBooks.get(0));
        testBooks.add(allBooks.get(1));
        testBooks.add(allBooks.get(2));

        List<Book> result = BookRecommender.getRecommendations(testBooks, bd);
        assertNotNull(result);
        assertEquals("Book 11", result.get(0).getTitle());
        assertEquals("Book 8", result.get(1).getTitle());
        assertEquals("Book 20", result.get(2).getTitle());
        assertEquals("Book 6", result.get(3).getTitle());
        assertEquals("Book 10", result.get(4).getTitle());
        assertEquals("Book 18", result.get(5).getTitle());
        assertEquals("Book 14", result.get(6).getTitle());
        assertEquals("Book 13", result.get(7).getTitle());
        assertEquals("Book 7", result.get(8).getTitle());
        assertEquals("Book 4", result.get(9).getTitle());

    }

    public void testTwo() {
        BookDAO bd = new DbBookDAO(true);
        List<Book> allBooks = bd.getAllBooks();
        Set<Book> testBooks = new HashSet<>();
        testBooks.add(allBooks.get(1));
        testBooks.add(allBooks.get(3));
        testBooks.add(allBooks.get(5));

        List<Book> result = BookRecommender.getRecommendations(testBooks, bd);
        assertNotNull(result);
        assertEquals("Book 11", result.get(0).getTitle());
        assertEquals("Book 10", result.get(1).getTitle());
        assertEquals("Book 8", result.get(2).getTitle());
        assertEquals("Book 20", result.get(3).getTitle());
        assertEquals("Book 18", result.get(4).getTitle());
        assertEquals("Book 7", result.get(5).getTitle());
        assertEquals("Book 14", result.get(6).getTitle());
        assertEquals("Book 13", result.get(7).getTitle());
        assertEquals("Book 3", result.get(8).getTitle());
        assertEquals("Book 1", result.get(9).getTitle());

    }

}
