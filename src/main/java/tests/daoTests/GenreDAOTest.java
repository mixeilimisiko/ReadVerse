package tests.daoTests;

import dao.ConnectionManager;
import dao.DbGenreDAO;
import dao.Interfaces.AuthorDAO;
import dao.Interfaces.GenreDAO;
import junit.framework.TestCase;
import model.Book;
import model.Genre;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenreDAOTest extends TestCase {

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
        setupScriptPath = "/sql/GenreDAOTestContent.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }


    public void testGetAllGenres() {
        DbGenreDAO gd = new DbGenreDAO(true);
        List<Genre> allGenres = gd.getAllGenres();
        assertEquals(26,allGenres.size());
    }

    public void testGetGenre() {
        DbGenreDAO gd = new DbGenreDAO(true);
        Genre genre = gd.getGenre("Adventure");
        assertEquals(2, genre.getId());
        assertEquals("Adventure", genre.getName());
        assertEquals("Adventure stories take readers on exciting journeys filled with challenges, exploration, and personal growth.", genre.getDescription());
    }


    public void testAddGenre(){
        DbGenreDAO gd = new DbGenreDAO(true);
        int oldSize = gd.getAllGenres().size();
        Genre genre = new Genre("Janri" + Integer.toString(oldSize+1), "Saintereso janri " + Integer.toString(oldSize+1));
        gd.addGenre(genre);
        assertEquals(oldSize + 1, gd.getAllGenres().size());
        Genre addedGenre = gd.getGenre("Janri" + Integer.toString(oldSize+1));
        assertEquals(oldSize + 1, addedGenre.getId());
        assertEquals("Janri" + Integer.toString(oldSize+1), addedGenre.getName());
        assertEquals("Saintereso janri " + Integer.toString(oldSize+1), addedGenre.getDescription());
    }

    public void testDeleteGenre(){
        DbGenreDAO gd = new DbGenreDAO(true);
        int genresSize = gd.getAllGenres().size();
        for(int i = genresSize; i < 28; i++) {
            gd.deleteGenre("Janri " + Integer.toString(i));
        }
        assertEquals(26, gd.getAllGenres().size());
    }


    public void testGetBookGenres() {
        DbGenreDAO gd = new DbGenreDAO(true);
        List<String> genreList =  gd.getBookGenres(1);
        assertEquals(2, genreList.size());
        assertEquals("Romance", genreList.get(0));
        assertEquals("Classics", genreList.get(1));
    }

    public void testGetTop5Books() {
        DbGenreDAO gd = new DbGenreDAO(true);
        List<Book> topBooks = gd.getTop5Books("Fiction");
        assertEquals(5, topBooks.size());
    }

    public void testGetBooksByCategories() {
        DbGenreDAO gd = new DbGenreDAO(true);
        List<String> genres = new ArrayList<>();
        genres.add("Satire");
        Set<String> genresSet = new HashSet<>(genres);
        List<Book> genreList = gd.getBooksByCategories(genres);
        for(int i = 0; i < genreList.size(); i++ ){
            List<String> currGenres = genreList.get(i).getGenres();
            Set<String> currSet = new HashSet<>(currGenres);
            for (String genre : currSet) {
                assertTrue(currGenres.contains(genre));
            }
        }

    }


}
