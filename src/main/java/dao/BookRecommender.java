package dao;

import dao.Interfaces.BookDAO;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BookRecommender {

    public static List<Book> getRecommendations(Set<Book> userFavourites, BookDAO bookDAO) {

        System.out.println(userFavourites.toString());
        Map<String, Integer> statistics = getStatistics(userFavourites);
        double total = 0;
        for (Integer val : statistics.values()) {
            total += val;
        }
        Map<String, Double> coefficients = new HashMap<>();
        for (String genre : statistics.keySet()) {
            coefficients.put(genre, (double)(1.0 + (statistics.get(genre) / total)));
        }

        String avgCoeffSQL = generateAvgCoeffSQL(coefficients.size());
        System.out.println(avgCoeffSQL);

        String mainQuery = "SELECT b.*, (b.rating * COALESCE(avg_coeff, 0)) AS final_rating FROM books b LEFT JOIN (" +

                avgCoeffSQL +
                ") AS subquery ON b.book_id = subquery.book_id ORDER BY final_rating DESC";

        try ( Connection conn = ConnectionManager.getInstance().getConnection(false);
             PreparedStatement pstmt = conn.prepareStatement(mainQuery)) {

            // Set genre parameters and coefficients based on the computed coefficients map
            int parameterIndex = 1;
            for (Map.Entry<String, Double> entry : coefficients.entrySet()) {
                pstmt.setString(parameterIndex++, entry.getKey());     // Parameter for genre name
                pstmt.setDouble(parameterIndex++, entry.getValue());  // Parameter for genre coefficient
//                System.out.println(entry.getKey());
//                System.out.println(entry.getValue());
            }

            ResultSet resultSet = pstmt.executeQuery();
            List<Book> recommendedBooks = new ArrayList<>();
            int cnt = 0;
            while (resultSet.next()) {
                if(cnt >= 10) break;

                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double rating = resultSet.getDouble("rating");
                int year = resultSet.getInt("year");
                String path = resultSet.getString("cover_path");
                List<String> genres = bookDAO.getBookGenres(id);
                // Create and return a Book object
                Book book = new Book(id, title, author, description, rating, genres, year, path);
                if(userFavourites.contains(book)) continue;
                cnt++;
                recommendedBooks.add(book);
            }
            return recommendedBooks;
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return null;
    }

    private static String generateAvgCoeffSQL(int n) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT book_id, AVG(CASE");
        for (int i = 0; i < n; i ++) {
            sqlBuilder.append(" WHEN bg.genre = ? THEN ?");
        }
        sqlBuilder.append(" ELSE 0 END) AS avg_coeff FROM book_genres bg GROUP BY book_id");
        return sqlBuilder.toString();
    }

    private static Map<String, Integer> getStatistics(Set<Book> userFavourites) {
        Map<String, Integer> statistics = new HashMap<>();
        for(Book book : userFavourites) {
            List<String> genres = book.getGenres();
            for(String genre : genres) {
                int cnt = 1;
                if(statistics.containsKey(genre)){
                    cnt = statistics.get(genre) + 1;
                }
                statistics.put(genre, cnt);
            }
        }
        return statistics;
    }
}
