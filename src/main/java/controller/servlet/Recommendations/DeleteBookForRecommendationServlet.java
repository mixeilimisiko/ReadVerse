package controller.servlet.Recommendations;

import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "DeleteBookForRecommendationServlet", value = "/DeleteBookForRecommendationServlet")
public class DeleteBookForRecommendationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        Set<Book> booksToProcess = (Set<Book>) request.getSession().getAttribute("booksToProcess");

        if (booksToProcess != null) {
            for(Book b : booksToProcess) {
                if(b.getId() == bookID) {
                    booksToProcess.remove(b);
                    break;
                }
            }
        }
        response.sendRedirect("recommendation.jsp");
    }
}
