package backend;

import java.util.List;
import java.util.stream.Collectors;

public class SearchGames {
    //Search for a game by name (case-insensitive)
    public static List<Card> searchByName(String query, List<Card> games) {
        if (query == null || query.trim().isEmpty()) {
            return games; // If search is empty, return full list
        }

        String lowerCaseQuery = query.toLowerCase();
        return games.stream()
                .filter(game -> game.getName().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}