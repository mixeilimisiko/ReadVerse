package listeners;

import dao.*;
import dao.Interfaces.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        UserDAO userDAO = new DbUserDAO();
        BookDAO bookDAO = new DbBookDAO();
        ReviewDAO reviewDAO = new DbReviewDAO();
        AuthorDAO authorDAO = new DbAuthorDAO();
        BookListDAO bookListDAO = new DbBookListDAO();
        GenreDAO genreDAO = new DbGenreDAO();
        ServletContext sc = servletContextEvent.getServletContext();
        sc.setAttribute("userDAO", userDAO);
        sc.setAttribute("bookDAO", bookDAO);
        sc.setAttribute("reviewDAO", reviewDAO);
        sc.setAttribute("authorDAO", authorDAO);
        sc.setAttribute("bookListDAO", bookListDAO);
        sc.setAttribute("genreDAO", genreDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // TODO : close connection
    }
}
