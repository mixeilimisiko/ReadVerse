package tests.modelTests;

import junit.framework.TestCase;
import model.Book;
import model.BookList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BookListTest extends TestCase {

    public void testConstructor() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));

        BookList bookList = new BookList(1, 1, "List 1", "Description 1", books);

        assertEquals(1, bookList.getListId());
        assertEquals(1, bookList.getUserId());
        assertEquals("List 1", bookList.getTitle());
        assertEquals("Description 1", bookList.getDescription());
        assertEquals(1, bookList.size());
        assertEquals("Book 1", bookList.getBook(0).getTitle());
    }

    public void testAllConstructors() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));

        // Test constructor with all parameters
        BookList constructorWithAllParams = new BookList(1, 1, "List 1", "Description 1", books);
        assertEquals(1, constructorWithAllParams.getListId());
        assertEquals(1, constructorWithAllParams.getUserId());
        assertEquals("List 1", constructorWithAllParams.getTitle());
        assertEquals("Description 1", constructorWithAllParams.getDescription());
        assertEquals(books, constructorWithAllParams.getBooks());
        // Test constructor without listId and books
        BookList constructorWithoutListIdAndBooks = new BookList(2, "List 2", "Description 2");
        assertEquals(0, constructorWithoutListIdAndBooks.getListId()); // Should default to 0
        assertEquals(2, constructorWithoutListIdAndBooks.getUserId());
        assertEquals("List 2", constructorWithoutListIdAndBooks.getTitle());
        assertEquals("Description 2", constructorWithoutListIdAndBooks.getDescription());
        assertEquals(new ArrayList<Book>(), constructorWithoutListIdAndBooks.getBooks());

    }

    public void testConstructorWithUserIdAndBooks() {
        int userId = 201;
        String title = "Another Book List";
        String description = "Books to read next";
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));
        books.add(new Book(2, "Book 2", "Author 2", "Description 2", 4.5,
                Arrays.asList("Genre A", "Genre B"), 2010));
        BookList bookList = new BookList(userId, title, description, books);
        assertEquals(userId, bookList.getUserId());
        assertEquals(title, bookList.getTitle());
        assertEquals(description, bookList.getDescription());
        assertEquals(2, bookList.size()); // Two books added in the constructor
        assertEquals(books, bookList.getBooks());
    }

    public void testConstructorWithListId() {
        int listId = 1;
        int userId = 101;
        String title = "My Book List";
        String description = "A list of my favorite books";
        BookList bookList = new BookList(listId, userId, title, description);
        assertEquals(listId, bookList.getListId());
        assertEquals(userId, bookList.getUserId());
        assertEquals(title, bookList.getTitle());
        assertEquals(description, bookList.getDescription());
        assertEquals(0, bookList.size()); // No books added yet
    }


    public void testGetters() {
        List<Book> books = new ArrayList<>();
        BookList bookList = new BookList(1, 1, "List 1", "Description 1", books);

        assertEquals(1, bookList.getListId());
        assertEquals(1, bookList.getUserId());
        assertEquals("List 1", bookList.getTitle());
        assertEquals("Description 1", bookList.getDescription());
        assertTrue(bookList.getBooks().isEmpty());
    }

    public void testSetters() {
        List<Book> initialBooks = new ArrayList<>();
        initialBooks.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));
        BookList bookList = new BookList(1, 1, "List 1", "Description 1", initialBooks);

        bookList.setListId(2);
        bookList.setUserId(2);
        bookList.setTitle("List 2");
        bookList.setDescription("Description 2");

        assertEquals(2, bookList.getListId());
        assertEquals(2, bookList.getUserId());
        assertEquals("List 2", bookList.getTitle());
        assertEquals("Description 2", bookList.getDescription());
        assertEquals(initialBooks, bookList.getBooks());

            List<Book> newBooks = new ArrayList<>();
            newBooks.add(new Book(2, "Book 2", "Author 2", "Description 2", 4.5,
                    Arrays.asList("Genre A", "Genre B"), 2010));
            assertEquals(initialBooks, bookList.getBooks());

            bookList.setBooks(newBooks);

            assertEquals(newBooks, bookList.getBooks());
    }


    public void testEquals() {
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();

        books1.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));
        books2.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));

        BookList bookList1 = new BookList(1, 1, "List 1", "Description 1", books1);
        BookList bookList2 = new BookList(1, 1, "List 1", "Description 1", books2);

        assertEquals(bookList1, bookList2);
    }

    public void testHashCode() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1", "Description 1", 4.0,
                Arrays.asList("Genre 1", "Genre 2"), 2000));

        BookList bookList = new BookList(1, 1, "List 1", "Description 1", books);

        assertEquals(new HashSet<>(Arrays.asList(bookList)).hashCode(), bookList.hashCode());
    }

    public void testGettersAndSetters() {
        List<Book> books = new ArrayList<>();
        BookList bookList = new BookList(1, 1, "List 1", "Description 1", books);

        bookList.addBook(new Book(2, "Book 2", "Author 2", "Description 2", 4.5,
                Arrays.asList("Genre 3"), 2005));
        bookList.addBook(new Book(3, "Book 3", "Author 3", "Description 3", 3.8,
                Arrays.asList("Genre 4"), 2008));

        assertEquals(2, bookList.size());
        assertEquals("Book 2", bookList.getBook(0).getTitle());

        bookList.removeBook(bookList.getBook(0));
        assertEquals(1, bookList.size());
        assertEquals("Book 3", bookList.getBook(0).getTitle());
    }
}