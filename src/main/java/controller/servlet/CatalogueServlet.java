package controller.servlet;

import dao.Interfaces.GenreDAO;
import dao.Interfaces.UserDAO;
import model.Book;
import model.Genre;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.IOException;

@WebServlet("/CatalogueServlet")
public class CatalogueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenreDAO genreDAO = (GenreDAO) req.getServletContext().getAttribute("genreDAO");
        List<Genre> genres = genreDAO.getAllGenres();
        Random random = new Random();
        Set<Integer> indexSet = new HashSet<>();
        int randomIndex = 0;
        List<Book> bookList = new ArrayList<>();
//        System.out.println("genres size :   " + genres.size());
        HashMap<String, List<Book>> allGenres = new HashMap<>();
        while(indexSet.size() < 4) {
            randomIndex = random.nextInt(genres.size());
            if(!indexSet.contains(randomIndex)) {
//                System.out.println("genres rand name :   " + genres.get(randomIndex).getName());
                bookList = genreDAO.getTop5Books(genres.get(randomIndex).getName());
                if(bookList.size() < 5) {
                    continue;
                }
                System.out.println(bookList.size());
                allGenres.put(genres.get(randomIndex).getName(), bookList);
                for(int i = 0; i < bookList.size(); i++) {
//                    System.out.println(bookList.get(i).getTitle());
                }
                indexSet.add(randomIndex);
            }

        }

        req.setAttribute("genres", genres);
        req.setAttribute("allGenres", allGenres);
        req.getRequestDispatcher("/catalogue.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
