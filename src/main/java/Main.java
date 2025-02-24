import backend.SteamAPIFetcher;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        System.out.println("Fetching Steam API...");
        System.out.println(fetcher.fetchGameData(440)[4]);
    }
}