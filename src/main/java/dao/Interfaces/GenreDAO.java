package dao.Interfaces;

import model.Book;
import model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface GenreDAO {

    public void addGenre(Genre genre);

    public Genre getGenre(String name);

    public void deleteGenre(String genreName);

    public List<Genre> getAllGenres();

    public List<String> getBookGenres(int book_id);

    public List<Book> getTop5Books(String genre);

    public List<Book> getBooksByCategories(List<String> genreList);

}
