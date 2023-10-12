package controller.servlet.BookPage;

import dao.Interfaces.BookDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BookServlet/*")
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookId = req.getParameter("bookID");
        if(bookId == null) {
            String requestURI = req.getRequestURI();
            bookId = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        }
        System.out.println("bookID " + bookId);

        BookDAO bookDAO = (BookDAO) req.getServletContext().getAttribute("bookDAO");
        Book currBook = bookDAO.findBookById(Integer.parseInt(bookId));
        req.setAttribute("book", currBook);
        req.setAttribute("bookId", bookId);
        System.out.println("book" + currBook.getTitle());
        req.getRequestDispatcher("/bookPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}