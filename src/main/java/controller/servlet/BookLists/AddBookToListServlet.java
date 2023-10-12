package controller.servlet.BookLists;

import dao.Interfaces.BookDAO;
import dao.Interfaces.BookListDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddBookToListServlet", value = "/AddBookToListServlet")
public class AddBookToListServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the list_id, user_id, and bookTitle from the request parameters
        int listId = Integer.parseInt(request.getParameter("listID"));
        int userId = Integer.parseInt(request.getParameter("userID"));
        String bookTitle = (String)request.getParameter("bookTitle");
        // Get the sourceJSP to control where to send redirect
        String source = (String)request.getParameter("sourceJSP");

        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        BookListDAO bookListDAO = (BookListDAO) getServletContext().getAttribute("bookListDAO");
        Book book = bookDAO.findBook(bookTitle);
        boolean bookExists = (book != null);
        if (bookExists) {
            // Book exists, add it to the list
            bookListDAO.addBookToList(userId, listId, book.getId());
            // Redirect to the display list page
            if(source.equals("displayList")) {
                response.sendRedirect("DisplayListServlet?listID=" + listId);
            }
        } else {
            // Book doesn't exist, display an error message
            if(source.equals("displayList")) {
                response.sendRedirect("DisplayListServlet?listID=" + listId + "&errorMessage=" + "The book does not exist!");
            }
        }
    }
}
