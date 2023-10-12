package controller.servlet.Recommendations;

import dao.Interfaces.BookDAO;
import model.Book;
import dao.BookRecommender;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "ProcessBooksServlet", value = "/ProcessBooksServlet")
public class ProcessBooksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Set<Book> userFavourites =  (Set<Book>) session.getAttribute("booksToProcess");
        BookDAO bookDAO = (BookDAO) request.getServletContext().getAttribute("bookDAO");
        List<Book> recommendedBooks = BookRecommender.getRecommendations(userFavourites, bookDAO);
        for(int i = 0 ; i < recommendedBooks.size(); i++ ) {
            System.out.println(recommendedBooks.get(i).getTitle());
        }
//        userFavourites.clear();
        request.setAttribute("recommendedBooks", recommendedBooks);

        RequestDispatcher dispatcher = request.getRequestDispatcher("displayRecommendations.jsp");
        dispatcher.forward(request, response);

    }

}
