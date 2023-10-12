package controller.servlet.BookLists;

import dao.Interfaces.BookListDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteBookFromListServlet", value = "/DeleteBookFromListServlet")
public class DeleteBookFromListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the user_id, list_id, and book_id from the request parameters
//        int user_id = Integer.parseInt(request.getParameter("userID"));
        int list_id = Integer.parseInt(request.getParameter("listID"));
        int book_id = Integer.parseInt(request.getParameter("bookID"));
        // Remove the book from the list in the database
        BookListDAO bookListDAO = (BookListDAO) getServletContext().getAttribute("bookListDAO");
        bookListDAO.removeBookFromList(list_id, book_id);

        // Redirect to the display list page
        response.sendRedirect("DisplayListServlet?listID=" + request.getParameter("listID"));
    }
}
