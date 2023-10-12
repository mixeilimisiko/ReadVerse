package tests.modelTests;

import junit.framework.TestCase;
import model.Author;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorTest extends TestCase {

    public void testConstructor() {
        Author author = new Author("Avtori", "sauketeso avtoria");
        assertEquals("Avtori", author.getName());
        assertEquals("sauketeso avtoria", author.getInfo());
    }

    public void testSecondConstructor() {
        Author author = new Author(1,"Avtori", "sauketeso avtoria", "");
        assertEquals(1, author.getId());
        assertEquals("Avtori", author.getName());
        assertEquals("sauketeso avtoria", author.getInfo());
    }


    public void testGetters() {
        Author author = new Author(1,"Avtori", "sauketeso avtoria", "");
        assertEquals(1, author.getId());
        assertEquals("Avtori", author.getName());
        assertEquals("sauketeso avtoria", author.getInfo());
    }

    public void testSetters() {
        Author author = new Author(1,"Avtori", "sauketeso avtoria", "");
        author.setId(2);
        assertEquals(2, author.getId());
        author.setName("Avtori2");
        assertEquals("Avtori2", author.getName());
        author.setInfo("esec sauketeso avtoria");
        assertEquals("esec sauketeso avtoria", author.getInfo());
    }



}