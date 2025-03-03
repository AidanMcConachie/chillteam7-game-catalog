import backend.Database;
import backend.SteamAPIFetcher;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        Database database = new Database();
        database.clear();
        database.addGame(413150);
        database.addGame(440);
        String[] info = database.fetchAllGameInfo(413150);
        String[] info2 = database.fetchAllGameInfo(440);
        System.out.println(Arrays.toString(info2));
        System.out.println(Arrays.toString(info));
    }

}