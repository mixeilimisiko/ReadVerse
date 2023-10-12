package dao;
import model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.Interfaces.AuthorDAO;
import model.Review;

public class DbAuthorDAO implements AuthorDAO {
    private boolean isTest;

    public DbAuthorDAO() {
        this.isTest = false;
    }
    public DbAuthorDAO(boolean isTest) {
        this.isTest = isTest;
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM authors");
            while (resultSet.next()) {
                int id = resultSet.getInt("author_id");
                String author_name = resultSet.getString("author_name");
                String author_info = resultSet.getString("author_info");
                String path = resultSet.getString("img_path");
                Author author = new Author(id, author_name, author_info, path);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author getAuthor(String name) {
        try (   Connection connection = ConnectionManager.getInstance().getConnection(isTest);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM authors WHERE author_name = ?")) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("author_id");
                String info = resultSet.getString("author_info");
                String path = resultSet.getString("img_path");
                return new Author(id, name, info, path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author getAuthor(int id) {
        try (   Connection connection = ConnectionManager.getInstance().getConnection(isTest);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM authors WHERE author_id = ?")) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("author_name");
                String info = resultSet.getString("author_info");
                String path = resultSet.getString("img_path");
                return new Author(id, name, info, path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addAuthor(Author author) {
        try (   Connection connection = ConnectionManager.getInstance().getConnection(isTest);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO authors (author_name, author_info, img_path) VALUES (?, ?, ?)")) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getInfo());
            statement.setString(3, author.getImgPath());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAuthor(String author_name) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM authors WHERE author_name = ?")) {
            statement.setString(1, author_name);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImage(String name) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
                PreparedStatement statement = connection.prepareStatement("SELECT img_path FROM authors WHERE author_name = ?")) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("img_path");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
