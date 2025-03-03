package backend;

// Might need maven for PostGreSQL

import java.sql.*;


public class Database {
    private int totalRecords;
    // change below
    String catalogJdbcURL = "jdbc:sqlite:database/catalog.db";
    String reviewJDBCURL = "jdbc:sqlite:database/reviews.db";
    SteamAPIFetcher fetcher = new SteamAPIFetcher();

    //
    public Database() {
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    // Can add more params if needed
    public void createDatabase() throws SQLException { // Only needs to be run once, perhaps when the user first loads up the UI?
        try (Connection connection = DriverManager.getConnection(catalogJdbcURL)) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS catalog (id, name, description, headerImage, price, genres, developers, publishers)");
        }
    }

    public void addGame(int steamid) throws Exception { // dbArguments: id, name, description, headerIMage, generes, developers, publishers
        String[] dbArguments = fetcher.fetchGameData(steamid);
        try {
            Connection connection = DriverManager.getConnection(catalogJdbcURL);
            String query = "INSERT INTO catalog (id, name, description, headerImage, price, genres, developers, publishers) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // formatting is done below

            PreparedStatement preparedStatement = connection.prepareStatement(query); // might just be Statement
            preparedStatement.setString(1, dbArguments[0]);
            preparedStatement.setString(2, dbArguments[1]);
            preparedStatement.setString(3, dbArguments[2]);
            preparedStatement.setString(4, dbArguments[3]);
            preparedStatement.setString(5, dbArguments[4]);
            preparedStatement.setString(6, dbArguments[5]);
            preparedStatement.setString(7, dbArguments[6]);
            preparedStatement.setString(8, dbArguments[7]);
            preparedStatement.executeUpdate(); // this might just be "execute"
            preparedStatement.close();
            totalRecords++;
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeGame(int steamid) {
        String query = "DELETE FROM catalog WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(catalogJdbcURL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, steamid);
            preparedStatement.executeUpdate();
            totalRecords--; // might not be best practice
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String fetchGameInfo(int steamid, String component) {
        String query = "SELECT " + component + " FROM catalog WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(catalogJdbcURL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, steamid + "");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(component);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public String[] fetchAllGameInfo(int steamid) {
        String query = "SELECT * FROM catalog WHERE id = ?";
        try{
            Connection connection = DriverManager.getConnection(catalogJdbcURL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, steamid + "");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new String[]{resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)};
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void clearDatabase() throws SQLException {
        String query = "DROP TABLE IF EXISTS catalog";
        try (Connection connection = DriverManager.getConnection(catalogJdbcURL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            totalRecords=0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createDatabase();
    }
}