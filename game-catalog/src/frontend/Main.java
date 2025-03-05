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
        cards.add(new Card("Elden Ring", "RPG", "102", "An open-world action RPG.", "https://upload.wikimedia.org/wikipedia/en/a/a1/Half-Life_Cover_Art.jpg"));
        cards.add(new Card("Cyberpunk 2077", "RPG", "201", "A futuristic RPG with an open world.", "https://upload.wikimedia.org/wikipedia/en/d/d2/Portal_standalonebox.jpg"));


        // had to change it so passes the `cards` list directly into the `catalogUI` constructor
        SwingUtilities.invokeLater(() -> new catalogUI(cards));

        // Create UI instance and add cards
        //catalogUI gameCatalogue = new catalogUI();
        //gameCatalogue.addCards(cards);
    }
}
