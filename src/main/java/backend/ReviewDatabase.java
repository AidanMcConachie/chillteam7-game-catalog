package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDatabase{
    String reviewJdbcURL = "jdbc:sqlite:database/review.db";
    public ReviewDatabase() {}
    public void createDatabase() throws SQLException {
        try(Connection connection = DriverManager.getConnection(reviewJdbcURL)) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS reviews id, username, rating, reviewtext");
        }
    }
    public void addReview(int id, String username, int rating, String reviewText) throws SQLException {
        Connection connection = DriverManager.getConnection(reviewJdbcURL);
        String query = "INSERT INTO reviews (id, username, rating, reviewText) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, username);
        preparedStatement.setInt(3, rating);
        preparedStatement.setString(4, reviewText);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
    public ArrayList<String[]> getReviews(int steamid) throws SQLException {}
}
