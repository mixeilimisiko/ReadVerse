package dao.Interfaces;

import model.BookList;

import java.util.List;

public interface BookListDAO {

    public void createList(BookList bookList);

    public void deleteList(int user_id, String list_title);

    public void deleteList(int list_id);

    public List<BookList> getAllUserLists(int user_id);

    public BookList getUserList(int user_id, String list_title);

    public BookList getListById(int list_id);

    public void addBookToList(int user_id, String list_title, int book_id);

    public void addBookToList(int user_id, int list_id, int book_id);

    public void removeBookFromList(int user_id, String list_title, int book_id);

    public void removeBookFromList(int list_id, int book_id);




}
