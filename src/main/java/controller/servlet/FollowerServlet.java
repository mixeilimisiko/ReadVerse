package controller.servlet;

import dao.Interfaces.BookListDAO;
import dao.Interfaces.UserDAO;
import model.BookList;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/FollowerServlet")
public class FollowerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) req.getServletContext().getAttribute("userDAO");
        int sessionID = Integer.parseInt(req.getParameter("sessionID"));
        int userID = Integer.parseInt(req.getParameter("userID"));
        Boolean followingOrNot = Boolean.valueOf(req.getParameter("followOrUnfollow"));
        User user = userDAO.getUserByID(String.valueOf(userID));
        BookListDAO bookListDAO = (BookListDAO) req.getServletContext().getAttribute("bookListDAO");
        List<BookList> userBookLists = bookListDAO.getAllUserLists(user.getId());
        String about = userDAO.getUsersAboutInfo(userID);

        if (followingOrNot) {
            userDAO.removeFollowing(sessionID, userID);
        } else {
            userDAO.addToFollowing(sessionID, userID);
        }

        int numberOfReviews = UserServlet.getNumberOfReviewsForUser(userID, req);
        double ratingSum = UserServlet.getSumOfRatingsForUser(userID, req);
        double ratingAVG = 0;
        if(numberOfReviews!=0) {
            ratingAVG = ratingSum / numberOfReviews;
        }

        List<User> followers;
        List<User> following;

        followers = userDAO.getUserFollowers(userID);
        following = userDAO.getUserFollowings(userID);

        UserServlet.setRequest(req, userID, user, numberOfReviews, ratingAVG, followers, following, userBookLists, about);
        req.setAttribute("my_profile_flag", false);
        req.setAttribute("following_curr_user", !followingOrNot);
        req.setAttribute("sessionID", sessionID);
        req.setAttribute("imagePath", userDAO.getAvatarPath(userDAO.getUserAvatar(userID)));

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }

}
