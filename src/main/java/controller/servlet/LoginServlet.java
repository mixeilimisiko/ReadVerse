package controller.servlet;

import controller.servlet.Homepage.HomepageServlet;
import controller.validator.LoginValidation;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        LoginValidation loginValidation = new LoginValidation(username, password, userDAO);

        boolean validUsername = loginValidation.validUsername();
        boolean correctPassword = loginValidation.correctPassword();

        if (validUsername && correctPassword) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage.jsp");
            req.getSession().setAttribute("current_user_id", userDAO.getUser(username).getId());
            List<User> followings = userDAO.getUserFollowings(userDAO.getUser(username).getId());
            HomepageServlet.homepageCook(reviewDAO, req, userDAO, bookDAO, followings);
            dispatcher.forward(req, resp);
        } else if (!validUsername){
            req.setAttribute("errorMess", "Not a valid username");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("errorMess", "Incorrect Password");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }
    }


}
