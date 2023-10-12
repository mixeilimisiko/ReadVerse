package controller.servlet.Recommendations;

import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "RecommendationsServlet", value = "/RecommendationsServlet")
public class RecommendationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the list of books entered by the user from the session
        Set<Book> booksForProcessing = (Set<Book>) request.getSession().getAttribute("booksToProcess");
        if(booksForProcessing == null) booksForProcessing = new HashSet<Book>();
        request.getSession().setAttribute("booksToProcess", booksForProcessing);

        String errorMessage = "";
        if(request.getParameter("errorMessage")!=null) errorMessage = request.getParameter("errorMessage");

        request.setAttribute("errorMessage", errorMessage);

        request.getRequestDispatcher("recommendation.jsp").forward(request, response);

    }
}
