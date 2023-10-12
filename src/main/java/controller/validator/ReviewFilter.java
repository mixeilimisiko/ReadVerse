package controller.validator;

import model.Review;
import services.DateUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReviewFilter {

    private static final long ONE_MONTH_IN_MILLISECONDS = 30L * 24 * 60 * 60 * 1000;

    public static void filterReviews(List<Review> reviews) {
        Date currentDate = DateUtils.getCurrentDateAndTime();

        reviews.removeIf(review -> isOlderThanOneMonth(review.getDate(), currentDate));
    }

    private static boolean isOlderThanOneMonth(Date reviewDate, Date currentDate) {
        long timeDifference = currentDate.getTime() - reviewDate.getTime();
        return timeDifference > ONE_MONTH_IN_MILLISECONDS;
    }
}