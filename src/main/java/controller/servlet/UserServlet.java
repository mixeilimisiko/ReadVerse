package controller.servlet;


import dao.Interfaces.BookListDAO;
import dao.Interfaces.ReviewDAO;
import dao.Interfaces.UserDAO;
import model.BookList;
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
import java.util.List;

@WebServlet("/UserServlet/*")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) req.getServletContext().getAttribute("userDAO");
        Integer userID = Integer.valueOf(req.getParameter("userID"));
        User user = userDAO.getUserByID(String.valueOf(userID));

        int numberOfReviews = getNumberOfReviewsForUser(userID, req);
        double ratingSum = getSumOfRatingsForUser(userID, req);
        double ratingAvg = 0;
        if(numberOfReviews!=0) {
            ratingAvg = ratingSum / numberOfReviews;
        }

        BookListDAO bookListDAO = (BookListDAO) req.getServletContext().getAttribute("bookListDAO");
        List<BookList> userBookLists = bookListDAO.getAllUserLists(user.getId());

        HttpSession session = req.getSession();
        int sessionID = (Integer) session.getAttribute("current_user_id");
        boolean myProfileFlag = (sessionID == userID);
        String about = userDAO.getUsersAboutInfo(userID);
        String reqAbout = req.getParameter("about_text");
        if (reqAbout != null) {
            about = reqAbout;
            userDAO.setUsersAboutInfo(sessionID, about);
        }

        List<User> followers;
        List<User> following;

        followers = userDAO.getUserFollowers(userID);
        following = userDAO.getUserFollowings(userID);

        boolean followingCurrentUser = false;
        for (int i = 0; i < followers.size(); i++) {
            if (followers.get(i).getId() == sessionID) {
                followingCurrentUser = true;
            }
        }
        req.setAttribute("imagePath", userDAO.getAvatarPath(userDAO.getUserAvatar(userID)));
        setRequest(req, userID, user, numberOfReviews, ratingAvg, followers, following, userBookLists, about);
        req.setAttribute("my_profile_flag", myProfileFlag);
        req.setAttribute("following_curr_user", followingCurrentUser);
        req.setAttribute("sessionID", sessionID);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }

    public static int getNumberOfReviewsForUser(int userID, HttpServletRequest req){
        int numberOfReviews = 0;
        ReviewDAO reviewDAO = (ReviewDAO) req.getServletContext().getAttribute("reviewDAO");
        List<Review> reviewList = reviewDAO.getAllReviews();
        for(int i = 0; i < reviewList.size(); i++){
            Review currReview = reviewList.get(i);
            String currReviewUserID = Integer.toString(currReview.getUserId());
            if(currReviewUserID.equals(Integer.toString(userID))){
                numberOfReviews++;
            }
        }
        return numberOfReviews;
    }

    public static double getSumOfRatingsForUser(int userID, HttpServletRequest req){
        double ratingSum = 0;
        ReviewDAO reviewDAO = (ReviewDAO) req.getServletContext().getAttribute("reviewDAO");
        List<Review> reviewList = reviewDAO.getAllReviews();
        for(int i = 0; i < reviewList.size(); i++){
            Review currReview = reviewList.get(i);
            String currReviewUserID = Integer.toString(currReview.getUserId());
            if(currReviewUserID.equals(Integer.toString(userID))){
                ratingSum += currReview.getRating();
            }
        }
        return ratingSum;
    }

    public static void setRequest(HttpServletRequest req, Integer userID, User user, int numberOfReviews, double ratingAvg,
                                  List<User> followers, List<User> following, List<BookList> userBookLists, String about) {
        req.setAttribute("username", user.getUsername());
        req.setAttribute("rating_average", ratingAvg);
        req.setAttribute("number_of_reviews", numberOfReviews);
        req.setAttribute("number_of_followers", followers.size());
        req.setAttribute("number_of_followings", following.size());
        req.setAttribute("user_id", userID);
        req.setAttribute("user_book_lists", userBookLists);
        req.setAttribute("about", about);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) req.getServletContext().getAttribute("userDAO");
        String selectedPicture = req.getParameter("selectedAvatar"); // Get the selected picture from the form
        HttpSession session = req.getSession();
        int sessionID = (Integer) session.getAttribute("current_user_id");
        userDAO.setUserAvatar(sessionID, Integer.parseInt(selectedPicture));
        User user = userDAO.getUserByID(String.valueOf(sessionID));
        int numberOfReviews = getNumberOfReviewsForUser(sessionID, req);
        double ratingSum = getSumOfRatingsForUser(sessionID, req);
        double ratingAvg = 0;
        if(numberOfReviews!=0) {
            ratingAvg = ratingSum / numberOfReviews;
        }
        BookListDAO bookListDAO = (BookListDAO) req.getServletContext().getAttribute("bookListDAO");
        List<BookList> userBookLists = bookListDAO.getAllUserLists(user.getId());
        String about = userDAO.getUsersAboutInfo(sessionID);
        String reqAbout = req.getParameter("about_text");
        if (reqAbout != null) {
            about = reqAbout;
            userDAO.setUsersAboutInfo(sessionID, about);
        }

        List<User> followers;
        List<User> following;

        followers = userDAO.getUserFollowers(sessionID);
        following = userDAO.getUserFollowings(sessionID);

        boolean followingCurrentUser = false;
        for (int i = 0; i < followers.size(); i++) {
            if (followers.get(i).getId() == sessionID) {
                followingCurrentUser = true;
            }
        }
//        System.out.println(userDAO.getAvatarPath(Integer.parseInt(selectedPicture)));
        setRequest(req, sessionID, user, numberOfReviews, ratingAvg, followers, following, userBookLists, about);
        req.setAttribute("my_profile_flag", true);
        req.setAttribute("following_curr_user", followingCurrentUser);
        req.setAttribute("sessionID", sessionID);
        req.setAttribute("imagePath", userDAO.getAvatarPath(Integer.parseInt(selectedPicture)));

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }
}
