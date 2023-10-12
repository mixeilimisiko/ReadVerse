package tests.modelTests;

import junit.framework.TestCase;
import model.Review;
import model.User;

public class ReviewTest extends TestCase {

    public void testConstructor(){
        Review review = new Review(1, 10, 5.0, "cool", null);
        assertEquals(1, review.getUserId());
        assertEquals(10, review.getBookId());
        assertEquals(5.0, review.getRating());
        assertNull(review.getDate());
        assertEquals("cool", review.getReviewTxt());
    }

    public void testSetters(){
        Review review = new Review(2, 3, 4.0, "haha", null);
        review.setDate(null);
        review.setUserId(1);
        review.setRating(5.0);
        review.setReviewTxt("cool");
        review.setBookId(10);
        assertEquals(1, review.getUserId());
        assertEquals(10, review.getBookId());
        assertEquals(5.0, review.getRating());
        assertNull(review.getDate());
        assertEquals("cool", review.getReviewTxt());
    }

    public void testComparator(){
        Review review1 = new Review(1, 10, 5.0, "cool", null);
        Review review2 = new Review(1, 10, 5.0, "cool", null);
        Review review3 = new Review(2, 3, 4.0, "h", null);
        assertEquals(review1, review2);
        assertEquals(review2, review1);
        assertFalse(review1.equals(review3));
        assertFalse(review2.equals(review3));
    }

    public void testHashCode(){
        Review review1 = new Review(1, 10, 5.0, "cool", null);
        Review review2 = new Review(1, 10, 5.0, "cool", null);
        Review review3 = new Review(2, 3, 4.0, "h", null);
        assertEquals(review1.hashCode(), review2.hashCode());
        assertFalse(review1.hashCode() == review3.hashCode());
    }

}
