package controller.servlet.Homepage;

import controller.servlet.LoginServlet;
import controller.validator.ReviewFilter;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomepageServlet", value = "/HomepageServlet")
public class HomepageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("current_user_id") == null) {
           // redirect to welcome.jsp
            response.sendRedirect("welcome.jsp");
            return;
        }
        int userID = (Integer) session.getAttribute("current_user_id");
        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        List<User> followings = userDAO.getUserFollowings(userID);
        homepageCook(reviewDAO, request, userDAO, bookDAO, followings);

//        String errorMessage = (String)request.getAttribute("errorMessage");
//        request.setAttribute("errorMessage" , errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
        dispatcher.forward(request, response);
    }

    public static void homepageCook(ReviewDAO reviewDAO, HttpServletRequest req, UserDAO userDAO, BookDAO bookDAO, List<User> followings) {
        List<Integer> followingIDs = new ArrayList<>();
        for(User u : followings) {
            followingIDs.add(u.getId());
        }
        List<Review> reviews = reviewDAO.getUsersReviews(followingIDs);
        ReviewFilter.filterReviews(reviews);
        List<Book> books = new ArrayList<>();
        List<String> usernames = new ArrayList<>();
        for(Review currReview : reviews){
            Book currBook = bookDAO.findBookById(currReview.getBookId());
            User user = userDAO.getUserByID(Integer.toString(currReview.getUserId()));
            books.add(currBook);
            usernames.add(user.getUsername());
        }
        req.setAttribute("books", books);
        req.setAttribute("reviews", reviews);
        req.setAttribute("usernames", usernames);
    }

}
