package controller.servlet;

import dao.Interfaces.AuthorDAO;
import dao.Interfaces.BookDAO;
import model.Author;
import model.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "AuthorServlet", value = "/AuthorServlet")
public class AuthorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorID = -1;
        String authorName = "";
        if(request.getParameter("authorID") != null) {
            authorID = Integer.parseInt(request.getParameter("authorID"));
        }else{
            authorName = (String)request.getParameter("authorName");
        }
        BookDAO bookDAO = (BookDAO) getServletContext().getAttribute("bookDAO");
        AuthorDAO authorDAO = (AuthorDAO) getServletContext().getAttribute("authorDAO");
        Author currAuthor = null;
        if(authorID == -1) {
            currAuthor = authorDAO.getAuthor(authorName);
        }else {
            currAuthor = authorDAO.getAuthor(authorID);
        }
        if (currAuthor != null) {
            // Use the BookDAO to retrieve the author's bibliography
            List<Book> Bibliography = bookDAO.getBibliography(currAuthor.getName());
            Set<String> Genres = new HashSet<>();
            for (Book currBook : Bibliography) {
                Genres.addAll(currBook.getGenres());
            }

            // Set the bibliography list and current author as request attributes
            request.setAttribute("author", currAuthor);
            request.setAttribute("bibliography", Bibliography);
            request.setAttribute("genres", Genres);
            // Forward the request to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("authorPage.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle the case when author is not found
            response.getWriter().println("Author not found!");
        }
    }

}
