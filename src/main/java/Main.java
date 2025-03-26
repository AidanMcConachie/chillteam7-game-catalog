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

    public static void main(String[] args) throws Exception {
        CatalogDatabase database = new CatalogDatabase();
        ReviewDatabase reviews = new ReviewDatabase();
//        List<Integer> gameIds = Arrays.asList(
//                730,    // Counter-Strike: Global Offensive
//                570,    // Dota 2
//                440,    // Team Fortress 2
//                550,    // Left 4 Dead 2
//                578080, // PUBG: BATTLEGROUNDS
//                271590, // Grand Theft Auto V
//                359550, // Tom Clancy's Rainbow Six Siege
//                252490, // Rust
//                620,    // Portal 2
//                4000,   // Garry's Mod
//                236390, // War Thunder
//                105600, // Terraria
//                220,    // Half-Life 2
//                304930, // Unturned
//                8930,   // Sid Meier's Civilization V
//                72850,  // The Elder Scrolls V: Skyrim
//                377160, // Fallout 4
//                582010, // Monster Hunter: World
//                271590, // Grand Theft Auto V
//                292030, // The Witcher 3: Wild Hunt
//                374320, // DARK SOULS III
//                413150 // Stardew Valley
//        );

//        for(Integer gameId : gameIds) {
//            database.addGame(gameId);
//        }

        int dbcount = database.getAllGameIDs().size();
        System.out.println(dbcount + "   db!");
        System.out.println(Arrays.toString(database.getAllGameIDs().toArray()));

        // Example cards
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Half-Life", new String[]{"FPS"}, "12345", "A revolutionary shooter", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYd_woEfxldwowEBzCTIlVV5h-HJsR13iHFQ&s"));
        cards.add(new Card("Portal", new String[]{"Puzzle"}, "67890", "A mind-bending game", "https://upload.wikimedia.org/wikipedia/en/thumb/9/9f/Portal_standalonebox.jpg/220px-Portal_standalonebox.jpg"));

        // samples
        cards.add(new Card("The Witcher 3: Wild Hunt", new String[]{"RPG"}, "11111", "An epic open-world RPG", "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Witcher_3_cover_art.jpg/220px-Witcher_3_cover_art.jpg"));
        cards.add(new Card("Minecraft", new String[]{"Sandbox"}, "22222", "A creative sandbox game", "https://static.wikia.nocookie.net/minecraft_gamepedia/images/8/8e/Minecraft_Deluxe_Collection_vertical_key_art.jpg/revision/latest/scale-to-width-down/250?cb=20221208095803"));

        cards.add(new Card("The Legend of Zelda: Breath of the Wild", new String[]{"Action-Adventure"}, "33333", "A groundbreaking open-world adventure", "https://upload.wikimedia.org/wikipedia/en/c/c6/The_Legend_of_Zelda_Breath_of_the_Wild.jpg"));
        cards.add(new Card("Red Dead Redemption 2", new String[]{"Action-Adventure"}, "44444", "A stunning Wild West epic", "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg"));
        cards.add(new Card("Dark Souls III", new String[]{"Action RPG"}, "55555", "A challenging and atmospheric RPG", "https://upload.wikimedia.org/wikipedia/en/b/bb/Dark_souls_3_cover_art.jpg"));
        cards.add(new Card("Overwatch", new String[]{"FPS"}, "66666", "A team-based multiplayer shooter", "https://upload.wikimedia.org/wikipedia/en/5/51/Overwatch_cover_art.jpg"));
        cards.add(new Card("Fortnite", new String[]{"Battle Royale"}, "77777", "A popular battle royale game", "https://i.ebayimg.com/images/g/e4MAAOSw~mpfjvfI/s-l1200.png"));

        cards.add(new Card("Cyberpunk 2077", new String[]{"RPG"}, "88888", "A futuristic open-world RPG", "https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg"));
        cards.add(new Card("Among Us", new String[]{"Party"}, "99999", "A social deduction game", "https://upload.wikimedia.org/wikipedia/en/9/9a/Among_Us_cover_art.jpg"));
        cards.add(new Card("Elden Ring", new String[]{"Action RPG"}, "00000", "A vast and challenging open-world RPG", "https://upload.wikimedia.org/wikipedia/en/b/b9/Elden_Ring_Box_art.jpg"));


        // had to change it so passes the `cards` list directly into the `catalogUI` constructor
        SwingUtilities.invokeLater(() -> new catalogUI(cards, database));



    }
}