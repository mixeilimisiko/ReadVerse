package controller.servlet.BookLists;

import dao.Interfaces.BookListDAO;
import model.BookList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteListServlet", value = "/DeleteListServlet")
public class DeleteListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int listId = Integer.parseInt(request.getParameter("listID"));

        // Create an instance of your BookListDAO to perform database operations
        BookListDAO bookListDAO = (BookListDAO) getServletContext().getAttribute("bookListDAO");

        // Check if the list with the given list_id exists and belongs to the user
        BookList bookList = bookListDAO.getListById(listId);
        int userId = bookList.getUserId();
        if (bookList != null && bookList.getUserId() == userId) {
            // Delete the list and its associated books from the database
            bookListDAO.deleteList(listId);

            // Redirect the user to a page (e.g., a dashboard) after successful deletion
            response.sendRedirect("UserServlet?userID=" + userId);
        } else {
            // Redirect the user to an error page if the list doesn't exist or doesn't belong to the user
            response.sendRedirect("DisplayListServlet/listID=" + listId +"&errorMessage=deletionfailed");
        }

    }

}
