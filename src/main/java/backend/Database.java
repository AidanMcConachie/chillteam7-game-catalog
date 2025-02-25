package backend;

// Might need maven for PostGreSQL

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Database {
    private int totalRecords;
    // change below
    String jdbcURL = "jdbc:postgresql://localhost:3306/catalog";
    String username = "postgres";
    String password = "0000";
    //
    public Database() {
    }
    public int getTotalRecords() {
       return totalRecords;
    }
    // Can add more params if needed
    public void addGame(String id, String name, String description, String headerImage, String genres, String developers, String publishers) {
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String query = "INSERT INTO catalog (id, name, description, headerImage, genres, developers, publishers) VALUES (?, ?, ?, ?, ?, ?)"; // formatting is done below

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
