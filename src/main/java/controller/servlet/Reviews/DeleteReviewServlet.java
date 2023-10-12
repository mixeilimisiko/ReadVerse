package controller.servlet.Reviews;

import dao.Interfaces.BookDAO;
import dao.Interfaces.ReviewDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteReviewServlet", value = "/DeleteReviewServlet")
public class DeleteReviewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int reviewId = Integer.parseInt(request.getParameter("reviewID"));
        double reviewRating = Double.parseDouble(request.getParameter("reviewRating"));
        int userId = Integer.parseInt(request.getParameter("userID"));
        int bookId = Integer.parseInt(request.getParameter("bookID"));

        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        Book book = bookDAO.findBookById(bookId);
        int numReviews = reviewDAO.getBookReviews(bookId).size();
        double newRating = (double)(numReviews*book.getRating() - reviewRating)/(numReviews-1);
        book.setRating(newRating);
        bookDAO.updateBook(book);


        // Delete the review from the database
        boolean deleted = reviewDAO.deleteReview(reviewId);

        if (deleted) {
            // Redirect to a success page or display a success message
            response.sendRedirect("UserReviewServlet?userID=" + userId);
        } else {
            // Redirect to an error page or display an error message
            response.getWriter().println("Error occured while deleting");
        }
    }
}
