package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookList {
    private int listId;
    private int userId;
    private String title;
    private String description;
    private List<Book> books;

    public BookList(int listId, int userId, String title, String description, List<Book> books) {
        this.listId = listId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.books = books;
    }

    public BookList(int listId, int userId, String title, String description) {
        this.listId = listId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.books = new ArrayList<Book>();
    }

    public BookList( int userId, String title, String description, List<Book> books) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.books = books;
    }

    public BookList( int userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.books = new ArrayList<Book>();
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public int size() {
        return books.size();
    }

    public Book getBook(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookList bookList = (BookList) o;
        return listId == bookList.listId &&
                userId == bookList.userId &&
                Objects.equals(title, bookList.title) &&
                Objects.equals(description, bookList.description) &&
                Objects.equals(books, bookList.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, userId, title, description, books);
    }
}





