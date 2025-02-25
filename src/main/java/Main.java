import backend.SteamAPIFetcher;


public class Main {
    public static void main(String[] args) throws Exception {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        fetcher.fetchGameData(440);
        System.out.println(fetcher.toString());
    }
}