import backend.Database;
import backend.SteamAPIFetcher;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        Database database = new Database();
        database.clearDatabase();
        database.addGame(413150);
        String[] info = database.fetchAllGameInfo(413150);
    }

}