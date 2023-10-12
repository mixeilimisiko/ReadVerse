package controller.servlet.BookLists;

import dao.Interfaces.BookListDAO;
import dao.Interfaces.UserDAO;
import model.Book;
import model.BookList;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DisplayListServlet", value = "/DisplayListServlet")
public class DisplayListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int listId = Integer.parseInt(request.getParameter("listID"));
        BookListDAO bookListDAO = (BookListDAO) getServletContext().getAttribute("bookListDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        BookList bookList = bookListDAO.getListById(listId);
        User user = userDAO.getUserByID(Integer.toString(bookList.getUserId()));
        // Retrieve session user id to check if it matches list's owner's id
        HttpSession session = request.getSession();
        int session_user_id = (Integer) session.getAttribute("current_user_id");
        boolean myPage = (bookList.getUserId() == session_user_id);
        if (bookList != null && user != null) {
            System.out.println(bookList.getTitle());
            System.out.println(bookList.getDescription());
            for(Book book : bookList.getBooks()) {
                System.out.println(book.getTitle());
            }
            // Set request attributes
            request.setAttribute("bookList", bookList);
            request.setAttribute("myPage", myPage);
            request.setAttribute("user", user);
            if (request.getParameter("errorMessage") != null) {
                request.setAttribute("errorMessage", request.getParameter("errorMessage"));
            }

            // Forward the request to the JSP page for displaying the list
            RequestDispatcher dispatcher = request.getRequestDispatcher("displayList.jsp");
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println("List not found!");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
