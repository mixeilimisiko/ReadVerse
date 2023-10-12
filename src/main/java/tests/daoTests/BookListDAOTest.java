package tests.daoTests;

import dao.ConnectionManager;
import dao.DbBookDAO;
import dao.DbBookListDAO;
import dao.Interfaces.BookDAO;
import dao.Interfaces.BookListDAO;
import junit.framework.TestCase;
import model.Book;
import model.BookList;
import services.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookListDAOTest extends TestCase {

    private BookListDAO bd;

    static{

    }

    public void setUp() throws SQLException, IOException {
        System.out.println("BEFORECLASS");
        bd = new DbBookListDAO(true);
        String setupScriptPath = "/sql/testSchema.sql";
        Connection conn = ConnectionManager.getInstance().getConnection(false);
        ScriptRunner.runScript(conn, setupScriptPath);
        setupScriptPath = "/sql/booksTestContent.sql";
        ScriptRunner.runScript(conn, setupScriptPath);
        conn.close();
    }

    public void testDummy(){
        BookListDAO dummyDAO = new DbBookListDAO();

    }

    public void testCreateList(){
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "list 7";
        String description = "description 7";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        books.add(allBooks.get(2));
        BookList bookList = new BookList(userId, title, description, books);
        bookList.setListId(7);
        bd.createList(bookList);
        assertEquals(bookList.getListId(), bd.getUserList(1, "list 7").getListId() );
        assertEquals(bookList, bd.getUserList(1, "list 7") );
    }

    public void testDeleteList(){
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "list 7";
        String description = "description 7";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        books.add(allBooks.get(2));
        BookList bookList = new BookList(userId, title, description, books);
        bookList.setListId(7);
        bd.createList(bookList);
        assertEquals(bookList.getListId(), bd.getUserList(1, "list 7").getListId() );
        assertEquals(bookList, bd.getUserList(1, "list 7") );
        bd.deleteList(bookList.getListId());
        assertEquals(null, bd.getListById(bookList.getListId()));
    }
    public void testDeleteListTwo() {
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "list 7";
        String description = "description 7";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        books.add(allBooks.get(2));
        BookList bookList = new BookList(userId, title, description, books);
        bookList.setListId(7);
        bd.createList(bookList);
        assertEquals(bookList.getListId(), bd.getUserList(1, "list 7").getListId() );
        assertEquals(bookList, bd.getUserList(1, "list 7") );
        bd.deleteList(1, "list 7");
        assertEquals(null, bd.getListById(bookList.getListId()));
    }

    public void testGetAllUserLists() {
        for (int i = 1; i < 4; i++) {
            List<BookList> userLists = bd.getAllUserLists(i);
            assertEquals(4 - i, userLists.size());
            for (int j = 0; j < userLists.size(); j++) {
                System.out.println(userLists.get(j).size());
                assertEquals(3 - j, userLists.get(j).size());
            }
        }
    }


    public void testGetUserList() {
        BookList bl = bd.getUserList(1, "List 1");
        List<Book> bookList = bl.getBooks();
        List<Book> cmpList = new ArrayList<>();
        List<String> genres = new ArrayList<>(Arrays.asList(new String[]{"Genre 1", "Genre 2", "Genre 3"}));
        cmpList.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.01, genres, 2001));
        genres = new ArrayList<>(Arrays.asList(new String[]{"Genre 2", "Genre 4", "Genre 5"}));
        cmpList.add(new Book(4, "Book 4", "Author 4", "Description 4", 4.04, genres, 2004));
        genres = new ArrayList<>(Arrays.asList(new String[]{"Genre 1", "Genre 4", "Genre 5"}));
        cmpList.add(new Book(5, "Book 5", "Author 5", "Description 5", 4.05, genres, 2005));
        assertEquals(cmpList.size(), bookList.size());
        for(Book b : bookList ) {
            System.out.println("A");
            assertTrue(cmpList.contains(b));
        }

    }
    public void testGetListById(){
        // Create a new book list
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "Test List";
        String description = "Test List Description";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        BookList bookList = new BookList(userId, title, description, books);
        bd.createList(bookList);

        int listId = bd.getUserList(userId, title).getListId();
        bookList.setListId(listId);

        BookList retrievedList = bd.getListById(listId);

        assertEquals(bookList, retrievedList);
    }

    public void testAddBookToList() {
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "Test List";
        String description = "Test List Description";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        BookList bookList = new BookList(userId, title, description, books);
        bd.createList(bookList);

        Book bookToAdd = allBooks.get(2);

        bd.addBookToList(userId, title, bookToAdd.getId());

        BookList updatedList = bd.getUserList(userId, title);
        assertTrue(updatedList.getBooks().contains(bookToAdd));
    }


    public void testAddBookToListTwo() {
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "Test List";
        String description = "Test List Description";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        BookList bookList = new BookList(userId, title, description, books);
        bd.createList(bookList);

        Book bookToAdd = allBooks.get(2);

        int listId = bd.getUserList(userId, title).getListId();

        bd.addBookToList(userId, listId, bookToAdd.getId());

        BookList updatedList = bd.getUserList(userId, title);
        assertTrue(updatedList.getBooks().contains(bookToAdd));
    }

    public void testRemoveBookFromList(){
        // Create a new book list
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "Test List";
        String description = "Test List Description";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        books.add(allBooks.get(2));
        BookList bookList = new BookList(userId, title, description, books);
        bd.createList(bookList);
        Book bookToRemove = allBooks.get(0);
        bd.removeBookFromList(userId, title, bookToRemove.getId());
        BookList updatedList = bd.getUserList(userId, title);
        assertFalse(updatedList.getBooks().contains(bookToRemove));
    }

    public void testRemoveBookFromListTwo(){
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "Test List";
        String description = "Test List Description";
        List<Book> books = new ArrayList<Book>();
        books.add(allBooks.get(0));
        books.add(allBooks.get(1));
        books.add(allBooks.get(2));
        BookList bookList = new BookList(userId, title, description, books);
        bd.createList(bookList);
        Book bookToRemove = allBooks.get(1);
        int listId = bd.getUserList(userId, title).getListId();
        bd.removeBookFromList(listId, bookToRemove.getId());
        BookList updatedList = bd.getUserList(userId, title);
        assertFalse(updatedList.getBooks().contains(bookToRemove));
    }

    public void testAddAndRemoveBookFromList() {
        BookDAO bookDAO = new DbBookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        int userId = 1;
        String title = "Test List";
        String description = "Test List Description";
        List<Book> initialBooks = new ArrayList<Book>();
        initialBooks.add(allBooks.get(0));
        initialBooks.add(allBooks.get(1));
        initialBooks.add(allBooks.get(2));
        BookList bookList = new BookList(userId, title, description, initialBooks);

        bd.createList(bookList);

        Book bookToAdd = allBooks.get(3);
        bd.addBookToList(userId, title, bookToAdd.getId());

        BookList updatedList = bd.getUserList(userId, title);
        assertTrue(updatedList.getBooks().contains(bookToAdd));

        bd.removeBookFromList(userId, title, bookToAdd.getId());

        updatedList = bd.getUserList(userId, title);
        assertFalse(updatedList.getBooks().contains(bookToAdd));
    }


    public void testBookListFunctionality() {
        List<Book> books1 = new ArrayList<>();
        books1.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.01,
                Arrays.asList("Genre 1", "Genre 2", "Genre 3"), 2001));
        books1.add(new Book(2, "Book 2", "Author 2", "Description 2", 4.02,
                Arrays.asList("Genre 2", "Genre 3", "Genre 4"), 2002));
        BookList bookList1 = new BookList(5, "Test List 1", "List Description 1", books1);

        bd.createList(bookList1);

        List<Book> books2 = new ArrayList<>();
        books2.add(new Book(3, "Book 3", "Author 3", "Description 3", 4.03,
                Arrays.asList("Genre 1", "Genre 4", "Genre 5"), 2003));
        books2.add(new Book(4, "Book 4", "Author 4", "Description 4", 4.04,
                Arrays.asList("Genre 2", "Genre 4", "Genre 5"), 2004));

        BookList bookList2 = new BookList(5, "Test List 2", "List Description 2", books2);

        bd.createList(bookList2);

        bd.addBookToList(5, "Test List 1", 3);
        bd.addBookToList(5, "Test List 2", 1);
        bd.removeBookFromList(5, "Test List 1", 2);

        BookList updatedList1 = bd.getUserList(5, "Test List 1");
        assertEquals(2, updatedList1.size());
        assertEquals(3, updatedList1.getBook(1).getId());

        BookList updatedList2 = bd.getUserList(5, "Test List 2");
        assertEquals(3, updatedList2.size());
        assertEquals(3, updatedList2.getBook(1).getId());

        bd.deleteList(5, "Test List 1");
        assertNull(bd.getUserList(5, "Test List 1"));
        assertNotNull(bd.getUserList(5, "Test List 2"));

        List<BookList> userLists = bd.getAllUserLists(5);
        assertEquals(1, userLists.size());
        assertEquals("Test List 2", userLists.get(0).getTitle());
    }



}
