package tests.daoTests;

import dao.ConnectionManager;
import dao.DbBookDAO;
import dao.DbReviewDAO;
import dao.Interfaces.BookDAO;
import dao.Interfaces.ReviewDAO;
import junit.framework.TestCase;
import model.Book;
import model.Review;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewDAOTest extends TestCase {

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
        setupScriptPath = "/sql/ReviewDAOTestContent.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }

    public void testGetAllReviews(){
        ReviewDAO rd = new DbReviewDAO(true);
        List<Review> tempList = rd.getAllReviews();
        assertEquals(25, tempList.size());
    }

    public void testDeleteReview(){
        ReviewDAO rd = new DbReviewDAO(true);
        List<Review> tempList = rd.getAllReviews();
        assertEquals(25, tempList.size());
        rd.deleteReview(1);
        rd.deleteReview(2);
        rd.deleteReview(3);
        tempList = rd.getAllReviews();
        assertEquals(22, tempList.size());
    }

    public void testGetBookReviews(){
        ReviewDAO rd = new DbReviewDAO(true);
        List<Review> tempList = rd.getBookReviews(1);
        assertEquals(5, tempList.size());
        rd.deleteReview(16);
        tempList = rd.getBookReviews(1);
        assertEquals(4, tempList.size());
    }

    public void testGetUserReviews(){
        ReviewDAO rd = new DbReviewDAO(true);
        List<Review> tempList = rd.getUserReviews(1);
        assertEquals(5, tempList.size());
        for(int i = 1; i <= 5; i++){
            rd.deleteReview(i);
        }
        tempList = rd.getUserReviews(1);
        assertEquals(0, tempList.size());
    }

    public void testAddReview(){
        ReviewDAO rd = new DbReviewDAO(true);
        BookDAO bd = new DbBookDAO(true);
        List<String> tempGenres = new ArrayList<>();
        tempGenres.add("Genre 1");
        tempGenres.add("Genre 2");
        Book book = new Book(6, "Art of War", "Miyamato Musashi", "Cool book", 0.0, tempGenres, 1600);
        bd.addBook(book);
        Review tempReview = new Review(1, 6, 4.0, "Cool book :D", new Date());
        rd.addReview(tempReview);
        List<Review> tempList = rd.getUserReviews(1);
        assertEquals(6, tempList.size());
        tempList = rd.getAllReviews();
        assertEquals(26, tempList.size());
        tempList = rd.getBookReviews(6);
        assertEquals(1, tempList.size());
    }

}
