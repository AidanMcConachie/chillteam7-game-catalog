import backend.Card;
import backend.CatalogDatabase;
import backend.ReviewDatabase;
import backend.SteamAPIFetcher;
import frontend.catalogUI;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Main {
    CatalogDatabase database;

    public static void main(String[] args) throws Exception {

        CatalogDatabase database = new CatalogDatabase();
        ReviewDatabase reviews = new ReviewDatabase();
        database.clear();
//        database.addGame(730);  // cs 2
//        database.addGame(440); // Team fortress 2
//        database.addGame(70);      // Half-Life
//        database.addGame(400);     // Portal
//        database.addGame(292030);  // The Witcher 3: Wild Hunt
//        database.addGame(1245620); // Elden Ring
//
//        //database.addGame(1174180); // Red Dead Redemption 2
//        database.addGame(374320);  // Dark Souls III
//        database.addGame(1091500); // Cyberpunk 2077
//        database.addGame(945360);  // Among Us

        List<Card> cards = new ArrayList<>();
        //reviews.clear();
        //reviews.addReview(440, "Username", 5, "Fun game!");
        //reviews.addReview(440, "User", 2, "Boring");
        ArrayList<String[]> tf2Reviews = reviews.getReviews(440);
        for(int i = 0; i < tf2Reviews.size(); i++){
            System.out.println(Arrays.toString(tf2Reviews.get(i)));
        }

        // had to change it so passes the `cards` list directly into the `catalogUI` constructor
        SwingUtilities.invokeLater(() -> new catalogUI(cards, database));







    }
}