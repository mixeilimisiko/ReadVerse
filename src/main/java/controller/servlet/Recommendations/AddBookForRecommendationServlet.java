package controller.servlet.Recommendations;

import dao.Interfaces.BookDAO;
import model.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "AddBookForRecommendationServlet", value = "/AddBookForRecommendationServlet")
public class AddBookForRecommendationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String bookTitle = request.getParameter("bookTitle");

        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        Book book = bookDAO.findBook(bookTitle);

        if (book == null) {
            // if book not found, set error message and redirect back to recommendation.jsp
            request.setAttribute("errorMessage", "Book not found!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("recommendation.jsp");
            dispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            Set<Book> booksToProcess = (Set<Book>) session.getAttribute("booksToProcess");

            // If booksToProcess is null, create a new list and set it as session attribute
            if (booksToProcess == null) {
                booksToProcess = new HashSet<>();
                session.setAttribute("booksToProcess", booksToProcess);
            }

            booksToProcess.add(book);
            response.sendRedirect("recommendation.jsp");
        }
    }
}
