import backend.Database;
import backend.SteamAPIFetcher;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        Database database = new Database();
        String[] data = fetcher.fetchGameData(440); // Example
        database.addGame(data); // implemented via a String[] instead
    }
}