package controller.servlet.BookLists;

import dao.DbBookListDAO;
import dao.Interfaces.BookListDAO;
import model.Book;
import model.BookList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreateListServlet", value = "/CreateListServlet")
public class CreateListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("createList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String title = (String)session.getAttribute("newListTitle");
        String description = (String)session.getAttribute("newListDescription");
        BookListDAO bookListDAO = (DbBookListDAO) request.getServletContext().getAttribute("bookListDAO");
        List<Book> selectedBooks = (List<Book>) session.getAttribute("selectedBooks");
        int current_user_id = (int) session.getAttribute("current_user_id");
        BookList newList = new BookList(current_user_id, title, description, selectedBooks);
        session.setAttribute("newListTitle", null);
        session.setAttribute("newListDescription", null);
        session.setAttribute("selectedBooks", null);

        bookListDAO.createList(newList);
        response.sendRedirect("createList.jsp");
    }
}
