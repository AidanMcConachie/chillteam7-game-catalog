import backend.Card;
import backend.Database;
import backend.SteamAPIFetcher;
import frontend.catalogUI;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Main {
    Database database;

    public static void main(String[] args) throws Exception {

        Database database = new Database();
        database.clear();
        database.addGame(730);
        database.addGame(440);
        int dbcount = database.getAllGameIDs().size();
        System.out.println(dbcount + "   db!");
        System.out.println(Arrays.toString(database.getAllGameIDs().toArray()));
        // Example cards
        // TO DO: Replace with database data)
        List<Card> cards = new ArrayList<>();

        // had to change it so passes the `cards` list directly into the `catalogUI` constructor
        SwingUtilities.invokeLater(() -> new catalogUI(cards, database));

        // Create UI instance and add cards
        //catalogUI gameCatalogue = new catalogUI();
        //gameCatalogue.addCards(cards);



    }
}