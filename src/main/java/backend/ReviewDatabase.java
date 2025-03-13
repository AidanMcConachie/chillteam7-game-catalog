package backend;

import org.postgresql.jdbc2.ArrayAssistant;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


// TODO: Test this class (WIP!)

public class ReviewDatabase{
    public static String reviewsJdbcURL = "jdbc:sqlite:database/reviews.db";
    public static Connection con;
    public ReviewDatabase() {}
    public static Connection getConnection() throws SQLException {
        if(con == null) {
            con = DriverManager.getConnection(reviewsJdbcURL);
        }
        return con;
    }
    public void create() throws SQLException {
        try{
            Connection connection = getConnection();
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS reviews (id, username, rating, reviewtext, datePosted)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addReview(int id, String username, int rating, String reviewText) throws SQLException {
        Connection connection = getConnection();
        String query = "INSERT INTO reviews (id, username, rating, reviewText, datePosted) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, username);
        preparedStatement.setInt(3, rating);
        preparedStatement.setString(4, reviewText);
        preparedStatement.setDate(5, Date.valueOf(LocalDate.now())); // Need to test
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
    public ArrayList<String[]> getReviews(int steamid) throws SQLException {
        String query = "SELECT * FROM reviews WHERE steamid = ?";
        ArrayList<String[]> reviews = new ArrayList<>();
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, steamid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                reviews.add(new String[]{resultSet.getString("username"), resultSet.getString("rating"), resultSet.getString("reviewText"), resultSet.getString("datePosted")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    public void clear() throws SQLException {
        String query = "DROP TABLE IF EXISTS reviews";
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        create();
    }
}
