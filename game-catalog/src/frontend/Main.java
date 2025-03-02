package frontend;

import backend.Card;
import backend.Database;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        // db.appendToCatalog(""); // Uncomment when needed

        // Example cards
        // TO DO: Replace with database data)
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Half-Life", "FPS", "12345", "A revolutionary shooter", "https://upload.wikimedia.org/wikipedia/en/a/a1/Half-Life_Cover_Art.jpg"));
        cards.add(new Card("Portal", "Puzzle", "67890", "A mind-bending game", "https://upload.wikimedia.org/wikipedia/en/d/d2/Portal_standalonebox.jpg"));

        // Create UI instance and add cards
        catalogUI gameCatalogue = new catalogUI();
        gameCatalogue.addCards(cards);

    }
}