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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserReviewServlet", value = "/UserReviewServlet")
public class UserReviewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));

        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");

        User currUser = userDAO.getUserByID(Integer.toString(userID));
        // Retrieve session user id to check if it matches current user profile
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("current_user_id"));
        int session_user_id = (Integer) session.getAttribute("current_user_id");
        System.out.println("SESSION USER ID " + session_user_id);
        boolean myPage = (currUser.getId() == session_user_id);

        if (currUser != null) {
            List<Review> reviews = reviewDAO.getUserReviews(userID);
            // Put the book and review list into a map to pass to the JSP
            Map<Review, Book> data = new HashMap<>();
            for(Review currReview : reviews){
                Book currBook = bookDAO.findBookById(currReview.getBookId());
                data.put(currReview, currBook);
            }

            request.setAttribute("data", data);
            request.setAttribute("user", currUser);
            request.setAttribute("myPage", myPage);

            RequestDispatcher dispatcher = request.getRequestDispatcher("userReviews.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle the case when the book is not found
            response.getWriter().println("User not found!");
        }

    }
}
