package controller.servlet;

import dao.DbBookDAO;
import dao.DbGenreDAO;
import dao.Interfaces.GenreDAO;
import model.Book;
import model.Genre;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SearchGenreServlet")
public class SearchGenreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //super.doPost(httpServletRequest, httpServletResponse);
        GenreDAO genreDAO = (GenreDAO) httpServletRequest.getServletContext().getAttribute("genreDAO");
        String searchGenres = httpServletRequest.getParameter("search");
        searchGenres.toLowerCase();
        httpServletRequest.setAttribute("searchName", searchGenres);
        List<String> genreNames = List.of(searchGenres.split("and"));
        List<String> genreNamesCorrect = new ArrayList<>();
        for(int i = 0; i < genreNames.size(); i++) {

            String currGenre = genreNames.get(i).trim();
            currGenre = Character.toUpperCase(currGenre.charAt(0)) + currGenre.substring(1).toLowerCase();
            genreNamesCorrect.add(currGenre);
        }
        List<Book> intersectedBooks = genreDAO.getBooksByCategories(genreNamesCorrect);
//        System.out.println(intersectedBooks.size());
        if(intersectedBooks.size() == 0) {
            String searchError = "No books have been shelved as \"" + searchGenres + "\"";
            httpServletRequest.setAttribute("searchError", searchError);
        }
        httpServletRequest.setAttribute("searchBooks", intersectedBooks);
        httpServletRequest.getRequestDispatcher("/searchGenre.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
