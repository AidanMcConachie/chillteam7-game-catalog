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
    public void addGame(String name, String genre, String id, String description) {
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String query = "INSERT INTO catalog (name, genre, id, description) VALUES (?, ?, ?, ?)"; // formatting is done below

            PreparedStatement preparedStatement = connection.prepareStatement(query); // might just be Statement
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, id);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate(); // this might just be execute

            preparedStatement.close();
            totalRecords++;
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
