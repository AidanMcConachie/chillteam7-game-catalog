import backend.Card;
import backend.Database;
import backend.SteamAPIFetcher;
import frontend.catalogUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Database database = new Database();
        //database.clear();
        //database.addGame(413150);
        //database.addGame(440);
        //database.addGame(730);

        // Example cards
        // TO DO: Replace with database data)
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < database.fetchAllGameIDs().size(); i++) {
            String[] current = database.fetchAllGameInfo(database.fetchAllGameIDs().get(i));
            cards.add(new Card(current[1], current[5], current[0], current[2], current[3]));
        }
        // had to change it so passes the `cards` list directly into the `catalogUI` constructor
        SwingUtilities.invokeLater(() -> new catalogUI(cards));

        // Create UI instance and add cards
        //catalogUI gameCatalogue = new catalogUI();
        //gameCatalogue.addCards(cards);



    }
}