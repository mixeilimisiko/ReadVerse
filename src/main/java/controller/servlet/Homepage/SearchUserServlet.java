package controller.servlet.Homepage;

import dao.Interfaces.UserDAO;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SearchUserServlet", value = "/SearchUserServlet")
public class SearchUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
        String username = request.getParameter("username");
        User user = userDAO.getUserByName(username);
        if(user == null){
            request.setAttribute("errorMessage", "User does not exist");
            RequestDispatcher dispatcher = request.getRequestDispatcher("HomepageServlet");
            dispatcher.forward(request, response);
        }else {
            response.sendRedirect("UserServlet?userID="+user.getId());
        }
    }
}
