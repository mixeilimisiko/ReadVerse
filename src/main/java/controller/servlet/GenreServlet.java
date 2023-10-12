package controller.servlet;

import dao.DbBookDAO;
import dao.DbGenreDAO;
import dao.DbGenreDAO;
import dao.DbBookDAO;
import model.Genre;
import model.Book;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/genre/*")
public class GenreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("genreName");
        DbGenreDAO genreDAO = (DbGenreDAO) req.getServletContext().getAttribute("genreDAO");
//        System.out.println("GENRE:" + name);

        Genre currGenre = genreDAO.getGenre(name);
        req.setAttribute("genre", currGenre);
        // TODO BOOKS BY GENRE AND PROCESS HERE
        DbBookDAO bookDAO = (DbBookDAO) req.getServletContext().getAttribute("bookDAO");
        List<Book> categoryBooks = bookDAO.getBooksByCategory(name);
//        System.out.println("zoma"+ categoryBooks.size());
        req.setAttribute("genreBooks", categoryBooks);
       // resp.sendRedirect("/genre.jsp");
        req.getRequestDispatcher("/genre.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doPost(httpServletRequest, httpServletResponse);
    }
}
