package controller.servlet.BookLists;

import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteFromNewListServlet", value = "/DeleteFromNewListServlet")
public class DeleteFromNewListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        List<Book> booksToProcess = (List<Book>) request.getSession().getAttribute("selectedBooks");

        if (booksToProcess != null) {
            for (int i = 0; i < booksToProcess.size(); i++) {
                if (booksToProcess.get(i).getId() == bookID) {
                    booksToProcess.remove(i);
                    break;
                }
            }
        }
        response.sendRedirect("createList.jsp");
    }
}
