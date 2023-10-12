package controller.servlet.Reviews;

import dao.Interfaces.BookDAO;
import dao.Interfaces.ReviewDAO;
import dao.Interfaces.UserDAO;
import model.Book;
import model.Review;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "BookReviewServlet", value = "/BookReviewServlet")
public class BookReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));

        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");

        Book currBook = bookDAO.findBookById(bookID);

        if (currBook != null) {
            // Use the ReviewDAO to retrieve the reviews for the given bookID
            List<Review> reviews = reviewDAO.getBookReviews(bookID);

            // Put the book and review list into a map to pass to the JSP
            Map<Review, Book> data = new HashMap<>();
            Map<Integer, String> usernames = new HashMap<>();
            for(Review currReview : reviews){
                data.put(currReview, currBook);
                User user = userDAO.getUserByID(Integer.toString(currReview.getUserId()));
                usernames.put(currReview.getUserId(), user.getUsername());
            }

            request.setAttribute("data", data);
            request.setAttribute("usernames", usernames);

            RequestDispatcher dispatcher = request.getRequestDispatcher("bookReviews.jsp");
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println("Book not found!");
        }
    }

}
