package model;

import java.util.Date;
import java.util.Objects;

public class Review {
    private int id;
    private int UserId;
    private int BookId;
    private double rating;
    private String reviewTxt;
    private Date date;

    public Review(int UserId, int BookId, double rating, String reviewTxt, Date date) {
        this.UserId = UserId;
        this.BookId = BookId;
        this.rating = rating;
        this.reviewTxt = reviewTxt;
        this.date = date;
    }

    public Review(int id, int UserId, int BookId, double rating, String reviewTxt, Date date) {
        this.id = id;
        this.UserId = UserId;
        this.BookId = BookId;
        this.rating = rating;
        this.reviewTxt = reviewTxt;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public String getReviewTxt() {
        return reviewTxt;
    }

    public void setReviewTxt(String reviewTxt) {
        this.reviewTxt = reviewTxt;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && UserId == review.UserId && BookId == review.BookId && rating == review.rating && Objects.equals(reviewTxt, review.reviewTxt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, UserId, BookId, reviewTxt);
    }


}
