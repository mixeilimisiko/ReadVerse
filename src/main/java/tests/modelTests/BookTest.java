package tests.modelTests;

import junit.framework.TestCase;
import model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookTest extends TestCase {

    public void testConstructor() {
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        Book book = new Book("Title", "Author", "Description", 4.5, genres, 2022);
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("Description", book.getDescription());
        assertEquals(4.5, book.getRating());
        assertEquals(genres, book.getGenres());
        assertEquals(2022, book.getYear());
        assertEquals("noCover.png", book.getBookcoverPath());

    }

    public void testNewConstructor() {
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        Book book = new Book(1, "Title", "Author", "Description", 4.5, genres, 2022, "customCover.png");
        assertEquals(1, book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("Description", book.getDescription());
        assertEquals(4.5, book.getRating());
        assertEquals(genres, book.getGenres());
        assertEquals(2022, book.getYear());
        assertEquals("customCover.png", book.getBookcoverPath());
    }


    public void testGetters() {
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        Book book = new Book(1, "Title", "Author", "Description", 4.5, genres, 2022);
        assertEquals(1, book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("Description", book.getDescription());
        assertEquals(4.5, book.getRating());
        assertEquals(genres, book.getGenres());
        assertEquals(2022, book.getYear());
    }

    public void testSetters() {
        List<String> genres = new ArrayList<>();
        Book book = new Book("Title", "Author", "Description", 4.5, genres, 2022);

        book.setId(1);
        assertEquals(1, book.getId());

        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());

        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());

        book.setDescription("New Description");
        assertEquals("New Description", book.getDescription());

        book.setRating(3.8);
        assertEquals(3.8, book.getRating());

        List<String> newGenres = Arrays.asList("Genre3", "Genre4");
        book.setGenres(newGenres);
        assertEquals(newGenres, book.getGenres());

        book.setYear(2023);
        assertEquals(2023, book.getYear());
    }



    public void testEquals() {
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        Book book1 = new Book(1, "Title", "Author", "Description", 4.5, genres, 2022);
        Book book2 = new Book(1, "Title", "Author", "Description", 4.5, genres, 2022);
        Book book3 = new Book(2, "Another Title", "Another Author", "Another Description", 3.7, genres, 2021);

        assertTrue(book1.equals(book2));
        assertFalse(book1.equals(book3));
    }

    public void testHashCode() {
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        Book book1 = new Book(1, "Title", "Author", "Description", 4.5, genres, 2022);
        Book book2 = new Book(1, "Title", "Author", "Description", 4.5, genres, 2022);
        Book book3 = new Book(2, "Another Title", "Another Author", "Another Description", 3.7, genres, 2021);

        assertEquals(book1.hashCode(), book2.hashCode());
        assertFalse(book1.hashCode() == book3.hashCode());
    }
    public void testGettersAndSetters() {
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        Book book = new Book("Title", "Author", "Description", 4.5, genres, 2022);

        // Test setters and then immediately test getters
        book.setId(1);
        assertEquals(1, book.getId());

        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());

        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());

        book.setDescription("New Description");
        assertEquals("New Description", book.getDescription());

        book.setRating(3.8);
        assertEquals(3.8, book.getRating());

        List<String> newGenres = Arrays.asList("Genre3", "Genre4");
        book.setGenres(newGenres);
        assertEquals(newGenres, book.getGenres());

        book.setYear(2023);
        assertEquals(2023, book.getYear());

        book.setBookcoverPath("newCover.png");
        assertEquals("newCover.png", book.getBookcoverPath());
    }
}