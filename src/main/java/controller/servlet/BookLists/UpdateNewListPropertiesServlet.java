package controller.servlet.BookLists;

import dao.Interfaces.BookListDAO;
import model.BookList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateNewListPropertiesServlet", value = "/UpdateNewListPropertiesServlet")
public class UpdateNewListPropertiesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String listDescription = request.getParameter("listDescription");
        if (listDescription != null) {
            session.setAttribute("newListDescription", listDescription);
        }else {
            int current_user_id = (int) session.getAttribute("current_user_id");
            String listTitle = request.getParameter("listTitle");
            BookListDAO bookListDAO = (BookListDAO) getServletContext().getAttribute("bookListDAO");
            BookList list = bookListDAO.getUserList(current_user_id, listTitle);
            if (list != null) {
                request.setAttribute("titleErrorMessage", "A list with such name already exists.");
                session.setAttribute("newListTitle", "");
            } else {
                session.setAttribute("newListTitle", listTitle);
            }
        }
        request.getRequestDispatcher("createList.jsp").forward(request, response);


    }
}
