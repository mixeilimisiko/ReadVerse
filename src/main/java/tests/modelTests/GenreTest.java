package tests.modelTests;

import junit.framework.TestCase;
import model.Genre;

public class GenreTest extends TestCase {

    public void testConstructor() {
        Genre genre = new Genre("axali janri", "saintereso janria");
        assertEquals("axali janri", genre.getName());
        assertEquals("saintereso janria", genre.getDescription());
    }

    public void testSecondConstructor() {
        Genre genre = new Genre(1, "axali janri", "saintereso janria");
        assertEquals("axali janri", genre.getName());
        assertEquals("saintereso janria", genre.getDescription());
        assertEquals(1, genre.getId());
    }


    public void testGetters() {
        Genre genre = new Genre(1, "axali janri", "saintereso janria");
        assertEquals("axali janri", genre.getName());
        assertEquals("saintereso janria", genre.getDescription());
        assertEquals(1, genre.getId());
    }

    public void testSetters() {
        Genre genre = new Genre(1, "axali janri", "saintereso janria");
        genre.setId(2);
        assertEquals(2, genre.getId());
        genre.setName("ufro axali janri");
        assertEquals("ufro axali janri", genre.getName());
        genre.setDescription("ufro saintereso janria");
        assertEquals("ufro saintereso janria", genre.getDescription());
    }



}