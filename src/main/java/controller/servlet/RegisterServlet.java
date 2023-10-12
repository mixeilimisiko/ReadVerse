package controller.servlet;

import controller.servlet.Homepage.HomepageServlet;
import controller.validator.RegisterValidation;
import dao.Interfaces.BookDAO;
import dao.Interfaces.ReviewDAO;
import dao.Interfaces.UserDAO;
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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordConfirmed = req.getParameter("passwordConfirmed");
        String email = req.getParameter("email");
        ReviewDAO reviewDAO = (ReviewDAO) getServletContext().getAttribute("reviewDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        RegisterValidation registerValidation = new RegisterValidation(username, password, passwordConfirmed, email, userDAO);

        boolean uniqueUser = registerValidation.uniqueUsername();
        boolean uniqueMail = registerValidation.uniqueMail();

        if (uniqueUser && uniqueMail) {
            userDAO.addUser(username, password, email, "", 1);
            User currUser = userDAO.getUser(username);
            int userID = currUser.getId();
            HttpSession session = req.getSession();
            session.setAttribute("current_user_id", userID);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage.jsp");
            List<User> followings = userDAO.getUserFollowings(userDAO.getUser(username).getId());
            HomepageServlet.homepageCook(reviewDAO, req, userDAO, bookDAO, followings);
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("userError", uniqueUser);
            req.setAttribute("emailError", uniqueMail);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
            dispatcher.forward(req, resp);
        }
    }

}
