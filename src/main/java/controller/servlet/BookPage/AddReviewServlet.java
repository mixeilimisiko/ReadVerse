package controller.servlet.BookPage;

import dao.Interfaces.BookDAO;
import dao.Interfaces.ReviewDAO;
import model.Book;
import model.Review;
import services.DateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AddReviewServlet", value = "/AddReviewServlet")
public class AddReviewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        int userID = (Integer) request.getSession().getAttribute("current_user_id");
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        double reviewRating = Double.parseDouble(request.getParameter("reviewRating"));
        String reviewText = request.getParameter("reviewText");
        Date reviewDate = DateUtils.getCurrentDateAndTime();

        Review newReview = new Review(userID, bookID, reviewRating, reviewText, reviewDate );
        reviewDAO.addReview(newReview);
        List<Review> bookReviews = reviewDAO.getBookReviews(bookID);
        double ratingAverage = 0;
        for(Review rev : bookReviews) {
            ratingAverage+=rev.getRating();
        }
        if(!bookReviews.isEmpty()) {
            ratingAverage = ratingAverage / (bookReviews.size());
        }
        BookDAO bookDAO = (BookDAO) request.getServletContext().getAttribute("bookDAO");
        Book currBook = bookDAO.findBookById(bookID);
        currBook.setRating(ratingAverage);
        bookDAO.updateBook((currBook));

        request.setAttribute("book", currBook);
        request.getRequestDispatcher("bookPage.jsp").forward(request, response);
        System.out.println("added successfully");
    }
}
