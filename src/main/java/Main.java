import backend.Database;
import backend.SteamAPIFetcher;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        Database database = new Database();
        String[] data = fetcher.fetchGameData(440); // Example
        //database.createDatabase();
        database.addGame(data[0], data[1], data[2], data[3], data[4], data[5], data[6]); // see comment in database file
    }
}