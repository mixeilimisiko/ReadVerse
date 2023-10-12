package dao.Interfaces;

import model.Review;

import java.util.List;

public interface ReviewDAO {

    public void addReview(Review review);

    public List<Review> getUserReviews(int userId);

    public List<Review> getBookReviews(int bookId);

    public boolean deleteReview(int reviewId);

    public List<Review> getAllReviews();

    public List<Review> getUsersReviews(List<Integer> userIDs);
}

