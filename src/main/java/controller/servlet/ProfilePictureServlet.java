package controller.servlet;

import dao.Interfaces.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProfilePictureServlet")
public class ProfilePictureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        List<String> pictures = userDAO.getAllAvatars();
        req.setAttribute("pictures", pictures);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/changePicture.jsp");
        dispatcher.forward(req, resp);
    }

}
