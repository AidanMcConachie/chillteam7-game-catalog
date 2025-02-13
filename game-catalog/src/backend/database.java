package backend;

// Might need maven for PostGreSQL
import java.sql.*;


public class database {
    private int totalRecords;
    // change below
    String jdbcURL = "jdbc:mysql://localhost:3306/catalog";
    String username = "root";
    String password = "";
    //
    public database() {
    }
    public int getTotalRecords() {
       return totalRecords;
    }
    // Can add more params if needed
    public void appendToCatalog(String name, String genre, String id, String description) {
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String query = "INSERT INTO catalog (name, genre, id, description) VALUES (?, ?, ?, ?)"; // formatting is done below

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, id);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate(); // this might just be execute

            preparedStatement.close();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
