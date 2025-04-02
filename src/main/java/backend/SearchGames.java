package backend;

import java.util.ArrayList;
import java.util.List;

public class SearchGames {
    public static List<Card> searchByName(String query, List<Card> games) {
        List<Card> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Card card : games) {
            if (card.getName().toLowerCase().startsWith(lowerQuery)) {
                results.add(card);
            }
        }
        return results;
    }
}
