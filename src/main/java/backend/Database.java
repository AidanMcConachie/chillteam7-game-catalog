package backend;

// Might need maven for PostGreSQL

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Database {
    private int totalRecords;
    // change below
    String jdbcURL = "jdbc:sqlite:database/catalog.db";
    //
    public Database() {
    }
    public int getTotalRecords() {
       return totalRecords;
    }
    // Can add more params if needed
    public void createDatabase() throws SQLException { // Only needs to be run once, perhaps when the user first loads up the UI?
        try(Connection connection = DriverManager.getConnection(jdbcURL)){
            connection.createStatement().execute("CREATE TABLE catalog (id, name, description, headerImage, genres, developers, publishers)");
        }
    }
    public void addGame(String id, String name, String description, String headerImage, String genres, String developers, String publishers) { // Perhaps we make this a String[]?
        try{
            Connection connection = DriverManager.getConnection(jdbcURL);
            String query = "INSERT INTO catalog (id, name, description, headerImage, genres, developers, publishers) VALUES (?, ?, ?, ?, ?, ?, ?)"; // formatting is done below

            PreparedStatement preparedStatement = connection.prepareStatement(query); // might just be Statement
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, headerImage);
            preparedStatement.setString(5, genres);
            preparedStatement.setString(6, developers);
            preparedStatement.executeUpdate(); // this might just be "execute"

            preparedStatement.close();
            totalRecords++;
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
