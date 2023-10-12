package dao.Interfaces;

import model.Book;

import java.util.List;

public interface BookDAO {

    public void addBook(Book book);

    public List<Book> getAllBooks();

    public void updateBook(Book book);

    public List<Book> getBooksByCategory(String genre);

    public List<Book> getBooksByCategories(List<String> categories);

    public List<String> getAllCategories();

    public List<String> getBookGenres(int bookID);

    public void deleteBook(int id);

    public Book findBook(String title);

    public Book findBookById(int id);

    public List<Book> getBibliography(String author);


}
