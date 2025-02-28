import backend.Database;
import backend.SteamAPIFetcher;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        Database database = new Database();
        // String[] data = fetcher.fetchGameData(440); // Example
        System.out.println(Arrays.toString(fetcher.fetchGameData(105600)));
        // database.addGame(data); // implemented via a String[] instead
    }
}