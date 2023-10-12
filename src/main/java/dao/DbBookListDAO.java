package dao;

import dao.Interfaces.BookListDAO;
import model.Book;
import model.BookList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBookListDAO implements BookListDAO {
    private boolean isTest;

    public DbBookListDAO() {
        this.isTest = false;
    }
    public DbBookListDAO(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public void createList(BookList bookList) {
        String insertListQuery = "INSERT INTO BookLists (title, description, user_id) VALUES (?, ?, ?)";
        try (// Insert into BookLists table
            Connection connection = ConnectionManager.getInstance().getConnection(isTest);
            PreparedStatement insertListStatement = connection.prepareStatement(insertListQuery, Statement.RETURN_GENERATED_KEYS)){
            insertListStatement.setString(1, bookList.getTitle());
            insertListStatement.setString(2, bookList.getDescription());
            insertListStatement.setInt(3, bookList.getUserId());
            insertListStatement.executeUpdate();
            // Get the generated list ID
            ResultSet generatedKeys = insertListStatement.getGeneratedKeys();
            int listId;
            if (generatedKeys.next()) {
                listId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get the generated list ID.");
            }
            // Insert into ListBooks table
            String insertListBooksQuery = "INSERT INTO ListBooks (list_id, book_id) VALUES (?, ?)";
            PreparedStatement insertListBooksStatement = connection.prepareStatement(insertListBooksQuery);
            insertListBooksStatement.setInt(1, listId);
            for (Book book : bookList.getBooks()) {
                insertListBooksStatement.setInt(2, book.getId());
                insertListBooksStatement.executeUpdate();
            }
            // Close the statements
            insertListStatement.close();
            insertListBooksStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteList(int user_id, String list_title) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest)) {
            // Retrieve the list ID for the given user ID and list title
            int listId = getListId(user_id, list_title);

            // Delete from ListBooks table
            String deleteListBooksQuery = "DELETE FROM ListBooks WHERE list_id = ?";
            try (PreparedStatement deleteListBooksStatement = connection.prepareStatement(deleteListBooksQuery)) {
                deleteListBooksStatement.setInt(1, listId);
                deleteListBooksStatement.executeUpdate();
            }

            // Delete from BookLists table
            String deleteListQuery = "DELETE FROM BookLists WHERE list_id = ?";
            try (PreparedStatement deleteListStatement = connection.prepareStatement(deleteListQuery)) {
                deleteListStatement.setInt(1, listId);
                deleteListStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteList(int list_id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest)) {
            // Delete from ListBooks table
            String deleteListBooksQuery = "DELETE FROM ListBooks WHERE list_id = ?";
            try (PreparedStatement deleteListBooksStatement = connection.prepareStatement(deleteListBooksQuery)) {
                deleteListBooksStatement.setInt(1, list_id);
                deleteListBooksStatement.executeUpdate();
            }

            // Delete from BookLists table
            String deleteListQuery = "DELETE FROM BookLists WHERE list_id = ?";
            try (PreparedStatement deleteListStatement = connection.prepareStatement(deleteListQuery)) {
                deleteListStatement.setInt(1, list_id);
                deleteListStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getListId(int user_id, String list_title) {
        int listId = -1;
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT list_id FROM BookLists WHERE user_id = ? AND title = ?")) {
            statement.setInt(1, user_id);
            statement.setString(2, list_title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    listId = resultSet.getInt("list_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listId;
    }

    @Override
    public List<BookList> getAllUserLists(int user_id) {
        List<BookList> userLists = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT list_id, title, description FROM BookLists WHERE user_id = ?")) {
            statement.setInt(1, user_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int listId = resultSet.getInt("list_id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    // Retrieve books for the current list from ListBooks table
                    List<Book> books = getBooksInList(listId);

                    // Create a new BookList object and add it to the userLists
                    BookList bookList = new BookList(listId, user_id, title, description, books);
                    userLists.add(bookList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userLists;
    }


    private List<Book> getBooksInList(int listId) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT b.* FROM Books b INNER JOIN ListBooks lb ON b.book_id = lb.book_id WHERE lb.list_id = ?")) {
            statement.setInt(1, listId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String description = resultSet.getString("description");
                    double rating = resultSet.getDouble("rating");
                    int year = resultSet.getInt("year");
                    String path = resultSet.getString("cover_path");
                    List<String> genres = getBookGenres(bookId);
                    Book book = new Book(bookId, title, author, description, rating, genres, year, path);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private List<String> getBookGenres(int bookID) {
        List<String> genres = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT genre FROM book_genres WHERE book_id = ?")) {
            preparedStatement.setInt(1, bookID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    @Override
    public BookList getUserList(int user_id, String list_title) {
        BookList bookList = null;

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT list_id, title, description FROM BookLists WHERE user_id = ? AND title = ?")) {
            statement.setInt(1, user_id);
            statement.setString(2, list_title);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int listId = resultSet.getInt("list_id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    // Retrieve books for the current list from ListBooks table
                    List<Book> books = getBooksInList(listId);

                    // Create a new BookList object with the retrieved information
                    bookList = new BookList(listId, user_id, title, description, books);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    @Override
    public BookList getListById(int list_id) {
        BookList bookList = null;

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement("SELECT user_id, title, description FROM BookLists WHERE list_id = ?")) {
            statement.setInt(1, list_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int user_id = resultSet.getInt("user_id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    // Retrieve books for the current list from ListBooks table
                    List<Book> books = getBooksInList(list_id);

                    // Create a new BookList object with the retrieved information
                    bookList = new BookList(list_id, user_id, title, description, books);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }


    @Override
    public void addBookToList(int user_id, String list_title, int book_id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest)) {
            // Retrieve the list ID based on user ID and list title
            String listQuery = "SELECT list_id FROM BookLists WHERE user_id = ? AND title = ?";
            try (PreparedStatement listStatement = connection.prepareStatement(listQuery)) {
                listStatement.setInt(1, user_id);
                listStatement.setString(2, list_title);

                try (ResultSet listResult = listStatement.executeQuery()) {
                    if (listResult.next()) {
                        int listId = listResult.getInt("list_id");

                        // Add the book to the list in ListBooks table
                        String insertQuery = "INSERT INTO ListBooks (list_id, book_id) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setInt(1, listId);
                            insertStatement.setInt(2, book_id);
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookToList(int user_id, int list_id, int book_id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement insertStatement = connection.prepareStatement(
                     "INSERT INTO ListBooks (list_id, book_id) VALUES (?, ?)")) {

            insertStatement.setInt(1, list_id);
            insertStatement.setInt(2, book_id);
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void removeBookFromList(int user_id, String list_title, int book_id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement listStatement = connection.prepareStatement(
                     "SELECT list_id FROM BookLists WHERE user_id = ? AND title = ?");
             PreparedStatement deleteStatement = connection.prepareStatement(
                     "DELETE FROM ListBooks WHERE list_id = ? AND book_id = ?")) {

            listStatement.setInt(1, user_id);
            listStatement.setString(2, list_title);
            ResultSet listResult = listStatement.executeQuery();

            if (listResult.next()) {
                int listId = listResult.getInt("list_id");
                deleteStatement.setInt(1, listId);
                deleteStatement.setInt(2, book_id);
                deleteStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeBookFromList(int list_id, int book_id) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement deleteStatement = connection.prepareStatement(
                     "DELETE FROM ListBooks WHERE list_id = ? AND book_id = ?")) {

            deleteStatement.setInt(1, list_id);
            deleteStatement.setInt(2, book_id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
