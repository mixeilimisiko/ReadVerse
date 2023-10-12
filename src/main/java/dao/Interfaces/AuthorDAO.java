package dao.Interfaces;

import model.Author;

import java.util.List;

public interface AuthorDAO {

    public List<Author> getAllAuthors();

    public Author getAuthor(String name);

    public Author getAuthor(int id);

    public void addAuthor(Author author);

    public void deleteAuthor(String author_name);

    public String getImage(String name);

}

