package controller.servlet.BookLists;

import dao.Interfaces.BookDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UpdateNewListContentServlet", value = "/UpdateNewListContentServlet")
public class UpdateNewListContentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String bookTitle = request.getParameter("bookTitle");
        BookDAO bookDAO = (BookDAO) request.getServletContext().getAttribute("bookDAO");
        Book book = bookDAO.findBook(bookTitle);

        if (book == null) {
            // Book with the given title does not exist
            request.setAttribute("bookErrorMessage", "Book does not exist.");
        } else {
            // Book exists, add it to the selectedBooks list
            List<Book> selectedBooks = (List<Book>) session.getAttribute("selectedBooks");
            if (selectedBooks == null) {
                selectedBooks = new ArrayList<>();
            }
            if(!selectedBooks.contains(book))selectedBooks.add(book);
            session.setAttribute("selectedBooks", selectedBooks);
        }
        request.getRequestDispatcher("createList.jsp").forward(request, response);
    }
}
