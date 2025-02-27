package backend;

// Might need maven for PostGreSQL

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Database {
    private int totalRecords;
    // change below
    String catalogJdbcURL = "jdbc:sqlite:database/catalog.db";
    String reviewJDBCURL = "jdbc:sqlite:database/reviews.db";
    //
    public Database() {
    }
    public int getTotalRecords() {
       return totalRecords;
    }
    // Can add more params if needed
    public void createCatalogDatabase() throws SQLException { // Only needs to be run once, perhaps when the user first loads up the UI?
        try(Connection connection = DriverManager.getConnection(catalogJdbcURL)){
            connection.createStatement().execute("CREATE TABLE catalog (id, name, description, headerImage, genres, developers, publishers)");
        }
    }
    public void addGame(String[] dbArguments) { // dbArguments: id, name, description, headerIMage, generes, developers, publishers
        try{
            Connection connection = DriverManager.getConnection(catalogJdbcURL);
            String query = "INSERT INTO catalog (id, name, description, headerImage, genres, developers, publishers) VALUES (?, ?, ?, ?, ?, ?, ?)"; // formatting is done below

            PreparedStatement preparedStatement = connection.prepareStatement(query); // might just be Statement
            preparedStatement.setString(1, dbArguments[0]);
            preparedStatement.setString(2, dbArguments[1]);
            preparedStatement.setString(3, dbArguments[2]);
            preparedStatement.setString(4, dbArguments[3]);
            preparedStatement.setString(5, dbArguments[4]);
            preparedStatement.setString(6, dbArguments[5]);
            preparedStatement.executeUpdate(); // this might just be "execute"

            preparedStatement.close();
            totalRecords++;
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void createReviewDatabase(int steamID) throws SQLException {
        try(Connection connection = DriverManager.getConnection(catalogJdbcURL)){
            connection.createStatement().execute("CREATE TABLE " + steamID + " (user, datePosted)"); // I don't really know if this works as expected
        }
    }
    public void addReview(String steamID, String datePosted) throws SQLException {
    }
}
