import backend.Card;
import backend.CatalogDatabase;
import backend.ReviewDatabase;
import backend.SteamAPIFetcher;
import frontend.catalogUI;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Main {
    CatalogDatabase database;
    ArrayList<Card> cards;

    public static void main(String[] args) throws Exception {
        CatalogDatabase database = new CatalogDatabase();
//        database.clear();
        ReviewDatabase reviews = new ReviewDatabase();
        List<Integer> gameIds = Arrays.asList(
                730,    // Counter-Strike: Global Offensive
                570,    // Dota 2
                440,    // Team Fortress 2
                550,    // Left 4 Dead 2
                578080, // PUBG: BATTLEGROUNDS
                271590, // Grand Theft Auto V
                359550, // Tom Clancy's Rainbow Six Siege
                252490, // Rust
                620,    // Portal 2
                4000,   // Garry's Mod
                236390, // War Thunder
                105600, // Terraria
                220,    // Half-Life 2
                304930, // Unturned
                8930,   // Sid Meier's Civilization V
                72850,  // The Elder Scrolls V: Skyrim
                377160, // Fallout 4
                582010, // Monster Hunter: World
                271590, // Grand Theft Auto V
                292030, // The Witcher 3: Wild Hunt
                374320, // DARK SOULS III
                413150 // Stardew Valley


        );

        for(Integer gameId : gameIds) {
            database.addGame(gameId);
        }


        database.addGame(1172470); // Apex Legends
        database.addGame(252950); // Rocket League
        database.addGame(1085660); // Destiny 2
        database.addGame(381210); // Dead by Daylight
        database.addGame(945360); // Amoung us
        database.addGame(413420); // Euro Track Simulator 2
        database.addGame(362890); // Black Mesa
        database.addGame(108710); // Red Dead Redemption 2



        int dbcount = database.getAllGameIDs().size();
        System.out.println(dbcount + "   db!");
//        System.out.println(Arrays.toString(database.getAllGameIDs().toArray()));

        // Example cards
        List<Card> cards = new ArrayList<>();

        // had to change it so passes the `cards` list directly into the `catalogUI` constructor
        SwingUtilities.invokeLater(() -> new catalogUI(cards, database, reviews));

    }
}