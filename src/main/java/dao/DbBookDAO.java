package dao;

import dao.Interfaces.BookDAO;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBookDAO implements BookDAO {
    private final boolean isTest;

    public DbBookDAO() {
        this.isTest = false;
    }
    public DbBookDAO(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public void addBook(Book book) {
        try(Connection connection = ConnectionManager.getInstance().getConnection(isTest);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO books (title, author, description, rating, year, cover_path) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getDescription());
            statement.setDouble(4, book.getRating());
            statement.setInt(5, book.getYear());
            statement.setString(6, book.getBookcoverPath());

            statement.executeUpdate();
            Book returnedBook = findBook(book.getTitle());
            int bookId = returnedBook.getId();
            try (PreparedStatement next_statement = connection.prepareStatement(
                    "INSERT INTO book_genres (book_id, genre) VALUES (?, ?)")) {
                for (String genre : book.getGenres()) {
                    next_statement.setInt(1, bookId);
                    next_statement.setString(2, genre);
                    next_statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private void addBookGenres(List<String> genres) {
//        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest)) {
//            PreparedStatement statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
//            ResultSet resultSet = statement.executeQuery();
//            resultSet.next();
//            int bookID = resultSet.getInt(1);
//            System.out.println("BOOOK ID " + bookID);
//
//            for (String genre : genres) {
//                statement = connection.prepareStatement(
//                        "INSERT INTO book_genres (book_id, genre) VALUES (?, ?)");
//                statement.setInt(1, bookID);
//                statement.setString(2, genre);
//
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private void deleteBookGenres(int bookID) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book_genres WHERE book_id = ?")){
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> genres = getBookGenres(id);
                books.add(new Book(id, title, author, description, rating, genres, year, path));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    public List<String> getBookGenres(int bookID) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT genre FROM book_genres WHERE book_id = ?")) {
            preparedStatement.setInt(1, bookID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<String> genres = new ArrayList<>();

                while (resultSet.next()) {
                    genres.add(resultSet.getString(1));
                }

                return genres;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBook(Book book) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE books SET title=?, author=?, description=?, rating=?, year=?, cover_path=? WHERE book_id=?")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getDescription());
            statement.setDouble(4, book.getRating());
            statement.setInt(5, book.getYear());
            statement.setString(6, book.getBookcoverPath());
            statement.setInt(7, book.getId());
            statement.executeUpdate();

            deleteBookGenres(book.getId());
            try (PreparedStatement next_statement = connection.prepareStatement(
                    "INSERT INTO book_genres (book_id, genre) VALUES (?, ?)")) {
                for (String genre : book.getGenres()) {
                    next_statement.setInt(1, book.getId());
                    next_statement.setString(2, genre);
                    next_statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Book> getBooksByCategory(String category) {
        return getBooksByCategories(List.of(category));
    }

    @Override
    public List<Book> getBooksByCategories(List<String> categories) {
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest)) {
            for (String category : categories) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "SELECT book_id FROM book_genres WHERE genre = ?")) {
                    statement.setString(1, category);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int bookId = resultSet.getInt(1);
                            Book book = findBookById(bookId);
                            if (book != null) {
                                if(books.contains(book)) continue;
                                books.add(book);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT genre FROM book_genres")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String category = resultSet.getString("genre");
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }



    @Override
    public void deleteBook(int id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE book_id=?")) {
            statement.setInt(1, id);

            statement.executeUpdate();

            deleteBookGenres(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book findBook(String title) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books WHERE title = ?")) {
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> genres = getBookGenres(id);
                // Create and return a Book object
                return new Book(id, title, author, description, rating, genres, year, path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Book findBookById(int id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books WHERE book_id = ?")) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> genres = getBookGenres(id);
                // Create and return a Book object
                return new Book(id, title, author, description, rating, genres, year, path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBibliography(String author) {
        List<Book> bibliography = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books WHERE author = ?")) {
            statement.setString(1, author);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                // String genre = resultSet.getString("genre");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> genres = getBookGenres(id);

                Book book = new Book(id, title, author, description, rating, genres, year, path);
                bibliography.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bibliography;
    }

}