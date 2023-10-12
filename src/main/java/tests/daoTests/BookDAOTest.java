package tests.daoTests;

import dao.ConnectionManager;
import dao.DbBookDAO;
import dao.Interfaces.BookDAO;
import junit.framework.TestCase;
import model.Book;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class BookDAOTest extends TestCase {

    private BookDAO bd;

    // es eshveba ertxel
    static {
        try {


        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // es eshveba tito testamde
    public void setUp() throws SQLException, IOException {
        bd = new DbBookDAO(true);
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);
        setupScriptPath = "/sql/booksTestContent.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }

    public void testDummy() {
        List<Book> allbooks = bd.getAllBooks();
        assertEquals(10, allbooks.size());

    }

    public void testGetAllBooks() {
        List<Book> allBooks = bd.getAllBooks();
        assertEquals(10, allBooks.size());
        for(int i = 0 ; i <  allBooks.size(); i++) {
            assertEquals("Book "+(i+1) ,allBooks.get(i).getTitle());
            assertEquals((i+1), allBooks.get(i).getId());
        }
    }

    public void testGetAllCategories() {
        List<String> allCategories = bd.getAllCategories();
        assertEquals(5, allCategories.size());
        for(int i = 0 ; i < allCategories.size(); i++) {
            assertEquals("Genre " + (i+1),(allCategories.get(i)));
        }
    }

    public void testGetBookGenres() {
        List<Book> allBooks = bd.getAllBooks();
        Map<Integer, String[]> checkMap = new HashMap<>();
        checkMap.put(1, new String[]{"Genre 1", "Genre 2", "Genre 3"});
        checkMap.put(2, new String[]{"Genre 2", "Genre 3", "Genre 4"});
        checkMap.put(3, new String[]{"Genre 1", "Genre 3", "Genre 5"});
        checkMap.put(4, new String[]{"Genre 2", "Genre 4", "Genre 5"});
        checkMap.put(5, new String[]{"Genre 1", "Genre 4", "Genre 5"});
        checkMap.put(6, new String[]{"Genre 1", "Genre 2", "Genre 3"});
        checkMap.put(7, new String[]{"Genre 1", "Genre 3", "Genre 4"});
        checkMap.put(8, new String[]{"Genre 2", "Genre 3", "Genre 5"});
        checkMap.put(9, new String[]{"Genre 1", "Genre 4", "Genre 5"});
        checkMap.put(10, new String[]{"Genre 2", "Genre 3", "Genre 4"});
        for(int i = 0; i < allBooks.size(); i++) {
            List<String> bookGenres = bd.getBookGenres(allBooks.get(i).getId());
            for(int j = 0; j < bookGenres.size(); j++) {
                assertEquals(checkMap.get(allBooks.get(i).getId())[j],  bookGenres.get(j));
            }
        }
    }

    public void testGetBooksByCategory(){
        List<String> allCategories = bd.getAllCategories();
        Map<Integer, int[]> checkMap = new HashMap<>();
        checkMap.put(1, new int[]{1, 3, 5, 6, 7, 9});
        checkMap.put(2, new int[]{1, 2, 4, 6, 8, 10});
        checkMap.put(3, new int[]{1, 2, 3, 6, 7, 8, 10});
        checkMap.put(4, new int[]{2, 4, 5, 7, 9, 10});
        checkMap.put(5, new int[]{3, 4, 5, 8, 9});
        for(int i = 0; i < allCategories.size(); i++ ) {
            List<Book> books = bd.getBooksByCategory(allCategories.get(i));
            assertEquals(checkMap.get(i+1).length, books.size());
            for(int j = 0; j < checkMap.get(i+1).length; j++) {
                assertTrue(Arrays.binarySearch(checkMap.get(i+1), books.get(j).getId())>=0);
            }
        }
    }


    public void testFindBook() {
        for(int i = 1; i <= 5; i++) {
            Book book = bd.findBook("Book "+i);
            assertEquals("Book "+i, book.getTitle());
            assertEquals("Author "+i, book.getAuthor());
            assertEquals("Description "+i, book.getDescription());
            assertEquals(4.0 + i/100.0, book.getRating());
            assertEquals(2000+i, book.getYear());
            assertEquals(bd.getBookGenres(i), book.getGenres());
        }
    }

    public void testFindBookById() {
        for(int i = 1; i <= 5; i++) {
            Book bookByTitle = bd.findBook("Book "+i);
            Book bookById = bd.findBookById(i);
            assertEquals(bookByTitle, bookById);
        }
    }

    public void testGetBibliography() {
        for(int i = 1; i <= 5; i++) {
            List<Book> bibliography = bd.getBibliography("Author " + i);
            assertTrue(bibliography.contains(bd.findBookById(i)));
            assertTrue(bibliography.contains(bd.findBookById(i+5)));
        }
    }

    public void testGetBooksByCategories() {
        List<String> allCategories = bd.getAllCategories();
        int min = 0;
        int max = 4;
        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            List<String> genres = new ArrayList<String>();
            int rand1 = random.nextInt(max - min + 1) + min;
            int rand2 = random.nextInt(max - min + 1) + min;
            while(rand1==rand2){
                rand2 = random.nextInt(max - min + 1) + min;
            }
            String genre1 = allCategories.get(rand1);
            String genre2 = allCategories.get(rand2);
            genres.add(genre1);
            genres.add(genre2);
            List<Book> checkList1 = bd.getBooksByCategory(genre1);
            List<Book> checkList2 = bd.getBooksByCategory(genre2);
            List<Book> bookGenres = bd.getBooksByCategories(genres);
            for(Book b: bookGenres) {
                assertTrue(checkList1.contains(b)||checkList2.contains(b));
            }
        }
    }

    public void testAddBook() throws SQLException, IOException {
        // clear database
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);

        List<String> genres = new ArrayList<>();
        genres.add("Genre 1");
        genres.add("Genre 2");
        Book newBook = new Book("Book 1", "Author 1", "Description 1", 4.0, genres, 2002);
        bd.addBook(newBook);
        Book foundBook = bd.findBook("Book 1");
        assertEquals(1, bd.findBook("Book 1").getId());
        assertEquals(newBook.getAuthor(), foundBook.getAuthor());
        assertEquals(newBook.getDescription(), foundBook.getDescription());
        assertEquals(newBook.getRating(), foundBook.getRating());
        assertEquals(newBook.getGenres(), foundBook.getGenres());
        assertEquals(newBook.getYear(), foundBook.getYear());
    }

    public void testUpdateBook() throws SQLException, IOException {
        // clear database
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);

        List<String> genres = new ArrayList<>();
        genres.add("Genre 1");
        genres.add("Genre 2");
        Book newBook = new Book("Book 1", "Author 1", "Description 1", 4.0, genres, 2002);
        bd.addBook(newBook);
        Book foundBook = bd.findBook("Book 1");
        int id = foundBook.getId();
        newBook.setId(id);
        newBook.setTitle("Book 1 updated");
        newBook.setAuthor("Author 1 updated");
        newBook.setDescription("Description 1 updated");
        newBook.setRating(4.5);
        newBook.setYear(2003);
        newBook.setBookcoverPath("customImage.png");
        genres.add("Genre 3");
        newBook.setGenres(genres);
        bd.updateBook(newBook);
        foundBook = bd.findBookById(newBook.getId());
        assertEquals(newBook.getTitle(), foundBook.getTitle());
        assertEquals(newBook.getAuthor(), foundBook.getAuthor());
        assertEquals(newBook.getDescription(), foundBook.getDescription());
        assertEquals(newBook.getRating(), foundBook.getRating());
        assertEquals(newBook.getGenres(), foundBook.getGenres());
        assertEquals(newBook.getYear(), foundBook.getYear());
        assertEquals(newBook.getBookcoverPath(), foundBook.getBookcoverPath());


    }

    public void testDeleteBook() throws SQLException, IOException {
        // clear database
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);

        List<String> genres = new ArrayList<>();
        genres.add("Genre 1");
        genres.add("Genre 2");
        Book newBook = new Book("Book 1", "Author 1", "Description 1", 4.0, genres, 2002);
        bd.addBook(newBook);
        Book foundBook = bd.findBook("Book 1");
        assertEquals(1, bd.findBook("Book 1").getId());
        assertEquals(newBook.getAuthor(), foundBook.getAuthor());
        assertEquals(newBook.getDescription(), foundBook.getDescription());
        assertEquals(newBook.getRating(), foundBook.getRating());
        assertEquals(newBook.getGenres(), foundBook.getGenres());
        assertEquals(newBook.getYear(), foundBook.getYear());
        bd.deleteBook(foundBook.getId());
        assertEquals(null, bd.findBookById(foundBook.getId()));
    }

    public void testCompositionOne() {

    }

    public void testCompositionTwo() {

    }
}
