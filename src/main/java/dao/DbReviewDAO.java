package dao;

import dao.Interfaces.ReviewDAO;
import model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbReviewDAO implements ReviewDAO {
//    private Connection connection;
    private boolean isTest;

    public DbReviewDAO() {
        this.isTest = false;
    }
    public DbReviewDAO(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public void addReview(Review rev) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reviews (book_id, user_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)")) {
            statement.setInt(1, rev.getBookId());
            statement.setInt(2, rev.getUserId());
            statement.setDouble(3, rev.getRating());
            statement.setString(4, rev.getReviewTxt());
            Timestamp sqlDate = new Timestamp(rev.getDate().getTime());
            statement.setTimestamp(5, sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Review> getUserReviews(int userId) {
        List<Review> userReviews = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE user_id = ?")) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int reviewId = resultSet.getInt("review_id");
                int bookId = resultSet.getInt("book_id");
                double rating = resultSet.getDouble("rating");
                String comment = resultSet.getString("comment");
                Timestamp reviewDateTimestamp = resultSet.getTimestamp("review_date");
                Date reviewDate = new Date(reviewDateTimestamp.getTime());

                Review review = new Review(reviewId, userId, bookId, rating, comment, reviewDate);
                userReviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userReviews;
    }

    @Override
    public List<Review> getBookReviews(int bookId) {
        List<Review> bookReviews = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE book_id = ?")) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int reviewId = resultSet.getInt("review_id");
                int userId = resultSet.getInt("user_id");
                double rating = resultSet.getDouble("rating");
                String comment = resultSet.getString("comment");
                Timestamp reviewDateTimestamp = resultSet.getTimestamp("review_date");
                Date reviewDate = new Date(reviewDateTimestamp.getTime());
                Review review = new Review(reviewId, userId, bookId, rating, comment, reviewDate);
                bookReviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookReviews;
    }

    @Override
    public boolean deleteReview(int reviewId) {
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM reviews WHERE review_id = ? ")) {
            statement.setInt(1, reviewId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reviews");
            while (resultSet.next()) {
                int id = resultSet.getInt("review_id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                double rating = resultSet.getDouble("rating");
                String reviewText = resultSet.getString("comment");
                Timestamp reviewDateTimestamp = resultSet.getTimestamp("review_date");
                Date reviewDate = new Date(reviewDateTimestamp.getTime());
                Review review = new Review(id, userId, bookId, rating, reviewText, reviewDate);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<Review> getUsersReviews(List<Integer> userIDs) {
        List<Review> usersReviews = new ArrayList<>();
        if (userIDs.isEmpty()) {
            return usersReviews;
        }
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < userIDs.size(); i++) {
            placeholders.append("?");
            if (i < userIDs.size() - 1) {
                placeholders.append(",");
            }
        }
        try (Connection connection = ConnectionManager.getInstance().getConnection(isTest);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM reviews WHERE user_id IN (" + placeholders + ") ORDER BY review_date DESC")) {

            // Set user IDs as parameters in the prepared statement
            for (int i = 0; i < userIDs.size(); i++) {
                statement.setInt(i + 1, userIDs.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("review_id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                double rating = resultSet.getDouble("rating");
                String reviewText = resultSet.getString("comment");
                Timestamp reviewDateTimestamp = resultSet.getTimestamp("review_date");
                Date reviewDate = new Date(reviewDateTimestamp.getTime());

                Review review = new Review(id, userId, bookId, rating, reviewText, reviewDate);
                usersReviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersReviews;
    }

}
