package controller.servlet;

import dao.Interfaces.BookDAO;
import model.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SearchBookServlet", value = "/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDAO bookDAO = (BookDAO) request.getServletContext().getAttribute("bookDAO");
        String title = request.getParameter("bookTitle");
        Book book = bookDAO.findBook(title);
        if(book == null){
            request.setAttribute("errorMessage", "Book does not exist");
            RequestDispatcher dispatcher = request.getRequestDispatcher("CatalogueServlet");
            dispatcher.forward(request, response);
        }else {
            response.sendRedirect("BookServlet?bookID="+book.getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
