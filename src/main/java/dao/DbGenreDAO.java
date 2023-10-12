package dao;

import dao.Interfaces.GenreDAO;
import model.Book;
import model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class DbGenreDAO implements GenreDAO {
    private boolean isTest;

    public DbGenreDAO() {
        this.isTest = false;
    }

    public DbGenreDAO(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public void addGenre(Genre genre) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO genres (genre_id, name, description) VALUES (?, ?, ?)")) {
            statement.setInt(1,genre.getId());
            statement.setString(2, genre.getName());
            statement.setString(3, genre.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Genre getGenre(String name) {

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM genres WHERE name = ?")) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("genre_id");
                String genreName = resultSet.getString("name");
                String description = resultSet.getString("description");

                Genre genre = new Genre(id, genreName, description);
                return genre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteGenre(String genreName) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM genres WHERE name = ?")) {
            statement.setString(1, genreName);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> listGenres = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM genres");
            while (resultSet.next()) {
                int id = resultSet.getInt("genre_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                Genre genre = new Genre(id, name, description);
                listGenres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(listGenres.size());
        return listGenres;
    }

    @Override
    public List<String> getBookGenres(int p_book_id) {
        List<String> listGenres = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM book_genres join books b on book_genres.book_id = b.book_id where book_genres.book_id = ?")) {
            statement.setInt(1, p_book_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String genreName = resultSet.getString("genre");
                listGenres.add(genreName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGenres;
    }

    @Override
    public List<Book> getTop5Books(String pGenre) {
        List<Book> listBooks = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT distinct * FROM book_genres join books b on book_genres.book_id = b.book_id where book_genres.genre = ? order by rating")) {
            statement.setString(1, pGenre);
            int count = 0;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next() && count < 5) {
                int currId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> currGenreList = getBookGenres(currId);
                Book currBook = new Book(currId,title,author, description,rating,currGenreList, year, path);
                listBooks.add(currBook);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooks;

    }

    @Override
    public List<Book> getBooksByCategories(List<String> genresList) {
        List<Book> bookList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT b.book_id , b.title, b.author, b.description, b.rating, b.year, b.cover_path FROM books b");

        for (int i = 0; i < genresList.size(); i++) {
            queryBuilder.append(" JOIN book_genres bg").append(i)
                    .append(" ON b.book_id = bg").append(i).append(".book_id AND bg").append(i)
                    .append(".genre = ?");
        }

        queryBuilder.append(" WHERE");
        for (int i = 0; i < genresList.size(); i++) {
            if (i > 0) {
                queryBuilder.append(" AND");
            }
            queryBuilder.append(" bg").append(i).append(".genre IS NOT NULL");
        }

        String query = queryBuilder.toString();
        System.out.println(query);
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < genresList.size(); i++) {
                statement.setString(i + 1, genresList.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int currId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> currGenreList = getBookGenres(currId);
                Book currBook = new Book(currId,title,author, description,rating,currGenreList, year, path);
                bookList.add(currBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return bookList;
       
    }


}
